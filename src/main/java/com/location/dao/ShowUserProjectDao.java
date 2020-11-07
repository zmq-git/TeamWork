package com.location.dao;


import com.location.bean.po.ProjectInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Repository
@Mapper
public interface ShowUserProjectDao {
    @Select("select * from project_info where state = #{status} and project_id in (select project_id from user_project_rel where user_id = (select user_id from user_info where user_account = #{userAccount}))")
    public List<ProjectInfoPO> findUserProject(@Param(value = "userAccount") String userAccount, @Param(value = "status") Integer status) throws Exception;
}
