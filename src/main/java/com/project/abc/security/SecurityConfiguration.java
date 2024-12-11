package com.project.abc.security;

import com.project.abc.commons.RequestDataProvider;
import com.project.abc.security.filters.*;
import com.project.abc.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final List<String> PERMIT_URL = List.of(
            "/user/login",
            "/user/register",
            "/health/status",
            "/uploads/**",
            "/item/items",
            "/category/categories"
    );

    public static List<String> ACTIVATION_NOT_REQUIRED_URLS = List.of();

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    RequestDataProvider requestDataProvider;

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.antMatcher("/**").cors().and().csrf().disable()
                        .headers().frameOptions().disable().and().antMatcher("/**").authorizeRequests();

        for (String url : PERMIT_URL) {
            registry.antMatchers(url).permitAll();
        }

        registry.anyRequest().authenticated()
                .and().antMatcher("/**").exceptionHandling()
                .authenticationEntryPoint(new JWTAuthEntryHandler())
                .accessDeniedHandler(new AccessDeniedeExceptionHandler()).and()
                .addFilterBefore(new JWTSecurityFilter(authenticationManager(), this.userService, this.serverConfig, this.requestDataProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JWTSecurityFilter.class)
                .addFilterBefore(new LoggingFilter(requestDataProvider), ExceptionHandlerFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
