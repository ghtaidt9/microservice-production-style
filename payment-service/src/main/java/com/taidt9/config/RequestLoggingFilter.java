package com.taidt9.config;

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

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("method={} path={} error={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getMessage(),
                    ex
            );
            throw ex;
        } finally {

            long duration = System.currentTimeMillis() - start;
            int status = response.getStatus();

            String userId = "anonymous";
            String requestId = request.getHeader("X-Request-Id");
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
            }

            try {
                var auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated()) {
                    userId = auth.getName();
                }
            } catch (Exception ignored) {}

            String method = request.getMethod();
            String path = request.getRequestURI();
            String ip = getClientIp(request);

            if (status >= 500) {
                log.error("requestId={} method={} path={} userId={} status={} duration_ms={} ip={}",
                        requestId, method, path, userId, status, duration, ip);
            } else if (status >= 400) {
                log.warn("requestId={} method={} path={} userId={} status={} duration_ms={} ip={}",
                        requestId, method, path, userId, status, duration, ip);
            } else {
                log.info("requestId={} method={} path={} userId={} status={} duration_ms={} ip={}",
                        requestId, method, path, userId, status, duration, ip);
            }
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        return xf == null ? request.getRemoteAddr() : xf.split(",")[0];
    }
}