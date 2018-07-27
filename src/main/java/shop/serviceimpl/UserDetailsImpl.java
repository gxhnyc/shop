package shop.serviceimpl;


import java.util.Arrays;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import shop.entity.Customer;

/**
 * 继承springsecurity提供的User类获得了UserDetails的基本实现，
 * 如username、password、authorities、enabled等字段及其getter；
 * 还有继承的equals和hashCode会根据username判断两个UserDetailsImpl是否相等，
 * 用于SessionRegistry中维护同一个用户名多次登录导致多个会话的情况
 */
public class UserDetailsImpl extends User{
	
	

	private static final long serialVersionUID = 1L;
	
	private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	
	
	/**
	 * 构造方法
	 * @param customer
	 */
	public UserDetailsImpl(Customer customer) {
		
		
		super(customer.getUsername(),
				customer.getPassword(),
				true,true,true,true,
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		
		this.customer=customer;
	}
	
	
}
