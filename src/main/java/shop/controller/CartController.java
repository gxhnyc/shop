package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import shop.entity.Cellphone;
import shop.service.ShoppingCartService;


/**
 * 购物车控制器
 * @author Administrator
 *
 */
@Controller
public class CartController {
	
	private ShoppingCartService shoppingCartService;
	@Autowired
	public CartController(ShoppingCartService shoppingCartService) {
		super();
		this.shoppingCartService = shoppingCartService;
	}
	//添加至购物车
	@RequestMapping(method=RequestMethod.POST,value="/uc/addcart")
	public String addCart(@ModelAttribute Cellphone cellphone,
								@RequestParam String cp_id,
								@AuthenticationPrincipal(expression="user.id") Long userid)
	{
		shoppingCartService.addCartItem(cp_id,userid,1);
		
		return "cellphone-details";
		
	}
}
