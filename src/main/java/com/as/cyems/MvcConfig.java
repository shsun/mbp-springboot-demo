package com.as.cyems;

import base.JqueryStyleSortArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SpringMVC配置.
 *
 * @author richard
 */
// @Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    // 添加试图解析器
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp("/WEB-INF/views/", ".jsp");
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(javaTimeModule)
                .indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));//LocalDateTime
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    /**
     * 参数解析器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new LoginUserArgumentResolver());

        SortArgumentResolver sort = new JqueryStyleSortArgumentResolver();
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver(sort);
        pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");
        pageableHandlerMethodArgumentResolver.setPageParameterName("page");
//        pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
    }

    /*
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new SessionInfoArgumentResolver());




        // argumentResolvers.add(new JsonPa);


    }
    */


//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        super.addArgumentResolvers(argumentResolvers);
//        argumentResolvers.add(new LoginUserInfoMethodArgumentResolver());
//    }


//
//    @Bean
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
//      public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//    }

}
