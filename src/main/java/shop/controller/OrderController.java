package shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import shop.entity.Cart;
import shop.entity.CartItem;
import shop.entity.Order;
import shop.entity.OrderDetails;
import shop.entity.OrderForm;
import shop.entity.OrderItem;
import shop.entity.Orders;
import shop.entity.ShippingAddress;
import shop.service.AddressService;
import shop.service.CartService;
import shop.service.OrderService;

/**
 * 订单控制器
 * @author Administrator
 *
 */
@Controller
public class OrderController {
	private OrderService orderService;
	private AddressService addressService;
	private CartService cartService;
	
	@Autowired
	public OrderController(OrderService orderService,AddressService addressService,CartService cartService) {
		super();
		this.orderService = orderService;
		this.addressService=addressService;
		this.cartService=cartService;
	}
	
	
	
	
	//---创建订单-----------
		@RequestMapping(method=RequestMethod.GET,value="/uc/create-order")
		public String createOrder(
				@ModelAttribute OrderForm orderForm,
				Model model,
				@AuthenticationPrincipal(expression="customer.c_id") Long c_id
				) {
			prepareInCreateOrder(model, c_id);		
			
			return "create-order";		
		}	
		@RequestMapping(method=RequestMethod.POST,value="/uc/create-order")
		public String createOrder(@ModelAttribute @Valid OrderForm orderForm,
									BindingResult bindingResult,
									@AuthenticationPrincipal(expression="customer.c_id") Long c_id,
									Model model) {
			if(bindingResult.hasErrors()) {
				prepareInCreateOrder(model, c_id);
				return "create-order";
			}	
			Order order=orderService.createOrder(c_id,orderForm.getShippingAddressID());
			return "redirect:/uc/showorders/"+order.getO_id();		
		}
		/**
		 * 创建订单时的准备工作
		 * @param model
		 * @param c_id
		 */
		public void prepareInCreateOrder(Model model, Long c_id) {
			//得到当前用户下的购物项
			List<CartItem> cartitems=cartService.findAllItems(c_id);
			//得到当前用户的所有地址
			List<ShippingAddress> addresses=addressService.findAllAddress(c_id);
			
			model.addAttribute("shoppingCart", new Cart(cartitems));
			model.addAttribute("addresses", addresses);
		}
	
	//----我的当前订单-----------------/uc/showorders
		@RequestMapping(method=RequestMethod.GET,value="/uc/showorders/{o_id}")
		public String showOrderbyId(
									@PathVariable Long o_id,
									Model model,
									@AuthenticationPrincipal(expression="customer.c_id") Long c_id)
		{
			OrderDetails orderDetails=orderService.findAllOrderItems(o_id);
			model.addAttribute("orderDetails", orderDetails);
			
			return "order-details";
			
		}
		
		@RequestMapping(method=RequestMethod.POST,value="/uc/showorders/{o_id}")
		public String showOrderbyId(@AuthenticationPrincipal(expression="customer.c_id") Long c_id)
		{	
			
			orderService.cleanCartItems(c_id);
			
			return "redirect:/";
			
		}
		//我的所有订单
		@RequestMapping(method=RequestMethod.GET,value="/uc/showorders")
		public String showOrders(Model model,
				@AuthenticationPrincipal(expression="customer.c_id") Long c_id) {
			
			Orders orders=new Orders(orderService.findAllOrders(c_id));
			model.addAttribute("orders", orders);
			
			return "showorders";
			
		}
		//----订单详情-----------------
				@RequestMapping(method=RequestMethod.GET,value="/uc/order-details/{o_id}")
				public String OrderDetails(
											@PathVariable Long o_id,
											Model model,
											@AuthenticationPrincipal(expression="customer.c_id") Long c_id)
				{
					OrderDetails orderDetails=orderService.findAllOrderItems(o_id);
					model.addAttribute("orderDetails", orderDetails);
					
					return "order-details";
					
				}
}
