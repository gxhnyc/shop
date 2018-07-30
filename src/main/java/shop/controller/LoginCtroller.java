
package shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户登录控制器（处理 /login 请求）
 * @author Administrator
 *
 */
@Controller
public class LoginCtroller {
	
	
	@RequestMapping(method=RequestMethod.GET,value="/login")
	public String login() {
		
		return "login";
		
	}
}
