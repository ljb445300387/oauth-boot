package club.yuit.oauth.boot.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import club.yuit.oauth.boot.support.common.TokenStoreType;
import club.yuit.oauth.boot.support.properities.BootLogLevelProperties;
import club.yuit.oauth.boot.support.properities.BootSmsCodeProperties;
import lombok.Data;

/**
 * @auther yuit
 * @create 2018/10/19 9:44
 * @description
 * @modify
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "boot.oauth")
@Data
public class BootSecurityProperties {

    /**
     * 定义token存储类型
     */
    private TokenStoreType tokenStoreType = TokenStoreType.memory;

    private String loginProcessUrl="/auth/authorize";

    /**
     * 日志输出等级，默认 INFO {@NestedConfigurationProperty} 生成嵌套类的配置元数据信息
     * 更友好的提示
     */
    @NestedConfigurationProperty
    private BootLogLevelProperties logging = new BootLogLevelProperties();

    @NestedConfigurationProperty
    private BootSmsCodeProperties sms =  new BootSmsCodeProperties();

    private String tokenSigningKey = "OAUTHBOOT@IUY09&098#UIOKNJJ-YUIT.CLUB";

}
