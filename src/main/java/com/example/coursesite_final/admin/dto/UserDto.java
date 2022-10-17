package com.example.coursesite_final.admin.dto;

import com.example.coursesite_final.member.entity.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    String userId;
    String name;
    String email;
    String nickname;
    LocalDateTime registeredAt;
    LocalDateTime updatedAt;

    boolean emailAuthYn;
    LocalDateTime emailAuthAt;

    String roleType;
    String userStatus;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitAt;

    private String zipcode;
    private String addr;
    private String addrDetail;

    long totalCount;
    long seq;

    private LocalDateTime lastLoginDt;

    public static UserDto fromEntity (SiteUser user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .registeredAt(user.getRegisteredAt())
                .updatedAt(user.getUpdatedAt())
                .emailAuthYn(user.isEmailAuthYn())
                .emailAuthAt(user.getEmailAuthAt())
                .resetPasswordKey(user.getResetPasswordKey())
                .resetPasswordLimitAt(user.getResetPasswordKeyLimitAt())
                .roleType(user.getRoleType().toString())
                .userStatus(user.getUserStatus())
                .zipcode(user.getZipcode())
                .addr(user.getAddr())
                .addrDetail(user.getAddrDetail())
                .build();
    }
    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return registeredAt != null ? registeredAt.format(formatter) : "";
    }
    public String getUpdatedAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return updatedAt != null ? updatedAt.format(formatter) : "";
    }
    public String getLastLoginDtText () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return lastLoginDt != null ? lastLoginDt.format(formatter) : "";
    }
}
