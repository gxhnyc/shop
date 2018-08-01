package shop.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import shop.entity.Cart;
import shop.entity.CartItem;

import shop.entity.Order;
import shop.entity.OrderForm;

import shop.entity.ShippingAddress;
import shop.entity.model.OrderState;
import shop.service.AddressService;
import shop.service.CartService;
import shop.service.OrderService;

/**
 * 订单控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController {
	private OrderService orderService;
	private AddressService addressService;
	private CartService cartService;

	private AlipayClient alipayClient;

	@Autowired
	public OrderController(OrderService orderService, AddressService addressService, CartService cartService,
			AlipayClient alipayClient) {
		super();
		this.orderService = orderService;
		this.addressService = addressService;
		this.cartService = cartService;
		this.alipayClient = alipayClient;
	}

	// ---创建订单-----------
	@RequestMapping(method = RequestMethod.GET, value = "/uc/create-order")
	public String createOrder(@ModelAttribute OrderForm orderForm, Model model,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id) {
		prepareInCreateOrder(model, c_id);

		return "create-order";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uc/create-order")
	public String createOrder(@ModelAttribute @Valid OrderForm orderForm, BindingResult bindingResult,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, Model model) {
		if (bindingResult.hasErrors()) {
			prepareInCreateOrder(model, c_id);
			return "create-order";
		}
		Order order = orderService.createOrder(c_id, orderForm.getShippingAddressID());

		System.out.println("OrderController.getOrderState():" + order.getOrderState());
		return "redirect:/uc/showorders/" + order.getO_id();
	}

	/**
	 * 创建订单时的准备工作
	 * 
	 * @param model
	 * @param c_id
	 */
	public void prepareInCreateOrder(Model model, Long c_id) {
		// 得到当前用户下的购物项
		List<CartItem> cartitems = cartService.findAllItems(c_id);
		// 得到当前用户的所有地址
		List<ShippingAddress> addresses = addressService.findAllAddress(c_id);

		model.addAttribute("shoppingCart", new Cart(cartitems));
		model.addAttribute("addresses", addresses);
	}

	// ----我的当前订单-----------------/uc/showorders
	@RequestMapping(method = RequestMethod.GET, value = "/uc/showorders/{o_id}")
	public String showOrderbyId(@PathVariable Long o_id, Model model,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id) {
		Order orderDetails = orderService.findAllOrderItems(o_id);
		model.addAttribute("orderDetails", orderDetails);

		return "show-order-details";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/uc/showorders/{o_id}")
	public String showOrderbyId(@AuthenticationPrincipal(expression = "customer.c_id") Long c_id) {

		orderService.cleanCartItems(c_id);

		return "redirect:/";

	}

	// 我的所有订单
	@RequestMapping(method = RequestMethod.GET, value = "/uc/showorders")
	public String showOrders(Model model, @AuthenticationPrincipal(expression = "customer.c_id") Long c_id) {

		List<Order> orders = orderService.findAllOrders(c_id);
		model.addAttribute("orders", orders);

		return "show-orders";

	}

	// ----订单详情-----------------
	@RequestMapping(method = RequestMethod.GET, value = "/uc/order-details/{o_id}")
	public String OrderDetails(@PathVariable Long o_id, Model model,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id) {
		Order orderDetails = orderService.findAllOrderItems(o_id);
		model.addAttribute("orderDetails", orderDetails);

		return "show-order-details";

	}

	// 删除订单
	@RequestMapping(method = RequestMethod.GET, value = "/uc/del-order/{o_id}")
	public String delOrder(@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, @PathVariable Long o_id) {
		System.out.println("OrderController.DelOrder--o_id:" + o_id);

		// 删除订单
		orderService.delOrder(o_id, c_id);

		return "redirect:/uc/showorders";
	}

	// 取消订单
	@RequestMapping(method = RequestMethod.POST, value = "/uc/cancel-order")
	public String cancelOrder(@ModelAttribute OrderForm orderForm, Model model,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, @RequestParam Long o_id) {
		System.out.println("OrderController.cancelOrder--o_id:" + o_id);

		// 取消订单
		orderService.cancelOrder(o_id, c_id);
		return "redirect:/";
	}

	// 支付宝支付
	@RequestMapping(method = RequestMethod.POST, value = "/uc/orders/{o_id}/pay", 
			produces = "text/html;charset=UTF-8"	) // 非常重要，支付宝api响应是html片段（不含编码），必须显式指定
	@ResponseBody
	public String pay(@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, 
			@PathVariable Long o_id)
			throws AlipayApiException {
		Order order = orderService.findAllOrderItems(o_id);

		if (order.getOrderState() != OrderState.Created) {
			throw new IllegalStateException("只有Created状态的订单才能发起支付");
		}

		BigDecimal totalAmount = BigDecimal.valueOf(order.totalResult()).divide(BigDecimal.valueOf(100)); // 订单总金额（元）
		System.out.println("totalAmount:" + totalAmount);

		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); // 即将发送给支付宝的请求（电脑网站支付请求）
		alipayRequest.setReturnUrl("http://shop.me/shop/uc/orders/sync-pay-cb"); // 浏览器端完成支付后跳转回商户的地址（同步通知）
		alipayRequest.setNotifyUrl("http://shop.me/shop/async-pay-cb"); // 支付宝服务端确认支付成功后通知商户的地址（异步通知）
		alipayRequest
				.setBizContent("{" + "    \"out_trade_no\":\"" + o_id.toString() + "-" + new Date().getTime() + "\"," + // 商户订单号，加时间戳是为了避免测试时订单号重复
						"    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," + // 产品码，固定
						"    \"total_amount\":" + totalAmount.toString() + "," + // 订单总金额（元）
						"    \"subject\":\"shop手机商城订单支付\"," + // 订单标题
						"    \"body\":\"TODO 显示订单项概要\"" + // 订单描述
						"  }"); // 填充业务参数

		// 直接将完整的表单html输出到页面
		return alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成支付表单
	}
	//支付宝支付成功后跳转页面
	@RequestMapping(method = RequestMethod.GET, value = "/uc/orders/sync-pay-cb")
	public String payOk(@RequestParam("out_trade_no") String orderNumber, Model model) {
		Long orderId = Long.valueOf(orderNumber.split("-")[0]); // 如 3-1533093080374
		model.addAttribute("orderId", orderId);
		return "order-pay-ok";
	}

}
