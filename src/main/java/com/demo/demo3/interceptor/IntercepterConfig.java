package com.demo.demo3.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    /**
     * 添加Web项目的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//         对所有访问路径，都通过MyInterceptor类型的拦截器进行拦截
        //添加拦截器
              InterceptorRegistration registration = registry.addInterceptor(new SecurityIntercepter());
               //排除的路径
//                registration.excludePathPatterns("/login");
//                registration.excludePathPatterns("/logout");
                 //将这个controller放行
                registration.excludePathPatterns("/error");
                //拦截全部
                registration.addPathPatterns("/**");
//        registry.addInterceptor(new SecurityIntercepter()).addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/index.html", "/user/login", "/css/**", "/images/**", "/js/**", "/fonts/**");
//        放行登录页，登陆操作，静态资源
    }

}
