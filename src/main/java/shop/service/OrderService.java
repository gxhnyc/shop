package shop.service;

import java.util.List;


import shop.entity.Order;
import shop.entity.OrderDetails;

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
	OrderDetails findAllOrderItems(Long o_id);
	
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
	
	
}
