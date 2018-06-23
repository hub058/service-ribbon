package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/6/23.
 * 写一个controller，在controller中用调用HelloService 的方法
 */
@RestController
public class HelloControler {

    @Autowired
    HelloService helloService;


    /*
     * 在浏览器上多次访问http://localhost:8764/hi?name=forezp，浏览器交替显示
     * hi forezp,i am from port:8762
     * hi forezp,i am from port:8763
     * 这说明当我们通过调用
     * restTemplate.getForObject(“http://SERVICE-HI/hi?name=“+name,String.class)方法时，
     * 已经做了负载均衡，访问了不同的端口的服务实例
     */
    @RequestMapping("/hi")
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
}
