package ru.bass2000.ripper.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeprecationHanglerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        final String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            final BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
            final String beanClassName = beanDefinition.getBeanClassName();
            try {
                final Class<?> beanClass = Class.forName(beanClassName);
                final DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                if (annotation != null) {
                    beanDefinition.setBeanClassName(annotation.newImpl().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}