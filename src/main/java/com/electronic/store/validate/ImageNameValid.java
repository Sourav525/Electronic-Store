package com.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImagenameValidator.class)
public @interface ImageNameValid {
    //default error mesage
    String message() default "Invalid Image Name";

    //represents a group of constrains
    Class<?>[] groups() default { };


    //additional info about annotation
    Class<? extends Payload>[] payload() default { };

}
