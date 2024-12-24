package com.trace.jachuiplan.CustomAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = ExactSizeValidator.class)
public @interface ExactSize {

    String message() default "값은 반드시 {value}자여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value(); // 문자열 길이를 지정

    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        ExactSize[] value();
    }
}