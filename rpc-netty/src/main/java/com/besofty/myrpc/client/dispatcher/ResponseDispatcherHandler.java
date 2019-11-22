package com.besofty.myrpc.client.dispatcher;

import com.besofty.myrpc.message.MyNettyResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ResponseDispatcherHandler extends SimpleChannelInboundHandler<MyNettyResponseMessage> {
    private MyNettyRequestCenter myNettyRequestCenter;

    public ResponseDispatcherHandler(MyNettyRequestCenter myNettyRequestCenter) {
        this.myNettyRequestCenter = myNettyRequestCenter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyNettyResponseMessage msg) throws Exception {
        myNettyRequestCenter.setResult(msg.getMessageHeader().getMessageId(), msg);
    }
}
