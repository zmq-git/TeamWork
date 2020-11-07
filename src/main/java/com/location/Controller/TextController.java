package com.location.Controller;

import com.location.bean.po.TaskItemPO;
import com.location.dao.TextDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020-4-16.
 */
@RestController
@Api(value = "学生Controller",tags = {"项目Controller","项目Controller"})
public class TextController {

    @Autowired
    TextDao textDao;

    @ApiOperation(value = "查询所有学生", notes = "查询所有学生")
    @RequestMapping(value = "/queryTaskByUserId", method = RequestMethod.GET)
    @ResponseBody
    public TaskItemPO getTaskById(Integer id) {
        try {
            TaskItemPO taskItemPO =  textDao.getTask(id);
            return taskItemPO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
