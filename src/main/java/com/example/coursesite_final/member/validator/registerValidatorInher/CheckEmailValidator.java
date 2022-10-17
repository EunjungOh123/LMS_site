package com.example.coursesite_final.member.validator.registerValidatorInher;

import com.example.coursesite_final.member.dto.UserRegisterDto;
import com.example.coursesite_final.member.repository.UserRepository;
import com.example.coursesite_final.member.type.ErrorCode;
import com.example.coursesite_final.member.validator.RegisterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends RegisterValidator<UserRegisterDto> {

    private final UserRepository userRepository;
    @Override
    protected void doValidate(UserRegisterDto dto, Errors errors) {
        if(userRepository.existsByEmail(dto.toEntity().getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류",
                    ErrorCode.EMAIL_ALREADY_USE.getDescription());
        }
    }
}
