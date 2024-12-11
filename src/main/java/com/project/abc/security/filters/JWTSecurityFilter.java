package com.project.abc.security.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.abc.commons.RequestDataProvider;
import com.project.abc.commons.exceptions.http.UnauthorizeException;
import com.project.abc.commons.jwt.JWT;
import com.project.abc.commons.jwt.JWTContent;
import com.project.abc.model.user.User;
import com.project.abc.security.SecurityConfiguration;
import com.project.abc.security.ServerConfig;
import com.project.abc.security.Session;
import com.project.abc.service.user.UserService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JWTSecurityFilter extends BasicAuthenticationFilter {

    private UserService userService;
    private final ServerConfig serverConfig;
    private RequestDataProvider requestDataProvider;

    public JWTSecurityFilter(AuthenticationManager authenticationManager,
                             UserService userService,
                             ServerConfig serverConfig,
                             RequestDataProvider requestDataProvider) {
        super(authenticationManager);
        this.userService = userService;
        this.serverConfig = serverConfig;
        this.requestDataProvider = requestDataProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token == null){
            token = request.getParameter("_authorization");
        }
        if (token == null){
            log.debug("token not found");
            chain.doFilter(request,response);
            return;
        }

        try {
            JWTContent jwtContent = JWT.decode(token,serverConfig.getSecretKey());
            log.debug("jwt token decoded, user = {}", jwtContent.getSubject());
            Optional<User> user = this.userService.findUserById(jwtContent.getSubject());
            if (user.isEmpty()){
                log.error("user not found for id = {}", jwtContent.getSubject());
                throw new UnauthorizeException("User Not Found");
            }
            // update request hash
            requestDataProvider.setRequestHash(requestDataProvider.getRequestHash() + "-" +  user.get().getEmail());
            MDC.put("request_unique_id", "[" + requestDataProvider.getRequestHash() + "]");
            if (user.get().getStatus().equals(User.UserStatus.PENDING_VERIFICATION)) {
                if (SecurityConfiguration.ACTIVATION_NOT_REQUIRED_URLS.contains(request.getRequestURI())) {
                    log.info("user status pending verification. but request url is allowed user = {}", user.get().getEmail());
                    Session.setUser(user.get());
                } else {
                    throw new UnauthorizeException("authorization token does not have access to perform this action");
                }
            } else if (user.get().getStatus().equals(User.UserStatus.TO_DELETE)) {
                throw new UnauthorizeException("authorization token does not have access to perform this action");
            } else {
                Session.setUser(user.get());
            }
        }catch (TokenExpiredException exception){
            request.setAttribute("tok-expired",true);
        }catch (Exception e){
            log.error("token decode failed");
        }
        chain.doFilter(request,response);
    }
}
