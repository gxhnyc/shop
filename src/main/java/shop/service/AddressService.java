package shop.service;

import java.util.List;

import shop.entity.ShippingAddress;

public interface AddressService {
	
	/**
	 * 添加收货地址
	 * @param shippingAddress
	 * @param c_id 
	 */
	void addAddress(ShippingAddress shippingAddress, Long c_id);
	
	/**
	 * 列出所有收货地址
	 * @param c_id
	 * @return
	 */
	List<ShippingAddress> findAllAddress(Long c_id);
	/**
	 * 修改收货地址
	 * @param shippingAddress
	 * @param c_id
	 */
	void editAddress(ShippingAddress shippingAddress);
	/**
	 * 通过C_ID和SHIP_ID查询地址
	 * @param id 
	 * @param ship_id 
	 * @return
	 */
	ShippingAddress findAddressByID(Long id, Long ship_id);
	/**
	 * 通过id删除收货地址
	 * @param id
	 */
	void delAddressByID(Long c_id,Long ship_id);
}
