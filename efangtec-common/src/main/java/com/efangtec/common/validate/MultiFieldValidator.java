package com.efangtec.common.validate;


import org.apache.commons.beanutils.BeanUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 多个字段关联验证
 * Created by Administrator on 2019-01-28.
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = {MultiFieldValidator.MultiFieldValidatorInner.class})
public @interface MultiFieldValidator {
    String message() default "MultiFieldValidator";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    class  MultiFieldValidatorInner implements ConstraintValidator<MultiFieldValidator, Object> {


        @Override
        public void initialize(MultiFieldValidator multiFieldValidator) {

        }

        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
            return false;
        }
    }
}
