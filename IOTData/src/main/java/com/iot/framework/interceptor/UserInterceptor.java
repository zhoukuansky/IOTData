package com.iot.framework.interceptor;

import com.iot.util.authentication.JwtToken;
import com.iot.util.authentication.PassToken;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * @author HP
 * 实现拦截器
 * public class UserInterceptor implements HandlerInterceptor
 * 继承HandlerInterceptorAdapter比实现HandlerInterceptor灵活，可以只写需要的方法
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        if (null == token) {
            throw new DescribeException(ExceptionEnum.NEED_LOGIN);
        }
        // 执行认证
        Map<String, Object> tokenData = JwtToken.verifyToken(token);
        if (tokenData == null) {
            throw new DescribeException(ExceptionEnum.TOKEN_OUTTIME);
        }
        httpServletRequest.setAttribute("currentUser", tokenData);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}