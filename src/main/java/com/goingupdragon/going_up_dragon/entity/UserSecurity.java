package com.goingupdragon.going_up_dragon.entity;

import com.goingupdragon.going_up_dragon.enums.Enums;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_security")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String snsEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.SignupType signupType;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.UserStatus status;

    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;
}

