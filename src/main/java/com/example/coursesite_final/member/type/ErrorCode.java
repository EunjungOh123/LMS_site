package com.example.coursesite_final.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERID_ALREADY_USE("이미 사용 중인 아이디입니다."),
    NICKNAME_ALREADY_USE("이미 사용 중인 닉네임입니다."),
    EMAIL_ALREADY_USE("이미 가입된 이메일입니다."),
    PASSWORD_PASSWORDCHECK_UNMATCH("비밀번호를 다시 입력해주세요."),
    CANNOT_FIND_PASSWORD("존재하지 않는 계정입니다. 다시 입력해 주세요."),
    USER_STATUS_STOP("현재 정지된 계정입니다.");

    private final String description;
}
