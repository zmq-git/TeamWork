package com.location.config.interceptor;


import com.google.gson.Gson;
import com.location.bean.vo.SecUserVO;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.LoginConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by shijian on 2019/9/29.
 */
@Component
public class TimeOutInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SecUserVO secUserVO =  (SecUserVO)session.getAttribute(LoginConst.LOGIN_USER);
        if(secUserVO==null){
            //System.out.println("session过期，进入超时拦截器");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.getWriter().write(new Gson().toJson(new ResponseVO(LoginConst.LOGIN_TIMEOUT, "登陆超时，请重新登陆", "")));
            return false;
        }
        return true;
    }
}
