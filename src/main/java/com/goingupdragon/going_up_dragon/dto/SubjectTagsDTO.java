// 코드 가독성을 높이기 위해 DTO 사용

package com.goingupdragon.going_up_dragon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectTagsDTO {
    private Integer subjectTagId;    // 태그 ID
    private String subjectTagName;   // 태그 이름
    private String subjectTagDesc;   // 태그 설명
}
