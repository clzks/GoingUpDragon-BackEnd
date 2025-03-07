package com.goingupdragon.going_up_dragon.dto.myPage;

import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.SubjectTagsDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class myPageStudentDashBoardDTO {
    private List<CourseDTO> recentCourseList;
    private List<SubjectTagsDTO> subejectTagList;
}
