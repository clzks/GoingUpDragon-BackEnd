package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.like.CourseLikeDTO;
import com.goingupdragon.going_up_dragon.dto.like.QnALikeDTO;
import com.goingupdragon.going_up_dragon.dto.like.ReviewLikeDTO;
import com.goingupdragon.going_up_dragon.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/likes")
@Tag(name = "Like API", description = "좋아요 관련 API")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // ✅ 특정 강의(courseId)에 대한 좋아요 목록 조회
    @Operation(
            summary = "Get likes for a course",
            description = "Fetches all likes associated with the specified course ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved likes"),
            @ApiResponse(responseCode = "400", description = "Invalid course ID provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<CourseLikeDTO>> getLikesForCourse(@PathVariable Integer courseId) {
        List<CourseLikeDTO> likes = likeService.getLikesForCourse(courseId);
        return ResponseEntity.ok(likes);
    }

    // ✅ 특정 리뷰(reviewId)에 대한 좋아요 목록 조회
    @Operation(
            summary = "Get likes for a review",
            description = "Fetches all likes associated with the specified review ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved likes"),
            @ApiResponse(responseCode = "400", description = "Invalid review ID provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<List<ReviewLikeDTO>> getLikesForReview(@PathVariable Integer reviewId) {
        List<ReviewLikeDTO> likes = likeService.getLikesForReview(reviewId);
        return ResponseEntity.ok(likes);
    }

    // ✅ 특정 QnA(qnaId)에 대한 좋아요 목록 조회
    @Operation(
            summary = "Get likes for a QnA",
            description = "Fetches all likes associated with the specified QnA ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved likes"),
            @ApiResponse(responseCode = "400", description = "Invalid QnA ID provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/qna/{qnaId}")
    public ResponseEntity<List<QnALikeDTO>> getLikesForQnA(@PathVariable Integer qnaId) {
        List<QnALikeDTO> likes = likeService.getLikesForQnA(qnaId);
        return ResponseEntity.ok(likes);
    }
}
