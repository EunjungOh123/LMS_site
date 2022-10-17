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
public class CheckUserIdValidator extends RegisterValidator<UserRegisterDto> {

    private final UserRepository userRepository;
    @Override
    protected void doValidate(UserRegisterDto dto, Errors errors) {
        if(userRepository.existsByUserId(dto.toEntity().getUserId())) {
            errors.rejectValue("userId", "아이디 중복 오류",
                    ErrorCode.USERID_ALREADY_USE.getDescription());
        }
    }
}
