package com.mobilebanking.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User profile response")
public class UserProfileResponse {

    @Schema(description = "Profile ID")
    private UUID id;

    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "Address line 1", example = "123 Main Street")
    private String addressLine1;

    @Schema(description = "Address line 2", example = "Apt 4B")
    private String addressLine2;

    @Schema(description = "City", example = "San Francisco")
    private String city;

    @Schema(description = "State/Province", example = "California")
    private String state;

    @Schema(description = "Postal/ZIP code", example = "94102")
    private String postalCode;

    @Schema(description = "Country", example = "United States")
    private String country;

    @Schema(description = "Avatar URL")
    private String avatarUrl;

    @Schema(description = "National ID number")
    private String nationalId;

    @Schema(description = "Passport number")
    private String passportNumber;

    @Schema(description = "Occupation", example = "Software Engineer")
    private String occupation;

    @Schema(description = "Employer name", example = "Tech Corp Inc.")
    private String employer;

    @Schema(description = "User bio")
    private String bio;

    @Schema(description = "Preferred language code", example = "en")
    private String preferredLanguage;

    @Schema(description = "Timezone", example = "America/Los_Angeles")
    private String timezone;

    @Schema(description = "Notifications enabled", example = "true")
    private Boolean notificationsEnabled;

    @Schema(description = "Marketing enabled", example = "false")
    private Boolean marketingEnabled;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Updated timestamp")
    private LocalDateTime updatedAt;
}
