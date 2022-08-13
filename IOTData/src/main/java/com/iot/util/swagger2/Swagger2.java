package com.iot.util.swagger2;

import com.iot.util.authentication.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @Configuration用于定义配置类，可替换xml配置文件， 被注解的类内部包含有一个或多个被@Bean注解的方法，
 * 这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
 * 并用于构建bean定义，初始化Spring容器。
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        List<Parameter> list = Arrays.asList(
                new ParameterBuilder()
                        .name("token")
                        .description("token（只有登陆和注册用户,下面四个接口,或者apiKey存在的地方可不填此参数！）")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        //.hidden(true)
                        .build()
        );
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(list)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iot.controller"))
                //.apis(RequestHandlerSelectors.basePackage("com.iot.model"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(CurrentUser.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IOTData_API文档")
                .description("API使用及参数定义")
                .termsOfServiceUrl("https://zk.zksky.top/IOTData/swagger-ui.html#/")
                .contact(new Contact("周宽、黄鑫", "https://zk.zksky.top/IOTData/swagger-ui.html#/", "zhoukuansky@163.com"))
                .version("1.0")
                .build();
    }
}