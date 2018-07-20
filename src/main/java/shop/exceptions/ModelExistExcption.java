package shop.exceptions;

public class ModelExistExcption extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelExistExcption() {
		super("该款手机型号已存在！");
	}
}
