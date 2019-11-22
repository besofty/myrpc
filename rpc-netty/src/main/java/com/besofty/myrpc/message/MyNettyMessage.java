package com.besofty.myrpc.message;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class MyNettyMessage<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeLong(messageHeader.getMessageId());
        byteBuf.writeBytes(JSONObject.toJSONString(messageBody).getBytes());
    }

    public abstract void decode(ByteBuf byteBuf);

}
