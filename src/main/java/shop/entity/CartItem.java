package shop.entity;

/**
 * 购物项类
 * @author Administrator
 *
 */
public class CartItem {
	
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
	
	
}
