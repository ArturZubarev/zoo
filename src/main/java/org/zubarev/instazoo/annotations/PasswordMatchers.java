package org.zubarev.instazoo.annotations;

import javax.validation.Payload;
import org.zubarev.instazoo.validatons.PasswordMatchersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
