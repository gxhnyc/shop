package shop.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import shop.service.IpService;

public class BaseController {
	protected Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IpService ipService;
	
	private String ip;
	private String province;
	/**
	 * 准备页面数据(高德地图转Ip地址)的公共方法
	 *  springmvc在执行控制器类中的@RequestMapping方法之前
	 *  会自动调用@ModelAttribute方法
	 */
	@ModelAttribute
	public void prepareAddr(
			HttpServletRequest request,
			HttpSession session,
			Model model
			) {
		logger.debug("开始准备页面位置数据......");
		
		ip=request.getRemoteAddr();//获取请求地址的ip
		
		province = ipService.ipToProvince(ip);
		
		model.addAttribute("province", province);
		/*province=(String) session.getAttribute("province");
		//1.判断Province是否为空，若空，重新获取本地ip,并设置到session中
		if(province==null) {			
			ip=request.getRemoteAddr();
			logger.debug("province为空，重新获取ip,当前ip:"+ip);
		//2.调用高德地图转ip地址方法
		province = ipService.ipToProvince(ip);
		
		//3.将province设置到session中
		session.setAttribute("province", province);//比较关键
		
		model.addAttribute("province", province);
		
		}*/
		
		
	}
}
