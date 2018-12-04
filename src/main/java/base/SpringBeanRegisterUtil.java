package base;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * springApplicationContext工具
 */
public class SpringBeanRegisterUtil {

    /**
     * 
     */
    private static ApplicationContext context = XSpringContextHolder.getApplicationContext();

    /**
     *
     */
    private static ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;

    /**
     *
     */
    private static BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();

    /**
     * 注册bean
     *
     * @param beanId 所注册bean的id
     * @param className bean的className， 三种获取方式：1、直接书写，如：com.mvc.entity.User 2、User.class.getName 3.user.getClass().getName()
     */
    public static void registerBean(String beanId, String className) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
    }

    /**
     * 移除bean
     *
     * @param beanId bean的id
     */
    public static void unregisterBean(String beanId) {
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }
}