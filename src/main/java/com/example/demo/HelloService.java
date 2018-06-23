package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/6/23.
 * 写一个测试类HelloService，
 * 通过之前注入ioc容器的restTemplate来消费service-hi服务的“/hi”接口，
 * 在这里我们直接用的程序名替代了具体的url地址，
 * 在ribbon中它会根据服务名来选择具体的服务实例，
 * 根据服务实例在请求的时候会用具体的url替换掉服务名
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    // TODO 3:改造HelloService类，在hiService方法上加上@HystrixCommand注解。
    // 该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，
    // 熔断方法直接返回了一个字符串，字符串为"hi,"+name+",sorry,error!";

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name){
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String hiError(String name){
        return "hi,"+name+",sorry,error!";
    }

    // TODO 4:启动：service-ribbon 工程
    /*
    启动：service-ribbon 工程，当我们访问http://localhost:8764/hi?name=forezp,浏览器显示：
    hi forezp,i am from port:8762
    此时关闭 service-hi 工程，当我们再访问http://localhost:8764/hi?name=forezp，浏览器会显示：
    hi ,forezp,orry,error!
    这就说明当 service-hi 工程不可用的时候，service-ribbon调用 service-hi的API接口时，
    会执行快速失败，直接返回一组字符串，而不是等待响应超时，这很好的控制了容器的线程阻塞。
    */
}
