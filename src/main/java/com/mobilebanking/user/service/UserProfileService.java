package com.mobilebanking.user.service;

import com.mobilebanking.user.dto.UserProfileRequest;
import com.mobilebanking.user.dto.UserProfileResponse;
import com.mobilebanking.user.entity.User;
import com.mobilebanking.user.entity.UserProfile;
import com.mobilebanking.user.exception.UserException;
import com.mobilebanking.user.repository.UserProfileRepository;
import com.mobilebanking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(UUID userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserException::profileNotFound);
        return mapToResponse(profile);
    }

    @Transactional
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request) {
        log.info("Updating profile for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(UserException::userNotFound);

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> UserProfile.builder().user(user).build());

        updateProfileFields(profile, request);
        profile = profileRepository.save(profile);

        log.info("Profile updated for user: {}", userId);
        return mapToResponse(profile);
    }

    @Transactional
    public UserProfileResponse updateAvatar(UUID userId, String avatarUrl) {
        log.info("Updating avatar for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(UserException::userNotFound);

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder().user(user).build();
                    return profileRepository.save(newProfile);
                });

        profile.setAvatarUrl(avatarUrl);
        profile = profileRepository.save(profile);

        log.info("Avatar updated for user: {}", userId);
        return mapToResponse(profile);
    }

    @Transactional
    public void deleteProfile(UUID userId) {
        log.info("Deleting profile for user: {}", userId);
        profileRepository.deleteByUserId(userId);
        log.info("Profile deleted for user: {}", userId);
    }

    private void updateProfileFields(UserProfile profile, UserProfileRequest request) {
        if (request.getAddressLine1() != null) {
            profile.setAddressLine1(request.getAddressLine1());
        }
        if (request.getAddressLine2() != null) {
            profile.setAddressLine2(request.getAddressLine2());
        }
        if (request.getCity() != null) {
            profile.setCity(request.getCity());
        }
        if (request.getState() != null) {
            profile.setState(request.getState());
        }
        if (request.getPostalCode() != null) {
            profile.setPostalCode(request.getPostalCode());
        }
        if (request.getCountry() != null) {
            profile.setCountry(request.getCountry());
        }
        if (request.getNationalId() != null) {
            profile.setNationalId(request.getNationalId());
        }
        if (request.getPassportNumber() != null) {
            profile.setPassportNumber(request.getPassportNumber());
        }
        if (request.getOccupation() != null) {
            profile.setOccupation(request.getOccupation());
        }
        if (request.getEmployer() != null) {
            profile.setEmployer(request.getEmployer());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getPreferredLanguage() != null) {
            profile.setPreferredLanguage(request.getPreferredLanguage());
        }
        if (request.getTimezone() != null) {
            profile.setTimezone(request.getTimezone());
        }
        if (request.getNotificationsEnabled() != null) {
            profile.setNotificationsEnabled(request.getNotificationsEnabled());
        }
        if (request.getMarketingEnabled() != null) {
            profile.setMarketingEnabled(request.getMarketingEnabled());
        }
    }

    private UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .addressLine1(profile.getAddressLine1())
                .addressLine2(profile.getAddressLine2())
                .city(profile.getCity())
                .state(profile.getState())
                .postalCode(profile.getPostalCode())
                .country(profile.getCountry())
                .avatarUrl(profile.getAvatarUrl())
                .nationalId(profile.getNationalId())
                .passportNumber(profile.getPassportNumber())
                .occupation(profile.getOccupation())
                .employer(profile.getEmployer())
                .bio(profile.getBio())
                .preferredLanguage(profile.getPreferredLanguage())
                .timezone(profile.getTimezone())
                .notificationsEnabled(profile.getNotificationsEnabled())
                .marketingEnabled(profile.getMarketingEnabled())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
