package shop.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

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
	 * @param operator
	 */
	public UserDetailsImpl(Customer customer) {
		super(customer.getUsername(),
				customer.getPassword(),buildAuthorities(customer));
		this.customer=customer;
	}

	
	
	/**
	 * 	e.g.
     *	ROLE_图书管理员
     *	PERM_BOOK_RO
     *	PERM_AUTHOR_RO
     *	PERM_PUBLISHER_RO
     *	将权限按以上格式设置到authorities属性中去
	 * @param operator
	 * @return
	 */
	private static List<GrantedAuthority> buildAuthorities(Customer customer) {

		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		return authorities;
	}

	
	
}
