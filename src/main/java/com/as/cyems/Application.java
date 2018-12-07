package com.as.cyems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 *
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
@ComponentScan(basePackages = {
        "base",
        "com.as.cyems"})
public class Application {

    /**
     * 启动应用程序
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 所有配置信息的入口
     * @param application
     * @return
     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }
}

/*
public class Application {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("PortalApplication is success!");
        System.err.println("sample started. http://localhost:8080/user/test");
    }
}
*/


/**
 * <p>
 * 测试 RUN<br>
 * 查看 h2 数据库控制台：http://localhost:8080/console<br>
 * 使用：JDBC URL 设置 jdbc:h2:mem:testdb 用户名 sa 密码 sa 进入，可视化查看 user 表<br>
 * 误删连接设置，开发机系统本地 ~/.h2.server.properties 文件<br>
 * <br>
 * 1、http://localhost:8080/user/test<br>
 * 2、http://localhost:8080/user/test1<br>
 * 3、http://localhost:8080/user/test2<br>
 * 4、http://localhost:8080/user/test3<br>
 * 5、http://localhost:8080/user/add<br>
 * 6、http://localhost:8080/user/selectsql<br>
 * 7、分页 size 一页显示数量  current 当前页码
 * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
 * 方式二：http://localhost:8080/user/pagehelper?size=1&current=1<br>
 * </p>
 */