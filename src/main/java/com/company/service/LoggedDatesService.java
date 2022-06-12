package com.company.service;

import com.company.repository.LoggedDatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggedDatesService {

    private final LoggedDatesRepository loggedDatesRepository;


}
