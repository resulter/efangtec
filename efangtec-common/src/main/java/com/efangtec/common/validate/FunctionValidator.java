package com.efangtec.common.validate;




import com.efangtec.common.utils.SpringContextHolder;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 调用service方法进行验证
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = {FunctionValidator.FunctionValidatorInner.class})
public @interface FunctionValidator{

    /**
     * 必须的属性
     * 显示 校验信息
     * 利用 {} 获取 属性值，参考了官方的message编写方式
     *@see org.hibernate.validator 静态资源包里面 message 编写方式
     */
    String message() default "Function方法验证错误{}";

    /**
     *类名
     */
    Class<?> className();

    /**
     * 方法名
     */
    String methodName();

    /**
     * 需要验证的名称
     */
    String[] attributes();

    /**
     * 必须的属性
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FunctionValidatorInner implements ConstraintValidator<FunctionValidator, Object> {
        private Class<?> className;
        private String methodName;
        private String[] attributes;
        @Override
        public void initialize(FunctionValidator constraintAnnotation) {
            this.className = constraintAnnotation.className();
            this.methodName = constraintAnnotation.methodName();
            this.attributes = constraintAnnotation.attributes();
        }

        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
            try {
                Map<String, Object> params = new HashMap<>();
                for (String s : attributes) {
                    params.put(s, BeanUtils.getProperty(obj, s));
                }
                Class<?> objClass = Class.forName(className.getName());
                Object bean = SpringContextHolder.getApplicationContext().getBean(className);
                Method method = objClass.getMethod(methodName, Map.class);
                method.invoke(bean, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
