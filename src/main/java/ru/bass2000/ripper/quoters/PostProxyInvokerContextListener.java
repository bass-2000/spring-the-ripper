package ru.bass2000.ripper.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            final String beanClassName = beanDefinition.getBeanClassName();
            try {
                final Class<?> beanClass = Class.forName(beanClassName);
                final Method[] methods = beanClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        final Object bean = context.getBean(name);
                        final Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Autowired
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
