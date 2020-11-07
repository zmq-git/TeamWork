package com.location.config.interceptor;


import com.location.bean.vo.SecOperateDO;
import com.location.bean.vo.SecUserVO;
import com.location.common.constants.LoginConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by shijian on 2019/9/29.
 */
@Component
public class UrlInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("进入权限拦截器");
        HttpSession session = request.getSession();
        String path = request.getServletPath();
        SecUserVO secUserVO =  (SecUserVO)session.getAttribute(LoginConst.LOGIN_USER);
        if(secUserVO==null|| "".equals(secUserVO)){
            System.out.println("session过期，进入超时拦截器");
            System.out.println("接口被拦截"+path);
            //response.getWriter().write(new Gson().toJson(new ResponseVO("4000", "", "")));
            //response.sendRedirect("/");
            return false;
        }else {
            //admin用户不校验
            if("admin".equals(secUserVO.getUsername())){
                return true;
            }else {
                List<SecOperateDO> secOperateDOS = secUserVO.getSecMenuVO().getSecOperateDOS();
                for(int i =0;i<secOperateDOS.size();i++){
                    if(path.equals(secOperateDOS.get(i).getDescription())){
                        System.out.println("接口权限通过"+path);
                        return true;
                    }
                }
                System.out.println("接口被拦截"+path);
                return false;
            }

        }
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle被调用");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.println("afterCompletion被调用");
    }
}

