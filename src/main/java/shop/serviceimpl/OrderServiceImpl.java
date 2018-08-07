package shop.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.entity.Cart;
import shop.entity.CartItem;

import shop.entity.Order;

import shop.entity.OrderItem;
import shop.entity.ShippingAddress;
import shop.entity.model.OrderState;
import shop.exception.AlipayResultException;
import shop.exception.AlipaySignatureException;
import shop.mapper.CartMapper;
import shop.mapper.OrderMapper;
import shop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	//调用日记处理的固定格式：
	 private Logger logger = LoggerFactory.getLogger(this.getClass());

	private OrderMapper orderMapper;
	private CartMapper cartMapper;
	private AlipayClient alipayClient;

	private String alipayReturnUrl;
	private String alipayNotifyUrl;

	private ObjectMapper objectMapper;
	private String alipayPublicKey;
	private String alipaySignType;
	private String appId;

	@Autowired
	public OrderServiceImpl(OrderMapper orderMapper, CartMapper cartMapper, 
			AlipayClient alipayClient, Environment env,
			ObjectMapper objectMapper) throws IOException {
		super();
		this.orderMapper = orderMapper;
		this.cartMapper = cartMapper;
		this.alipayClient = alipayClient;

		this.alipayReturnUrl = env.getProperty("alipay.returnUrl");
		this.alipayNotifyUrl = env.getProperty("alipay.notifyUrl");
		this.alipayPublicKey = FileUtils.readFileToString(
                new File(env.getProperty("alipay.alipayPublicKeyFile")), 
                "UTF-8");
		this.alipaySignType  = env.getProperty("alipay.signType");
		this.appId = env.getProperty("alipay.appId");
		
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
		
		logger.debug("插入订单！");
		orderMapper.createOrder(order);
		
		/*System.out.println(order.getC_id() + "--------" + order.getCreateTime() + "----" + order.getO_id());*/
		/*System.out.println("创建订单后，订单的id为：" + order.getO_id());*/
		
		// 2.将购物项转移至订单项
		Cart cart = new Cart(cartMapper.findAllItems(c_id));
		for (CartItem citem : cart.getCartitems()) {
			OrderItem oitem = new OrderItem();
			oitem.setO_id(order.getO_id());//
			oitem.setCellphone(citem.getCellphone());
			oitem.setAmount(citem.getAmount());
			
			logger.debug("插入订单项！");
			orderMapper.createOrderItem(oitem);
		}

		// 3.清空购物项
		logger.debug("清空购物车！");
		cartMapper.cleanCartItems(c_id);
		
		 logger.info("订单已创建: #" + order.getO_id());
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
	
	/*
	 * 支付宝支付功能实现
	 * (non-Javadoc)
	 * @see shop.service.OrderService#aliPay(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String aliPay(Long c_id, Long o_id) {
		// 1.根据订单号找到订单（订单数据）
		Order order = findAllOrderItems(o_id);
		// 2.判断订单状态是否为已创建（下一步是‘已支付’），以确定是否发起支付
		if (order.getOrderState() != OrderState.Created) {
			throw new IllegalStateException("只有Created状态的订单才能发起支付");
		}
		// 支付宝交易采用的是bigDecimal数据类型，单位是元
		int totalAmountInFen = order.totalResult();
		BigDecimal totalAmount = BigDecimal.valueOf(totalAmountInFen).divide(BigDecimal.valueOf(100)); // 订单总金额（元）
		/*System.out.println("totalAmount:" + totalAmount);*/

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
			
			 logger.debug("alipay.bizContentStr: " + bizContentStr);
			 
			alipayRequest.setBizContent(bizContentStr);
			
			 String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成支付表单
	        //数据库表中设置订单金额数据：
			 orderMapper.setTotalAmount(o_id, totalAmountInFen);
			
			return form;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // 调用SDK生成支付表单

	}
	/**
	 * 支付宝验签
	 */
	@Override
	public void verifySignature(Map<String, String> paramMap) 
			throws AlipaySignatureException{
		 try {
	            if (!AlipaySignature.rsaCheckV1(paramMap, alipayPublicKey, "UTF-8", alipaySignType)) {
	            	 logger.warn("支付宝签名错误！");
	            	throw new AlipaySignatureException();
	            }
	        } catch (AlipayApiException e) {
	            throw new AlipaySignatureException(e);
	        }
		
	}

	@Override
	public void handlePayResult(Map<String, String> paramMap) {
		 // 验签
       // verifySignature(paramMap);
        
        // 获取必要的请求参数
        String orderNumber = paramMap.get("out_trade_no");
        Long orderId = Long.valueOf(orderNumber.split("-")[0]);
        Integer totalAmount = new BigDecimal(paramMap.get("total_amount")).multiply(BigDecimal.valueOf(100)).intValue();
        String appId = paramMap.get("app_id");
        String tradeStatus = paramMap.get("trade_status");
        
        // 各种核对:找到已支付的订单，订单状态修改、核对金额、核对alipay 的 appId...
        Order order = orderMapper.findOneToPay(orderId);
        if (order.getOrderState() != OrderState.Created) {
            throw new AlipayResultException("订单状态非Created");
        }
        if (!order.getTotalAmount().equals(totalAmount)) {
            throw new AlipayResultException("订单总金额不一致");
        }
        if (!appId.equals(this.appId)) {
            throw new AlipayResultException("appId不一致");
        }
        
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            // 设置订单状态为已支付
        	logger.debug("设置订单状态为Paid");
            orderMapper.setStateToPaid(orderId);
        }
    
		
	}

	@Scheduled(cron = "0 0 3 * * *") // 每天凌晨3点执行该方法
	/*@Scheduled(cron = "* * * * * *") // 每秒钟删除...*/ 
   @Override
    public void deleteCanceledOrders() {
        logger.info("删除过期已取消订单");
        Integer count = orderMapper.deleteCanceledOrders();
        logger.info("已删除" + count + "个订单");
    }


}
