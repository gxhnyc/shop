package shop.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import shop.entity.Customer;

public interface CustomerMapper {
	/**
	 * 注册用户
	 * @param customer
	 */
	public void register(Customer customer);
	/**
	 * 判断用户名是否已经存在
	 * @param username
	 * @return
	 */
	public int userNameExist(String username);
	/**
	 * 通过用户名查找用户
	 * @param username
	 * @return
	 */
	public Customer findOneByUsername(String username);
	//更新登录时间
	public void updateLoginTime(Long c_id);
	/**
	 * 通过登录成功监听器更新最后一次登录时间
	 * @param c_id
	 * @param date
	 */
	public void updateLastLoginTime(@Param("c_id") Long c_id,@Param("date") Date date);
}
