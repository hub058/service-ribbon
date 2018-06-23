package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Spring cloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign
 * 在工程的启动类中,通过@EnableDiscoveryClient向服务中心注册；
 * 并且向程序的ioc注入一个bean: restTemplate;
 * 并通过@LoadBalanced注解表明这个restRemplate开启负载均衡的功能
 *
 * ?? 启动的时候如果报错 NoSuchMethodError
 * Springboot升级2.0版本以后，以来的Springcloud版本是Finchley.RELEASE
 * 不兼容较低的其他版本
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}

	@Bean
    @LoadBalanced
    RestTemplate restTemplate(){
	    return new RestTemplate();
    }
}
