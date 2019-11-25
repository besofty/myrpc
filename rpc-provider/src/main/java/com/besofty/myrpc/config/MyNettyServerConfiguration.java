package com.besofty.myrpc.config;

import com.besofty.myrpc.server.MyNettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyNettyServerConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MyNettyServer myNettyServer() {
        return new MyNettyServer();
    }
}
