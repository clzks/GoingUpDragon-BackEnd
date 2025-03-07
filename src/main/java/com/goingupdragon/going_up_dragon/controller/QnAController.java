package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.service.QnAService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("qna")
@RequiredArgsConstructor
@Tag(name = "QnA API", description = "Q&A 관련 API")
public class QnAController {

    private final QnAService qnaService;

    @Operation(summary = "특정 QnA 조회", description = "QnA ID를 사용하여 특정 질문을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 QnA를 찾을 수 없음")
    })
    @GetMapping("/{qnaId}")
    public ResponseEntity<QnADTO> getQnADetail(
            @Parameter(description = "조회할 QnA ID", example = "1")
            @PathVariable Integer qnaId) {
        QnADTO qnaDTO = qnaService.getQnADetail(qnaId);
        return ResponseEntity.ok(qnaDTO);
    }

    @Operation(summary = "강의별 QnA 리스트 조회", description = "강의 ID를 사용하여 해당 강의의 모든 QnA를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 강의의 QnA를 찾을 수 없음")
    })
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QnADTO>> getQnAsByCourse(
            @Parameter(description = "조회할 강의 ID", example = "5")
            @PathVariable Integer courseId) {
        List<QnADTO> qnaList = qnaService.getQnAsByCourseId(courseId);
        return ResponseEntity.ok(qnaList);
    }

    @Operation(summary = "관련 QnA 추천", description = "해당 강의의 메인 카테고리를 기반으로 랜덤 QnA 5개 추천 (현재 강의 제외)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추천 QnA 조회 성공"),
            @ApiResponse(responseCode = "404", description = "관련 QnA를 찾을 수 없음")
    })
    @GetMapping("/related/{courseId}")
    public ResponseEntity<List<QnADTO>> getRelatedQnAs(
            @Parameter(description = "기준이 되는 강의 ID", example = "10")
            @PathVariable Integer courseId) {
        List<QnADTO> relatedQnAs = qnaService.getRandomQnAsByMainCategory(courseId);
        return ResponseEntity.ok(relatedQnAs);
    }

    @GetMapping("/{qnaId}/replies")
    public ResponseEntity<List<QnADTO>> getRepliesByQnAId(@PathVariable Integer qnaId) {
        List<QnADTO> replies = qnaService.getRepliesByQnAId(qnaId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/count/instructor/{instructorId}")
    public ResponseEntity<Integer> getInstructorQnACount(@PathVariable Integer instructorId) {
        Integer count = qnaService.getInstructorQnACount(instructorId);
        return ResponseEntity.ok(count);
    }

    // ✅ 강사의 QnA 리스트 조회 API
    @GetMapping("/instructor/{infoId}")
    public ResponseEntity<List<QnADTO>> getInstructorQnAList(@PathVariable Integer infoId) {
        List<QnADTO> qnaList = qnaService.getInstructorQnAList(infoId);
        return ResponseEntity.ok(qnaList);
    }
}

