package com.example.coursesite_final.member.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserInputDto {
    private String userId;
    private String name;
    private String nickname;
    private String password;

    private String newPassword;

    private String zipcode;
    private String addr;
    private String addrDetail;
}
