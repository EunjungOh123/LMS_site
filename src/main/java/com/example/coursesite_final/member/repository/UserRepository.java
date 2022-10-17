package com.example.coursesite_final.member.repository;

import com.example.coursesite_final.member.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByUserId (String userId);
    boolean existsByNickname (String nickname);
    boolean existsByEmail (String email);
    Optional<SiteUser> findByEmailAuthKey (String emailAuthKey);
    Optional<SiteUser> findByUserId (String userId);
    Optional<SiteUser> findByUserIdAndEmailAndName (String userId, String email, String name);
    Optional<SiteUser> findByResetPasswordKey(String resetPasswordKey);
}
