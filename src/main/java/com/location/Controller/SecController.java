package com.location.Controller;

import com.location.bean.vo.*;
import com.location.service.impl.*;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.LoginConst;
import com.location.common.constants.WebConst;
import com.location.common.exception.WebException;
import com.location.common.utils.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "权限Controller",tags = {"权限Controller","权限Controller"})
@RestController
@RequestMapping(WebConst.UNIFY_URL_PREFIX + "/sec")
public class SecController extends BaseController {

    @Autowired
    SecUserServie secUserServie;

    @Autowired
    SecUserService secUserService;

    @Autowired
    SecMenuService secMenuService;

    @Autowired
    SecUserRoleRelService secUserRoleRelService;

    @Autowired
    SecRoleService secRoleService;

    @Autowired
    SecRolePermissionService secRolePermissionService;

    @Autowired
    SecPermissionEntityEtlService secPermissionEntityEtlService;

    @Autowired
    SecOrgService secOrgService;

    @Autowired
    SecStaffService secStaffService;

    @RequestMapping(value = "/userNameById/{id}", method = RequestMethod.GET)
    public ResponseVO getSecUserNameById(@PathVariable(value = "id") long userId) {
        //throw new WebException(WebExceptionEnum.PERMISSION_DENIED);
        return ResponseUtil.wrap(secUserServie.getSecUserNameById(userId));
    }

    @RequestMapping(value = "/menuName", method = RequestMethod.GET)
    public ResponseVO getSecMenuNameById() {
        return ResponseUtil.wrap(secMenuService.getMenuNameById());
    }

    @ApiOperation(value = "根据用户名查询对应权限", notes = "根据用户名查询对应权限")
    @ApiResponses({
            @ApiResponse(response = SecMenuVO.class, code = 1, message = "对应的权限结果")
    })
    @RequestMapping(value = "/getSecUserMenuByUserName", method = RequestMethod.GET)
    public ResponseVO getSecUserMenuByUserNmae(@RequestParam(value="userName",required = true) String userName)throws Exception{
        return ResponseUtil.wrap(secUserServie.getSecUserMenuByUserNmae(userName));
    }

    @ApiOperation(value = "查询登陆用户下所有的组织", notes = "查询登陆用户下所有的组织")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织ID",
                    required = false, dataType = "bigint",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "组织关系报文")
    })
    @RequestMapping(value = "/getSecOrgByOrgId" ,method = RequestMethod.GET)
    public ResponseVO getSecOrgByOrgId(@RequestParam(value="orgId") long orgId){
        try {
            return ResponseUtil.wrap(secOrgService.getSecOrgByOrgId(orgId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @ApiOperation(value = "组织新增，修改，删除", notes = "组织新增，修改，删除")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "secOrgSaveVO", value = "组织对象实体",
                    required = false, dataType = "secOrgSaveVO",dataTypeClass = SecOrgSaveVO.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "新增返回是否成功")
    })
    @RequestMapping(value = "/saveSecOrg" ,method = RequestMethod.POST)
    public ResponseVO saveSecOrg(@RequestBody SecOrgSaveVO secOrgSaveVO, HttpServletRequest request){
        if(secOrgSaveVO==null||secOrgSaveVO.getType()==null){
            return ResponseUtil.wrapException(new WebException("3001", "参数不能为空"));
        }
        if(secOrgSaveVO.getOrgId()!=null&&secOrgSaveVO.getRelatOrgId()!=null&&secOrgSaveVO.getOrgId()==secOrgSaveVO.getRelatOrgId()){
            return ResponseUtil.wrapException(new WebException("3001", "上级组织不能是当前组织"));
        }
        SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            secOrgService.saveSecOrg(secOrgSaveVO,secUserVO.getStaffId());
            return ResponseUtil.wrap("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
        }
    }
    @ApiOperation(value = "组织下所有账号查询", notes = "组织下所有账号查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "relatOrgId", value = "上级组织ID",
                    required = false, dataType = "bigint",dataTypeClass = Long.class),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态。1生效，0失效",
                    required = false, dataType = "bigint",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回该组织下账号和组织及角色信息")
    })
    @RequestMapping(value = "/getSecAccAuthByOrgIdAndUserId" ,method = RequestMethod.GET)
    public ResponseVO getSecAccAuthByOrgIdAndUserId(@RequestParam(value="relatOrgId") Long relatOrgId, @RequestParam(value = "userName",required = false)String userName, @RequestParam(value = "status")int status, @RequestParam(value = "page")int page, @RequestParam(value = "pageSize") int pageSize){
        try {
            return ResponseUtil.wrap(secOrgService.getSecAccAuthByOrgIdAndUserId(relatOrgId,userName,status,page,pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "账号下所有角色查询", notes = "账号下所有角色查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "userId", value = "账号ID",
                    required = false, dataType = "bigint",dataTypeClass = Long.class),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态，1生效，0失效",
                    required = false, dataType = "bigint",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/getRoleByUserId" ,method = RequestMethod.GET)
    public ResponseVO getRoleByUserId(@RequestParam(value = "userId")long userId, @RequestParam(value = "status")int status){
        try {
            return ResponseUtil.wrap(secUserRoleRelService.getRoleByUserId(userId,status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "组织下账号新增,修改，删除", notes = "组织下账号新增，修改，删除")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "secUserSaveVO", value = "账号新增实体",
                    required = false, dataType = "secUserSaveVO",dataTypeClass = SecUserSaveVO.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/saveUserInfo" ,method = RequestMethod.POST)
    public ResponseVO saveUserInfo(@RequestBody SecUserSaveVO secUserSaveVO, HttpServletRequest request){
        if(secUserSaveVO.getOrgId()==null||secUserSaveVO.getStaffName()==null){
            return ResponseUtil.wrapException(new WebException("3002","入参组织ID和用户昵称不能为空"));
        }
        SecUserVO secUserVO =(SecUserVO) request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            secUserServie.saveUserInfo(secUserSaveVO,secUserVO.getStaffId());
            return ResponseUtil.wrap("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002","保存异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织ID",
                    required = false, dataType = "orgId",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/getAllRoles" ,method = RequestMethod.GET)
    public ResponseVO getAllRoles(@RequestParam(value = "orgId")long orgId){
        try {
            return ResponseUtil.wrap(secOrgService.getAllRoles(orgId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "角色新增，修改，删除", notes = "角色新增，修改，删除")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "secRoleInfoVO", value = "账号新增实体",
                    required = false, dataType = "secRoleInfoVO",dataTypeClass = SecRoleInfoVO.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/saveSecRole" ,method = RequestMethod.POST)
    public ResponseVO saveSecRole(@RequestBody SecRoleInfoVO secRoleInfoVO, HttpServletRequest request){
        if(secRoleInfoVO.getType()==null){
            return ResponseUtil.wrapException(new WebException("3002","未获取到操作类型"));
        }
        SecUserVO secUserVO = (SecUserVO) request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            secRoleService.saveSecRole(secRoleInfoVO,secUserVO.getStaffId());
            return ResponseUtil.wrap("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002","保存异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "根据角色ID查询角色下所有信息", notes = "根据角色ID查询角色下所有信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "角色ID",
                    required = false, dataType = "roleId",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/getRoleInfoByRoleId" ,method = RequestMethod.GET)
    public ResponseVO getRoleInfoByRoleId(@RequestParam(value = "roleId")long roleId){
        try {
            return ResponseUtil.wrap(secRoleService.getRoleInfoByRoleId(roleId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }
    @ApiOperation(value = "查询权限管理页面所有权限", notes = "查询权限管理页面所有权限")
    @ApiImplicitParams(value = {

    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/getSecMenus" ,method = RequestMethod.GET)
    public ResponseVO getSecMenus(){
        try {
            return ResponseUtil.wrap(secMenuService.getSecMenus());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }

    @RequestMapping(value = "/generateBusinessId" ,method = RequestMethod.GET)
    public ResponseVO generateBusinessId()throws Exception{
        long businessId = secMenuService.generateBusinessId();
        return ResponseUtil.wrap(businessId);
    }

    @ApiOperation(value = "权限新增，删除，修改", notes = "权限新增，删除，修改")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "secMenusSaveVO", value = "保存对象实体",
                    required = false, dataType = "secMenusSaveVO",dataTypeClass = SecMenusSaveVO.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/saveSecMenus" ,method = RequestMethod.POST)
    public ResponseVO saveSecMenus(@RequestBody SecMenusSaveVO secMenusSaveVO, HttpServletRequest request){
        SecUserVO secUserVO = (SecUserVO) request.getSession().getAttribute(LoginConst.LOGIN_USER);
        if(secMenusSaveVO.getMenuId()==secMenusSaveVO.getParentId()){
            return ResponseUtil.wrapException(new WebException("3001","上级菜单不能是当前菜单"));
        }
        try {
            secMenuService.saveSecMenus(secMenusSaveVO,secUserVO.getStaffId());
            return ResponseUtil.wrap("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }

    @ApiOperation(value = "根据用户查询用户下所有权限", notes = "根据用户查询用户下所有权限")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "userId", value = "角色ID",
                    required = false, dataType = "userId",dataTypeClass = Long.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回信息")
    })
    @RequestMapping(value = "/getAuthByUserId" ,method = RequestMethod.GET)
    public ResponseVO getAuthByUserId(@RequestParam(value = "userId")long userId){
        try {
            return ResponseUtil.wrap(secUserService.getAuthByUserId(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001","查询异常"+e.getMessage()));
        }
    }


}
