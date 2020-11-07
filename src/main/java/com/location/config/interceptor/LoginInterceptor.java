package com.location.config.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangcl on 2019/8/22.
 * 登录拦截
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html; charset=utf-8");

       /* if (null == request.getSession().getAttribute(LoginConst.LOGIN_USER)) {
            ResponseVO responseVO = ResponseUtil.wrapException(new WebException(WebExceptionEnum.NO_LOGIN));
            response.getWriter().write(new Gson().toJson(responseVO));
            return false;
        }*/
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
