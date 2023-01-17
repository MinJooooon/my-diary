package com.mydiary.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequestrequest, HttpServletResponse httpServletResponseresponse,
                         AuthenticationException ex) throws IOException {
        httpServletResponseresponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
