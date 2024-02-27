package com.example.smalldemo.validation;

import com.example.smalldemo.anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return false;
        }
        if(s.equals("已发布")||s.equals("草稿")){
            return true;
        }
        return false;
    }
}
