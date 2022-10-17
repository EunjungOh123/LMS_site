package com.example.coursesite_final.member.validator.findPasswordValidatorInher;

import com.example.coursesite_final.member.dto.FindPasswordDto;
import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.repository.UserRepository;
import com.example.coursesite_final.member.type.ErrorCode;
import com.example.coursesite_final.member.validator.FindPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CheckUserInfoMatchValidator extends FindPasswordValidator<FindPasswordDto> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(FindPasswordDto dto, Errors errors) {
        Optional<SiteUser> optionalSiteUser
                = userRepository.findByUserIdAndEmailAndName(dto.getUserId(), dto.getEmail(), dto.getName());

        if(!optionalSiteUser.isPresent()) {
            errors.rejectValue("userId", "입력 오류", ErrorCode.CANNOT_FIND_PASSWORD.getDescription());
        }
    }
}
