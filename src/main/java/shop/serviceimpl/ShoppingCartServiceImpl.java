package shop.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mapper.ShoppingCartMapper;
import shop.service.ShoppingCartService;
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	private ShoppingCartMapper shoppingCartMapper;
	
	public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper) {
		super();
		this.shoppingCartMapper = shoppingCartMapper;
	}

	@Override
	public void addCartItem(String cp_id, Long userID,Integer amount) {
			
		shoppingCartMapper.addCartItem(cp_id, userID, amount);
	}

}
