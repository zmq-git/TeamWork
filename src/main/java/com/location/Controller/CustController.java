package com.location.Controller;


import com.location.bean.vo.*;
import com.location.service.impl.SecUserSVImpl;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.LoginConst;
import com.location.common.constants.WebConst;
import com.location.common.exception.WebException;

import com.location.common.utils.RegPatternUtil;
import com.location.common.utils.ResponseUtil;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by wangcl on 2019/8/26.
 */
@Api(value = "客户Controller",tags = {"客户Controller","客户Controller"})
@RestController
@RequestMapping(WebConst.UNIFY_URL_PREFIX + "/cust")
public class CustController extends BaseController {

    @ApiOperation(value = "用户密码修改接口", notes = "用户密码修改接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名",
                    required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码",
                    required = true, dataType = "String", dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "页面返回统一封装对象")
    })
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public ResponseVO modifyPassword(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) throws Exception {
        secUserSV.modifyPassword(username, password);
        return ResponseUtil.wrap("修改成功！");
    }

    @ApiOperation(value = "用户基本信息修改接口", notes = "用户基本信息修改接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名",
                    required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "staffName", value = "昵称",
                    required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "portraitAddress", value = "头像",
                    required = false, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "mail", value = "邮箱",
                    required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "phoneNum", value = "手机号",
                    required = true, dataType = "String", dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/modifyStaffName", method = RequestMethod.POST)
    public ResponseVO modifyStaffName(@RequestBody SecUserVO secUserVO) throws Exception {
        try {
            if (secUserVO.getPortraitAddress() != null) {
                secUserVO.getPortraitAddress().getBytes();}
                secUserSV.modifyStaffName(secUserVO);
                return ResponseUtil.wrap("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "修改异常"));
        }
    }
    @Autowired
    SecUserSVImpl secUserSV;
}
