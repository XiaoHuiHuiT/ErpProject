package com.qc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.qc.system.mapper","com.qc.business.mapper","com.qc.statistical.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}