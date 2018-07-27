package shop.entity;

import java.util.List;

public class Orders {
	
	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Orders(List<Order> orders) {
		super();
		this.orders = orders;
	}
	
	
	
}
