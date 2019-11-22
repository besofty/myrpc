package com.besofty.myrpc.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ResponseMessageBody extends MessageBody {
    private Object res;
}
