package com.hanyang.dataportal.core.vaild;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum,Enum> {
    private ValidEnum annotation;
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }


    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value == enumValue) {
                    result = true;
                    break;
                }
            }
        }
        if(!result){
            context.buildConstraintViolationWithTemplate(this.annotation.enumClass()+" "+this.annotation.message()).addConstraintViolation();
        }
        return result;
    }
}
