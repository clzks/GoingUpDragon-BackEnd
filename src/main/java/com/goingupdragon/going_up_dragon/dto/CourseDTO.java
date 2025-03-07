package com.goingupdragon.going_up_dragon.dto;

import com.goingupdragon.going_up_dragon.enums.Enums;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDTO {
    private Integer courseId;
    private String courseTitle;
    private String courseDescription;
    private Integer instructorId;
    private String instructorName;
    private String mainCategoryName;
    private Integer mainCategoryId;
    private String subCategoryName;
    private Integer subCategoryId;
    private Enums.CourseLevel courseLevel;
    private Enums.CourseLanguage courseLanguage;
    private Integer price;
    private List<Integer> subjectTagIds;  // ✅ 태그 ID 리스트
    private List<String> subjectTagNames; // ✅ 태그 이름 리스트
    private LocalDateTime startDate;
    private Integer reviewCount;
    private Float rate;
    private Long enrollmentCount;
    private Integer duration;
}
