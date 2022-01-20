package com.seangull.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.seangull.dao.mapper")
public class DaoApplication {

    public static void main(String[] args){
        SpringApplication.run(DaoApplication.class,args);
    }

}
