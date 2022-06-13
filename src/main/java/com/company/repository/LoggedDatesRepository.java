package com.company.repository;

import com.company.entity.LoggedDatesEntity;
import com.company.mapper.LoggedDatesInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface LoggedDatesRepository extends JpaRepository<LoggedDatesEntity, Long> {

    @Query(value = "select a.firstname as firstname, a.lastname as lastname, a.nickname as nickname, " +
            "l.loginDate as loginDate, l.logoutDate as logoutDate " +
            "from LoggedDatesEntity as l " +
            "inner join l.agent as a " +
            "where a.nickname = :nickname ")
    Page<LoggedDatesInfoMapper> findAllByAgent(@Param("nickname") String nickname, Pageable pageable);

    Optional<LoggedDatesEntity> findByAgent_NicknameAndLogoutDateIsNull(String nickname);

    @Transactional
    @Modifying
    @Query("update LoggedDatesEntity set logoutDate = :logoutDate where agentId = :agentId and loginDate is not null")
    int updateLogout(@Param("logoutDate") LocalDateTime logoutDate,
                     @Param("agentId") String agentId);

}