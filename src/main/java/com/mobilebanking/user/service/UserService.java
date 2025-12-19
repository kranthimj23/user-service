package com.mobilebanking.user.service;

import com.mobilebanking.user.dto.*;
import com.mobilebanking.user.entity.User;
import com.mobilebanking.user.entity.UserStatus;
import com.mobilebanking.user.entity.UserStatusHistory;
import com.mobilebanking.user.exception.UserException;
import com.mobilebanking.user.repository.UserRepository;
import com.mobilebanking.user.repository.UserStatusHistoryRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserStatusHistoryRepository statusHistoryRepository;
    private final Counter userCreatedCounter;
    private final Counter userUpdatedCounter;
    private final Counter statusChangedCounter;

    public UserService(
            UserRepository userRepository,
            UserStatusHistoryRepository statusHistoryRepository,
            MeterRegistry meterRegistry) {
        this.userRepository = userRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        
        this.userCreatedCounter = Counter.builder("user.created")
                .description("Number of users created")
                .register(meterRegistry);
        this.userUpdatedCounter = Counter.builder("user.updated")
                .description("Number of user updates")
                .register(meterRegistry);
        this.statusChangedCounter = Counter.builder("user.status.changed")
                .description("Number of status changes")
                .register(meterRegistry);
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());

        if (userRepository.existsByAuthId(request.getAuthId())) {
            throw UserException.authIdAlreadyExists();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserException.emailAlreadyExists();
        }

        if (request.getPhoneNumber() != null && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw UserException.phoneAlreadyExists();
        }

        User user = User.builder()
                .authId(request.getAuthId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                .status(UserStatus.PENDING_VERIFICATION)
                .accountNumber(generateAccountNumber())
                .build();

        user = userRepository.save(user);
        userCreatedCounter.increment();
        
        log.info("User created successfully with ID: {}", user.getId());
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserException::userNotFound);
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByAuthId(UUID authId) {
        User user = userRepository.findByAuthId(authId)
                .orElseThrow(UserException::userNotFoundByAuthId);
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserException::userNotFound);
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersByStatus(UserStatus status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String search, Pageable pageable) {
        return userRepository.searchUsers(search, pageable).map(this::mapToResponse);
    }

    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(UserException::userNotFound);

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null) {
            if (!request.getPhoneNumber().equals(user.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                throw UserException.phoneAlreadyExists();
            }
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getCurrency() != null) {
            user.setCurrency(request.getCurrency());
        }

        user = userRepository.save(user);
        userUpdatedCounter.increment();
        
        log.info("User updated successfully: {}", id);
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateStatus(UUID id, UpdateStatusRequest request, String changedBy, String ipAddress) {
        log.info("Updating status for user: {} to {}", id, request.getStatus());

        User user = userRepository.findById(id)
                .orElseThrow(UserException::userNotFound);

        UserStatus previousStatus = user.getStatus();
        
        if (!isValidStatusTransition(previousStatus, request.getStatus())) {
            throw UserException.invalidStatusTransition(previousStatus.name(), request.getStatus().name());
        }

        UserStatusHistory history = UserStatusHistory.builder()
                .user(user)
                .previousStatus(previousStatus)
                .newStatus(request.getStatus())
                .reason(request.getReason())
                .changedBy(changedBy)
                .changedFromIp(ipAddress)
                .build();
        statusHistoryRepository.save(history);

        user.setStatus(request.getStatus());
        user = userRepository.save(user);
        statusChangedCounter.increment();

        log.info("User status updated from {} to {}", previousStatus, request.getStatus());
        return mapToResponse(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw UserException.userNotFound();
        }

        statusHistoryRepository.deleteByUserId(id);
        userRepository.deleteById(id);
        
        log.info("User deleted successfully: {}", id);
    }

    private boolean isValidStatusTransition(UserStatus from, UserStatus to) {
        if (from == to) return true;
        
        return switch (from) {
            case PENDING_VERIFICATION -> to == UserStatus.ACTIVE || to == UserStatus.SUSPENDED || to == UserStatus.CLOSED;
            case ACTIVE -> to == UserStatus.INACTIVE || to == UserStatus.SUSPENDED || to == UserStatus.CLOSED;
            case INACTIVE -> to == UserStatus.ACTIVE || to == UserStatus.CLOSED;
            case SUSPENDED -> to == UserStatus.ACTIVE || to == UserStatus.CLOSED;
            case CLOSED -> false;
        };
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .authId(user.getAuthId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .kycStatus(user.getKycStatus())
                .accountNumber(user.getAccountNumber())
                .currency(user.getCurrency())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
