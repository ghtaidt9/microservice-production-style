package com.taidt9.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    @Bean
    public GlobalFilter internalRequestFilter() {
        return (exchange, chain) -> {
            exchange.getRequest().mutate().header("X-INTERNAL-REQUEST", "true").build();
            return chain.filter(exchange);
        };
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        long start = System.currentTimeMillis();

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();
        String ip = getClientIp(exchange);

        if (path.startsWith("/actuator") || path.startsWith("/swagger") || path.startsWith("/favicon")) {
            return chain.filter(exchange);
        }

        return chain.filter(exchange).doOnError(ex -> log.error("method={} path={} error={}", method, path, ex.getMessage(), ex)).then(Mono.fromRunnable(() -> {

            long duration = System.currentTimeMillis() - start;
            int status = exchange.getResponse().getStatusCode() != null ? exchange.getResponse().getStatusCode().value() : 200;

            if (status >= 500) {
                log.error("method={} path={} status={} duration_ms={} ip={}", method, path, status, duration, ip);
            } else if (status >= 400) {
                log.warn("method={} path={} status={} duration_ms={} ip={}", method, path, status, duration, ip);
            } else {
                log.info("method={} path={} status={} duration_ms={} ip={}", method, path, status, duration, ip);
            }
        }));
    }

    private String getClientIp(ServerWebExchange exchange) {
        String xf = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        return xf == null ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() : xf.split(",")[0];
    }

    @Override
    public int getOrder() {
        return -1;
    }
}