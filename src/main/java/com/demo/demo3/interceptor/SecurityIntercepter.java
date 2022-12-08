package com.demo.demo3.interceptor;

import com.alibaba.fastjson.JSON;
import com.demo.demo3.annotation.SecurityParameter;
import com.demo.demo3.utils.AesUtils;
import jdk.internal.instrumentation.Logger;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class SecurityIntercepter implements HandlerInterceptor {
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---HandlerInterceptor---");
        boolean decode = false ; //默认情况是不进行解密
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod ();
        String httpMethod = request.getMethod() ;
        SecurityParameter serializedField  = handlerMethod.getMethodAnnotation(SecurityParameter.class) ;

//        System.out.println(request.getContentType());
//        System.out.println(MediaType.APPLICATION_JSON_VALUE) ;
        //  不拦截get请求
        if(httpMethod.equals("GET")) {
            return true;
            //如果是post请求且是json
        } else if(httpMethod.equals("POST")) {
            //如果类型是空就放行
            if (request.getContentType() == null) {
                return true;
            }
            //如果类型不是json 就放行
            if (!(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ||
                    request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                return true ;
        }
        System.out.println("--test1--"+request);
        //对没有要求加密的post请求也不拦截
        if(serializedField == null){
            System.out.println("--对没有要求加密的post请求也不拦截--");
            //如果没有用加密注解， 就直接放行。
            return true ;
        }
        decode = serializedField.inDecode() ;
        if(decode){
            System.out.println("进入解析json请求");
//            到这只解析有加密要求的注解和 POST请求中是json。不符合以上的直接不涉及数据加密
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String body = requestWrapper.getBodyString();
            Map map = JSON.parseObject(body, Map.class);
            Object content = map.get("content");
//            System.out.println(content);
            if(StringUtils.isEmpty(content)){
                //TODO:后面新增异常处理
//                throw new RuntimeException("秘钥为空！");
            }
            System.out.println("解密前-> "+content);
//            Object afterBody  = AesUtils.decrypt("Oj3IctQiSxsaZwcYoNp4jwZ/8w7tr+sIVZtQIvSZvDlfJhJBEMwXKxQxq9ROUCIY");

            Object afterBody  = AesUtils.decrypt(content.toString());
            System.out.println("解密后-> "+afterBody.toString());

            requestWrapper.setBody(afterBody.toString());
            String temp = new RequestWrapper(requestWrapper).getBodyString() ;
            System.out.println("过滤器中缓存"+temp);
//            stringRedisTemplate.opsForValue().set("asd","dederfr");

            System.out.println("---HandlerInterceptor--end--");
            return true;
        }
        return true;
    }
}