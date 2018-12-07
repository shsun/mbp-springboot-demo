package bak;

import base.JqueryStyleSortArgumentResolver;
import com.as.cyems.SessionInfoArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;


//@EnableWebMvc
//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }

    /*
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        super.addDefaultHttpMessageConverters(converters);
    }
    */



    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // LocalDateTime
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(javaTimeModule)
                .indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));




        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        converters.add(messageConverter);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login");
        super.addInterceptors(registry);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

//        MappingJackson2HttpMessageConverter

//        Jackson2ObjectMapperFactoryBean


//        argumentResolvers.add(new LoginUserArgumentResolver());

        SortArgumentResolver sort = new JqueryStyleSortArgumentResolver();
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver(sort);
        pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");
        pageableHandlerMethodArgumentResolver.setPageParameterName("page");
//        pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
        argumentResolvers.add(pageableHandlerMethodArgumentResolver);


        argumentResolvers.add(new SessionInfoArgumentResolver());

    }



    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 不序列化null的属性
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 默认的时间序列化格式
        return mapper;
    }


}


//<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"></bean>
//<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
//<property name="messageConverters">
//<list>
//<bean class="org.springframework.http.converter.StringHttpMessageConverter">
//<property name="supportedMediaTypes">
//<list>
//<value>text/plain;charset=UTF-8</value>
//<value>application/json;charset=UTF-8</value>
//</list>
//</property>
//</bean>
//<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
//<property name="objectMapper">
//<bean id="jacksonObjectMapper" class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
//<property name="timeZone">
//<bean class="sun.util.calendar.ZoneInfo" factory-method="getTimeZone">
//<constructor-arg type="java.lang.String" value="Asia/Shanghai"/>
//</bean>
//</property>
//</bean>
//</property>
//</bean>
//</list>
//</property>
//</bean>
