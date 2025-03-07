package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.LikeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Service
public interface LikeTableRepository extends JpaRepository<LikeTable, Integer> {

    // ✅ 특정 강의(courseId)에 대한 좋아요 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.course.courseId = :courseId")
    List<LikeTable> findByCourse(@Param("courseId") Integer courseId);

    // ✅ 특정 리뷰(reviewId)에 대한 좋아요 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.review.reviewId = :reviewId")
    List<LikeTable> findByReview(@Param("reviewId") Integer reviewId);

    // ✅ 특정 QnA(qnaId)에 대한 좋아요 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.qna.qnaId = :qnaId")
    List<LikeTable> findByQnA(@Param("qnaId") Integer qnaId);

    @Query("SELECT COUNT(l) FROM LikeTable l WHERE l.qna.qnaId = :qnaId")
    Integer countLikesByQnAId(@Param("qnaId") Integer qnaId);


    // ✅ 특정 유저가 특정 Course에 좋아요를 눌렀는지 확인
    @Query("SELECT COUNT(l) > 0 FROM LikeTable l WHERE l.user.infoId = :infoId AND l.course.courseId = :courseId")
    boolean existCourseLike(@Param("infoId") Integer infoId, @Param("courseId") Integer courseId);

    // ✅ 특정 유저가 특정 QnA에 좋아요를 눌렀는지 확인
    @Query("SELECT COUNT(l) > 0 FROM LikeTable l WHERE l.user.infoId = :infoId AND l.qna.qnaId = :qnaId")
    boolean existQnALike(Integer infoId, Integer qnaId);

    // ✅ 특정 유저가 특정 Review에 좋아요를 눌렀는지 확인
    @Query("SELECT COUNT(l) > 0 FROM LikeTable l WHERE l.user.infoId = :infoId AND l.review.reviewId = :reviewId")
    boolean existReviewLike(Integer infoId, Integer reviewId);


    // ✅ 특정 유저가 좋아요한 QnA 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.user.infoId = :infoId AND l.qna.qnaId IS NOT NULL")
    List<LikeTable> findUserQnaLikes(Integer infoId);

    // ✅ 특정 유저가 좋아요한 Course 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.user.infoId = :infoId AND l.course.courseId IS NOT NULL")
    List<LikeTable> findUserCourseLikes(Integer infoId);

    // ✅ 특정 유저가 좋아요한 Review 목록 조회
    @Query("SELECT l FROM LikeTable l WHERE l.user.infoId = :infoId AND l.review.reviewId IS NOT NULL")
    List<LikeTable> findUserReviewLikes(Integer infoId);


    // ✅ 특정 유저의 좋아요 데이터 삭제 (QnA, Course, Review 개별 삭제)
    @Modifying
    @Query("DELETE FROM LikeTable l WHERE l.user.infoId = :infoId AND l.qna.qnaId = :qnaId")
    void deleteUserQnaLike(@Param("infoId") Integer infoId, @Param("qnaId") Integer qnaId);

    @Modifying
    @Query("DELETE FROM LikeTable l WHERE l.user.infoId = :infoId AND l.course.courseId = :courseId")
    void deleteUserCourseLike(@Param("infoId") Integer infoId, @Param("courseId") Integer courseId);

    @Modifying
    @Query("DELETE FROM LikeTable l WHERE l.user.infoId = :infoId AND l.review.reviewId = :reviewId")
    void deleteUserReviewLike(@Param("infoId") Integer infoId, @Param("reviewId") Integer reviewId);

}
