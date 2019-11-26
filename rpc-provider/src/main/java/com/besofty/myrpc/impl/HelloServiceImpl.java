package com.besofty.myrpc.impl;

import com.besofty.myrpc.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name + " hello!";
    }

    @Override
    public String doFirst(String step) {
        return step + " do success";
    }

}