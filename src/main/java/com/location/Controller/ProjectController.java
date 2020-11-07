package com.location.Controller;


import com.location.bean.po.TaskItemPO;
import com.location.bean.vo.SecUserVO;
import com.location.bean.vo.*;
import com.location.service.*;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.LoginConst;
import com.location.common.constants.WebConst;
import com.location.common.exception.WebException;
import com.location.common.utils.ResponseUtil;
import com.location.service.impl.SecUserService;
import com.location.service.impl.TaskDealServiceImpl;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ProjectController
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/7
 * @Version V1.0
 **/

@Api(value = "项目Controller",tags = {"项目Controller","项目Controller"})
@RestController
@RequestMapping(WebConst.UNIFY_URL_PREFIX + "/project")
public class ProjectController extends BaseController {
    SecUserService secUserService;

    @Autowired
    ProjectUserQService projectUserQService;

    @Autowired
    UserService userService;

    @Autowired
    ShowUserProjectService showUserProjectService;

    @Autowired
    ItemRelatService itemRelatService;

    @Autowired
    TaskDealService taskDealService;

    @Autowired
    ProjectService projectService;

    @Autowired
    InterationService interationService;

    @Autowired
    SecOrgService secOrgService;

    @Autowired
    InfoNotifyService infoNotifyService;



    private transient static final Log log = LogFactory.getLog(ProjectController.class);

    @ApiOperation(value = "项目成员新增", notes = "项目成员新增")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "项目成员新增结果")
    })
    @RequestMapping(value = "/addProjectMember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO addProjectMember(@RequestBody ProjectUserAddVO projectUserAddVO, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            projectUserAddVO.setCreater(new BigDecimal(secUserVO.getStaffId()));
            return ResponseUtil.wrap(projectUserQService.addProjectMember(projectUserAddVO));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
            //return null;
        }

    }

    @ApiOperation(value = "根据成员名称模糊查询", notes = "根据成员名称模糊查询")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "成员信息")
    })
    @RequestMapping(value = "/getUserInfoByUserName", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getUserInfoByUserName(@RequestParam(value = "userName",required = true) String userName){
        try{
            return ResponseUtil.wrap(userService.getUserInfobyUserName(userName));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
            //return null;
        }

    }

    @ApiOperation(value = "根据任务分类id获取任务分类树结构", notes = "根据任务分类id获取任务分类树结构")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回任务分类树状结构")
    })
    @RequestMapping(value = "/getTaskClassificationById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getTaskClassificationById(@RequestParam(value = "taskId",required = true) BigDecimal taskId){
        try{
            return ResponseUtil.wrap(itemRelatService.getTaskClassificationById(taskId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
            //return null;
        }

    }
    @ApiOperation(value = "任务分类新增，修改，删除", notes = "任务分类新增，修改，删除")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "新增返回是否成功")
    })
    @RequestMapping(value = "/taskDeal" ,method = RequestMethod.POST)
    public ResponseVO taskDeal(@RequestBody ItemRelatVO itemRelatVO, HttpServletRequest request){
        if(itemRelatVO==null||itemRelatVO.getType()==null){
            return ResponseUtil.wrapException(new WebException("3001", "参数不能为空"));
        }
        if(itemRelatVO.getClassId()!=null&&itemRelatVO.getParentClassId()!=null&&itemRelatVO.getClassId()==itemRelatVO.getParentClassId()){
            return ResponseUtil.wrapException(new WebException("3001", "上级分类不能是当前分类"));
        }
        SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            itemRelatService.taskClassificationDeal(itemRelatVO,new BigDecimal(secUserVO.getStaffId()));
            return ResponseUtil.wrap("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "根据任务id删除任务", notes = "根据任务id删除任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回任务删除结果")
    })
    @RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO deleteTaskById(@RequestParam(value = "taskId",required = true) BigDecimal taskId){
        try{
            taskDealService.deleteTask(taskId);
            return ResponseUtil.wrap("处理成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "删除异常" + e.getMessage()));
            //return null;
        }

    }
    @ApiOperation(value = "根据迭代id获取迭代任务", notes = "根据迭代id获取迭代任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "根据迭代id获取迭代任务")
    })
    @RequestMapping(value = "/queryTaskByIterationId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTaskByIterationId(@RequestParam(value = "IterationId",required = true) BigDecimal IterationId, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(taskDealService.query3TaskByIterationId(IterationId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
            //return null;
        }

    }

    @ApiOperation(value = "任务修改", notes = "任务修改")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回是否成功")
    })
    @RequestMapping(value = "/editTask" ,method = RequestMethod.POST)
    public ResponseVO taskDeal(@RequestBody EditTaskVO editTaskVO, HttpServletRequest request){
        if(editTaskVO==null||editTaskVO.getId()==null){
            return ResponseUtil.wrapException(new WebException("3001", "参数不能为空"));
        }
        if(editTaskVO.getId()!=null&&editTaskVO.getParentId()!=null&&editTaskVO.getId()==editTaskVO.getParentId()){
            return ResponseUtil.wrapException(new WebException("3001", "当前id不能是父级分类"));
        }
        SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            taskDealService.editTask(editTaskVO);
            return ResponseUtil.wrap("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "修改异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "根据任务id添加子任务", notes = "根据任务id添加子任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回是否成功")
    })
    @RequestMapping(value = "/addChildTask" ,method = RequestMethod.POST)
    public ResponseVO addChildTask(@RequestBody AddTaskVO addTaskVO, HttpServletRequest request){
        if(addTaskVO==null&&addTaskVO.getTaskTrip()==null){
            return ResponseUtil.wrapException(new WebException("3001", "参数不能为空"));
        }
        if(addTaskVO.getId()!=null&&addTaskVO.getParentId()!=null&&addTaskVO.getId()==addTaskVO.getParentId()){
            return ResponseUtil.wrapException(new WebException("3001", "当前id不能是父级分类"));
        }
        SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            taskDealService.addTask(addTaskVO,new BigDecimal(secUserVO.getStaffId()));
            return ResponseUtil.wrap("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "修改异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "根据用户id查询所有迭代", notes = "根据用户id查询所有迭代")
    @ApiResponses({
            @ApiResponse(response = InterationInfoVO.class, code = 1, message = "迭代信息list格式")
    })
    @RequestMapping(value = "/getIterInfoByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getIterInfoByUserId(@RequestParam(value = "userId",required = true) BigDecimal userId){
        try{
            return ResponseUtil.wrap(interationService.findInterInfoByUserId(userId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
            //return null;
        }

    }

    @ApiOperation(value = "新增迭代", notes = "新增迭代")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回是否成功")
    })
    @RequestMapping(value = "/addInteraitonInfo" ,method = RequestMethod.POST)
    public ResponseVO addInteraitonInfo(@RequestBody InterationInfoVO interationInfoVO, HttpServletRequest request){
        if(interationInfoVO==null||interationInfoVO.getProjectId()==null){
            return ResponseUtil.wrapException(new WebException("3001", "参数不能为空"));
        }
        SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
        try {
            interationService.addInterInfo(interationInfoVO,new BigDecimal(secUserVO.getStaffId()));
            return ResponseUtil.wrap("创建成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "保存新建项目信息", notes = "保存新建项目信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "ProjectInfoVO", value = "项目对象实体",
                    required = true, dataType = "ProjectInfoVO", dataTypeClass = ProjectInfoVO.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "新建项目是否成功")
    })
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO save(@RequestBody ProjectInfoVO projectInfoVO, HttpServletRequest request){
        try{
                SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
                projectService.addProjectInfo(projectInfoVO,new BigDecimal(secUserVO.getStaffId()));
                return ResponseUtil.wrap("新建项目成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
            //return null;
        }
    }

    @ApiOperation(value = "查询用户已加入的项目", notes = "查询用户已加入的项目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "userAccount", value = "用户名",
                    required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回用户加入的项目")
    })
    @RequestMapping(value = "/findUserProjectInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO findUserProject(@RequestParam String userAccount){
        try{
            return ResponseUtil.wrap(showUserProjectService.findUserAllProject(userAccount));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
            //return null;
        }
    }

    @ApiOperation(value = "查询所有项目", notes = "查询所有项目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页面数量",
                    required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "分页位置",
                    required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "projectPeriod", value = "项目状态",
                    required = false, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回所有的项目")
    })
    @RequestMapping(value = "/findAllProjectInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getAllProject(@RequestParam(value = "pageSize") Integer pageSize, @RequestParam("current") Integer current, @RequestParam("projectPeriod") Integer projectPeriod, HttpServletRequest request) {
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(projectService.getProjectAll(pageSize,current,secUserVO.getStaffId(),projectPeriod));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "精确查询项目名", notes = "精确查询项目名")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectName", value = "项目名称",
                    required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回项目名是否存在")
    })
    @RequestMapping(value = "/findPreciseProject", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO findPreciseProject(@RequestParam(value = "projectName", required = true) String projectName){
        try{
            return ResponseUtil.wrap(projectService.getProjectInfo(projectName));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "根据项目名模糊查询", notes = "根据项目名模糊查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectName", value = "项目名称",
                    required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回模糊查询的项目")
    })
    @RequestMapping(value = "/findProjectByProjectName", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO findProjectByProjectName(@RequestParam(value = "projectName", required = true) String projectName, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(projectService.getProjectInfoByProjectName(projectName,new BigDecimal(secUserVO.getStaffId())));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "删除项目", notes = "删除项目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目编号",
                    required = true, dataType = "BigDecimal")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "删除项目的结果")
    })
    @RequestMapping(value = "/deleteProjectInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO deleteProjectInfo(@RequestParam(value = "projectId", required = true) BigDecimal projectId){
        try{
            projectService.deleteProjectInfo(projectId);
            return ResponseUtil.wrap(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "删除异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "修改项目信息", notes = "修改项目信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "ProjectInfoVO", value = "项目对象实体",
                    required = true, dataType = "ProjectInfoVO", dataTypeClass = ProjectInfoVO.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回修改后的项目信息")
    })
    @RequestMapping(value = "/updateProjectInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO updateProjectInfo(@RequestBody ProjectInfoVO projectInfoVO){
        try{
            projectService.updateProjectInfo(projectInfoVO);
            return ResponseUtil.wrap(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "修改异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询项目数量", notes = "查询项目数量")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectPeriod", value = "项目状态",
                    required = true, dataType = "Integer", dataTypeClass = Integer.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "项目数量")
    })
    @RequestMapping(value = "/getProjectNumber", method = RequestMethod.GET)
    public ResponseVO getProjectNumber(@RequestParam("projectPeriod") Integer projectPeriod, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(projectService.getProjectNumber(secUserVO.getStaffId(),projectPeriod));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }


    @ApiOperation(value = "根据状态编码和状态类型获取状态名称", notes = "根据状态编码和状态类型获取状态名称")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "stateId", value = "状态类型ID",
                    required = true, dataType = "BigDecimal", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "stateCode", value = "状态code",
                    required = true, dataType = "Integer", dataTypeClass = String.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "状态名称")
    })
    @RequestMapping(value = "/queryTaskStateInfoById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTaskStateInfoById(@RequestParam(value = "stateId",required = true) BigDecimal stateId, @RequestParam(value = "stateCode",required = true) Integer stateCode) {
        try {
            return ResponseUtil.wrap(itemRelatService.getStateNameByStateCode(stateId, stateCode));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
            //return null;
        }
    }
    @ApiOperation(value = "根据项目id查询所有迭代", notes = "根据项目id查询所有迭代")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目编号",
                    required = true, dataType = "BigDecimal")
    })
    @ApiResponses({
            @ApiResponse(response = InterationInfoVO.class, code = 1, message = "迭代信息list格式")
    })
    @RequestMapping(value = "/getIterInfoByProjectId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getIterInfoByProjectId(@RequestParam(value = "projectId",required = true) BigDecimal projectId){
        try{
            return ResponseUtil.wrap(interationService.findInterInfoByProjectId(projectId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));

        }

    }


    @ApiOperation(value = "根据分类id获取分类关联状态", notes = "根据分类id获取分类关联状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "classificationId", value = "分类id",
                    required = true, dataType = "BigDecimal", dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "分类名称")
    })
    @RequestMapping(value = "/queryStateInfoById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryStateInfoById(@RequestParam(value = "classificationId",required = true) BigDecimal classificationId){
        try{
            return ResponseUtil.wrap(itemRelatService.queryClassInfoById(classificationId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }

    }

    @ApiOperation(value = "根据分类id获取分类名称", notes = "根据分类id获取分类名称")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "classificationId", value = "分类id",
                    required = true, dataType = "BigDecimal", dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "分类名称")
    })
    @RequestMapping(value = "/queryItemNameById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryItemNameById(@RequestParam(value = "classificationId",required = true) BigDecimal classificationId){
                try {
                    return ResponseUtil.wrap(itemRelatService.queryItemNameById(classificationId));
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
                }
    }

    @ApiOperation(value = "精确查询迭代名", notes = "精确查询迭代名")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "interationName", value = "迭代名称",
                    required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "迭代id",
                    required = true, dataType = "BigDecimal")
    })
    @ApiResponses({
            @ApiResponse(response = InterationInfoVO.class, code = 1, message = "迭代名称是否存在")
    })
    @RequestMapping(value = "/findPreciseInterationName", method = RequestMethod.GET)
    public ResponseVO findPreciseInteration(@RequestParam(value = "interationName") String interationName, @RequestParam(value = "projectId") BigDecimal projectId) {
        try{
            return ResponseUtil.wrap(interationService.getInterInfo(interationName,projectId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "新增迭代下任务", notes = "新增迭代下任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回是否成功")
    })
    @RequestMapping(value = "/addInterationTask", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO addInterationTask(@RequestBody AddTaskVO addTaskVO, HttpServletRequest request){
        try {
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            taskDealService.addInterationTask(addTaskVO,addTaskVO.getInterationId(),new BigDecimal(secUserVO.getStaffId()));
            return ResponseUtil.wrap("创建成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "保存异常" + e.getMessage()));
        }

    }
    @ApiOperation(value = "根据任务id修改任务状态", notes = "根据任务id修改任务状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "taskId", value = "任务id",
                    required = true, dataType = "BigDecimal", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "taskState", value = "任务状态",
                    required = true, dataType = "Integer", dataTypeClass = String.class),
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回任务状态修改结果")
    })
    @RequestMapping(value = "/updateTaskState", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO updateTaskState(@RequestParam(value = "taskId",required = true) BigDecimal taskId, @RequestParam(value = "taskState",required = true) Integer taskState){
        try{
            taskDealService.updateTaskState(taskId,taskState);
            return ResponseUtil.wrap("处理成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "状态修改异常" + e.getMessage()));
            //return null;
        }

    }


    @ApiOperation(value = "成员注册", notes = "成员注册")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "成员注册结果")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO register(@RequestBody RegisterVO registerVO) {
        try {
            userService.addUser(registerVO);
            return ResponseUtil.wrap("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "注册异常" + e.getMessage()));
            //return null;
        }
    }

    @ApiOperation(value = "根据账号精确查询用户", notes = "根据账号精确查询用户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "userName", value = "账号",
                    required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回用户是否存在")
    })
    @RequestMapping(value = "/getSecUserByUserName", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getSecUserByUserName(@RequestParam(value = "userName", required = true) String userName){
        try{
            return ResponseUtil.wrap(userService.getSecUserByUserName(userName));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }


    @ApiOperation(value = "查询所有角色名字", notes = "查询所有角色名字")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回角色姓名")
    })
    @RequestMapping(value = "/getAllRoleType", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getAllRoleType(Integer roleType){
        try{
            return ResponseUtil.wrap(userService.getAllRoleType(roleType));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询所有公司和部门名字", notes = "查询所有公司和部门名字")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回公司和部门名字")
    })
    @RequestMapping(value = "/getAllOrgName", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getAllOrgName(){
        try{
            return ResponseUtil.wrap(secOrgService.getAllOrgName());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询所有的组织树状信息", notes = "查询所有的组织树状信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织id",
                    required = true, dataType = "Long")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回所有组织的层级信息")
    })
    @RequestMapping(value = "/getAllOrgInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getAllOrgInfo(@RequestParam Long orgId){
        try{
            return ResponseUtil.wrap(secOrgService.getAllOrgInfo(orgId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }


    @ApiOperation(value = "根据迭代id查询项目成员信息", notes = "根据迭代id查询项目成员信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "interationId", value = "迭代ID",
                    required = true, dataType = "BigDecimal")
    })
    @ApiResponses({
            @ApiResponse(response = UserStaffVO.class, code = 1, message = "项目成员信息")
    })
    @RequestMapping(value = "/queryMemberByIteId", method = RequestMethod.GET)
    public ResponseVO queryMemberByIteId(@RequestParam("interationId") BigDecimal interationId){
        try{
            return ResponseUtil.wrap(interationService.getUsersByInterationId(interationId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "修改任务归属迭代", notes = "修改任务归属迭代")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回修改任务归属迭代结果")
    })
    @RequestMapping(value = "/updateTaskIter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO updateTaskIter(@RequestParam(value = "taskId",required = true) BigDecimal taskId, @RequestParam(value = "iterId",required = true) BigDecimal iterId){
        try{
            taskDealService.updateTaskIter(taskId,iterId);
            return ResponseUtil.wrap("处理成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "修改异常" + e.getMessage()));
            //return null;
        }

    }


    @ApiOperation(value = "根据审核人查询消息内容", notes = "根据审核人查询消息内容")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回消息内容")
    })
    @RequestMapping(value = "/queryNotifyByAuditor", method = RequestMethod.GET)
    public ResponseVO queryNotifyByAuditor(@RequestParam(defaultValue = "1",required = false) Integer state, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(infoNotifyService.getNotifyByAuditor(new BigDecimal(secUserVO.getStaffId()),state));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "消息更新接口", notes = "消息更新接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息信息id",
                    required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "auditState", value = "审核状态",
                    required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回消息更新结果")
    })
    @RequestMapping(value = "/updateNotifyInfoState", method = RequestMethod.GET)
    public ResponseVO updateNotifyInfoState(@RequestParam("id") BigDecimal id, @RequestParam("auditState") Integer auditState){
        try{
            infoNotifyService.updateNotifyInfoState(id,auditState);
            return ResponseUtil.wrap("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "修改异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询组织下的所有用户信息", notes = "查询组织下的所有用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织id",
                    required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回查询到的用户信息")
    })
    @RequestMapping(value = "/queryStaffInfoByOrgId", method = RequestMethod.GET)
    public ResponseVO queryStaffInfoByOrgId(@RequestParam("orgId") Integer orgId) {
        try {
            return ResponseUtil.wrap(userService.getStaffInfoByOrgId(orgId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "全量模版查询", notes = "全量模版查询")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "分类名称")
    })
    @RequestMapping(value = "/queryTemplate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTemplate(Integer state,HttpServletRequest request){
        try{
            return ResponseUtil.wrap(itemRelatService.queryTemplate(state));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "模版创建", notes = "模版创建")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "模版创建")
    })
    @RequestMapping(value = "/createTemplate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO createTemplate(@RequestBody TemplateInfoVO templateInfoVO, HttpServletRequest request){
        try{
            if(templateInfoVO ==null){
                throw new Exception("参数为空");
            }
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            itemRelatService.createTemplate(templateInfoVO,new BigDecimal(secUserVO.getStaffId()));//new BigDecimal(secUserVO.getStaffId())
            return ResponseUtil.wrap("创建成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "创建异常" + e.getMessage()));
        }

    }

    @ApiOperation(value = "模版查询", notes = "模版查询")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "模板信息")
    })
    @RequestMapping(value = "/queryTemplateById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTemplateById(BigDecimal id,HttpServletRequest request){
        try{
            return ResponseUtil.wrap(itemRelatService.queryTemplateById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询项目下的所有用户信息", notes = "查询项目下的所有用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id",
                    required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回查询到的用户信息")
    })
    @RequestMapping(value = "/queryStaffInfoByProjectId", method = RequestMethod.GET)
    public ResponseVO queryStaffInfoByProjectId(@RequestParam("projectId") Integer projectId) {
        try {
            return ResponseUtil.wrap(userService.getStaffInfoByProjectId(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }


    @ApiOperation(value = "移除项目成员", notes = "移除项目成员")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id",
                    required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "body", name = "userIdList", value = "项目成员id",
                    required = true, dataType = "List")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回删除结果")
    })
    @RequestMapping(value = "/deleteProjectMember", method = RequestMethod.GET)
    public ResponseVO deleteProjectMember(@RequestParam("projectId") Integer projectId, @RequestParam("userIdList") List<Integer> userIdList) {
        try {
            projectUserQService.deleteProjectMember(projectId,userIdList);
            return ResponseUtil.wrap("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "删除异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "模板删除", notes = "模板删除")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "模板删除")
    })
    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO deleteTemplate(BigDecimal id,HttpServletRequest request){
        try{
            itemRelatService.deleteTemplate(id);
            return ResponseUtil.wrap("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "删除异常：" + e.getMessage()));
        }
    }

    @ApiOperation(value = "修改分类关联模版", notes = "修改分类关联模版")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "修改分类关联模版")
    })
    @RequestMapping(value = "/updateClassTemplate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO updateClassTemplate(BigDecimal classId,BigDecimal templateId,HttpServletRequest request){
        try{
            itemRelatService.updateClassTemplate(classId,templateId);
            return ResponseUtil.wrap("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "修改异常：" + e.getMessage()));
        }
    }


    @ApiOperation(value = "查询审核人信息", notes = "查询审核人信息")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回审核人信息")
    })
    @RequestMapping(value = "/queryAuditorStaffInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryAuditorStaffInfo() {
        try {
            return ResponseUtil.wrap(userService.getAuditorStaffInfo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "获取全量分类", notes = "获取全量分类")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回全量分类")
    })
    @RequestMapping(value = "/getTaskClassification", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO getTaskClassification(){
        try{
            return ResponseUtil.wrap(itemRelatService.getTaskClassification());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "查询异常" + e.getMessage()));
            //return null;
        }

    }

    @ApiOperation(value = "根据迭代id获取迭代下所有任务", notes = "根据迭代id获取迭代下所有任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "根据迭代id获取迭代下所有任务")
    })
    @RequestMapping(value = "/queryAllTaskByIterationId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryAllTaskByIterationId(@RequestParam(value = "IterationId",required = true) BigDecimal IterationId, HttpServletRequest request){
        try{
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            return ResponseUtil.wrap(taskDealService.queryTaskByIterationId(IterationId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
            //return null;
        }

    }


    @ApiOperation(value = "查询项目类型", notes = "查询项目类型")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回项目类型")
    })
    @RequestMapping(value = "/queryProjectType", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryProjectType() {
        try {
            return ResponseUtil.wrap(projectService.getProjectTypeInfo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "根据分类ID查询模板信息", notes = "根据分类ID查询模板信息")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "根据分类ID查询模板信息")
    })
    @RequestMapping(value = "/queryTemplateInfoByClassId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTemplateInfoByClassId(BigDecimal id,HttpServletRequest request){
        try{
            return ResponseUtil.wrap(itemRelatService.queryTemplateByclassificationId(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "修改项目成员角色", notes = "修改项目成员角色")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id",
                    required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "项目成员id",
                    required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "body", name = "roleIdList", value = "角色id",
                    required = true, dataType = "List")
    })
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回修改结果")
    })
    @RequestMapping(value = "/updateProjectMemberRole", method = RequestMethod.GET)
    public ResponseVO updateProjectMemberRole(@RequestParam("projectId") Integer projectId, @RequestParam("userId") Integer userId,
                                              @RequestParam("roleIdList") List<Integer> roleIdList, HttpServletRequest request) {
        try {
            SecUserVO secUserVO =(SecUserVO)request.getSession().getAttribute(LoginConst.LOGIN_USER);
            projectUserQService.updateProjectMenberRole(projectId,userId,roleIdList,secUserVO.getStaffId());
            return ResponseUtil.wrap("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3002", "修改异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询所有消息类型名字", notes = "查询所有消息类型名字")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "返回所有消息类型名字")
    })
    @RequestMapping(value = "/queryInfoTypeName", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryInfoTypeName(){
        try{
            return ResponseUtil.wrap(infoNotifyService.getInfoTypeName());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }

    @ApiOperation(value = "查询用户下的任务", notes = "查询用户下的任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "查询用户下的任务")
    })
    @RequestMapping(value = "/queryTaskByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryTaskByUserId(Integer userId){
        try{
            return ResponseUtil.wrap(taskDealService.queryTaskByUserId(userId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询异常" + e.getMessage()));
        }
    }
    @ApiOperation(value = "提交任务", notes = "提交任务")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "提交任务")
    })
    @RequestMapping(value = "/submitTask", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO submitTask(BigDecimal taskId){
        try{
            taskDealService.submitTask(taskId);
            return ResponseUtil.wrap("提交成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "提交失败" + e.getMessage()));
        }
    }
    @ApiOperation(value = "判断当前用户是否为项目管理员", notes = "判断当前用户是否为项目管理员")
    @ApiResponses({
            @ApiResponse(response = ResponseVO.class, code = 1, message = "判断当前用户是否为项目管理员")
    })
    @RequestMapping(value = "/queryProjectRole", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO queryProjectRole(BigDecimal projectId,BigDecimal userId){
        try{
            return ResponseUtil.wrap(projectService.queryProjectRole(projectId,userId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.wrapException(new WebException("3001", "查询失败" + e.getMessage()));
        }
    }
}