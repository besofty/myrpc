package com.besofty.myrpc.controller;

import com.besofty.myrpc.HelloService;
import com.besofty.myrpc.proxy.MyNettyInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestNettyController {
    @MyNettyInterface
    private HelloService helloService;

    @GetMapping("/test_proxy")
    public String testProxy(@RequestParam String name) {
        return helloService.sayHello(name) + "  " + helloService.doFirst("say hello ");
    }
}
