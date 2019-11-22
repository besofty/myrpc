package com.besofty.myrpc.config;

import com.besofty.myrpc.proxy.MyNettyBeanPostProcessor;
import com.besofty.myrpc.client.MyNettyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyNettyClientCconfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public MyNettyClient myNettyClient() {
        return new MyNettyClient();
    }

    @Bean
    public MyNettyBeanPostProcessor myNettyBeanPostProcessor() {
        return new MyNettyBeanPostProcessor();
    }
}
