package pl.kw.app.account.boundary.primary.dto;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = PeselValueValidator.class)
public @interface PeselValue {
    String message() default "{invalidPeselFormat}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
