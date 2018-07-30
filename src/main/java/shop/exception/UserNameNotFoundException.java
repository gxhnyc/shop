package shop.exception;
/**
 * 用户名未找到 异常，
 * 需要在构造方法内传入异常信息
 * @author Administrator
 *
 */
public class UserNameNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public UserNameNotFoundException(String message) {
		super(message);
	}
}
