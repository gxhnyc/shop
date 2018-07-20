package shop.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import shop.mapper.CustomerMapper;
import shop.serviceimpl.UserDetailsImpl;

/**
 * 当有会员成功登录时，springSecurity会发布事件InteractiveAuthenticationSuccessEvent到Spring容器
 * spring容器会找到实现了该事件监听接口的组件，并调用其onApplicationEvent()方法
 * @author Administrator
 *
 */
@Component
public class LoginSucController implements ApplicationListener<InteractiveAuthenticationSuccessEvent>{
	private CustomerMapper customerMapper;
	
	@Autowired
	public LoginSucController(CustomerMapper customerMapper) {
		super();
		this.customerMapper = customerMapper;
	}


	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		
		UserDetailsImpl userDetails=(UserDetailsImpl) event.getAuthentication().getPrincipal();
		System.out.println(userDetails.getUsername()+"登录成功！");
		
		//更新登录时间
		customerMapper.updateLastLoginTime(userDetails.getCustomer().getC_id(),new Date());
	}
	

}
