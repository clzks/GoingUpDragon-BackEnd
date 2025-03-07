package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.courseId = :courseId")
    Long findEnrollmentCountForCourse(@Param("courseId") Integer courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.user.infoId = :infoId")
    List<Enrollment> findEnrollmentByStudent(@Param("infoId") Integer infoId);
}
