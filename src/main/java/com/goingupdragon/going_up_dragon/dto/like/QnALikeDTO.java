package com.goingupdragon.going_up_dragon.dto.like;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnALikeDTO {
    private Integer likeId;
    private Integer infoId;
    private Integer qnaId;
}