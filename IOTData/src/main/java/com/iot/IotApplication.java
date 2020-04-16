package com.iot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zk
 * @date 2020.02-2020.03
 */
@EnableSwagger2
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.iot.dao")
@EnableCaching//开启注解驱动的缓存管理
public class IotApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IotApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IotApplication.class);
    }

}
