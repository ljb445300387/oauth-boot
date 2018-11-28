package club.yuit.oauth.boot.config.auth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import club.yuit.oauth.boot.config.MyLogoutSuccessHandler;
import club.yuit.oauth.boot.support.oauth2.BootAccessDeniedHandler;

/**
 * @author yuit
 * @create time 2018/10/15 14:57
 * @description
 * @modify by
 * @modify time
 **/
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint point;

	@Autowired
	private BootAccessDeniedHandler handler;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private MyLogoutSuccessHandler logoutSuccessHandler;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		resources.tokenStore(tokenStore).resourceId("boot-server");

		resources.authenticationEntryPoint(point).accessDeniedHandler(handler);

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/static/bower_components/bootstrap/dist/css/bootstrap.min.css", //
						"/static/css/**", //
						"/static/dist/**", //
						"/static/fonts/**", //
						"/static/lib/**", //
						"/login**", //
						"/logout**", //
						"/static/bootstrap.min.css")
				.permitAll().anyRequest().access("#oauth2.hasAnyScope('all')").and().csrf().disable();
		http.logout().logoutSuccessHandler(logoutSuccessHandler);
		http.csrf().ignoringAntMatchers("/logout/**");
		http.cors().configurationSource(configurationSource());
	}

	private CorsConfigurationSource configurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedHeader("Content-Type");
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.GET);
		source.registerCorsConfiguration("/logout", config);
		source.registerCorsConfiguration("/oauth/authorize", config);
		return source;
	}

}
