package shop.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 收件地址类
 * @author Administrator
 *
 */
public class ShippingAddress {
	private Long ship_id;
	private Long c_id;
	
	@NotEmpty
	private String consignee;//收件人
	
	@Pattern(regexp="^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",
			message="请输入正确的11位手机号码")
	private String tel;
	
	@Size(min=1,max=1024,message="必填")
	private String address;
	
	public Long getShip_id() {
		return ship_id;
	}
	public void setShip_id(Long ship_id) {
		this.ship_id = ship_id;
	}
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	
	
	
}
