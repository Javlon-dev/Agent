package com.company.repository;

import com.company.entity.LoggedDatesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoggedDatesRepository extends JpaRepository<LoggedDatesEntity, Long> {

    Page<LoggedDatesEntity> findAllByAgent_Nickname(Pageable pageable);

    Optional<LoggedDatesEntity> findByAgent_NicknameAndLogoutDateIsNull(String nickname);

}