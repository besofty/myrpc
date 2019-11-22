package com.besofty.myrpc.server.codec;

import com.besofty.myrpc.message.MyNettyRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MessageProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        MyNettyRequestMessage requestMessage = new MyNettyRequestMessage();
        requestMessage.decode(msg);
        out.add(requestMessage);
    }
}
