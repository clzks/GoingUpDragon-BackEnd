package com.goingupdragon.going_up_dragon.entity;

import com.goingupdragon.going_up_dragon.enums.Enums;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(nullable = false, length = 255)
    private String courseTitle;

    @ManyToOne
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo instructor; // 강사 정보 (외래키)

    @Column(nullable = false)
    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id", insertable = false, updatable = false)
    private Categories mainCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", insertable = false, updatable = false)
    private Categories subCategory;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.CourseLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.CourseLanguage language;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_tag1", insertable = false, updatable = false)
    private SubjectTags subjectTag1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_tag2", insertable = false, updatable = false)
    private SubjectTags subjectTag2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_tag3", insertable = false, updatable = false)
    private SubjectTags subjectTag3;

    @Column(nullable = false, length = 255)
    private String courseThumbnail;

    @Column(nullable = false, length = 255)
    private String shortDescription;

    public List<Integer> getSubjectTagIds() {
        return Stream.of(subjectTag1, subjectTag2, subjectTag3) // ✅ 객체 자체를 먼저 Stream으로 변환
                .filter(Objects::nonNull) // ✅ `null` 제거 (객체 자체 필터링)
                .map(SubjectTags::getSubjectTagId) // ✅ 안전하게 ID 추출
                .collect(Collectors.toList());
    }

    public List<String> getSubjectTagNames() {
        return Stream.of(subjectTag1, subjectTag2, subjectTag3)
                .filter(Objects::nonNull) // null 값 제거
                .map(SubjectTags::getSubjectTagName)
                .collect(Collectors.toList());
    }
}
