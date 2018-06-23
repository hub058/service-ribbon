package com.example.demo;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
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
// TODO 2:在程序的启动类加@EnableHystrix注解开启Hystrix
@EnableHystrix
// TODO 22:在主程序启动类中加入@EnableHystrixDashboard注解，开启hystrixDashboard
// 打开浏览器：访问http://localhost:8764/hystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ServiceRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}

	@Bean
    @LoadBalanced
    RestTemplate restTemplate(){
	    return new RestTemplate();
    }

    // 在Springboot2.0以上版本中需要添加这样一个Servlet
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
