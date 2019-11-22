package com.besofty.myrpc.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageFrameDecoder extends LengthFieldBasedFrameDecoder {
    public MessageFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
