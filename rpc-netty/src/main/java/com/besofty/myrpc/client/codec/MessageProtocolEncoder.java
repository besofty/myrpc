package com.besofty.myrpc.client.codec;

import com.besofty.myrpc.message.MyNettyRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageProtocolEncoder extends MessageToMessageEncoder<MyNettyRequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyNettyRequestMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
