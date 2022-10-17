package com.example.coursesite_final.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusType {

    /**
     * 현재 가입 요청중
     */

    REQUEST("USER_STATUS_REQ"),

    /**
     * 현재 이용중인 상태
     */

    AVAILABLE("USER_STATUS_AVAILABLE"),

    /**
     * 현재 정지된 상태
     */

    STOP("USER_STATUS_STOP"),

    /**
     * 현재 탈퇴된 회원
     */

    WITHDRAW("USER_STATUS_WITHDRAW");

    private final String description;
}
