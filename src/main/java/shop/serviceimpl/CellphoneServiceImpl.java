package shop.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.entity.Cellphone;
import shop.exception.ModelExistExcption;
import shop.mapper.CellphoneMapper;
import shop.service.CellphoneService;



@Service
@Transactional
public class CellphoneServiceImpl implements CellphoneService {
	
	private CellphoneMapper cellphoneMapper;		
	@Autowired
	public CellphoneServiceImpl(CellphoneMapper cellphoneMapper) {
		this.cellphoneMapper = cellphoneMapper;
	}

	@Override
	public List<Cellphone> findAll() {
		// 查询出所有手机
		return cellphoneMapper.findAll();
	}

	@Override
	public List<Cellphone> findByModel(String model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cellphone findByID(String cp_id) {
		// 通过手机id查询手机信息
		return cellphoneMapper.findByID(cp_id);
	}

	

	@Override
	public void createOne(Cellphone cellphone) {
		if(cellphoneMapper.modelExist(cellphone.getCp_model())>0) {
			throw new ModelExistExcption();
		}
		
		cellphoneMapper.createOne(cellphone);
		
	}

	@Override
	public void deleteOne(String id) {
		//通过id删除手机信息
		cellphoneMapper.deleteOne(id);
		
	}

	@Override
	public void updateOne(Cellphone cellphone) {
		// TODO Auto-generated method stub
		cellphoneMapper.updateOne(cellphone);
	}

	@Override
	public List<Cellphone> fuzzyQuery(Cellphone cellphone) {
		// 模糊查询
		return cellphoneMapper.fuzzyQuery(cellphone);
	}

}
