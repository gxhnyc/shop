package shop.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import shop.entity.Customer;
import shop.mapper.CustomerMapper;
import shop.serviceimpl.UserDetailsImpl;

/**
 * --实现 ：更新最后一次登录时间
 * 当有人登录成功时，springsecurity会发布事件InteractiveAuthenticationSuccessEvent到spring容器，
 * spring容器会找到实现了该事件监听器接口的组件，并调用其onApplicationEvent方法
 * @author Administrator
 *
 */
@Component
public class LoginSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	private CustomerMapper customerMapper;
	@Autowired	
	public LoginSuccessListener(CustomerMapper customerMapper) {
		super();
		this.customerMapper = customerMapper;
	}


	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		UserDetailsImpl userDetails=(UserDetailsImpl) event.getAuthentication().getPrincipal();
		System.out.println(userDetails.getUsername()+"成功登录！");
		//更新最后一次登录时间
		customerMapper.updateLastLoginTime(userDetails.getCustomer().getC_id(),new Date());
	}

}
