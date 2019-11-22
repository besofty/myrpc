package com.besofty.myrpc.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RequestMessageBody extends MessageBody {
    private String serviceName;
    private String methodName;
    private List<Object> args;
}
