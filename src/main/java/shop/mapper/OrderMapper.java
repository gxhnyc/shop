package shop.mapper;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import shop.entity.Order;
import shop.entity.OrderDetails;
import shop.entity.OrderItem;
import shop.entity.Orders;

public interface OrderMapper {
	
	/**
	 * 生成订单
	 * @param c_id
	 * @param ship_id
	 */
	void createOrder(Order order);
	
	/**
	 * 创建订单项表
	 * @param oitem
	 */
	void createOrderItem(OrderItem oitem);
	
	/**
	 * 清空购物车
	 * @param c_id
	 */
	void cleanCartItmes(Long c_id);
	
	/**
	 * 清空订单
	 * @param c_id
	 */	
	void cleanOrders(Long c_id);
	
	/**
	 * 根据用户id和订单创建时间来查找订单id
	 * @param c_id
	 * @param createTime
	 * @return
	 */
	Long findOrderId(@Param("c_id") Long c_id, @Param("createTime") Date createTime);
	
	/**
	 * 根据创建订单生成的订单号及当前用户id查询订单项
	 * @param o_id
	 * @return
	 */
	OrderDetails findAllOrderItems(@Param("o_id") Long o_id);
	

	/**
	 * 查询当前用户下的所有订单
	 * @param c_id
	 * @return
	 */
	List<Order> findAllOrders(Long c_id);
	
}
