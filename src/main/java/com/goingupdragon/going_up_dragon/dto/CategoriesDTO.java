// 코드 가독성을 높이기 위해 DTO 사용

package com.goingupdragon.going_up_dragon.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoriesDTO {
    private Integer categoryId;      // 카테고리 ID
    private String categoryName;     // 카테고리 이름
    private Integer depth;           // 깊이 (메인 또는 서브 카테고리)
    private String categoryDesc;     // 카테고리 설명
    private List<CategoriesDTO> subCategories;  // 하위 카테고리 목록
    private List<SubjectTagsDTO> tags;          // 태그 목록
}
