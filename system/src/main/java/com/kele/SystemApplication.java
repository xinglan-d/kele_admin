package com.kele;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author dzy
 * @date 2020-01-20 10:48:51
 */
@SpringBootApplication
@EnableEurekaClient
@Log4j2
public class SystemApplication {

    public static void main(String[] args)  {
        SpringApplication.run(SystemApplication.class, args);
    }

}
