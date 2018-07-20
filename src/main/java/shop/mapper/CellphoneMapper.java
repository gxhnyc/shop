package shop.mapper;

import java.util.List;

import shop.entity.Cellphone;



public interface CellphoneMapper {
	//----------------------------查询-----------------------------------
		/**
		 * 列出所有手机
		 * @return
		 */
		public List<Cellphone> findAll();
		/**
		 * 通过手机id查询
		 * @param cp_id
		 * @return
		 */
		public Cellphone findByID(String cp_id);
		/**
		 * 添加一台手机
		 * @param cellphone
		 */
		public void createOne(Cellphone cellphone);
		/**
		 * 判断手机型号是否已经存在
		 * @param cp_model
		 * @return
		 */
		public Integer modelExist(String cp_model);
		/**
		 * 更新手机信息
		 * @param cellphone
		 */
		public void updateOne(Cellphone cellphone);
		/**
		 * 通过id删除手机信息
		 * @param id
		 */
		public void deleteOne(String id);
		/**
		 * 模糊查询
		 * @param cellphone
		 * @return
		 */
		public List<Cellphone> fuzzyQuery(Cellphone cellphone);
		
}
