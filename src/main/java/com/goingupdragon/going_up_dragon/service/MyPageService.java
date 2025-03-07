package com.goingupdragon.going_up_dragon.service;


import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.dto.ReviewsDTO;
import com.goingupdragon.going_up_dragon.dto.myPage.MyPageInstructorDTO;
import com.goingupdragon.going_up_dragon.dto.myPage.MyPageStudentDTO;
import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.entity.Review;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.enums.Enums;
import com.goingupdragon.going_up_dragon.repository.*;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {

    private final CourseRepository courseRepository;
    private final QnARepository qnARepository;
    private final ReviewRepository reviewRepository;
    private final UserInfoRepository userInfoRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LikeTableRepository likeTableRepository;

    public MyPageService(CourseRepository courseRepository, QnARepository qnARepository, ReviewRepository reviewRepository, UserInfoRepository userInfoRepository, EnrollmentRepository enrollmentRepository, LikeTableRepository likeTableRepository){
        this.courseRepository = courseRepository;
        this.reviewRepository = reviewRepository;
        this.qnARepository = qnARepository;
        this.userInfoRepository = userInfoRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.likeTableRepository = likeTableRepository;
    }

    public MyPageInstructorDTO GetMyPageInstructorSampleDTO(Integer infoId)
    {
        Enums.Role role = userInfoRepository.findByInfoId(infoId).getRole();

        if (role == Enums.Role.instructor) {

            UserInfo instructor = userInfoRepository.findById(infoId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));

            List<CourseDTO> courseList = courseRepository.findInstructorSampleCourses(infoId)
                    .stream()
                    .map(course -> new CourseDTO( // Course → CourseDTO 변환
                            course.getCourseId(),
                            course.getCourseTitle(),
                            course.getShortDescription(),
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            course.getMainCategory().getCategoryName(),
                            course.getMainCategory().getCategoryId(),
                            course.getSubCategory().getCategoryName(),
                            course.getSubCategory().getCategoryId(),
                            course.getLevel(),
                            course.getLanguage(),
                            course.getPrice(),
                            List.of(), // subjectTagIds (필요하면 설정)
                            List.of(), // subjectTagNames (필요하면 설정)
                            course.getStartDate(),
                            reviewRepository.findReviewCount(course.getCourseId()), // 리뷰 개수
                            reviewRepository.findReviewRate(course.getCourseId()), // 평균 평점
                            enrollmentRepository.findEnrollmentCountForCourse(course.getCourseId()), // 수강생 수
                            course.getDuration()
                    ))
                    .collect(Collectors.toList());

            // 3. 강사의 강의에 달린 리뷰 가져오기
            List<ReviewsDTO> reviewList = reviewRepository.findInstructorSampleReviews(infoId)
                    .stream()
                    .map(review -> new ReviewsDTO(
                            review.getReviewId(),
                            review.getUser().getInfoId(),
                            review.getUser().getNickname(),
                            review.getRate(),
                            review.getComment(),
                            review.getReply(),
                            review.getCreatedAt().toString(),
                            review.getReplyCreateAt() != null ? review.getReplyCreateAt().toString() : null
                    ))
                    .collect(Collectors.toList());

            List<QnADTO> qnaList = qnARepository.findInstructorSampleQnAs(infoId)
                    .stream()
                    .map(qna -> new QnADTO(
                            qna.getQnaId(),
                            qna.getUser().getInfoId(),
                            qna.getUser().getNickname(),
                            qnARepository.countQnAsByStudentId(qna.getUser().getInfoId()), // 학생이 작성한 QnA 개수
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            qna.getCourse().getCourseId(),
                            qna.getCourse().getCourseTitle(),
                            qna.getCourse().getShortDescription(),
                            qna.getTitle(),
                            qna.getContent(),
                            qna.getCustomTag1(),
                            qna.getCustomTag2(),
                            qna.getCustomTag3(),
                            qna.getCreatedAt(),
                            qna.getViewCount(),
                            likeTableRepository.countLikesByQnAId(qna.getQnaId()),
                            qnARepository.countRepliesByQnAId(qna.getQnaId()) // QnA의 답변 개수
                    ))
                    .collect(Collectors.toList());

            // 5. DTO 생성
            return  new MyPageInstructorDTO(
                    instructor.getInfoId(),
                    instructor.getNickname(),
                    instructor.getBio(),
                    courseList,
                    reviewList,
                    qnaList
            );
        }

        return null;
    }

    public MyPageInstructorDTO GetMyPageInstructorDTO(Integer infoId)
    {
        Enums.Role role = userInfoRepository.findByInfoId(infoId).getRole();

        if (role == Enums.Role.instructor) {
            // 1. 기본 정보 가져오기
            UserInfo instructor = userInfoRepository.findById(infoId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));

            // 2. 강사가 개설한 강의 목록 가져오기
            List<CourseDTO> courseList = courseRepository.findInstructorCourses(infoId)
                    .stream()
                    .map(course -> new CourseDTO( // Course → CourseDTO 변환
                            course.getCourseId(),
                            course.getCourseTitle(),
                            course.getShortDescription(),
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            course.getMainCategory().getCategoryName(),
                            course.getMainCategory().getCategoryId(),
                            course.getSubCategory().getCategoryName(),
                            course.getSubCategory().getCategoryId(),
                            course.getLevel(),
                            course.getLanguage(),
                            course.getPrice(),
                            List.of(), // subjectTagIds (필요하면 설정)
                            List.of(), // subjectTagNames (필요하면 설정)
                            course.getStartDate(),
                            reviewRepository.findReviewCount(course.getCourseId()), // 리뷰 개수
                            reviewRepository.findReviewRate(course.getCourseId()), // 평균 평점
                            enrollmentRepository.findEnrollmentCountForCourse(course.getCourseId()), // 수강생 수
                            course.getDuration()
                    ))
                    .collect(Collectors.toList());

            // 3. 강사의 강의에 달린 리뷰 가져오기
            List<ReviewsDTO> reviewList = reviewRepository.findInstructorReviews(infoId)
                    .stream()
                    .map(review -> new ReviewsDTO(
                            review.getReviewId(),
                            review.getUser().getInfoId(),
                            review.getUser().getNickname(),
                            review.getRate(),
                            review.getComment(),
                            review.getReply(),
                            review.getCreatedAt().toString(),
                            review.getReplyCreateAt() != null ? review.getReplyCreateAt().toString() : null
                    ))
                    .collect(Collectors.toList());

            // 4. 강사의 강의에 달린 QnA 가져오기
            List<QnADTO> qnaList = qnARepository.findInstructorQnAs(infoId)
                    .stream()
                    .map(qna -> new QnADTO(
                            qna.getQnaId(),
                            qna.getUser().getInfoId(),
                            qna.getUser().getNickname(),
                            qnARepository.countQnAsByStudentId(qna.getUser().getInfoId()), // 학생이 작성한 QnA 개수
                            instructor.getInfoId(),
                            instructor.getNickname(),
                            qna.getCourse().getCourseId(),
                            qna.getCourse().getCourseTitle(),
                            qna.getCourse().getShortDescription(),
                            qna.getTitle(),
                            qna.getContent(),
                            qna.getCustomTag1(),
                            qna.getCustomTag2(),
                            qna.getCustomTag3(),
                            qna.getCreatedAt(),
                            qna.getViewCount(),
                            likeTableRepository.countLikesByQnAId(qna.getQnaId()),
                            qnARepository.countRepliesByQnAId(qna.getQnaId()) // QnA의 답변 개수
                    ))
                    .collect(Collectors.toList());

            // 5. DTO 생성
            return  new MyPageInstructorDTO(
                    instructor.getInfoId(),
                    instructor.getNickname(),
                    instructor.getBio(),
                    courseList,
                    reviewList,
                    qnaList
            );
        }

        return null;
    }

    public MyPageStudentDTO GetMyPageStudentDTO(Integer infoId){

        List<Course> courses = courseRepository.findCoursesByStudentId(infoId);

        List<CourseDTO> courseList = courses.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());

        List<Course> likeCourses = courseRepository.findLikedCoursesByUserId(infoId);

        List<CourseDTO> likeCourseList = likeCourses.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());

        List<Review> reviews = reviewRepository.findReviewsByUserId(infoId);

        List<ReviewsDTO> reviewList = reviews.stream().map(review -> new ReviewsDTO(
                review.getReviewId(),
                review.getUser().getInfoId(),  // ✅ 회원 info_id 추가
                review.getUser().getNickname(),  // ✅ 회원 닉네임 추가
                review.getRate(),
                review.getComment(),
                review.getReply(),
                review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), // ✅ 시간 제거
                review.getReplyCreateAt() != null ? review.getReplyCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null // ✅ 시간 제거 + null 체크
        )).collect(Collectors.toList());

        return new MyPageStudentDTO(
                infoId,
                userInfoRepository.findNicknameByInfoId(infoId),
                userInfoRepository.findBioByInfoId(infoId),
                courseList,
                reviewList,   // 리뷰리스트
                likeCourseList,
                courseRepository.findDistinctSubjectTagNamesByUserId(infoId),   // subjectList
                reviewRepository.findReviewCountByUserId(infoId),   // reviewCount
                reviewRepository.findReviewRateByUserId(infoId)      // rate
        );
    }

    public CourseDTO convertToDTO(Course course){

        Float averageRating = reviewRepository.findReviewRate(course.getCourseId());
        Integer reviewCount = reviewRepository.findReviewCount(course.getCourseId());
        Long count = enrollmentRepository.findEnrollmentCountForCourse(course.getCourseId());

        return new CourseDTO(course.getCourseId(),
                course.getCourseTitle(),
                course.getShortDescription(),
                course.getInstructor().getInfoId(),
                course.getInstructor().getNickname(),
                course.getMainCategory().getCategoryName(),
                course.getMainCategory().getCategoryId(),
                course.getSubCategory().getCategoryName(),
                course.getSubCategory().getCategoryId(),
                course.getLevel(),
                course.getLanguage(),
                course.getPrice(),
                course.getSubjectTagIds(),
                course.getSubjectTagNames(),
                course.getStartDate(),
                reviewCount != null ? reviewCount : 0, // ✅ `null` 방지
                averageRating = (averageRating != null)
                        ? (float) ((int) (averageRating * 10) / 10.0)  // 소수점 첫째 자리만 유지
                        : 0.0f,
                count != null ? count : 0,
                course.getDuration()
        );
    }
}
