package com.mobilebanking.user.dto;

import com.mobilebanking.user.entity.KycStatus;
import com.mobilebanking.user.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response")
public class UserResponse {

    @Schema(description = "User ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Auth ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID authId;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Phone number", example = "+14155551234")
    private String phoneNumber;

    @Schema(description = "Date of birth", example = "1990-01-15")
    private LocalDate dateOfBirth;

    @Schema(description = "User status", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "KYC status", example = "VERIFIED")
    private KycStatus kycStatus;

    @Schema(description = "Account number", example = "1234567890")
    private String accountNumber;

    @Schema(description = "Currency", example = "USD")
    private String currency;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Updated timestamp")
    private LocalDateTime updatedAt;
}
