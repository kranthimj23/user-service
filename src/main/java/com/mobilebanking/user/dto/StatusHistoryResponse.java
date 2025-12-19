package com.mobilebanking.user.dto;

import com.mobilebanking.user.entity.UserStatus;
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
@Schema(description = "User status history entry")
public class StatusHistoryResponse {

    @Schema(description = "History entry ID")
    private UUID id;

    @Schema(description = "Previous status", example = "PENDING_VERIFICATION")
    private UserStatus previousStatus;

    @Schema(description = "New status", example = "ACTIVE")
    private UserStatus newStatus;

    @Schema(description = "Reason for change", example = "User verified successfully")
    private String reason;

    @Schema(description = "Changed by", example = "admin@mobilebanking.com")
    private String changedBy;

    @Schema(description = "Changed from IP", example = "192.168.1.1")
    private String changedFromIp;

    @Schema(description = "Changed at timestamp")
    private LocalDateTime changedAt;
}
