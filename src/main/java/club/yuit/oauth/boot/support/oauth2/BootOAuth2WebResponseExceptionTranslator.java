package club.yuit.oauth.boot.support.oauth2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @author yuit
 * @create 2018/11/2 9:14
 * @description
 * @modify
 */
@Component("bootWebResponseExceptionTranslator")
public class BootOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

		// Try to extract a SpringSecurityException from the stacktrace
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

		// 异常栈获取 OAuth2Exception 异常
		Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);

		// 异常栈中有OAuth2Exception
		if (ase != null) {
			return handleOAuth2Exception((OAuth2Exception) ase);
		}

		ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
		}

		ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class,
				causeChain);
		if (ase instanceof AccessDeniedException) {
			return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
		}

		ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer
				.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
		if (ase instanceof HttpRequestMethodNotSupportedException) {
			return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
		}

		// 不包含上述异常则服务器内部错误
		return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
	}

	private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

		int status = e.getHttpErrorCode();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
			headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
		}

		BootOAuth2Exception exception = new BootOAuth2Exception(e.getMessage(), e);

		ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(exception, headers,
				HttpStatus.valueOf(status));

		return response;

	}

	public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
		this.throwableAnalyzer = throwableAnalyzer;
	}

	private static class ForbiddenException extends OAuth2Exception {
		private static final long serialVersionUID = 1L;

		public ForbiddenException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		public int getHttpErrorCode() {
			return 403;
		}
	}

	private static class ServerErrorException extends OAuth2Exception {

		private static final long serialVersionUID = 1L;

		public ServerErrorException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "server_error";
		}

		public int getHttpErrorCode() {
			return 500;
		}

	}

	private static class UnauthorizedException extends OAuth2Exception {

		private static final long serialVersionUID = 1L;

		public UnauthorizedException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "unauthorized";
		}

		public int getHttpErrorCode() {
			return 401;
		}

	}

	private static class MethodNotAllowed extends OAuth2Exception {

		private static final long serialVersionUID = 1L;

		public MethodNotAllowed(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "method_not_allowed";
		}

		public int getHttpErrorCode() {
			return 405;
		}

	}
}
