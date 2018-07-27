
package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户登录控制器（处理 /login 请求）
 * @author Administrator
 *
 */
@Controller
public class LoginCtroller {
	
	
	@RequestMapping(method=RequestMethod.GET,value="/login")
	public String login(@RequestParam(required=false) String error,Model model) {
		
		if(error!=null) {
			model.addAttribute("loginError", true);
		}		
		
		return "login";
		
	}
}
