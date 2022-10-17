package com.example.coursesite_final.member.validator;

/* 중복 검사를 위한 Validator(객체 검증용 인터페이스) 구현한 추상 클래스 */

import com.example.coursesite_final.member.dto.UserRegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class RegisterValidator<T> implements Validator {
    // 인자로 넘어온 클래스, 즉 검증해야 되는 인스턴스의 클래스가 해당 validator가 검증할 수 있는 클래스인지 판단하는 메서드
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDto.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked") // 비검사 경고: 컴파일러의 경고를 제거하기 위해 사용
    @Override
    public void validate(Object target, Errors errors) { // 실질적인 검증 작업
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            throw e;
        }
    }
    protected abstract void doValidate(final T dto, final Errors errors); // 검증 로직이 들어갈 메서드
}
