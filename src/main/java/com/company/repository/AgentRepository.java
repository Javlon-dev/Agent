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

    @Transactional
    @Modifying
    @Query("update AgentEntity set token = :token , loginDate = :date where id = :id ")
    void updateTokenAndLogin(@Param("token") String token,
                             @Param("date") LocalDateTime date,
                             @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update AgentEntity set token = null , logoutDate = :date where id = :id ")
    void updateLogout(@Param("date") LocalDateTime date,
                      @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update AgentEntity set token = null , logoutDate = :date where token = :token ")
    void updateToken(@Param("date") LocalDateTime date,
                      @Param("token") String token);

}