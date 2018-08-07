package shop.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.entity.ShippingAddress;
import shop.mapper.AddressMapper;
import shop.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	private AddressMapper addressMapper;
	
	@Autowired	
	public AddressServiceImpl(AddressMapper addressMapper) {
		super();
		this.addressMapper = addressMapper;
	}

	@Override
	public void addAddress(ShippingAddress shippingAddress, Long c_id) {
		//设置用户id
		shippingAddress.setC_id(c_id);
		//通过shippingAddress对象添加地址
		addressMapper.addAddress(shippingAddress);
	}

	@Override
	public List<ShippingAddress> findAllAddress(Long c_id) {
		//System.out.println("addressServiceImpl:findAllAdress(c_id)--"+c_id);
		List<ShippingAddress> addresses=addressMapper.findAllAddress(c_id);
		
		return addresses;
	}

	@Override
	public void editAddress(ShippingAddress shippingAddress) {
		
		addressMapper.editAddress(shippingAddress);
	}

	@Override
	public void delAddressByID(Long c_id,Long ship_id) {
		addressMapper.delAddressByID(c_id,ship_id);

	}

	@Override
	public ShippingAddress findAddressByID(Long id, Long ship_id) {
		
		return addressMapper.findAddressByID(id,ship_id);
	}

}
