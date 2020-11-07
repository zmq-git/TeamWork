package com.location.Controller;

import com.location.bean.vo.LoginReqVO;
import com.location.bean.vo.SecUserVO;
import com.location.common.exception.ErrorCodeException;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.LoginConst;
import com.location.common.constants.WebConst;
import com.location.common.enums.WebExceptionEnum;
import com.location.common.exception.WebException;
import com.location.common.utils.IpUtil;
import com.location.common.utils.ResponseUtil;
import com.location.service.impl.SecUserRemoteService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 登录控制层
 * Created by liubj 2019/8/30.
 */
@Controller
@RequestMapping(WebConst.UNIFY_URL_PREFIX + "/oa")
@Api(value = "登陆Controller",tags = {"登陆Controller","登陆Controller"})
public class LoginController extends BaseController{

    @Autowired
    private SecUserRemoteService userSV;

    private transient final Logger log = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "用户登录", notes = "验证用户名和密码")
    @ApiResponses({
            @ApiResponse(response = SecUserVO.class, code = 1, message = "用户登录返回信息")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO login(@RequestBody LoginReqVO loginReqVO, HttpServletRequest request){
        HttpSession session = request.getSession();
        try {
            SecUserVO userInfo = userSV.login(loginReqVO.getUsername(), loginReqVO.getPassword());
            if(userInfo !=null && session !=null){
                userInfo.setIp(IpUtil.getIpAddr(request));
                userInfo.setSessionId(session.getId());
                session.setAttribute(LoginConst.LOGIN_USER, userInfo);//将用户信息放到session中
                return ResponseUtil.wrap(userInfo);
            }else{
                return ResponseUtil.wrap(userInfo);
            }
        } catch (Exception e) {
            if (e instanceof ErrorCodeException) {
                return ResponseUtil.wrapErrorCodeException((ErrorCodeException)e);
            } else {
                return ResponseUtil.wrapException(new WebException(WebExceptionEnum.OTHER_EXCEPTION));
            }
        }
    }

    @ApiOperation(value = "用户登出", notes = "用户退出登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "无入参", value = "无入参", required = false)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "页面返回统一封装对象")
    })
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO logout(HttpServletRequest request){
        HttpSession session = request.getSession();//获取当前session
        try {
            if(session !=null){
                session.removeAttribute(LoginConst.LOGIN_USER);
                session.invalidate();//关闭session
            }
            return ResponseUtil.wrap(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException(WebExceptionEnum.OTHER_EXCEPTION));
        }
    }

}
