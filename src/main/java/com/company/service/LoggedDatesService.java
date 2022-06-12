package com.company.service;

import com.company.dto.AgentDTO;
import com.company.dto.LoggedDatesDTO;
import com.company.entity.AgentEntity;
import com.company.entity.LoggedDatesEntity;
import com.company.exception.AppBadRequestException;
import com.company.mapper.LoggedDatesInfoMapper;
import com.company.repository.LoggedDatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggedDatesService {

    private final LoggedDatesRepository loggedDatesRepository;


    public Boolean login(AgentEntity agentEntity) {
        Optional<LoggedDatesEntity> logged = loggedDatesRepository
                .findByAgent_NicknameAndLogoutDateIsNull(agentEntity.getNickname());

        if (logged.isPresent()) {
            log.warn("Agent Already Logged nickname={}", agentEntity.getNickname());
            throw new AppBadRequestException("Agent Already Logged!");
        }

        LoggedDatesEntity entity = new LoggedDatesEntity();
        entity.setAgentId(agentEntity.getId());
        entity.setLoginDate(LocalDateTime.now());

        loggedDatesRepository.save(entity);
        return true;
    }

    public Boolean logout(AgentEntity agentEntity) {
        Optional<LoggedDatesEntity> logged = loggedDatesRepository
                .findByAgent_NicknameAndLogoutDateIsNull(agentEntity.getNickname());

        if (!logged.isPresent()) {
            log.warn("Agent Not Logged nickname={}", agentEntity.getNickname());
            throw new AppBadRequestException("Agent Not Logged!");
        }

        return loggedDatesRepository.updateLogout(LocalDateTime.now(), agentEntity.getId()) > 0;
    }

    public PageImpl<LoggedDatesDTO> pagination(int page, int size, String nickname) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "loginDate"));

        Page<LoggedDatesInfoMapper> entityPage = loggedDatesRepository.findAllByAgent(nickname, pageable);

        List<LoggedDatesDTO> dtoList = entityPage
                .map(this::toDTOMapper)
                .toList();

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public LoggedDatesDTO toDTOMapper(LoggedDatesInfoMapper mapper) {
        LoggedDatesDTO dto = new LoggedDatesDTO();
        dto.setAgent(new AgentDTO(mapper.getFirstname(), mapper.getLastname(), mapper.getNickname()));
        dto.setLoginDate(mapper.getLoginDate());
        dto.setLogoutDate(mapper.getLogoutDate());
        return dto;
    }
}
