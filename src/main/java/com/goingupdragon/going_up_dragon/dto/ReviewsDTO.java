package com.goingupdragon.going_up_dragon.dto;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewsDTO {
    private Integer reviewId;
    private Integer infoId;  // ✅ 회원 ID 추가
    private String nickName; // ✅ 회원 닉네임 추가
    private float rate;
    private String comment;
    private String reply;
    private String createdAt;
    private String replyCreatedAt;
}
