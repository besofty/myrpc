package com.besofty.myrpc.client;

import com.besofty.myrpc.client.codec.MessageFrameDecoder;
import com.besofty.myrpc.client.codec.MessageFrameEncoder;
import com.besofty.myrpc.client.codec.MessageProtocolDecoder;
import com.besofty.myrpc.client.codec.MessageProtocolEncoder;
import com.besofty.myrpc.client.dispatcher.MyNettyFuture;
import com.besofty.myrpc.client.dispatcher.MyNettyRequestCenter;
import com.besofty.myrpc.client.dispatcher.ResponseDispatcherHandler;
import com.besofty.myrpc.message.MessageHeader;
import com.besofty.myrpc.message.MyNettyRequestMessage;
import com.besofty.myrpc.message.RequestMessageBody;
import com.besofty.myrpc.message.ResponseMessageBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class MyNettyClient {
    private static final MyNettyRequestCenter requestCenter = new MyNettyRequestCenter();
    private static final Bootstrap bootstrap = new Bootstrap();
    private ChannelFuture channelFuture;

    public void start() throws InterruptedException {
        initBootstrap();
        channelFuture = bootstrap.connect("127.0.0.1", 8090);
        channelFuture.sync();
    }

    public void stop() throws ExecutionException, InterruptedException {
        channelFuture.channel().closeFuture().get();
    }

    public void addRequest(MyNettyRequestMessage myNettyRequestMessage, MyNettyFuture myNettyFuture) {
        requestCenter.add(2L, myNettyFuture);
        channelFuture.channel().writeAndFlush(myNettyRequestMessage);
    }


    private void initBootstrap() {
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new MessageFrameDecoder());
                pipeline.addLast(new MessageFrameEncoder());
                pipeline.addLast(new MessageProtocolEncoder());
                pipeline.addLast(new MessageProtocolDecoder());

                pipeline.addLast(new ResponseDispatcherHandler(requestCenter));

                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });
    }
}
