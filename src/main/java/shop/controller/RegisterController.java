package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import shop.entity.Customer;
import shop.exception.UserNameExistException;
import shop.service.CustomerService;

/**
 * 用户注册控制器
 * @author Administrator
 *
 */
@Controller
public class RegisterController {
	private CustomerService customerService;
	@Autowired
	public RegisterController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public String register(@ModelAttribute Customer customer) {
		
		return "shop-register";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/register")
	public String register(@ModelAttribute Customer customer,BindingResult bindingResult,Model model) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("用户名或密码错误！");
			
			return "shop-register";
		}
		try {
		customerService.register(customer);
		}catch(UserNameExistException uep) {
			System.out.println(uep.getMessage());
			bindingResult.rejectValue("username", "Customer.username.existsException", "用户名已存在！");
			return "shop-register";
		}
		
		return "redirect:/login";
	}
	
}
