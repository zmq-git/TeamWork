package com.location.dao;

import com.location.bean.po.UserProjectRelPO;
import com.location.bean.vo.InterationInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/7
 * @Version V1.0
 **/

@Repository
@Mapper
public interface ProjectUserDao extends TeamBaseDao{
    @Insert("insert into user_project_rel (REL_ID,USER_ID,PROJECT_ID,STATE,PROJECT_ROLE,CREATE_DATE,CREATER,DONE_DATE,DONE_CODE,EFF_DATE,EXP_DATE) values (#{relId},#{userId},#{projectId},#{state},#{projectRole},now(),#{creater},now(),#{doneCode},#{effDate},#{expDate}) ")
    public void save(UserProjectRelPO userProjectRelPO) throws Exception;
    @Select("select NEXTVAL('user_project_seq')")
    public BigDecimal getRelId()throws Exception;
    @Select("select * from user_project_rel where userId = #{userId}")
    public List<UserProjectRelPO> getProjetInfoByUserId(BigDecimal userId)throws Exception;
    @Select("select PROJECT_ID from user_project_rel where userId = #{userId}")
    public List<BigDecimal> getProjetIdByUserId(BigDecimal userId)throws Exception;
    @Select("select INTERATION_ID as interationId,INTERATION_NAME as interationName,INTERATION_DESC as interationDesc,CREATER as creater,INTERATION_PICTURE as interationPicture,CREATE_DATE as createDate,START_DATE as startDate,END_DATE as endDate from INTERATION_ITEM where INTERATION_ID in (select INTERATION_ID from PROJECT_INTERATION_REL where PROJECT_ID in (select PROJECT_ID from USER_PROJECT_REL where user_id =  #{userId}) and state =1)")
    public List<InterationInfoVO> getIterInfoByUserId(BigDecimal userId) throws Exception;
    @Select("select user_Id from user_project_rel where PROJECT_ID = #{projectId}")
    public List<BigDecimal> getUserIdsByprojectId(BigDecimal projectId)throws Exception;
}