package com.efangtec.web.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Slf4j
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        // 扫描类上的 @drugsType
        DrugsType drugsType = AnnotationUtils.findAnnotation(handlerType, DrugsType.class);
        return createRequestCondition(drugsType);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        // 扫描方法上的 @drugsType
        DrugsType drugsType = AnnotationUtils.findAnnotation(method, DrugsType.class);
        return createRequestCondition(drugsType);
    }

    private RequestCondition<DrugsCondition> createRequestCondition(DrugsType drugsType) {
        return drugsType == null ? null : new DrugsCondition(drugsType.type());
    }

}