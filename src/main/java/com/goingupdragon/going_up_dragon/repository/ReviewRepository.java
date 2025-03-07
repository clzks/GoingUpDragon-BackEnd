package com.goingupdragon.going_up_dragon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goingupdragon.going_up_dragon.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

    List<Review>  findAllByCourse_CourseId(Integer courseId); // 강의 리뷰 전체 조회

    @Query("SELECT COUNT(r) FROM Review r WHERE r.course.courseId = :courseId")
    Integer findReviewCount(@Param("courseId") Integer courseId);

    @Query("SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.course.courseId = :courseId")
    Float findReviewRate(@Param("courseId") Integer courseId);

    @Query(value = """
    SELECT course_id 
    FROM review 
    WHERE course_id NOT IN (
        SELECT e.course_id FROM enrollments e WHERE e.info_id = :infoId
    )
    GROUP BY course_id 
    HAVING AVG(rate) >= 3.5 
    ORDER BY RAND() 
    LIMIT :limit
    """, nativeQuery = true)
    List<Integer> findCourseIdsWithHighRating( @Param("infoId") int infoId, @Param("limit") int limit);

    @Query("SELECT r FROM Review r WHERE r.course.instructor.infoId = :infoId")
    List<Review> findInstructorReviews(@Param("infoId") Integer infoId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.course.instructor.infoId = :infoId")
    Integer countInstructorReviews(@Param("infoId") Integer infoId);

    @Query(value = "SELECT * FROM review WHERE course_id IN (SELECT course_id FROM courses WHERE info_id = :infoId) LIMIT 5", nativeQuery = true)
    List<Review> findInstructorSampleReviews(@Param("infoId") Integer infoId);

    @Query("SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.course.instructor.infoId = :infoId")
    Float findInstructorReviewRate(@Param("infoId") Integer infoId);

    @Query("SELECT r FROM Review r WHERE r.user.infoId = :infoId")
    List<Review> findReviewsByUserId(@Param("infoId") Integer infoId);

    // 학생이 작성한 리뷰 평균 평점 조회
    @Query("SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.user.infoId = :infoId")
    Float findReviewRateByUserId(@Param("infoId") Integer infoId);

    // 학생이 작성한 리뷰 개수 조회
    @Query("SELECT COUNT(r) FROM Review r WHERE r.user.infoId = :infoId")
    Integer findReviewCountByUserId(@Param("infoId") Integer infoId);
}
