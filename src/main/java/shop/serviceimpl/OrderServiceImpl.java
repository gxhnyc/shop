package shop.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.entity.Cart;
import shop.entity.CartItem;

import shop.entity.Order;

import shop.entity.OrderItem;
import shop.entity.ShippingAddress;
import shop.entity.model.OrderState;
import shop.mapper.CartMapper;
import shop.mapper.OrderMapper;
import shop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private OrderMapper orderMapper;
	private CartMapper cartMapper;
	private AlipayClient alipayClient;

	private String alipayReturnUrl;
	private String alipayNotifyUrl;

	private ObjectMapper objectMapper;

	@Autowired
	public OrderServiceImpl(OrderMapper orderMapper, CartMapper cartMapper, AlipayClient alipayClient, Environment env,
			ObjectMapper objectMapper) {
		super();
		this.orderMapper = orderMapper;
		this.cartMapper = cartMapper;
		this.alipayClient = alipayClient;

		this.alipayReturnUrl = env.getProperty("alipay.returnUrl");
		this.alipayNotifyUrl = env.getProperty("alipay.notifyUrl");

		this.objectMapper = objectMapper;
	}

	@Override
	public Order createOrder(Long c_id, Long ship_id) {
		/**
		 * 当点击“提交订单”按钮后，先将购物车的内容转移至订单， 即 购物项数据存放至订单项，并将购物项表清空
		 */

		// 1.创建订单表（关联用户id,商品id,地址id,创建时间）
		Order order = new Order();

		order.setC_id(c_id);
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setShip_id(ship_id);
		order.setShippingAddress(shippingAddress);
		order.setCreateTime(new Date());

		order.setOrderState(OrderState.Created);

		orderMapper.createOrder(order);
		System.out.println(order.getC_id() + "--------" + order.getCreateTime() + "----" + order.getO_id());
		System.out.println("创建订单后，订单的id为：" + order.getO_id());
		// 2.将购物项转移至订单项
		Cart cart = new Cart(cartMapper.findAllItems(c_id));
		for (CartItem citem : cart.getCartitems()) {
			OrderItem oitem = new OrderItem();
			oitem.setO_id(order.getO_id());//
			oitem.setCellphone(citem.getCellphone());
			oitem.setAmount(citem.getAmount());
			orderMapper.createOrderItem(oitem);
		}

		// 3.清空购物项
		cartMapper.cleanCartItems(c_id);

		return order;

	}

	@Override
	public void cleanOrders(Long c_id) {
		orderMapper.cleanOrders(c_id);

	}

	@Override
	public void cleanCartItems(Long c_id) {
		orderMapper.cleanCartItmes(c_id);

	}

	@Override
	public Order findAllOrderItems(Long o_id) {

		return orderMapper.findAllOrderItems(o_id);
	}

	@Override
	public List<Order> findAllOrders(Long c_id) {

		return orderMapper.findAllOrders(c_id);
	}

	@Override // 取消订单
	public void cancelOrder(Long o_id, Long c_id) {
		// 1.将订单项的内容转移至购物车

		Order odetails = orderMapper.findAllOrderItems(o_id);

		for (OrderItem oitem : odetails.getOrderItems()) {
			CartItem citem = new CartItem();
			citem.setCellphone(oitem.getCellphone());
			citem.setAmount(oitem.getAmount());
			// 根据用户id和地址及商品，创建购物项
			cartMapper.addCartItem(citem.getCellphone().getCp_id(), c_id, citem.getAmount());
		}
		// 2.取消订单（根据订单号和用户id）：删除order_item,删除order

		for (OrderItem oitem : odetails.getOrderItems()) {

			// 根据订单id和商品id，删除订单项
			orderMapper.cancelOrderItem(o_id, oitem.getCellphone().getCp_id());
		}
		orderMapper.cancelOrder(o_id, c_id);
	}

	@Override
	public void delOrder(Long o_id, Long c_id) {
		// 1.将订单项的内容直接删除

		Order odetails = orderMapper.findAllOrderItems(o_id);

		// 2.删除订单（根据订单号和用户id）：删除order_item,删除order

		for (OrderItem oitem : odetails.getOrderItems()) {

			// 根据订单id和商品id，删除订单项
			orderMapper.cancelOrderItem(o_id, oitem.getCellphone().getCp_id());
		}
		orderMapper.cancelOrder(o_id, c_id);

	}

	@Override
	public Order findOneById(Long c_id, Long o_id) {

		return orderMapper.findOneById(c_id, o_id);
	}

	@Override
	public String aliPay(Long c_id, Long o_id) {
		// 1.根据订单号找到订单（订单数据）
		Order order = findAllOrderItems(o_id);
		// 2.判断订单状态是否为已创建（下一步是‘已支付’），以确定是否发起支付
		if (order.getOrderState() != OrderState.Created) {
			throw new IllegalStateException("只有Created状态的订单才能发起支付");
		}
		// 支付宝交易采用的是bigDecimal数据类型，单位是元
		BigDecimal totalAmount = BigDecimal.valueOf(order.totalResult()).divide(BigDecimal.valueOf(100)); // 订单总金额（元）
		System.out.println("totalAmount:" + totalAmount);

		// 3.发送给支付宝的请求（用户从网站发起支付请求）
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); // 即将发送给支付宝的请求（电脑网站支付请求）
		// 浏览器端完成支付后跳转回商户的地址（同步通知）
		alipayRequest.setReturnUrl(alipayReturnUrl);
		// 支付宝服务端确认支付成功后通知商户的地址（异步通知）
		alipayRequest.setNotifyUrl(alipayNotifyUrl);

		Map<String, Object> bizContent = new HashMap<>(); // biz - business
		// 填充业务参数
		bizContent.put("out_trade_no", "" + o_id + "-" + new Date().getTime()); // 商户订单号，加时间戳是为了避免测试时订单号重复
		bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 产品码，固定
		bizContent.put("total_amount", totalAmount); // 订单总金额（元）
		bizContent.put("subject", "shop手机商城订单支付"); // 订单标题
		bizContent.put("body", "TODO 显示订单项概要"); // 订单描述

		// 直接将完整的表单html输出到页面
		try {
			String bizContentStr = objectMapper.writeValueAsString(bizContent);
			System.out.println("alipay.bizContentStr: " + bizContentStr);
			alipayRequest.setBizContent(bizContentStr);

			return alipayClient.pageExecute(alipayRequest).getBody();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // 调用SDK生成支付表单

	}

}
