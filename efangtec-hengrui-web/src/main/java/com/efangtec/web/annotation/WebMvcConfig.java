package com.efangtec.web.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        CustomRequestMappingHandlerMapping customRequestMappingHandlerMapping = new CustomRequestMappingHandlerMapping();
        customRequestMappingHandlerMapping.setOrder(0);
        customRequestMappingHandlerMapping.setInterceptors(getInterceptors());
        return customRequestMappingHandlerMapping;
    }

   /* @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        ApiHandlerMapping handlerMapping = new ApiHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }*/
}