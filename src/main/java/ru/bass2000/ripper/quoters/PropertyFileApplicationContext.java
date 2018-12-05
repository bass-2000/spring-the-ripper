package ru.bass2000.ripper.quoters;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class PropertyFileApplicationContext extends GenericApplicationContext {
    public PropertyFileApplicationContext(String fileName) {
        final PropertiesBeanDefinitionReader beanDefinitionReader = new PropertiesBeanDefinitionReader(this);
        int i = beanDefinitionReader.loadBeanDefinitions(fileName);
        System.out.println("Found " + i + " beans");
        refresh();
    }

    public static void main(String[] args) {
        final PropertyFileApplicationContext propertyFileApplicationContext = new PropertyFileApplicationContext("context.properties");
        propertyFileApplicationContext.getBean(Quoter.class).sayQuote();
    }
}
