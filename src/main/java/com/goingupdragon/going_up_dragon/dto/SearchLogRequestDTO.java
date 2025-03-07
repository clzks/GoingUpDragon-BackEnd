package com.goingupdragon.going_up_dragon.dto;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLogRequestDTO {
    private String searchQuery;  // 검색어
    private Integer userId;      // 로그인한 사용자 ID (로그인하지 않았다면 null)
}
