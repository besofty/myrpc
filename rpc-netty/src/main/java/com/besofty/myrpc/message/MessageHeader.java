package com.besofty.myrpc.message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageHeader {
    private Long messageId;
}
