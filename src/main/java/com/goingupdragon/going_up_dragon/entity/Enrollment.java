package com.goingupdragon.going_up_dragon.entity;

import com.goingupdragon.going_up_dragon.enums.Enums;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"info_id", "course_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo user;  // UserInfo 엔티티와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;  // Course 엔티티와 연결

    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_status", nullable = false)
    private Enums.EnrollmentStatus status;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;
}

