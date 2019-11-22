package com.besofty.myrpc.client.dispatcher;

import com.besofty.myrpc.message.MyNettyResponseMessage;
import com.besofty.myrpc.message.ResponseMessageBody;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MyNettyRequestCenter {
    private Map<Long, MyNettyFuture> nettyFutureMap = new ConcurrentHashMap<>();

    public void add(Long messageId, MyNettyFuture myNettyFuture) {
        nettyFutureMap.put(messageId, myNettyFuture);
    }

    public void setResult(Long messageId, MyNettyResponseMessage result) {
        MyNettyFuture myNettyFuture = nettyFutureMap.get(messageId);
        if (myNettyFuture != null) {
            nettyFutureMap.remove(messageId);
            myNettyFuture.setSuccess(result);
        }
    }
}
