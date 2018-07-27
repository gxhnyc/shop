package shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import shop.entity.ShippingAddress;

public interface AddressMapper {
	
	/**
	 * 列出所有地址
	 * @param c_id
	 * @return
	 */
	List<ShippingAddress> findAllAddress(Long c_id);
	
	/**
	 * 添加收货地址
	 * @param shippingAddress
	 */
	void addAddress(ShippingAddress shippingAddress);
	
	/**
	 * 修改地址
	 * @param shippingAddress
	 */
	void editAddress(ShippingAddress shippingAddress);
	/**
	 * 通过ID查询地址
	 * @param id 
	 * @return
	 */
	ShippingAddress findAddressByID(@Param("c_id") Long c_id,@Param("ship_id") Long ship_id);
	/**
	 * 通过id删除收货地址
	 * @param id
	 * @param ship_id 
	 */
	void delAddressByID(@Param("c_id") Long c_id,@Param("ship_id") Long ship_id);
	
}
