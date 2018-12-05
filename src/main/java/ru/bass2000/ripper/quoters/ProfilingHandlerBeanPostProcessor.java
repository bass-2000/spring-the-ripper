package ru.bass2000.ripper.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.TreeMap;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> classMap = new TreeMap<String, Class>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        final MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            classMap.put(beanName, beanClass);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        final Class beanClass = classMap.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("ПРОФИЛИРУЮ");
                        final long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        final long after = System.nanoTime();
                        System.out.println(after - before);
                        System.out.println("ВСЕ");
                        return retVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }
        return bean;
    }
}