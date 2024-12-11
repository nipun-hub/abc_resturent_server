package com.project.abc.security.filters;

import com.project.abc.commons.jwt.JWT;
import com.project.abc.commons.jwt.JWTContent;
import com.project.abc.security.ServerConfig;
import com.project.abc.security.Session;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class JWTAppendAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    ServerConfig serverConfig;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Session.getUser() == null) {
            return body;
        }
        JWTContent.JWTContentBuilder contentBuilder = JWTContent.builder().subject(Session.getUser().getId());
        contentBuilder.expiredIn(DateUtils.MILLIS_PER_SECOND * serverConfig.getTokenExpireTime());
        if (Session.getUser() != null) {
            contentBuilder.payload(Map.of("role", String.valueOf(Session.getUser().getRole())));
        }
        String token = JWT.encode(contentBuilder.build(), serverConfig.getSecretKey());
        response.getHeaders().add("Authorization", token);
        return body;
    }
}
