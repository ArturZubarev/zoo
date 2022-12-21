package org.zubarev.instazoo.annotations;

import jakarta.validation.Payload;
import org.zubarev.instazoo.validatons.PasswordMatchersValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchersValidator.class)
@Documented
public @interface PasswordMatchers {
    String message() default "Password do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
