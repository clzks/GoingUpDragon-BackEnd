package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_tag_id")
    private Integer subjectTagId;  // 태그 고유 번호

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Categories category;  // 카테고리와의 관계 (Foreign Key)

    @Column(name = "subject_tag_name", length = 255, nullable = false)
    private String subjectTagName;  // 태그 이름

    @Column(name = "subject_tag_desc", length = 255, nullable = false)
    private String subjectTagDesc;  // 태그 설명
}

