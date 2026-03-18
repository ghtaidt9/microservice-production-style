package com.taidt9.productservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/actuator")
                || path.startsWith("/favicon")
                || path.startsWith("/swagger");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        Exception exceptionCaught = null;

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            exceptionCaught = ex;
            throw ex;
        } finally {
            long duration = System.currentTimeMillis() - start;
            logRequest(request, response, duration, exceptionCaught);
        }
    }

    private void logRequest(HttpServletRequest request, HttpServletResponse response,
                            long duration, Exception exception) {

        int status = response.getStatus();
        String method = request.getMethod();
        String path = request.getRequestURI();
        String ip = getClientIp(request);
        String userId = getUserId();

        if (status >= 500) {
            if (exception != null) {
                log.error("method={} path={} userId={} status={} duration_ms={} ip={} error={}",
                        method, path, userId, status, duration, ip, exception.getMessage(), exception);
            } else {
                log.error("method={} path={} userId={} status={} duration_ms={} ip={}",
                        method, path, userId, status, duration, ip);
            }
        } else if (status >= 400) {
            log.warn("method={} path={} userId={} status={} duration_ms={} ip={}",
                    method, path, userId, status, duration, ip);
        } else {
            log.info("method={} path={} userId={} status={} duration_ms={} ip={}",
                    method, path, userId, status, duration, ip);
        }
    }

    private String getUserId() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception ignored) {}
        return "anonymous";
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        return xf == null ? request.getRemoteAddr() : xf.split(",")[0];
    }
}