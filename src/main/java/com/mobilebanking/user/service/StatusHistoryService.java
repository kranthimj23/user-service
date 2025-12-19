package com.mobilebanking.user.service;

import com.mobilebanking.user.dto.StatusHistoryResponse;
import com.mobilebanking.user.entity.UserStatusHistory;
import com.mobilebanking.user.repository.UserStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusHistoryService {

    private final UserStatusHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public Page<StatusHistoryResponse> getStatusHistory(UUID userId, Pageable pageable) {
        return historyRepository.findByUserIdOrderByChangedAtDesc(userId, pageable)
                .map(this::mapToResponse);
    }

    private StatusHistoryResponse mapToResponse(UserStatusHistory history) {
        return StatusHistoryResponse.builder()
                .id(history.getId())
                .previousStatus(history.getPreviousStatus())
                .newStatus(history.getNewStatus())
                .reason(history.getReason())
                .changedBy(history.getChangedBy())
                .changedFromIp(history.getChangedFromIp())
                .changedAt(history.getChangedAt())
                .build();
    }
}
