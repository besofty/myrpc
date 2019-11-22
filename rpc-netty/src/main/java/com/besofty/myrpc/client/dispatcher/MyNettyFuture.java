package com.besofty.myrpc.client.dispatcher;

import com.besofty.myrpc.message.MyNettyResponseMessage;
import com.besofty.myrpc.message.ResponseMessageBody;
import io.netty.util.concurrent.DefaultPromise;

public class MyNettyFuture extends DefaultPromise<MyNettyResponseMessage> {
}
