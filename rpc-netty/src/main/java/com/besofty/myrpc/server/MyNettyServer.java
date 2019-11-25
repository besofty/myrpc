package com.besofty.myrpc.server;

import com.besofty.myrpc.server.codec.MessageFrameDecoder;
import com.besofty.myrpc.server.codec.MessageFrameEncoder;
import com.besofty.myrpc.server.codec.MessageProtocolDecoder;
import com.besofty.myrpc.server.codec.MessageProtocolEncoder;
import com.besofty.myrpc.server.handler.MessageServerProcessHandler;
import com.besofty.myrpc.util.PackageUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyNettyServer {
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;
    private MessageServerProcessHandler messageServerProcessHandler;

    private void init() {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(new NioEventLoopGroup());
    }

    private void initMessageServerProcessHandler() {
        messageServerProcessHandler = new MessageServerProcessHandler();
        List<Class<?>> classes = PackageUtil.getClasses("com.besofty.myrpc.impl");
        for (Class clas : classes) {
            try {
                addMessageServerProcess(clas.getInterfaces()[0].getCanonicalName(), clas.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> void addMessageServerProcess(String serviceName, T serviceProvider) {
        messageServerProcessHandler.addServiceProvider(serviceName, serviceProvider);
    }

    private void initChildHandler() {
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new MessageFrameDecoder());
                pipeline.addLast(new MessageFrameEncoder());
                pipeline.addLast(new MessageProtocolDecoder());
                pipeline.addLast(new MessageProtocolEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                pipeline.addLast(messageServerProcessHandler);
            }
        });
    }

    public void start() throws InterruptedException {
        init();
        initMessageServerProcessHandler();
        initChildHandler();
        channelFuture = serverBootstrap.bind(8090).sync();
    }

    public void stop() throws ExecutionException, InterruptedException {
        channelFuture.channel().closeFuture().get();
    }

}
