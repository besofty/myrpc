package com.besofty.myrpc.server.codec;

import com.besofty.myrpc.message.MyNettyResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class MessageProtocolEncoder extends MessageToMessageEncoder<MyNettyResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyNettyResponseMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
