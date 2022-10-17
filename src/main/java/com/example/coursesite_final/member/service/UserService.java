package com.example.coursesite_final.member.service;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.admin.model.UserParam;
import com.example.coursesite_final.member.dto.UserRegisterDto;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {

     /**
      * 회원 가입 (유효성 검사로 중복 체크)
      */
     void registerUser (UserRegisterDto registerDto);

     /**
      * 이메일 인증키를 통해 계정을 활성화 (GUEST > USER)
      */
     boolean emailAuth(String emailAuthKey);

     /**
      * 회원 목록 리턴(관리자에서만 사용 가능)
      */
     List<UserDto> list (UserParam param);

     /**
      * 회원 가입 시 유효성 검사에 대한 에러 처리
      */
     Map<String, String> validateHandling(Errors errors);

}
