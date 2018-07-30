package shop.exception;

/**
 * 手机型号已存在异常
 * @author Administrator
 *
 */

public class ModelExistExcption extends ServiceException {
	
	private static final long serialVersionUID = 1L;

	public ModelExistExcption() {
		super("该款手机型号已存在！");
	}
}
