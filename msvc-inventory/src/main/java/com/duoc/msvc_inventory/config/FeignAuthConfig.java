package com.duoc.msvc_inventory.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// msvc-product exige JWT en todos sus endpoints. Sin esto, la llamada Feign salia SIN token y
// recibia 401. Se reenvia el mismo "Authorization" con el que llego la peticion original.
@Configuration
public class FeignAuthConfig {

    @Bean
    public RequestInterceptor bearerTokenForwarder() {
        return template -> {
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
            if (attrs instanceof ServletRequestAttributes servletAttrs) {
                String auth = servletAttrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                if (auth != null) {
                    template.header(HttpHeaders.AUTHORIZATION, auth);
                }
            }
        };
    }
}
