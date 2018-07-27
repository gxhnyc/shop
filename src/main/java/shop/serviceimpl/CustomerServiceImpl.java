package shop.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.entity.Customer;
import shop.exceptions.UserNameExistException;
import shop.mapper.CustomerMapper;
import shop.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerMapper customerMapper;
	private PasswordEncoder passwordEncoder;
	@Autowired	
	public CustomerServiceImpl(CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
		super();
		this.customerMapper = customerMapper;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	public void register(Customer customer) {
		//判断用户名是否已存在
		if(customerMapper.userNameExist(customer.getUsername())>0) {
			throw new UserNameExistException();
		}
		//密码加密
		String encodePassword=passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodePassword);
		// 注册用户
		customerMapper.register(customer);
	}

}
