package com.mobilebanking.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_profiles", indexes = {
    @Index(name = "idx_user_profiles_user_id", columnList = "user_id", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 500)
    private String addressLine1;

    @Column(length = 500)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 100)
    private String country;

    @Column(length = 500)
    private String avatarUrl;

    @Column(length = 50)
    private String nationalId;

    @Column(length = 50)
    private String passportNumber;

    @Column(length = 100)
    private String occupation;

    @Column(length = 100)
    private String employer;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 10)
    @Builder.Default
    private String preferredLanguage = "en";

    @Column(length = 50)
    private String timezone;

    @Builder.Default
    private Boolean notificationsEnabled = true;

    @Builder.Default
    private Boolean marketingEnabled = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
