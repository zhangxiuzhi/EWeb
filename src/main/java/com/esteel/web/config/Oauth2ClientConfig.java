package com.esteel.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2018-01-03
 * Time: 14:23
 */
@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig {

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext context, OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(details, context);

        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        template.setAccessTokenProvider(provider);
        return template;
    }

    /**
     * 注册处理redirect uri的filter
     * @param oauth2RestTemplate
     * @return
     */
    @Bean
    public OAuth2ClientAuthenticationProcessingFilter oauth2ClientAuthenticationProcessingFilter(
            OAuth2RestTemplate oauth2RestTemplate,TokenStore jwtTokenStore) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
        filter.setRestTemplate(oauth2RestTemplate);


        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(jwtTokenStore);
        filter.setTokenServices(services);



        //设置回调成功的页面
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                this.setDefaultTargetUrl("/");
                this.setTargetUrlParameter("_target");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });
        return filter;
    }



    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey("-----BEGIN PUBLIC KEY-----\n" +
                "                MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgfPvPKzlAoyf/OG/sf+/eMpf8ggbKH4ArOjD09ep7a/C7Uu2j3NUv+MSCmJPkwY9suXzkD6vpJVcXKc5uh/ytfLOinBE5FcWAou3rRBEhSQDdzoqJ1BqMMWWhrxGhshSuhYf8f5rRHMN2IVq0QBm6HGfp3N/qkurpz+qcTtahFKG9K4KPM5/bLvJ75c6GvaO//xLSjyB37s9EMtBcj6azQt3qwdEJDuupmKNjgDexzF/UrY22+UbTFOWqufmz9Qa6nNmIIOSfEYpKgFa0HRX379RgFL8Uu+i0IJPNDqxjLef9rxYAgoYv2sKMI5MJMKuX8DxfTN31LvCXQLtcVXgNQIDAQAB\n" +
                "                -----END PUBLIC KEY-----");
        return converter;
    }

}