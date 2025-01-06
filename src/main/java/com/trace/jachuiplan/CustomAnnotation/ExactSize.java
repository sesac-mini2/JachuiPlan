/// 이재혁
package com.trace.jachuiplan.CustomAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExactSize.List.class)
@Documented
@Constraint(validatedBy = ExactSizeValidator.class)
public @interface ExactSize {

    String message() default "값은 반드시 {value}자여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return size the element must be equal to
     */
    int value();

    /**
     * Defines several {@link ExactSize} annotations on the same element.
     *
     * @see ExactSize
     */
    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        ExactSize[] value();
    }
}