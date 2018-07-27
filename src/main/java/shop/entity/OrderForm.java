package shop.entity;

import javax.validation.constraints.NotNull;

public class OrderForm {
	@NotNull(message="必填")
	private Long shippingAddressID;

	public Long getShippingAddressID() {
		return shippingAddressID;
	}

	public void setShippingAddressID(Long shippingAddressID) {
		this.shippingAddressID = shippingAddressID;
	}
	
}
