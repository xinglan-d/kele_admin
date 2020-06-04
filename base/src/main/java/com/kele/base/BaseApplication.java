package com.kele.base;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author dzy
 * @date 2020-01-20 10:48:51
 */
@SpringBootApplication
@Log4j2
public class BaseApplication {

    /**
     *
     *
     * @param args
     * @return void
     * @author duzongyue
     * @date 2020-02-04 09:12:21
     */
    public static void main(String[] args)
    {
        SpringApplication.run(BaseApplication.class, args);
    }

}
