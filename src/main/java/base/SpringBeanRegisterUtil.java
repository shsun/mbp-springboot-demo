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
     * @param name 所注册bean的id
     * @param className bean的className， 三种获取方式：1、直接书写，如：com.mvc.entity.User 2、User.class.getName 3.user.getClass().getName()
     */
//    public static <T> T registerBean(String name, String className, Object... args) {
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
//        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
//        beanDefinitionRegistry.registerBeanDefinition(name, beanDefinition);
//    }

    /**
     * InrSer ser = registerBean("test", InrSer.class);
     *
     * InrSer ser2 = registerBean("test2", InrSer.class, "一灰灰Blog", 20);
     *
     * @param name
     * @param clazz
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T registerBean(String name, Class<T> clazz, Object... args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) configurableContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return configurableContext.getBean(name, clazz);
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