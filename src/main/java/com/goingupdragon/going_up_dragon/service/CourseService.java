package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.CourseDTO;
import com.goingupdragon.going_up_dragon.dto.CourseSearchResponseDTO;
import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.enums.Enums;
import com.goingupdragon.going_up_dragon.repository.CourseRepository;
import com.goingupdragon.going_up_dragon.repository.EnrollmentRepository;
import com.goingupdragon.going_up_dragon.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, ReviewRepository reviewRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository; this.reviewRepository = reviewRepository; this.enrollmentRepository = enrollmentRepository;}

    public CourseDTO getCourse(Integer courseId) {
        Course course = courseRepository.findCourse(courseId);

        return convertToDTO(course);
    }

    public List<CourseDTO> getCourseList(Integer infoId) {
        List<Course> courseList =  courseRepository.findInstructorCourses(infoId);

        return courseList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CourseDTO> getTopRatedCourses(int infoId, int limit) {
        // 1️⃣ 평균 평점 3.5 이상인 강의 ID를 랜덤하게 limit 개수만큼 가져옴
        List<Integer> courseIds = reviewRepository.findCourseIdsWithHighRating(infoId, limit);

        if (courseIds.isEmpty()) {
            return Collections.emptyList(); // ✅ 강의가 없으면 빈 리스트 반환
        }

        // 2️⃣ 해당 강의 ID 리스트로 강의 정보 조회
        List<Course> courses = courseRepository.findCoursesByIds(courseIds);

        // 3️⃣ DTO로 변환하여 반환
        return courses.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getFreeCoursesNotEnrolled(int infoId, int limit){
        List<Course> courseList = courseRepository.findFreeCoursesExcludingEnrolled(infoId, limit);

        return courseList.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());
    }


    public List<CourseDTO> getEasyCoursesNotEnrolled(Enums.CourseLevel courseLevel, int infoId, int limit){
        List<Course> courseList = courseRepository.findCoursesByLevel(courseLevel,infoId, limit);

        return courseList.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());
    }

    public Page<CourseDTO> getCoursesByFiltersAndSort(int mainCategory, int subCategory, Enums.CourseLevel level, Enums.CourseLanguage language, String timeFilter, Collection<Integer> selectedTags, String sortBy, int size, int offset) {

        List<Course> courseList = courseRepository.findCoursesByFiltersAndSort(mainCategory, subCategory, level, language, timeFilter, selectedTags, sortBy, size, offset);

        List<CourseDTO> courseDTOList = courseList.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());

        // 총 개수 조회
        int totalCourses = (int) courseRepository.countCoursesByFilters(mainCategory, subCategory, level, language, timeFilter, selectedTags);

        // Page 객체로 변환
        return new PageImpl<>(courseDTOList, PageRequest.of(offset / size, size), totalCourses);
    }

    public Integer getInstructorCoursesCount(int infoId){

        return courseRepository.countInstructorCourses(infoId);
    }

    public List<CourseDTO> getCourseListByInfoId(Integer infoId){
        List<Course> courseList = courseRepository.findCoursesByStudentId(infoId);

        return courseList.stream()
                .map(this::convertToDTO) // ✅ DTO 변환
                .collect(Collectors.toList());
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

    public List<CourseSearchResponseDTO> searchCourses(String searchQuery) {
        return courseRepository.searchCourses(searchQuery);
    }

}
