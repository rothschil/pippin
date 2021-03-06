package io.github.rothschil.oauth2.config;

import io.github.rothschil.oauth2.provider.UserAuthenticationProvider;
import io.github.rothschil.oauth2.provider.UserSmsAuthenticationProvider;
import io.github.rothschil.oauth2.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 访问控制交给资源服务器，只保留 /oauth/**,/login/**,/logout/**
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/27 15:18
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * userDetailsService 获取token的时候对用户进行一些自定义过滤，并将保存用户信息（用户名，密码，角色等）
     */
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 使用MD5对client_secreat进行加密，可以使用默认的加密方式也可以自定义，这里使用MD5加密方式
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return Md5Util.md5Encode(String.valueOf(charSequence), null);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(Md5Util.md5Encode(String.valueOf(charSequence), null));
            }
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置用户签名服务 主要是user-details 机制，
     *
     * @param auth 签名管理器构造器，用于构建用户具体权限控制
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(smsAuthenticationProvider())
                .authenticationProvider(authenticationProvider());
        ;
    }

    /**
     * @Description Spring security认证Bean
     * @date 2019/7/4 17:39
     * @since 1.0
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authenticationProvider = new UserAuthenticationProvider();
        return authenticationProvider;
    }

    @Bean
    public AuthenticationProvider smsAuthenticationProvider() {
        AuthenticationProvider authenticationProvider = new UserSmsAuthenticationProvider();
        return authenticationProvider;
    }

    @Deprecated
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().permitAll(); //新增login form支持用户登录及授权
    }

    /**
     * @param web
     * @return void
     * @throws
     * @Description Spring Security应该忽略URLS以xxx开头的路由
     * @date 20/11/27 15:52
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth2/**");
    }
}