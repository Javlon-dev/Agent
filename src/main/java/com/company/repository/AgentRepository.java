package com.company.repository;

import com.company.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<AgentEntity, String> {

    Optional<AgentEntity> findByNickname(String nickname);

    Optional<AgentEntity> findByNicknameAndPassword(String nickname, String password);

    @Transactional
    @Modifying
    @Query("update AgentEntity set firstname = :firstname , lastname = :lastname where id = :id ")
    int updateBio(@Param("firstname") String firstname,
                  @Param("lastname") String lastname,
                  @Param("id") String id);

}