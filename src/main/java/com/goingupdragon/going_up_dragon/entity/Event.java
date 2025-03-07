package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "event_end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "event_title", length = 255, nullable = false)
    private String title;

    @Column(name = "event_thumbnail", length = 255, nullable = false)
    private String thumbnail;
}
