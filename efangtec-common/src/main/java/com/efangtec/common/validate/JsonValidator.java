package com.efangtec.common.validate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2019-01-28.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {JsonValidator.JsonValidatorInner.class})
public @interface JsonValidator {

    String message() default "json校验错误";
    String jsonName();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class JsonValidatorInner implements ConstraintValidator<JsonValidator, String> {
        private Logger logger = LoggerFactory.getLogger(JsonValidatorInner.class);
        String jsonName;
        Class<?>[] groups;
        @Override
        public void initialize(JsonValidator constraintAnnotation) {
            constraintAnnotation.payload();
            groups = constraintAnnotation.groups();
            jsonName = constraintAnnotation.jsonName();
        }
        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

            for (Class clazz : groups) {
                Object object = createObject(clazz);
                Field[] methods = clazz.getDeclaredFields();
                List<String> fieldNames = Arrays.asList(methods).stream().map(a -> a.getName()).collect(Collectors.toList());
                int index = fieldNames.indexOf(jsonName);
                if (index < 0) {
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate( "jsonschema name not exists").addConstraintViolation();
                    return false;
                }
                try {
                    String jsonPath = (String) methods[index].get(object);
                    String jsonschema = null;
                    ClassPathResource classPathResource = new ClassPathResource(jsonPath);
                    if (!classPathResource.exists()) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate( "jsonschema path  not exists").addConstraintViolation();
                        return false;
                    }
                    jsonschema = IOUtils.toString(classPathResource.getInputStream(), StandardCharsets.UTF_8);
                    JSONObject jsonObject = validataJson(jsonschema, s);  // {status:true,info:[]}
                    if (jsonObject.getBoolean("status") == false) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate( jsonObject.getJSONArray("info").toJSONString()).addConstraintViolation();
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return true;
        }

        public Object createObject(Class clazz) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return this;
                }
            });
            return enhancer.create();
        }


        private JSONObject validataJson(String schemaJsonStr, String targetJsonStr) {
            JSONObject jsonObject = new JSONObject();
            if(StringUtils.isBlank(targetJsonStr)){
                JSONArray jsonArray = new JSONArray();
                jsonArray.add("targetJsonStr is not empty");
                jsonObject.put("status", false);
                jsonObject.put("info", jsonArray);
                return jsonObject;
            }
            ProcessingReport report = null;
            try {
                JsonNode data = JsonLoader.fromString(targetJsonStr);
                JsonNode schema = JsonLoader.fromString(schemaJsonStr);
                report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Iterator<ProcessingMessage> it = report.iterator();
            JSONArray jsonArray = new JSONArray();
            while (it.hasNext()) {
                ProcessingMessage processingMessage = it.next();
                JsonNode jsonNode = processingMessage.asJson();
                JSONObject node = JSON.parseObject(jsonNode.toString());
                String message  = ("node:" +node.getString("instance")+" message: "+node.getString("message") );
                jsonArray.add(message);
            }
            if (ObjectUtils.isEmpty(jsonArray)) {
                jsonObject.put("status", true);
            } else {
                jsonObject.put("status", false);
            }
            jsonObject.put("info", jsonArray);
            return jsonObject;
        }


    }
}
