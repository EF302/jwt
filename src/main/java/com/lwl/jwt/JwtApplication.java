package com.lwl.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
//@MapperScan("com.lwl.jwt.dao")
public class JwtApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(JwtApplication.class, args);

        System.out.println(context.getBean(DataSource.class));
    }

}
