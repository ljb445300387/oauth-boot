package club.yuit.oauth.boot.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import club.yuit.oauth.boot.response.HttpResponse;
import club.yuit.oauth.boot.utils.HttpUtils;

/**
 * @author yuit
 * @create 2018/11/6 17:45
 * @description
 * @modify
 */
@Component
public class BootLoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		HttpUtils.writerError(HttpResponse.baseResponse(401, exception.getMessage()), response);
	}
}
