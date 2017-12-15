package com.luxoft.ubs.restfacade.web;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException, ServletException {

        httpServletResponse.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println("HTTP Status 401 - " + authenticationException.getMessage());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("LUXOFTUBSRealm");
        super.afterPropertiesSet();
    }
}
