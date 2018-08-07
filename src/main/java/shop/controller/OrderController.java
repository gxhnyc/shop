package shop.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class OrderController extends BaseController{
	private OrderService orderService;
	private AddressService addressService;
	private CartService cartService;



	@Autowired
	public OrderController(OrderService orderService, AddressService addressService, CartService cartService) {
		super();
		this.orderService = orderService;
		this.addressService = addressService;
		this.cartService = cartService;
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

		/*System.out.println("OrderController.getOrderState():" + order.getOrderState());*/
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

		try {
			List<Order> orders = orderService.findAllOrders(c_id);
			model.addAttribute("orders", orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		/*System.out.println("OrderController.DelOrder--o_id:" + o_id);*/

		// 删除订单
		orderService.delOrder(o_id, c_id);

		return "redirect:/uc/showorders";
	}

	// 取消订单
	@RequestMapping(method = RequestMethod.POST, value = "/uc/cancel-order")
	public String cancelOrder(@ModelAttribute OrderForm orderForm, Model model,
			@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, @RequestParam Long o_id) {
		/*System.out.println("OrderController.cancelOrder--o_id:" + o_id);*/

		// 取消订单
		orderService.cancelOrder(o_id, c_id);
		return "redirect:/";
	}

	// 点击支付宝支付
	@RequestMapping(method = RequestMethod.POST, value = "/uc/orders/{o_id}/pay", 
			produces = "text/html;charset=UTF-8"	) // 非常重要，支付宝api响应是html片段（不含编码），必须显式指定
	@ResponseBody
	public String pay(@AuthenticationPrincipal(expression = "customer.c_id") Long c_id, 
			@PathVariable Long o_id)
			throws AlipayApiException {
		
		return orderService.aliPay(c_id,o_id);
	}
	
	//支付宝支付成功后跳转页面
	@RequestMapping(method = RequestMethod.GET, value = "/uc/orders/sync-pay-cb")
	public String payOk(@RequestParam("out_trade_no") String orderNumber,
			 @RequestParam Map<String, String> paramMap, // 将所有请求参数封装到map中
			Model model) {
		/*1.同步验签：支付宝服务器在买家成功支付后，提交get请求至商家服务器，
		 *	商家服务器对支付宝发来的请求进行验签
		 */
		//orderService.verifySignature(paramMap);
		
		/*
		 * 2.同步验签完成后，商家服务器将支付成功页面返回给买家
		 */
		Long orderId = Long.valueOf(orderNumber.split("-")[0]); // 如 3-1533093080374
		model.addAttribute("orderId", orderId);
		return "order-pay-ok";
	}
	//异步验签
	@RequestMapping(method = RequestMethod.POST, value = "/async-pay-cb")
    @ResponseBody // 响应内容是text/plain
    public String onPayResult(@RequestParam Map<String, String> paramMap) {
      
		orderService.handlePayResult(paramMap);
		 logger.debug("handlePayResult,异步处理----");
        return "success";
    }

}
