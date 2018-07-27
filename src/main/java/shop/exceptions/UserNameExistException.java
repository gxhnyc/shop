package shop.exceptions;
/**
 * 用户名已存在 异常类
 * @author Administrator
 *
 */
public class UserNameExistException extends ServiceException {
	
	
	 
	private static final long serialVersionUID = 1L;

	public UserNameExistException() {
		super("用户名已存在！");
	}

}
