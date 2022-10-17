package com.example.coursesite_final.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordCheck;


}
