package com.goingupdragon.going_up_dragon.dto.like;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseLikeDTO {
    private Integer likeId;
    private Integer infoId;
    private Integer courseId;
}