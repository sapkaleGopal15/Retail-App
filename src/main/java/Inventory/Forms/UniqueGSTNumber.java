package Inventory.Forms;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueGSTNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGSTNumber {
    String message() default "GST number already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


