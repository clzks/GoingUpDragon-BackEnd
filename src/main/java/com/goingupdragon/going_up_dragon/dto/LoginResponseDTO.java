package com.goingupdragon.going_up_dragon.dto;

import com.goingupdragon.going_up_dragon.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String token;  // 🔹 JWT 토큰 필드 추가
    private String nickname;  // 🔹 닉네임 추가
    private Enums.Role role;  // 🔹 역할 추가
    private Integer infoId; // infoId 값 가져오기
}
