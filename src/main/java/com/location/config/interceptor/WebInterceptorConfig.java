package com.location.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wangcl on 2019/8/22.
 * 登录拦截器配置
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private TimeOutInterceptor timeOutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(loginInterceptor).addPathPatterns("/**/oa/login");
        //  .excludePathPatterns("/login", "/register");
        registry.addInterceptor(timeOutInterceptor).addPathPatterns("/teamwork-api/**")
                .excludePathPatterns("/location-api/oa/login", "/location-api/oa/logout")
                .excludePathPatterns("/location-api/open/**")
                .excludePathPatterns("/teamwork-api/oa/login")
                .excludePathPatterns("/teamwork-api/project/getAllOrgName")
                .excludePathPatterns("/teamwork-api/project/getAllRoleType")
                .excludePathPatterns("/teamwork-api/project/queryAuditorStaffInfo")
                .excludePathPatterns("/teamwork-api/project/queryTaskByUserId")
                .excludePathPatterns("/teamwork-api/project/getSecUserByUserName")
                .excludePathPatterns("/teamwork-api/project/register")
                .excludePathPatterns("/location-api/billing/**");
      /* registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/login","/logout","/toLogin","/error/**","/upload/show/**","/static/**");*/
/**
        registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/location-api/**")
                .excludePathPatterns("/location-api/oa/**", "/location-api/sec/**")
                .excludePathPatterns("/location-api/open/**")
                .excludePathPatterns("/location-api/billing/**");
        */
    }

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(15000);//接口超时设置15秒
        configurer.registerCallableInterceptors(timeoutInterceptor());
    }
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

}
