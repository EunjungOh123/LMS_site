package com.example.coursesite_final.member.service.Impl;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.admin.mapper.UserMapper;
import com.example.coursesite_final.admin.model.UserParam;
import com.example.coursesite_final.member.dto.UserRegisterDto;
import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.mail.MailSendService;
import com.example.coursesite_final.member.repository.UserRepository;
import com.example.coursesite_final.member.service.UserService;
import com.example.coursesite_final.member.type.RoleType;
import com.example.coursesite_final.member.type.UserStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailSendService mailSendService;
    private final UserMapper userMapper;

    @Override
    public void registerUser(UserRegisterDto registerDto) {
        SiteUser user = registerDto.toEntity();
        String encPassword = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt());
        user.setEmailAuthYn(false)
                .setPassword(encPassword)
                .setEmailAuthKey(UUID.randomUUID().toString())
                .setRegisteredAt(LocalDateTime.now())
                .setUserStatus(UserStatusType.REQUEST.toString());

        userRepository.save(user);

        String text = mailSendService.createRegisterTextMessage(registerDto.getUserId(), user.getEmailAuthKey());
        String subject = "[회원 가입] 이메일 인증을 완료해주세요.";
        mailSendService.sendMail(registerDto.getEmail(), subject, text);
    }

    @Override
    public boolean emailAuth(String emailAuthKey) {
        Optional<SiteUser> optionalUser = userRepository.findByEmailAuthKey(emailAuthKey);
        if (!optionalUser.isPresent()) {
            return false;
        }
        SiteUser user = optionalUser.get();

        if(user.isEmailAuthYn()) { // 이미 활성화가 된 상태라면
            return false;
        }

        user.setEmailAuthYn(true).
                setEmailAuthAt(LocalDateTime.now())
                .setRoleType(RoleType.USER)
                .setUserStatus(UserStatusType.AVAILABLE.toString());

        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserDto> list(UserParam param) {
        long totalCount = userMapper.selectListCount(param);
        List<UserDto> list = userMapper.selectList(param);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for(UserDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }
        return list;
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
}
