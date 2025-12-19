package com.mobilebanking.user.controller;

import com.mobilebanking.user.dto.ApiResponse;
import com.mobilebanking.user.dto.UserProfileRequest;
import com.mobilebanking.user.dto.UserProfileResponse;
import com.mobilebanking.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/profile")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Profile", description = "User profile management endpoints")
public class ProfileController {

    private final UserProfileService profileService;

    @GetMapping
    @Operation(summary = "Get user profile", description = "Retrieve user profile by user ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile found",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Profile not found"
            )
    })
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        UserProfileResponse response = profileService.getProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping
    @Operation(summary = "Update user profile", description = "Create or update user profile")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile updated successfully",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileResponse response = profileService.createOrUpdateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", response));
    }

    @PutMapping("/avatar")
    @Operation(summary = "Update avatar", description = "Update user avatar URL")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Avatar updated successfully",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateAvatar(
            @Parameter(description = "User ID") @PathVariable UUID userId,
            @RequestParam String avatarUrl) {
        UserProfileResponse response = profileService.updateAvatar(userId, avatarUrl);
        return ResponseEntity.ok(ApiResponse.success("Avatar updated successfully", response));
    }

    @DeleteMapping
    @Operation(summary = "Delete profile", description = "Delete user profile")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "204",
                    description = "Profile deleted successfully"
            )
    })
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        profileService.deleteProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
