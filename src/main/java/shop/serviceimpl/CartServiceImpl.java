package shop.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.entity.Cart;
import shop.entity.CartItem;

import shop.mapper.CartMapper;
import shop.service.CartService;
@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	private CartMapper cartMapper;
	
	public CartServiceImpl(CartMapper shoppingCartMapper) {
		super();
		this.cartMapper = shoppingCartMapper;
	}

	@Override
	public void addCartItem(String cp_id, Long c_id,Integer amount) {
		//若对应购物项不存在，则直接添加，否则更新购物项
		if(cartMapper.findAmount(cp_id,c_id)==null) {			
			cartMapper.addCartItem(cp_id, c_id, amount);
		}
		else cartMapper.addAmountToCartItem(cp_id, c_id, amount);
		
	}

	@Override
	public List<CartItem> findAllItems(Long c_id) {
		
		return cartMapper.findAllItems(c_id);
	}

	@Override
	public void delCartItem(Long c_id, String cp_id) {
		cartMapper.delCartItem(c_id,cp_id);
		
	}

	@Override
	public void decItem(Long c_id, String cp_id) {
		addToCart(cp_id, c_id, -1);
	}	

	@Override
	public void incItem(Long c_id, String cp_id) {
		addToCart(cp_id, c_id, +1);
	}
	private void addToCart(String cp_id, Long c_id, int amount) {
		//根据用户id和手机id查询是否存在购物项
		Integer num=cartMapper.findAmount(cp_id, c_id);
		//若存在 
		if(num!=null) {
			if(num+amount==0) {//应用差量若为0,则删除该购物项
				cartMapper.delCartItem(c_id, cp_id);
			}else {
				cartMapper.addAmountToCartItem(cp_id, c_id, amount);
			}
		}else {
			cartMapper.addCartItem(cp_id, c_id, amount);
		}	
	}

	@Override
	public void updateItemAmount(Long c_id, String cp_id, Integer amount) {
		 if (amount <= 0) {
	            throw new IllegalArgumentException("购物车项的数量必须大于0");
	        }
	        cartMapper.updateItemAmount(c_id, cp_id, amount);
	    }
		

	@Override
	public Cart findOneByUserId(Long c_id) {
		
		 return new Cart(findAllItems(c_id));
	}
}
