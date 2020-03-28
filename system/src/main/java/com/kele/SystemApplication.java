package com.kele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author dzy
 * @date 2020-01-20 10:48:51
 */
@SpringBootApplication
public class SystemApplication {



    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SpringApplication.run(SystemApplication.class, args);


    }

}
