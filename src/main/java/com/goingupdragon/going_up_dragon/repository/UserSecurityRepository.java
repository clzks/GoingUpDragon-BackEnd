package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
    // 이메일을 통해 사용자를 조회하는 메소드
    Optional<UserSecurity> findByEmail(String email);
}