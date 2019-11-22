package com.besofty.myrpc.server;

import com.besofty.myrpc.proxy.MyNettyImpl;
import com.besofty.myrpc.server.codec.MessageFrameDecoder;
import com.besofty.myrpc.server.codec.MessageFrameEncoder;
import com.besofty.myrpc.server.codec.MessageProtocolDecoder;
import com.besofty.myrpc.server.codec.MessageProtocolEncoder;
import com.besofty.myrpc.server.handler.MessageServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.reflections.Reflections;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Server {
    private static ServerBootstrap serverBootstrap;
    private static ChannelFuture channelFuture;
    private static MessageServerProcessHandler messageServerProcessHandler;

    private static void init() {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(new NioEventLoopGroup());
    }

    private static void initMessageServerProcessHandler() {
        //TODO
        //入参 要扫描的包名
        Reflections f = new Reflections("com.besofty.myrpc.impl");
        //入参 目标注解类
        Set<Class<?>> set = f.getTypesAnnotatedWith(MyNettyImpl.class);
        messageServerProcessHandler = new MessageServerProcessHandler();
    }

    private static void initChildHandler() {
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

    public static void start() throws InterruptedException {
        init();
        initMessageServerProcessHandler();
        initChildHandler();
        channelFuture = serverBootstrap.bind(8090).sync();
    }

    public static void stop() throws ExecutionException, InterruptedException {
        channelFuture.channel().closeFuture().get();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        start();
        stop();
    }
}
