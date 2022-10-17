package com.example.coursesite_final.member.entity;

import com.example.coursesite_final.member.type.RoleType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
@Table(name = "user_info")
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true) //  Chain 형태로 이어서 원하는 set 메서드를 생성
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    private String userId;

    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private LocalDateTime emailAuthAt; // 이메일 인증 날짜
    private boolean emailAuthYn;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordKeyLimitAt;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    private String userStatus; // 이용 가능한 상태 or 정지된 상태 or 가입 요청 중인 상태

    private String zipcode;
    private String addr;
    private String addrDetail;

    private LocalDateTime lastLoginDt;
}
