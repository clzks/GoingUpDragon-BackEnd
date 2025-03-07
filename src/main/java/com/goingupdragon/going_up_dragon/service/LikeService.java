package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.like.CourseLikeDTO;
import com.goingupdragon.going_up_dragon.dto.like.QnALikeDTO;
import com.goingupdragon.going_up_dragon.dto.like.ReviewLikeDTO;
import com.goingupdragon.going_up_dragon.repository.LikeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeTableRepository likeTableRepository;

    // 생성자를 추가하여 의존성 주입
    @Autowired
    public LikeService(LikeTableRepository likeTableRepository) {
        this.likeTableRepository = likeTableRepository;
    }

    public List<CourseLikeDTO> getLikesForCourse(Integer courseId) {
        return likeTableRepository.findByCourse(courseId).stream()
                .map(like -> new CourseLikeDTO(like.getLikeId(), like.getUser().getInfoId(), like.getCourse().getCourseId()))
                .collect(Collectors.toList());
    }

    public List<ReviewLikeDTO> getLikesForReview(Integer reviewId) {
        return likeTableRepository.findByReview(reviewId).stream()
                .map(like -> new ReviewLikeDTO(like.getLikeId(), like.getUser().getInfoId(), like.getReview().getReviewId()))
                .collect(Collectors.toList());
    }

    public List<QnALikeDTO> getLikesForQnA(Integer qnaId) {
        return likeTableRepository.findByQnA(qnaId).stream()
                .map(like -> new QnALikeDTO(like.getLikeId(), like.getUser().getInfoId(), like.getQna().getQnaId()))
                .collect(Collectors.toList());
    }
}
