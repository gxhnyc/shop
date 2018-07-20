package shop.service;

import org.springframework.transaction.annotation.Transactional;

import shop.entity.Customer;

@Transactional
public interface CustomerService {
	/**
	 * 注册用户
	 * @param customer
	 */
	public void register(Customer customer);
}
