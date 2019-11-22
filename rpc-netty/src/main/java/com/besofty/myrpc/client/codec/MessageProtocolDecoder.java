package com.besofty.myrpc.client.codec;

import com.besofty.myrpc.message.MyNettyResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MessageProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        MyNettyResponseMessage responseMessage = new MyNettyResponseMessage();
        responseMessage.decode(msg);
        out.add(responseMessage);
    }
}
