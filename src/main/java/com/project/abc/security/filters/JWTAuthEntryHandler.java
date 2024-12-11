package com.project.abc.security.filters;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.http.UnauthorizeException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JWTAuthEntryHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        final boolean isExpired = httpServletRequest.getAttribute("tok-expired") != null;
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (isExpired) {
            httpServletResponse.getOutputStream().println(new UnauthorizeException(ExType.TOKEN_EXPIRED, "token expired").getJsonAsString(null));
        } else {
            httpServletResponse.getOutputStream().println(new UnauthorizeException("authorization token invalid", ExType.INVALID_TOKEN).getJsonAsString(null));
        }
    }
}
