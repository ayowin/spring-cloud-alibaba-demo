package com.seangull.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.seangull.dao.mapper")
public class ProducerApplication {

    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class,args);
    }

}
