package com.example.coursesite_final.member.service;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.member.dto.FindPasswordDto;
import com.example.coursesite_final.member.dto.UserInputDto;
import org.springframework.validation.Errors;

import java.util.Map;

public interface UserSettingsService {

    /**
     * 본인 인증 후 비밀번호 재설정을 위한 링크 이메일로 전송
     */
    void sendResetPasswordKey(FindPasswordDto passwordDto);

    /**
     * 입력 받은 uuid 대해서 password로 초기화
     */
    boolean resetPassword (String uuid, String password);

    /**
     * 입력 받은 uuid 값이 유효한지 확인
     */
    boolean checkResetPasswordKey (String uuid);

    /**
     * 회원 상세 정보
     */
    UserDto detail (String userId);

    /**
     * 회원 상태 변경
     */
    void updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    void updatePassword(String userId, String password);

    /**
     * 회원 가입 시 유효성 검사에 대한 에러 처리
     */
    Map<String, String> validateHandling(Errors errors);

    /**
     * 회원정보 수정
     */
    ServiceResult updateUser(UserInputDto parameter);

    /**
     * 회원 정보 페이지내 비밀번호 변경 기능
     */
    ServiceResult updateUserPassword(UserInputDto parameter);

    /**
     * 회원을 탈퇴시켜 주는 로직
     */
    ServiceResult withdraw(String userId, String password);
}
