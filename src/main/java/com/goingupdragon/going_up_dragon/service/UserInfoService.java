package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public Optional<UserInfo> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = ((User) authentication.getPrincipal()).getUsername();
            return userInfoRepository.findByNickname(username); // 예: username으로 UserInfo를 조회
        }
        return Optional.empty();
    }

    public Optional<UserInfo> findById(Integer infoId) {
        return userInfoRepository.findById(infoId);
    }
}




