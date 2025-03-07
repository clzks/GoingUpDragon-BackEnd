package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByNickname(String nickname); // nickName ❌ → nickname ✅
    Optional<UserInfo> findByUser(UserSecurity userSecurity);
    UserInfo findByInfoId(Integer infoId);

    @Query("SELECT u.nickname FROM UserInfo u WHERE u.infoId = :infoId")
    String findNicknameByInfoId(@Param("infoId") Integer infoId);

    @Query("SELECT u.bio FROM UserInfo u WHERE u.infoId = :infoId")
    String findBioByInfoId(@Param("infoId") Integer infoId);

}

