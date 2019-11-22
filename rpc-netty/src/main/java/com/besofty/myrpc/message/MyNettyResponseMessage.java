package com.besofty.myrpc.message;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.nio.charset.Charset;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MyNettyResponseMessage extends MyNettyMessage<ResponseMessageBody> {
    @Override
    public void decode(ByteBuf msg) {
        long messageId = msg.readLong();
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMessageId(messageId);
        setMessageHeader(messageHeader);
        setMessageBody(JSONObject.parseObject(msg.toString(Charset.forName("UTF-8")), ResponseMessageBody.class));
    }
}
