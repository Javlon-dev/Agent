package com.company;

import com.company.service.bin.AdminService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AgentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AgentApplication.class, args);
        AdminService adminService = context.getBean(AdminService.class);
        adminService.createAdmin();
    }

}
