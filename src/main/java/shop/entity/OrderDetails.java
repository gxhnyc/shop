package shop.entity;

import java.util.Date;
import java.util.List;

public class OrderDetails {
	private Long o_id;
	private Long c_id;
	private List<OrderItem> orderItems;
	private ShippingAddress shippingAddress;
	private Date createTime;
	
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
}
