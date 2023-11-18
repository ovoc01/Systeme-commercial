package org.syscom.module_achat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.syscom.module_achat.configuration.interceptor.SessionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    private final SessionInterceptor sessionInterceptor;
    public WebMvcConfig(SessionInterceptor sessionInterceptor){
        this.sessionInterceptor = sessionInterceptor;
    }

     @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the SessionInterceptor for all paths under /home
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/home/**");
    }
}
