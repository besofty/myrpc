package com.besofty.myrpc.config;

import com.besofty.myrpc.server.MyNettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyNettyServerConfiguration {
    @Value("${myNetty.port}")
    private Integer port;

    @Value("${myNetty.packageName}")
    private String packageName;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MyNettyServer myNettyServer() {
        return new MyNettyServer(port, packageName);
    }
}
