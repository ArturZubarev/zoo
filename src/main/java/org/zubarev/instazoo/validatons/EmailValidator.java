package org.zubarev.instazoo.validatons;

import org.zubarev.instazoo.annotations.ValidEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс описывает объект, проводящий валидацию адреса э. почты
 */

public class EmailValidator implements ConstraintValidator<ValidEmail,String> {

    public static final String EMAIL_PATTERN= "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return (validateEmail(email));
    }

    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }
    private boolean validateEmail(String email){
        Pattern pattern=Pattern.compile(EMAIL_PATTERN);
        /**
         * Матчер осуществляет принимает regex и проверяет соответствие входящей строки этому regex
         */
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
}
