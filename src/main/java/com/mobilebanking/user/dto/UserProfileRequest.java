package com.mobilebanking.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User profile update request")
public class UserProfileRequest {

    @Size(max = 500, message = "Address line 1 must not exceed 500 characters")
    @Schema(description = "Address line 1", example = "123 Main Street")
    private String addressLine1;

    @Size(max = 500, message = "Address line 2 must not exceed 500 characters")
    @Schema(description = "Address line 2", example = "Apt 4B")
    private String addressLine2;

    @Size(max = 100, message = "City must not exceed 100 characters")
    @Schema(description = "City", example = "San Francisco")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    @Schema(description = "State/Province", example = "California")
    private String state;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    @Schema(description = "Postal/ZIP code", example = "94102")
    private String postalCode;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Schema(description = "Country", example = "United States")
    private String country;

    @Size(max = 50, message = "National ID must not exceed 50 characters")
    @Schema(description = "National ID number", example = "123-45-6789")
    private String nationalId;

    @Size(max = 50, message = "Passport number must not exceed 50 characters")
    @Schema(description = "Passport number", example = "AB1234567")
    private String passportNumber;

    @Size(max = 100, message = "Occupation must not exceed 100 characters")
    @Schema(description = "Occupation", example = "Software Engineer")
    private String occupation;

    @Size(max = 100, message = "Employer must not exceed 100 characters")
    @Schema(description = "Employer name", example = "Tech Corp Inc.")
    private String employer;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    @Schema(description = "User bio", example = "A passionate developer...")
    private String bio;

    @Size(max = 10, message = "Language code must not exceed 10 characters")
    @Schema(description = "Preferred language code", example = "en")
    private String preferredLanguage;

    @Size(max = 50, message = "Timezone must not exceed 50 characters")
    @Schema(description = "Timezone", example = "America/Los_Angeles")
    private String timezone;

    @Schema(description = "Enable notifications", example = "true")
    private Boolean notificationsEnabled;

    @Schema(description = "Enable marketing communications", example = "false")
    private Boolean marketingEnabled;
}
