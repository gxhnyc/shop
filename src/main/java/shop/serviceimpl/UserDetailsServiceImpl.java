package shop.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import shop.entity.Customer;
import shop.exceptions.UserNameNotFoundException;
import shop.mapper.CustomerMapper;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private CustomerMapper customerMapper;
	@Autowired
	public UserDetailsServiceImpl(CustomerMapper customerMapper) {
		super();
		this.customerMapper = customerMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UserNameNotFoundException {
		 
		 //更新登录时间
		 customerMapper.updateLoginTime(customerMapper.findOneByUsername(username).getC_id());
		 Customer customer = customerMapper.findOneByUsername(username);
	        System.out.println("userDetailsServiceImpl.loadUserByUserName()："+customer.getUsername()+
	        		"最后一次登录时间："+customer.getLastdate());
	        if (customer == null) { // 按照接口要求，用户名不存在时必须抛异常UsernameNotFoundException
	            throw new UserNameNotFoundException("用户名不存在: " + username);
	        } 
	        
	        // 从mapper得到的是实体operator，需要转换成springsecurity所需的UserDetails对象
	        return new UserDetailsImpl(customer);
	}
	
}
