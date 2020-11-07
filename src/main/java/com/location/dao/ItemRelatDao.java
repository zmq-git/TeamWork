package com.location.dao;

import com.location.bean.po.ItemRelatPO;
import com.location.bean.vo.TemplateInfoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/

@Repository
@Mapper
public interface ItemRelatDao extends TeamBaseDao{
    @Select("select id as classId, STATE_NAME as className from ITEM_RELAT where id = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public Map getItemRelatById(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;
    @Select("select id from ITEM_RELAT where ITEM_ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public List<BigDecimal> getItemRelatByItemId(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;
    @Select("select NEXTVAL(#{seqName})")
    public BigDecimal getNewId(String sequence)throws Exception;

    @Insert("insert into ITEM_RELAT(" +
            "id," +
            "RELAT_ID," +
            "PROJECT_ID," +
            "ITEM_ID," +
            "RELAT_ITEM_ID," +
            "RELAT_TYPE," +
            "STATE," +
            "CREATER," +
            "DONE_CODE," +
            "CREATE_DATE," +
            "DONE_DATE," +
            "DES," +
            "STATE_CODE," +
            "STATE_NAME,"+
            "TORSION_RELATION )values(" +
            "#{id}," +
            "#{relatId}," +
            "#{projectId}," +
            "#{itemId}," +
            "#{relatItemId}," +
            "#{relatType}," +
            "#{state}," +
            "#{creater}," +
            "#{doneCode}," +
            "CURRENT_TIMESTAMP," +
            "CURRENT_TIMESTAMP," +
            "#{describe}," +
            "#{state_code}," +
            "#{stateName},"+
            "#{torsionRelation})")
    public void saveItemRelat(ItemRelatPO itemRelatPO)throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP,STATE=0 where  ITEM_ID=#{id}")
    public void delItemRelatChild(@Param(value = "id") BigDecimal id)throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP, ITEM_ID= #{itemId},STATE_NAME=#{stateName} where id = #{id}")
    public void updateItemRelat(ItemRelatPO itemRelatPO)throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP,STATE=0 where id = #{id} and RELAT_TYPE =#{relatType}")
    public void delItemRelat(@Param(value = "id") BigDecimal id, @Param(value = "relatType") String relatType)throws Exception;

    @Select("select STATE_NAME from ITEM_RELAT where RELAT_ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType} and STATE_CODE =#{stateCode}")
    public String getStateNameByStateCode(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType, @Param(value = "stateCode") Integer stateCode) throws Exception;

    @Select("select STATE_NAME from ITEM_RELAT where id = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public String getStateNameById(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;
    @Select("select ID as id,STATE_CODE as stateCode,STATE_NAME as stateName,DES as des,TORSION_RELATION as torsionRelation from ITEM_RELAT where RELAT_ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public List<Map> getStateInfoById(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;

    @Select("<script>select id,des ,creater,state,CREATE_DATE as createDate,STATE_NAME as templateName from ITEM_RELAT where <if test='id!=null'>ID = #{id} and </if> <if test='status!=null'>STATE =#{status} and </if> RELAT_TYPE =#{relatType}</script>")
    public List<TemplateInfoVO> queryTemplate(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP,STATE=0 where  ID=#{id}")
    public void delelteRecord(@Param(value = "id") BigDecimal id)throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP,STATE=0 where  RELAT_ID=#{id}")
    public void delelteRelRecord(@Param(value = "id") BigDecimal id)throws Exception;

    @Select("select id from ITEM_RELAT where RELAT_ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public List<BigDecimal> getItemRelatByRelateId(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;

    //根据id获取关联RELAT_ID
    @Select("select RELAT_ID from ITEM_RELAT where ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType}")
    public BigDecimal getRelateIdById(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType) throws Exception;

    @Update("update ITEM_RELAT set DONE_DATE = CURRENT_TIMESTAMP,RELAT_ID=#{relateId} where  ID=#{id}")
    public void updateClassTemplate(@Param(value = "id") BigDecimal id, @Param(value = "relateId") BigDecimal relateId)throws Exception;

    //通过任务分类id得到任务的最终状态值
    @Select("select max(state_code) from item_relat where relat_type ='TYPE_NAME' and state = #{state} and  relat_id = (select relat_id from item_relat where relat_type ='TASK_REL_TYPE' and state = #{state} and id = #{taskTrip})")
    public Integer getEndStateByTaskTrip(@Param("taskTrip") BigDecimal taskTrip, @Param("state") Integer state) throws Exception;

    @Select("select TORSION_RELATION as torsionRelation from ITEM_RELAT where RELAT_ID = #{id} and STATE =#{status} and RELAT_TYPE =#{relatType} and STATE_CODE =#{taskState}")
    public String getTorsionRelation(@Param(value = "id") BigDecimal id, @Param(value = "status") Integer status, @Param(value = "relatType") String relatType, @Param(value = "taskState") Integer taskState) throws Exception;
}