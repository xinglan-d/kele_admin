package com.kele;

import com.kele.system.model.intercept.CustomSecurityInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dzy
 * @date 2020-01-20 10:48:51
 */
@SpringBootApplication
@EnableEurekaClient
@Log4j2
public class SystemApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    /**
     * 拦截器固定写法
     * 如果不实现此方法拦截器不工作
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，连接以/admin为前缀的 url路径
        registry.addInterceptor(new CustomSecurityInterceptor()).addPathPatterns("/**");

    }
}
