package club.yuit.oauth.boot.support.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import club.yuit.oauth.boot.response.HttpResponse;
import club.yuit.oauth.boot.utils.HttpUtils;

/**
 * @author yuit
 * @create 2018/11/2 10:48
 * @description token 校验失败
 * @modify
 */
@Component
public class BootOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		HttpUtils.writerError(HttpResponse.baseResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()), response);
	}

}
