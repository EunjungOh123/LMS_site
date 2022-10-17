package com.example.coursesite_final.member.service.Impl;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.member.dto.FindPasswordDto;
import com.example.coursesite_final.member.dto.UserInputDto;
import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.exception.ResetPasswordException;
import com.example.coursesite_final.member.mail.MailSendService;
import com.example.coursesite_final.member.repository.UserRepository;
import com.example.coursesite_final.member.service.UserSettingsService;
import com.example.coursesite_final.member.type.UserStatusType;
import com.example.coursesite_final.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/* 비밀번호 변경 위해서 인증키 담긴 메일 보내기 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserSettingsServiceImpl implements UserSettingsService {
    private final MailSendService mailSendService;
    private final UserRepository userRepository;

    @Override
    public void sendResetPasswordKey(FindPasswordDto passwordDto) {

        String userId = passwordDto.getUserId();
        String email = passwordDto.getEmail();
        String resetPasswordKey = UUID.randomUUID().toString();

        String subject = "[회원 비밀번호 분실] 비밀번호 재설정을 위한 인증 완료해주세요.";
        String text = mailSendService.resetPasswordTextMessage(userId, resetPasswordKey);
        mailSendService.sendMail(email, subject, text);
        // 이미 form 입력 받을 때 유효성 검사 진행했으므로 isPresent() 사용 안함
        SiteUser user = userRepository.findByUserIdAndEmailAndName
                (passwordDto.getUserId(), passwordDto.getEmail(), passwordDto.getName()).get();


        user.setResetPasswordKey(resetPasswordKey)
                .setResetPasswordKeyLimitAt(LocalDateTime.now().plusDays(1));

        userRepository.save(user);
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<SiteUser> optionalUser = userRepository.findByResetPasswordKey(uuid);
        if (!optionalUser.isPresent()) {
            throw new ResetPasswordException("일치하는 정보가 없습니다.");
        }

        SiteUser user = optionalUser.get();

        if (user.getResetPasswordKeyLimitAt() == null) {
            throw new ResetPasswordException("유효한 날짜가 아닙니다.");
        } else if (user.getResetPasswordKeyLimitAt().isBefore(LocalDateTime.now())) {
            throw new ResetPasswordException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(encPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean checkResetPasswordKey(String uuid) {
        Optional<SiteUser> optionalUser = userRepository.findByResetPasswordKey(uuid);
        if (!optionalUser.isPresent()) {
            return false;
        }
        SiteUser user = optionalUser.get();

        if (user.getResetPasswordKeyLimitAt() == null) {
            return false;
        }
        if (user.getResetPasswordKeyLimitAt().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto detail(String userId) {
        Optional<SiteUser> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            return null;
        }
        SiteUser user = optionalUser.get();
        return UserDto.fromEntity(user);
    }

    @Override
    public void updateStatus(String userId, String userStatus) {
        Optional<SiteUser> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        SiteUser user = optionalUser.get();
        user.setUserStatus(userStatus);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String userId, String password) {
        Optional<SiteUser> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        SiteUser user = optionalUser.get();
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(encPassword);
        userRepository.save(user);
    }

    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        /* 실패 시 message 값들을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = "valid_" + error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Override
    public ServiceResult updateUser(UserInputDto parameter) {
        String userId = parameter.getUserId();

        Optional<SiteUser> optionalSiteUser = userRepository.findByUserId(userId);
        if (!optionalSiteUser.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        SiteUser user = optionalSiteUser.get();

        user.setNickname((parameter.getNickname()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setZipcode(parameter.getZipcode());
        user.setAddr(parameter.getAddr());
        user.setAddrDetail(parameter.getAddrDetail());
        userRepository.save(user);

        return new ServiceResult();
    }

    @Override
    public ServiceResult updateUserPassword(UserInputDto parameter) {
        String userId = parameter.getUserId();

        Optional<SiteUser> optionalMember = userRepository.findByUserId(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        SiteUser user = optionalMember.get();

        if (!PasswordUtils.equals(parameter.getPassword(), user.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());

        user.setPassword(encPassword);

        userRepository.save(user);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {
        Optional<SiteUser> optionalMember = userRepository.findByUserId(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        SiteUser user = optionalMember.get();

        if (!PasswordUtils.equals(password, user.getPassword())) {
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        user.setName("삭제 회원");
        user.setNickname("");
        user.setPassword("");
        user.setRegisteredAt(null);
        user.setUpdatedAt(null);
        user.setEmailAuthYn(false);
        user.setEmailAuthAt(null);
        user.setEmailAuthKey("");
        user.setResetPasswordKey("");
        user.setResetPasswordKeyLimitAt(null);
        user.setUserStatus(UserStatusType.WITHDRAW.toString());
        user.setZipcode("");
        user.setAddr("");
        user.setAddrDetail("");
        userRepository.save(user);

        return new ServiceResult();
    }
}
