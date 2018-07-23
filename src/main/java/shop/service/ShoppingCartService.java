package shop.service;

public interface ShoppingCartService {
	
	/**
	 * 添加购物项
	 * @param cp_id
	 * @param userID
	 */
	void addCartItem(String cp_id, Long userID,Integer amount);
	
	
}
