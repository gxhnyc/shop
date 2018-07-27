package shop.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.entity.Cart;
import shop.entity.CartItem;
import shop.entity.Customer;
import shop.entity.Order;
import shop.entity.OrderDetails;
import shop.entity.Order;
import shop.entity.OrderItem;
import shop.entity.Orders;
import shop.entity.ShippingAddress;
import shop.mapper.CartMapper;
import shop.mapper.OrderMapper;
import shop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private OrderMapper orderMapper;
	private CartMapper cartMapper;
	@Autowired	
	public OrderServiceImpl(OrderMapper orderMapper,CartMapper cartMapper) {
		super();
		this.orderMapper = orderMapper;
		this.cartMapper=cartMapper;
	}

	@Override
	public Order createOrder(Long c_id, Long ship_id) {
		/**
		 * 当点击“提交订单”按钮后，先将购物车的内容转移至订单，
		 * 即 购物项数据存放至订单项，并将购物项表清空
		 */
		
		//1.创建订单表（关联用户id,商品id,地址id,创建时间）
		Order order=new Order();
		
		
		order.setC_id(c_id);		
		ShippingAddress shippingAddress=new ShippingAddress();
		shippingAddress.setShip_id(ship_id);
		order.setShippingAddress(shippingAddress);		
		order.setCreateTime(new Date());
		
		orderMapper.createOrder(order);
		System.out.println(order.getC_id()+"--------"+order.getCreateTime()+"----"+order.getO_id());
		System.out.println("创建订单后，订单的id为："+order.getO_id());
		//2.将购物项转移至订单项
		Cart cart=new Cart(cartMapper.findAllItems(c_id));		
		for(CartItem citem:cart.getCartitems()) {
			OrderItem oitem=new OrderItem();
			oitem.setO_id(order.getO_id());//-----获取不到 订单id
			oitem.setCellphone(citem.getCellphone());
			oitem.setAmount(citem.getAmount());
			orderMapper.createOrderItem(oitem);		
		}
		
		//3.清空购物项
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
	public OrderDetails findAllOrderItems(Long o_id) {
		
		return orderMapper.findAllOrderItems(o_id);
	}

	@Override
	public List<Order> findAllOrders(Long c_id) {
		
		return orderMapper.findAllOrders(c_id);
	}

}
