package com.goingupdragon.going_up_dragon.entity;

import com.goingupdragon.going_up_dragon.enums.Enums;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer infoId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserSecurity user;

    @Column(unique = true, nullable = false, length = 255)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 255)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Enums.Role role;

    @Column(length = 255)
    private String profileAddress;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchLog> searchLogs;
}

