package club.yuit.oauth.boot.exception;

/**
 * @author yuit
 * @create Time 2018/8/6 15:56
 * @description 认证失败
 * @modify by
 * @modify time
 **/
public class AuthFailureException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthFailureException() {
        this("认证失败！");
    }

    public AuthFailureException(String message) {
        super(message);
    }
}
