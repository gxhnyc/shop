package shop.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 购物车类
 * @author Administrator
 *
 */

public class Cart {
	
	private List<CartItem> cartitems;//购物项List
	
	public Cart(List<CartItem> cartitems) {
		this.cartitems=cartitems;
	}
	public Cart() {}

	public List<CartItem> getCartitems() {
		return cartitems;
	}
	/**
	 * 总计
	 * @return
	 */
	 @JsonProperty("totalResult") // 调用该方法将返回值以totalCost为名写入json
	public Integer totalResult() {
		int result=0;
		for(CartItem cartitem:cartitems) {
			result+=cartitem.getAmount()*cartitem.getCellphone().getCp_price();
		}		
		return result;		
	}
}
