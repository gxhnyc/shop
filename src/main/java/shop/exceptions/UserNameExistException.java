package shop.exceptions;

public class UserNameExistException extends ServiceException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNameExistException() {
		super("用户名已存在！");
	}

}
