package club.yuit.oauth.boot.config.auth2;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import club.yuit.oauth.boot.filter.BootClientCredentialsTokenEndpointFilter;
import club.yuit.oauth.boot.support.BootSecurityProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yuit
 * @create 2018/11/8 10:52
 * @description
 * @modify
 */
@Component
@Setter
@Getter
public class BootOAuth2SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private ClientCredentialsTokenEndpointFilter filter;

    private BootSecurityProperties properties;

    private BootClientCredentialsTokenEndpointFilter bootClientCredentialsTokenEndpointFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
        http.addFilterBefore(bootClientCredentialsTokenEndpointFilter,filter.getClass());
    }


}
