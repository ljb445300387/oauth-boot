package club.yuit.oauth.boot.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @auther yuit
 * @create 2018/10/19 16:18
 * @description
 * @modify
 */
public class VerificationCodeFailureException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VerificationCodeFailureException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerificationCodeFailureException(String msg) {
        super(msg);
    }
}
