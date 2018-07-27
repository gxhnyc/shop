package shop.entity;

/**
 * 订单项类（对应购物项）
 * 当提交订单后，购物车的内容转移至订单项，并清空购物车
 * @author Administrator
 *
 */
public class OrderItem {
	private Long o_id;
	private Cellphone cellphone;
	private Integer amount;
	
	public Cellphone getCellphone() {
		return cellphone;
	}
	public void setCellphone(Cellphone cellphone) {
		this.cellphone = cellphone;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Long getO_id() {
		return o_id;
	}
	public void setO_id(Long o_id) {
		this.o_id = o_id;
	}
}
