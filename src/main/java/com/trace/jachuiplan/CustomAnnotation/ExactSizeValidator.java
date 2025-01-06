/// 이재혁
package com.trace.jachuiplan.CustomAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExactSizeValidator implements ConstraintValidator<ExactSize, String> {

    private int exactSize;

    @Override
    public void initialize(ExactSize constraintAnnotation) {
        this.exactSize = constraintAnnotation.value(); // 어노테이션의 값 초기화
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null은 유효한 값으로 간주 (null 검증은 @NotNull로 처리)
        if (value == null) {
            return true;
        }
        return value.length() == exactSize;
    }
}