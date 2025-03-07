package com.goingupdragon.going_up_dragon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchResponseDTO {
    private Integer courseId;
    private String courseThumbnail;
    private String courseTitle;
    private Integer instructorId;
    private String instructorNickname;
    private Double avgRate;
    private Long reviewCount;
    private Integer price;
}
