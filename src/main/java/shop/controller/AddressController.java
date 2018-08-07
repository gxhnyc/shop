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

import shop.entity.ShippingAddress;
import shop.service.AddressService;


/**
 * 地址控制器
 * @author Administrator
 *
 */
@Controller
public class AddressController extends BaseController{
	
	private AddressService addressService;	
	@Autowired
	public AddressController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}
	//查看我的收货地址/uc/showaddress
	@RequestMapping(method=RequestMethod.GET,value="/uc/showaddress")
	public String showAddress(Model model,
			@AuthenticationPrincipal(expression="customer.c_id") Long c_id) {
		
		List<ShippingAddress> addresses=addressService.findAllAddress(c_id);
		model.addAttribute("addresses", addresses);
		
		return "show-address";		
	}
	
	
	//添加收件地址
		@RequestMapping(method=RequestMethod.GET,value="/uc/add-address")
		public String addAddress(@ModelAttribute ShippingAddress shippingAddress) {		
			
			return "add-address";		
		}
		@RequestMapping(method=RequestMethod.POST,value="/uc/add-address")
		public String addAddress(@ModelAttribute @Valid ShippingAddress shippingAddress,
										BindingResult bindingResult,
										@AuthenticationPrincipal(expression="customer.c_id") Long c_id) 
		{	
			if(bindingResult.hasErrors()) {
				logger.debug("添加地址时出错！");
				return "add-address";
			}
			addressService.addAddress(shippingAddress,c_id);
			
			return "redirect:/uc/showcart";
			
		}
		//修改收货地址------------------	
		@RequestMapping(method=RequestMethod.GET,value="/uc/edit-address/{ship_id}")
		public String editAddress(@PathVariable Long ship_id,
				@AuthenticationPrincipal(expression="customer.c_id") Long c_id,
										Model model) 
		{	
			//为保证安全，应该通过用户id和地址id去查找
			ShippingAddress shippingAddress=addressService.findAddressByID(c_id,ship_id);
			
			model.addAttribute("shippingAddress", shippingAddress);
			
			return "edit-address";
			
		}
		@RequestMapping(method=RequestMethod.POST,value="/uc/edit-address/{ship_id}")
		public String editAddress(@ModelAttribute @Valid ShippingAddress shippingAddress,
										BindingResult bindingResult,
										@PathVariable Long ship_id,
										@AuthenticationPrincipal(expression="customer.c_id") Long c_id,
										Model model) 
		{	
			if(bindingResult.hasErrors()) {
				logger.debug("修改地址时有错误！");
				return "edit-address";
			}		
			shippingAddress.setShip_id(ship_id);
			shippingAddress.setC_id(c_id);
			addressService.editAddress(shippingAddress);		
			return "redirect:/uc/showcart";		
		}
		//删除收货地址-------------------------
		
		@RequestMapping(method=RequestMethod.GET,value="/uc/del-address/{id}")
		public String delAddress(@PathVariable Long id,
				@AuthenticationPrincipal(expression="customer.c_id") Long c_id
				){
			
			addressService.delAddressByID(c_id,id);
			
			return "redirect:/uc/showcart";		
		}
}
