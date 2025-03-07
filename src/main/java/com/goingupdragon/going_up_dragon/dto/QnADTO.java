package com.goingupdragon.going_up_dragon.dto;

import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnADTO {
    private Integer qnaId;
    private Integer studentId;
    private String studentName;
    private Integer studentQnACount;
    private Integer InstructorId;
    private String InstructorName;
    private Integer courseId;
    private String courseTitle;
    private String courseDesc;
    private String title;
    private String main;
    private Integer tag1;
    private Integer tag2;
    private Integer tag3;
    private LocalDateTime createAt;
    private Integer viewCount;
    private Integer qnaLike;
    private Integer replyCount;
}
