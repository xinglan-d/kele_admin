package com.kele;

import com.kele.base.model.common.menu.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author dzy
 * @date 2020-01-20 10:48:51
 */
@SpringBootApplication
public class SystemApplication {



    public static void main(String[] args) {
//        Menu.getInstance().getSysMenu();
        SpringApplication.run(SystemApplication.class, args);
    }

}
