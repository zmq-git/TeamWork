package com.location.dao;

import com.location.bean.po.TaskItemPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2020-4-16.
 */
@Mapper
@Repository
public interface TextDao {
    @Select("select * from task_item where TASK_ID=#{id} and STATE = 1")
    public TaskItemPO getTask(Integer id) throws Exception;
}
