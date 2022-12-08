package com.demo.demo3.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author jiangbing
 * @description 替换HttpServletRequest
 * @since 1.0
 **/
@Slf4j
public class ReplaceStreamFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //如果是文件上传则会报错可以判断是否是文件上传不读取流即可
        if(ServletFileUpload.isMultipartContent((HttpServletRequest) request)) {
            chain.doFilter(request, response);
            return;
        }else{
            ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
