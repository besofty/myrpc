package com.besofty.myrpc.proxy;


import com.besofty.myrpc.client.MyNettyClient;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;


public class MyNettyProxyProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    @Resource
    private MyNettyClient myNettyClient;

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(),
                field -> FieldUtils.writeField(field, bean, ServerProxy.getProxy(myNettyClient, field), true),
                field -> field.isAnnotationPresent(MyNettyInterface.class));
        return pvs;
    }
}