package shop.exceptions;

public class UserNameNotFoundException extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNameNotFoundException(String message) {
		super(message);
	}
}
