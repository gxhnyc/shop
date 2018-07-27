package shop.service;

import java.util.List;

import shop.entity.CartItem;


public interface CartService {
	
	/**
	 * 添加购物项
	 * @param cp_id
	 * @param userID
	 */
	void addCartItem(String cp_id, Long c_id,Integer amount);
	/**
	 * 查询当前用户下的所有购物项
	 * @param c_id
	 * @return
	 */
	List<CartItem> findAllItems(Long c_id);
	/**
	 * 删除购物项
	 * @param c_id
	 * @param cp_id
	 */
	void delCartItem(Long c_id, String cp_id);
	/**
	 * 购物项-1
	 * @param c_id
	 * @param cp_id
	 */
	void decItem(Long c_id, String cp_id);
	/**
	 * 购物项+1
	 * @param c_id
	 * @param cp_id
	 */
	void incItem(Long c_id, String cp_id);
	
	
}
