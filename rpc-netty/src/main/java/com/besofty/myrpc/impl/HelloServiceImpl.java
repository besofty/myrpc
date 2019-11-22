package com.besofty.myrpc.impl;

import com.besofty.myrpc.HelloService;
import com.besofty.myrpc.proxy.MyNettyImpl;

@MyNettyImpl
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name + " hello!";
    }
}
