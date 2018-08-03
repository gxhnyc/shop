package shop.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shop.entity.model.OrderState;
/**
 * 订单关系类（全）
 * @author Administrator
 *
 */
public class Order {
	private Long o_id;
	private Long c_id;
	private List<OrderItem> orderItems=new ArrayList<OrderItem>();
	private ShippingAddress shippingAddress;
	private Date createTime;
	private OrderState orderState;
	
	private Integer totalAmount;
	
	
	public Integer totalResult() {
		int result=0;
		for(OrderItem item:orderItems) {
			result+=item.getAmount()*item.getCellphone().getCp_price();
		}
		
		return result;
		
	}
	public Long getO_id() {
		return o_id;
	}
	public void setO_id(Long o_id) {
		this.o_id = o_id;
	}
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public OrderState getOrderState() {
		return orderState;
	}
	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}
	
	
	public String stateText() {
        switch (orderState) {
        case Created:
            return "待支付";
            
        case Paid:
            return "待发货";
            
        case Shipped:
            return "已发货";
            
        case Delivered:
            return "已送达";
            
        case Commented:
            return "已评论";
            
        case Canceled:
            return "已取消";

        default:
            return "?-" + orderState;
        }
    }
	public Integer getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
