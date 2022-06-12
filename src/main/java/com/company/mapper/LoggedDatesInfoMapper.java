package com.company.mapper;

import java.time.LocalDateTime;

public interface LoggedDatesInfoMapper {

    String getFirstname();
    String getLastname();
    String getNickname();

    LocalDateTime getLoginDate();
    LocalDateTime getLogoutDate();

}
