package shop.service;

import java.util.List;
import java.util.Map;

import shop.entity.Order;
import shop.exception.AlipaySignatureException;

public interface OrderService {
	
	
	/**
	 * 生成订单
	 * @param c_id
	 * @param ship_id
	 * @return 
	 */
	Order createOrder(Long c_id,Long ship_id);
	
	/**
	 * 清空订单
	 * @param c_id
	 */	
	void cleanOrders(Long c_id);
	
	/**
	 * 清空购物项
	 * @param c_id
	 */
	void cleanCartItems(Long c_id);
	
/*	*//**
	 * 列出所有订单项
	 * @param c_id
	 * @return
	 *//*
	List<OrderItem> findAllItems(Long c_id);*/
	
	
	/**
	 * 根据创建订单生成的订单号及当前用户id查询订单项
	 * @param o_id
	 * @return
	 */
	Order findAllOrderItems(Long o_id);
	
	/**
	 * 查询当前用户下的所有订单
	 * @param c_id
	 * @return
	 */
	List<Order> findAllOrders(Long c_id);
	/**
	 * 取消订单
	 * @param o_id
	 * @param c_id
	 */
	void cancelOrder(Long o_id, Long c_id);
	/**
	 * 删除订单
	 * @param o_id
	 * @param c_id
	 */
	void delOrder(Long o_id, Long c_id);
	/**
	 * 通过订单号和用户id查找订单
	 * @param userId
	 * @param id
	 * @return
	 */
	Order findOneById(Long userId, Long o_id);
	/**
	 * 点击支付宝支付
	 * @param c_id
	 * @param o_id
	 * @return payForm表单
	 */
	String aliPay(Long c_id, Long o_id);
	/**
	 * 支付宝验签
	 * @param paramMap
	 * @throws AlipaySignatureException
	 */
	void verifySignature(Map<String, String> paramMap) throws AlipaySignatureException;
	/**
	 * 处理支付宝服务器返回的结果（将商家端订单阙状态修改为Paid + 异步验签）
	 * @param paramMap
	 */
	void handlePayResult(Map<String, String> paramMap);
	
	 /**
	 * 删除已取消订单，通过cron调用，返回类型必须为void，参数列表必须为空
	 */
	void deleteCanceledOrders();
	
}
