package shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.entity.Cart;
import shop.entity.CartItem;
import shop.entity.ShippingAddress;
import shop.service.AddressService;
import shop.service.CartService;


/**
 * 购物车控制器
 * @author Administrator
 * 
 */
@Controller
public class CartController {
	
	private CartService cartService;
	private AddressService addressService;
	@Autowired
	public CartController(CartService cartService,AddressService addressService) {
		super();
		this.cartService = cartService;
		this.addressService=addressService;
	}
	
	
	//查看购物车
		@RequestMapping(method=RequestMethod.GET,value="/uc/showcart")
		public String showCart(	@ModelAttribute CartItem cartItem,
									Model model,
									@AuthenticationPrincipal(expression="customer.c_id") Long c_id)
		{
			List<CartItem> cartItems=cartService.findAllItems(c_id);
			System.out.println("cartItems.size:"+cartItems.size());
			List<ShippingAddress> addresses=addressService.findAllAddress(c_id);
			model.addAttribute("addresses", addresses);
			model.addAttribute("shoppingCart", new Cart(cartItems));
			return "show-cart";
			
		}
	//添加至购物车
	@RequestMapping(method=RequestMethod.POST,value="/uc/addcart")
	public String addCart(		@RequestParam String cp_id,
								@AuthenticationPrincipal(expression="customer.c_id") Long c_id)
	{
		cartService.addCartItem(cp_id,c_id,1);
		
		return "redirect:/details/"+cp_id;
		
	}
	
	//删除购物车
	
	@RequestMapping(method=RequestMethod.POST,value="/uc/remove-item")
	public String delCartItem(@RequestParam String cp_id,
								@AuthenticationPrincipal(expression="customer.c_id") Long c_id,
								Model model
	){
		cartService.delCartItem(c_id,cp_id);
		
		List<CartItem> cartItems=cartService.findAllItems(c_id);
		model.addAttribute("shoppingCart", new Cart(cartItems));
		return "shopping-cart";
		
	}
	/*//购物项+1：/uc/shopping-cart/item-inc
	@RequestMapping(method=RequestMethod.POST,value="/uc/shopping-cart/item-inc")
	public String itemDec(@RequestParam String cp_id,
							@AuthenticationPrincipal(expression="customer.c_id") Long c_id) 
	{
		cartService.incItem(c_id,cp_id);
		
		return "redirect:/uc/showcart";
		
	}
	//购物项-1：/uc/shopping-cart/item-dec
	@RequestMapping(method=RequestMethod.POST,value="/uc/shopping-cart/item-dec")
	public String itemInc(@RequestParam String cp_id,
							@AuthenticationPrincipal(expression="customer.c_id") Long c_id) 
	{
		cartService.decItem(c_id,cp_id);
		
		return "redirect:/uc/showcart";
		
	}*/
	//ajax异步提交更新购物项商品数量
	  @RequestMapping(method = RequestMethod.POST, value = "/uc/shopping-cart/update-item-amount")
	    @ResponseBody // 把返回值作为响应内容，加了jackson库之后，会转换为json文本
	    public Cart updateItemAmount(@AuthenticationPrincipal(expression="customer.c_id") Long c_id,
	                                         @RequestParam String cp_id,
	                                         @RequestParam Integer amount) {
	        cartService.updateItemAmount(c_id, cp_id, amount);
	        return cartService.findOneByUserId(c_id);
	    }
	
	
	
}
