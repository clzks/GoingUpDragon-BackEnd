package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Integer qnaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo user;  // UserInfo 엔티티와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;  // Course 엔티티와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private QnA parentQnA;  // 부모 질문 (NULL 가능, 대댓글 구조)

    @Column(name = "qna_title", length = 255, nullable = false)
    private String title;

    @Column(name = "qna_main", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "custom_tag1")
    private Integer customTag1;

    @Column(name = "custom_tag2")
    private Integer customTag2;

    @Column(name = "custom_tag3")
    private Integer customTag3;
}
