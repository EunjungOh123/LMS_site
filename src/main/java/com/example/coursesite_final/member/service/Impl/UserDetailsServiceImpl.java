package com.example.coursesite_final.member.service.Impl;

import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.exception.UserEmailNotAuthException;
import com.example.coursesite_final.member.exception.UserStopException;
import com.example.coursesite_final.member.repository.UserRepository;
import com.example.coursesite_final.member.type.RoleType;
import com.example.coursesite_final.member.type.UserStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 UserDetailsService는 Spring Security에서 유저의 정보를 가져오는 인터페이스
 기본 오버라이딩 메서드는 loadUserByUsername
 유저의 정보를 불러와서 UserDetails로 리턴 > 단점은 사용자의 여러 정보들 중에서 제한적인 내용만을 이용한다는 것
*/

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<SiteUser> optionalUser = userRepository.findByUserId(userId);

        /* userId로 SiteUser 객체를 조회
        만약 userId에 해당하는 데이터가 없을 경우에는 UsernameNotFoundException 오류
         */

        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
        SiteUser user = optionalUser.get();

        if(UserStatusType.REQUEST.toString().equals(user.getUserStatus())) {
            throw new UserEmailNotAuthException("이메일 활성화 이후 로그인 해주세요.");
        }

        if(UserStatusType.STOP.toString().equals(user.getUserStatus())) {
            throw new UserStopException("정지된 회원입니다. 관리자에게 문의해주세요.");
        }

        if(UserStatusType.WITHDRAW.toString().equals(user.getUserStatus())) {
            throw new UserStopException("탈퇴한 회원입니다. 관리자에게 문의해주세요.");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        /* RoleType에 따라 권한을 부여*/
        if(user.getRoleType() == RoleType.GUEST) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(RoleType.GUEST.getValue()));
        } else if (user.getRoleType() == RoleType.ADMIN) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(RoleType.ADMIN.getValue()));
        } else {
            grantedAuthorityList.add(new SimpleGrantedAuthority(RoleType.USER.getValue()));
        }
        // 스프링 시큐리티의 User 객체를 생성하여 리턴
        return new User(user.getUserId(), user.getPassword(), grantedAuthorityList);
    }
}
