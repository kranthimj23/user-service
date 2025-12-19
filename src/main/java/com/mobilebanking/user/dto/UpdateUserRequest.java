package com.mobilebanking.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update user request payload")
public class UpdateUserRequest {

    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @Schema(description = "User phone number in E.164 format", example = "+14155551234")
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past")
    @Schema(description = "User date of birth", example = "1990-01-15")
    private LocalDate dateOfBirth;

    @Size(max = 3, message = "Currency code must be 3 characters")
    @Schema(description = "Preferred currency code", example = "USD")
    private String currency;
}
