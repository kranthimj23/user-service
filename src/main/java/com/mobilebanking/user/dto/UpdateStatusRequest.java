package com.mobilebanking.user.dto;

import com.mobilebanking.user.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update user status request")
public class UpdateStatusRequest {

    @NotNull(message = "Status is required")
    @Schema(description = "New user status", example = "ACTIVE")
    private UserStatus status;

    @Size(max = 500, message = "Reason must not exceed 500 characters")
    @Schema(description = "Reason for status change", example = "User verified successfully")
    private String reason;
}
