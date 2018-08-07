package shop.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import shop.entity.Customer;
import shop.exception.UserNameNotFoundException;
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
		 	
		 	Customer customer = customerMapper.findOneByUsername(username);
		 		
	        if (customer == null) { // 按照接口要求，用户名不存在时必须抛异常UsernameNotFoundException
	            throw new UserNameNotFoundException("用户名不存在: " + username);
	        }
	        else{
	        System.out.println("userDetailsServiceImpl.loadUserByUserName()："+customer.getUsername()+
	        		"最后一次登录时间："+customer.getLastdate());
	       /* System.out.println("UserDetailsServiceImpl:"+customer.getC_id()+"-----------");*/
	        // 从mapper得到的是实体operator，需要转换成springsecurity所需的UserDetails对象
	        return new UserDetailsImpl(customer);
	        }
	}
	
}
