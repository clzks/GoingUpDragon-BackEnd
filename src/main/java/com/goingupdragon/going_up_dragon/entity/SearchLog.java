package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    @Column(name = "search_log_id")
    private Long searchLogId;

    @ManyToOne
    @JoinColumn(name = "info_id", nullable = true) // 외래 키
    private UserInfo userInfo;

    @Column(name = "search_query", length = 255, nullable = false)
    private String searchQuery;

    @Column(name = "search_category", length = 255, nullable = true)
    private String searchCategory;

    @Column(name = "search_time", nullable = false)
    private LocalDateTime searchTime;
}


