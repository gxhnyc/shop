package shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import shop.entity.CartItem;

/**
 * 购物车mapper
 * @author Administrator
 *
 */
public interface CartMapper {
	
	/**
	 * 1.添加购物项(新建)
	 * @param cp_id
	 * @param c_id
	 * @param amount
	 */
	void addCartItem(	@Param("cp_id") String cp_id, 
						@Param("c_id") Long c_id,
						@Param("amount") Integer amount
						);
	
	
	/**
	 * 2.判断该购物项商品数量，若不存在，则返回Null
	 * @param cp_id
	 * @param c_id
	 * @return
	 */
	Integer findAmount(	@Param("cp_id") String cp_id,
								@Param("c_id") Long c_id
								);
	
	
	/**
	 * 3.更改购物项的数量
	 * @param cp_id
	 * @param c_id
	 * @param amount
	 */
	void addAmountToCartItem(	@Param("cp_id") String cp_id, 
								@Param("c_id") Long c_id,
								@Param("amount") Integer amount
								);
	
	/**
	 * 4.查询当前用户下的所有购物项
	 * @param c_id
	 * @return
	 */
	List<CartItem> findAllItems(Long c_id);
	
	
	/**
	 * 5.删除购物项
	 * @param c_id
	 * @param cp_id
	 */
	void delCartItem(@Param("c_id") Long c_id, 
						@Param("cp_id") String cp_id);

	/**
	 * 清空当前用户的购物项
	 * @param c_id
	 */
	void cleanCartItems(Long c_id);
	
	
}
