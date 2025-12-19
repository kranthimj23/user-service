package com.mobilebanking.user.repository;

import com.mobilebanking.user.entity.UserStatusHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStatusHistoryRepository extends JpaRepository<UserStatusHistory, UUID> {

    Page<UserStatusHistory> findByUserIdOrderByChangedAtDesc(UUID userId, Pageable pageable);

    void deleteByUserId(UUID userId);
}
