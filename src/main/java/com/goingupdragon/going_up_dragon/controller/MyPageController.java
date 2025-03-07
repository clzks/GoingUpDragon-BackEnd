package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.myPage.MyPageInstructorDTO;
import com.goingupdragon.going_up_dragon.dto.myPage.MyPageStudentDTO;
import com.goingupdragon.going_up_dragon.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Tag(name = "마이페이지 API", description = "강사 및 학생의 마이페이지 정보를 제공하는 API입니다.")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(
            summary = "강사 마이페이지 조회",
            description = "강사의 ID(infoId)를 이용해 강사 마이페이지 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 강사 마이페이지 정보를 조회하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (infoId가 유효하지 않음)"),
            @ApiResponse(responseCode = "404", description = "강사 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/instructor/{infoId}")
    public ResponseEntity<MyPageInstructorDTO> getInstructorMyPage(@PathVariable Integer infoId) {
        MyPageInstructorDTO instructorDTO = myPageService.GetMyPageInstructorDTO(infoId);
        return ResponseEntity.ok(instructorDTO);
    }

    @Operation(
            summary = "강사 마이페이지 조회",
            description = "강사의 ID(infoId)를 이용해 강사 샘플 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 강사 샘플  정보를 조회하였습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (infoId가 유효하지 않음)"),
            @ApiResponse(responseCode = "404", description = "강사 샘플 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/instructor/sample/{infoId}")
    public ResponseEntity<MyPageInstructorDTO> getInstructorSampleMyPage(@PathVariable Integer infoId) {
        MyPageInstructorDTO instructorDTO = myPageService.GetMyPageInstructorSampleDTO(infoId);
        return ResponseEntity.ok(instructorDTO);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<MyPageStudentDTO> getStudentMyPage(@PathVariable("studentId") Integer studentId) {
        MyPageStudentDTO myPageStudentDTO = myPageService.GetMyPageStudentDTO(studentId);
        return ResponseEntity.ok(myPageStudentDTO);
    }
}