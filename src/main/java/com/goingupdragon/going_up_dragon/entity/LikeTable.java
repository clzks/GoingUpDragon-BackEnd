package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo user;  // UserInfo 엔티티와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;  // Review 엔티티와 연결 (NULL 가능)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;  // Course 엔티티와 연결 (NULL 가능)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;  // QnA 엔티티와 연결 (NULL 가능)
}
