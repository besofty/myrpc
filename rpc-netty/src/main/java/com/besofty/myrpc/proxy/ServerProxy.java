package com.besofty.myrpc.proxy;

import com.besofty.myrpc.client.MyNettyClient;
import com.besofty.myrpc.client.dispatcher.MyNettyFuture;
import com.besofty.myrpc.message.MessageHeader;
import com.besofty.myrpc.message.MyNettyRequestMessage;
import com.besofty.myrpc.message.MyNettyResponseMessage;
import com.besofty.myrpc.message.RequestMessageBody;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ServerProxy {

    //生成代理类
    public static <T> T getProxy(MyNettyClient myNettyClient, Field field) {
        return (T) Proxy.newProxyInstance(field.getType().getClassLoader(),
                new Class[]{field.getType()}, new ProxyObjHandler(myNettyClient));
    }

    /**
     * 统一的增强逻辑
     */
    static class ProxyObjHandler implements InvocationHandler {
        private MyNettyClient myNettyClient;

        public ProxyObjHandler(MyNettyClient myNettyClient) {
            this.myNettyClient = myNettyClient;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MyNettyRequestMessage myNettyRequestMessage = new MyNettyRequestMessage();
            myNettyRequestMessage.setMessageHeader(new MessageHeader().setMessageId(2L));
            myNettyRequestMessage.setMessageBody(new RequestMessageBody()
                    .setServiceName(method.getDeclaringClass().getName())
                    .setMethodName(method.getName())
                    .setArgs(Arrays.asList(args)));
            MyNettyFuture myNettyFuture = new MyNettyFuture();
            myNettyClient.addRequest(myNettyRequestMessage, myNettyFuture);
            MyNettyResponseMessage responseMessageBody = myNettyFuture.get();
            return responseMessageBody.getMessageBody().getRes();
        }
    }
}
