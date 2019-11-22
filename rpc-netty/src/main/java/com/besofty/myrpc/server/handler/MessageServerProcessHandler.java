package com.besofty.myrpc.server.handler;

import com.besofty.myrpc.message.MyNettyRequestMessage;
import com.besofty.myrpc.message.MyNettyResponseMessage;
import com.besofty.myrpc.message.RequestMessageBody;
import com.besofty.myrpc.message.ResponseMessageBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageServerProcessHandler extends SimpleChannelInboundHandler<MyNettyRequestMessage> {
    /**
     * <服务名，服务实例>
     */
    private Map<String/*service name*/, Object/*service provider*/> serviceProviders = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyNettyRequestMessage msg) throws Exception {
        RequestMessageBody requestMessageBody = msg.getMessageBody();
        Object serviceProvider = serviceProviders.get(requestMessageBody.getServiceName());
        //TODO 如果没找到，报错
        // 利用反射找到对应的方法
        List<Object> args = requestMessageBody.getArgs();
        List<Class<?>> argTypes = args.stream().map(Object::getClass).collect(Collectors.toList());
        Method method = serviceProvider.getClass().getMethod(requestMessageBody.getMethodName(), argTypes.toArray(new Class<?>[args.size()]));
        Object res = method.invoke(serviceProvider, requestMessageBody.getArgs().toArray(new Object[args.size()]));
        MyNettyResponseMessage responseMessage = new MyNettyResponseMessage();
        responseMessage.setMessageHeader(msg.getMessageHeader());
        ResponseMessageBody responseMessageBody = new ResponseMessageBody();
        responseMessageBody.setRes(res);
        responseMessage.setMessageBody(responseMessageBody);
        ctx.writeAndFlush(responseMessage);
    }

    public synchronized <T> void addServiceProvider(String serviceName, T serviceProvider) {
        serviceProviders.put(serviceName, serviceProvider);
    }
}
