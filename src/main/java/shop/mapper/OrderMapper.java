package shop.mapper;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import shop.entity.Order;
import shop.entity.OrderItem;


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
	Order findAllOrderItems(@Param("o_id") Long o_id);
	

	/**
	 * 查询当前用户下的所有订单
	 * @param c_id
	 * @return OrderDetails
	 */
	List<Order> findAllOrders(Long c_id);
	
	/**
	 * 根据订单号及用户Id取消订单
	 * @param o_id
	 * @param c_id
	 */
	void cancelOrder(@Param("o_id") Long o_id, @Param("c_id") Long c_id);
	
	/**
	 * 根据订单id和商品id删除订单项
	 * @param o_id
	 * @param cp_id
	 */
	void cancelOrderItem(@Param("o_id") Long o_id,@Param("cp_id") String cp_id);

	/**
	 * 通过订单号和用户id查找订单
	 * @param userId
	 * @param id
	 * @return
	 */
	Order findOneById(@Param("c_id") Long c_id,@Param("o_id") Long o_id);
	
	/**
	 * 用户支付成功后，商户服务器端将交易价格设置到数据库中
	 * @param o_id
	 * @param totalAmountInFen
	 */
	void setTotalAmount(@Param("o_id") Long o_id,@Param("totalAmount") int totalAmount);
	
	/**
	 * 通过订单id找到已支付的订单
	 * @param o_id
	 * @return
	 */
	Order findOneToPay(Long o_id);
	
	/**
	 * 设置订单状态为”已支付“
	 * @param o_id
	 */	 
	void setStateToPaid(Long o_id);
	
	/**
	 * 删除已取消订单，通过cron调用，返回类型必须为void，参数列表必须为空
	 * @return
	 */
	Integer deleteCanceledOrders();
	
}
