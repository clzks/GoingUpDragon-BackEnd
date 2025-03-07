package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo user; // 외래키 (회원 정보)

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // 외래키 (강의 정보)

    private float rate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String reply;

    private LocalDateTime replyCreateAt;
}

