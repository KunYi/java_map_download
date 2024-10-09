package com.jmd.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jmd.common.StaticVar;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/web/**").addResourceLocations("classpath:/web/");
        if (StaticVar.IS_LINUX) {
            registry.addResourceHandler("/chunk-**").addResourceLocations("classpath:/web/");
            registry.addResourceHandler("/scripts-**").addResourceLocations("classpath:/web/");
            registry.addResourceHandler("/main-**").addResourceLocations("classpath:/web/");
            registry.addResourceHandler("/styles-**").addResourceLocations("classpath:/web/");
            registry.addResourceHandler("/polyfills-**").addResourceLocations("classpath:/web/");
            registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/web/");
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/index.html").setViewName("");
    }

}
