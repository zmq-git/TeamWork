package com.location.dao;

import com.location.bean.po.InfoNotifyPO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/12/18
 * @Version V1.0
 **/
@Repository
@Mapper
public interface InfoNotifyDao {
    @Insert("insert into info_notify(id,info_type,subject,applicant,auditor,apply_time,approval_time,state,user_id,deal_state,url) values(#{id},#{infoType},#{subject},#{applicant},#{auditor},CURRENT_TIMESTAMP,#{approvalTime},#{state},#{userId},#{dealState},#{url})")
    public void addNotifyInfo(InfoNotifyPO infoNotifyPO) throws Exception;
    @Select("select nextval('info_id_sequence')")
    public BigDecimal getNotifyId() throws Exception;
    @Select("select * from info_notify where auditor = #{auditor} and state = #{state}")
    public List<InfoNotifyPO> getNotifyInfoByAuditor(@Param("auditor") BigDecimal auditor, @Param("state") Integer state) throws Exception;
    @Select("select * from info_notify where state = #{state}")
    public List<InfoNotifyPO> getNotifyInfoByState(Integer state) throws Exception;
    @Update("update info_notify set state = 2,approval_time = CURRENT_TIMESTAMP,deal_state = #{dealState} where id = #{id}")
    public void updateNotifyState(@Param("id") BigDecimal id, @Param("dealState") Integer dealState) throws Exception;
    @Select("select * from info_notify where id = #{id}")
    public InfoNotifyPO getNotifyInfoById(BigDecimal id) throws Exception;
    @Update("update info_notify set state = 0 where id = #{id}")
    public void deleteNotifyInfo(@Param("id") BigDecimal id) throws Exception;
}
