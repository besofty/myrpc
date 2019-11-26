package com.besofty.myrpc.config;

import com.besofty.myrpc.proxy.MyNettyProxyProcessor;
import com.besofty.myrpc.client.MyNettyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyNettyClientCconfiguration {
    @Value("${myNetty.port}")
    private Integer port;

    @Value("${myNetty.host}")
    private String host;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MyNettyClient myNettyClient() {
        return new MyNettyClient(host, port);
    }

    @Bean
    public MyNettyProxyProcessor myNettyBeanPostProcessor() {
        return new MyNettyProxyProcessor();
    }
}
