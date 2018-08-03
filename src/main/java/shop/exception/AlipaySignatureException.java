package shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//修改错误页面状态码信息（bad_request:400）
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "签名无效")
public class AlipaySignatureException extends ServiceException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlipaySignatureException(Exception cause) {
		super(cause);
		
	}
	
	public AlipaySignatureException() {}
}
