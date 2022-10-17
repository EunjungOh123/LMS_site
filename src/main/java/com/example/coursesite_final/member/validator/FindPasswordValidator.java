package com.example.coursesite_final.member.validator;

import com.example.coursesite_final.member.dto.FindPasswordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class FindPasswordValidator<T> implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FindPasswordDto.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            throw e;
        }
    }
    protected abstract void doValidate(final T dto, final Errors errors); // 검증 로직이 들어갈 메서드
}
