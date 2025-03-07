package com.goingupdragon.going_up_dragon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.goingupdragon.going_up_dragon.entity.QnA;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface QnARepository extends JpaRepository<QnA, Integer> {

    @Query("SELECT q FROM QnA q " +
            "LEFT JOIN FETCH q.course c " +
            "LEFT JOIN FETCH q.user u " +
            "WHERE c.courseId = :courseId")
    List<QnA> findQnAsByCourseId(@Param("courseId") Integer courseId);


    @Query("SELECT q FROM QnA q " +
            "JOIN FETCH q.user u " +
            "JOIN FETCH q.course c " +
            "JOIN FETCH c.instructor " +
            "WHERE q.qnaId = :qnaId")
    Optional<QnA> findQnADetail(@Param("qnaId") Integer qnaId);

    @Query("SELECT COUNT(q) FROM QnA q WHERE q.user.infoId = :studentId")
    Integer countQnAsByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT COUNT(q) FROM QnA q WHERE q.parentQnA.qnaId = :qnaId")
    int countRepliesByQnAId(@Param("qnaId") Integer qnaId);

    @Query(value = """
    SELECT * FROM qna 
    WHERE course_id IN (
        SELECT course_id FROM courses 
        WHERE main_category_id = (SELECT main_category_id FROM courses WHERE course_id = :courseId)
    ) 
    AND course_id != :courseId 
    AND parent_id IS NULL  -- ✅ 부모가 없는(질문글)만 선택
    ORDER BY RAND() 
    LIMIT 5
""", nativeQuery = true)
    List<QnA> findRandomQnAsByMainCategory(@Param("courseId") Integer courseId);

    @Query(value = """
    SELECT * FROM qna 
    WHERE parent_id = :qnaId
    ORDER BY created_at ASC
""", nativeQuery = true)
    List<QnA> findRepliesByQnAId(@Param("qnaId") Integer qnaId);

    @Query("SELECT q FROM QnA q WHERE q.course.instructor.infoId = :infoId AND q.parentQnA IS NULL")
    List<QnA> findInstructorQnAs(@Param("infoId") Integer infoId);

    @Query("SELECT COUNT(q) FROM QnA q WHERE q.course.instructor.infoId = :infoId AND q.parentQnA IS NULL")
    Integer countInstructorQnAs(@Param("infoId") Integer infoId);

    @Query(value = "SELECT * FROM qna WHERE course_id IN (SELECT course_id FROM courses WHERE info_id = :infoId) AND parent_id IS NULL LIMIT 5", nativeQuery = true)
    List<QnA> findInstructorSampleQnAs(@Param("infoId") Integer infoId);

}
