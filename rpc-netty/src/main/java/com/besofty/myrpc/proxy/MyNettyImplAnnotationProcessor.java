package com.besofty.myrpc.proxy;

import com.besofty.myrpc.server.MyNettyServer;
import com.besofty.myrpc.util.PackageUtil;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

public class MyNettyImplAnnotationProcessor implements BeanDefinitionRegistryPostProcessor {
    @Resource
    private MyNettyServer myNettyServer;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<Class<?>> classes = PackageUtil.getClasses("com.besofty.myrpc.impl");
        for (Class clas : classes) {
            try {
                myNettyServer.addMessageServerProcess(clas.getInterfaces()[0].getCanonicalName(), clas.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}