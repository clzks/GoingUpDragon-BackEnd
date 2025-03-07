package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import com.goingupdragon.going_up_dragon.enums.Enums;
import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
import com.goingupdragon.going_up_dragon.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SignupService {

    private final UserSecurityRepository userSecurityRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder를 주입

    @Autowired
    public SignupService(UserSecurityRepository userSecurityRepository,
                         UserInfoRepository userInfoRepository,
                         PasswordEncoder passwordEncoder) { // PasswordEncoder를 생성자 주입
        this.userSecurityRepository = userSecurityRepository;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder; // PasswordEncoder 초기화
    }

    @Transactional
    public void registerUser(String email, String password, String phoneNumber) {
        // 1. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화

        // 2. user_security 테이블에 데이터 삽입
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setEmail(email);
        userSecurity.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        userSecurity.setSignupType(Enums.SignupType.normal); // ✅ Enum 사용
        userSecurity.setJoinDate(LocalDateTime.now());
        userSecurity.setStatus(Enums.UserStatus.active);
        userSecurity.setLastLogin(null);
        userSecurity.setPhoneNumber(phoneNumber);

        userSecurity = userSecurityRepository.save(userSecurity);

        // 3. user_info 테이블에 데이터 삽입
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(userSecurity);
        userInfo.setNickname(generateNickname());
        userInfo.setBio("소개를 입력해주세요");
        userInfo.setProfileImage("https://example.com/profile3.jpg");
        userInfo.setRole(Enums.Role.student);
        userInfo.setProfileAddress(null);

        userInfoRepository.save(userInfo);
    }

    private String generateNickname() {
        int randomNum = new Random().nextInt(9000) + 1000; // 1000~9999
        return "코딩하는 학습자" + randomNum;
    }
}