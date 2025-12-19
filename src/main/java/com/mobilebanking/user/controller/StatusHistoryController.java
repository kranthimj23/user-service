package com.mobilebanking.user.controller;

import com.mobilebanking.user.dto.ApiResponse;
import com.mobilebanking.user.dto.StatusHistoryResponse;
import com.mobilebanking.user.service.StatusHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/status-history")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Status History", description = "User status history endpoints")
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    @GetMapping
    @Operation(summary = "Get status history", description = "Retrieve user status change history")
    public ResponseEntity<ApiResponse<Page<StatusHistoryResponse>>> getStatusHistory(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<StatusHistoryResponse> response = statusHistoryService.getStatusHistory(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
