package com.goingupdragon.going_up_dragon.dto.myPage;

import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.dto.ReviewsDTO;
import com.goingupdragon.going_up_dragon.dto.SubjectTagsDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageStudentDTO {
    private Integer infoId;                       // 회원번호
    private String nickName;                      // nickName
    private String bio;                           // 자기소개
    private List<CourseDTO> courseList;           // 신청헀던 강의 리스트
    private List<ReviewsDTO> reviewList;          // 작성한 리뷰 리스트
    private List<CourseDTO> courseLikeList;       // 좋아요 누른 강의 리스트
    private List<String> subjectTagList;  // 신청한 강의의 모든 강의태그 (중복x)
    private Integer reviewCount;  // 작성한 리뷰 수
    private float rate;           // 작성한 리뷰 평균 평점
}
