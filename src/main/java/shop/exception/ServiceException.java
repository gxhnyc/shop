package shop.exception;
/**
 * 继承运行时异常类
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception cause) {
		
	}
	
	public ServiceException() {}
	
	public ServiceException(String message, Exception cause) {
        super(message, cause);
    }
    
}
