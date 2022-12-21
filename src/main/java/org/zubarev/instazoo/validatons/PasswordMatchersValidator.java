package org.zubarev.instazoo.validatons;

import org.zubarev.instazoo.annotations.PasswordMatchers;
import org.zubarev.instazoo.payload.request.SignUpRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchersValidator implements ConstraintValidator <PasswordMatchers,Object>{
    @Override
    public void initialize(PasswordMatchers passwordMatchers){

    }

    /**
     *
     * проверяем, совпадает ли значение полей с паролями
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        SignUpRequest userSignUpRequest= (SignUpRequest)value;
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());

    }
}
