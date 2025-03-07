package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.CategoriesDTO;
import com.goingupdragon.going_up_dragon.entity.Categories;
import com.goingupdragon.going_up_dragon.service.CategoryService;
import com.goingupdragon.going_up_dragon.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 생성자 주입
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // swagger
    @Operation(
            summary = "Get categories with tags by dept",
            description = "Fetch categories and tags based on the department number (dept 1 or dept 2)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories and tags"),
            @ApiResponse(responseCode = "400", description = "Invalid department number"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

//    @GetMapping("/{depth}")
//    public ResponseEntity<?> getCategoriesWithTags(@PathVariable Integer depth) {
//        // 입력값 검증 (1과 2만 허용)
//        if (depth == null || (depth != 1 && depth != 2)) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Invalid department number. Please provide 1 (main category) or 2 (sub category).");
//        }
//
//        try {
//            // 카테고리 및 태그 조회
//            List<Categories> categories = categoryService.getCategoriesWithTags(depth);
//            return ResponseEntity.ok(categories);
//        } catch (Exception e) {
//            // 예외 처리
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while fetching categories: " + e.getMessage());
//        }
//    }

    // 메인 카테고리 서브카테고리 태그 조회
    @GetMapping("/{depth}")
    public ResponseEntity<?> getCategoriesWithTags(@PathVariable Integer depth) {
        if (depth == null || (depth != 1 && depth != 2)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid department number. Please provide 1 (main category) or 2 (sub category).");
        }

        try {
            // 카테고리 및 태그 조회
            List<Categories> categories = categoryService.getCategoriesWithTags(depth);

            // 엔티티를 DTO로 변환
            List<CategoriesDTO> categoriesDTO = categories.stream()
                    .map(DtoMapper::toCategoriesDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoriesDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching categories: " + e.getMessage());
        }
    }


}
