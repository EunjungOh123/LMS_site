package com.example.coursesite_final.member.dto;

// 회원가입의 Form 데이터 전달에 활용할 객체

import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {

    @Size(min = 2, max = 15, message = "아이디를 2~10자 사이로 입력해주세요.")
    private String userId;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}", // 공백 허용 X
            message = "영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordCheck;

    @Size(min = 2, max = 8, message = "이름을 2~8자 사이로 입력해주세요.")
    private String name;

    @Size(min = 2, max = 8, message = "닉네임을 2~8자 사이로 입력해주세요.")
    private String nickname;
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    public SiteUser toEntity () {
        return SiteUser.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .email(email)
                .roleType(RoleType.GUEST)
                .build();
    }
}
