package com.yang.manet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.yang.manet.mapper")
@SpringBootApplication
public class ManetApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ManetApplication.class, args);
    }
}
