package com.location.dao;

import com.location.bean.vo.SecOrgDO;
import com.location.bean.vo.SecOrgRelatDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ISecOrgDAO {
    @Select("select org_id from sec_org where status = 1 and org_id not in (select org_id from sec_org_relat where status = 1)")
    public List<Long> getAllSecOrgTopId() throws Exception;

    @Select("select relat_parent_org_id from sec_org_relat where org_id = #{orgId} and status =#{status}")
    public Integer getTopOrgIdByOrgId(@Param(value = "orgId") int orgId, @Param(value = "status") int status) throws Exception;

    @Select("select org_name from sec_org where org_id = #{orgId} and status =#{status}")
    public String getSecOrgNameByOrgId(@Param(value = "orgId") long OrgId, @Param(value = "status") int status) throws Exception;

    @Select("select org_id as orgId,org_name as orgName,description as description from sec_org where org_id = #{orgId} and status =#{status}")
    public Map getSecOrgInfo(@Param(value = "orgId") long OrgId, @Param(value = "status") int status) throws Exception;

    @Select("select * from sec_org where id = #{id}")
    public SecOrgDO getSecOrgById(@Param("id") int OrgId);

    @Select("select org_id from sec_org_relat where relat_parent_org_id = #{relatOrgId} and status =#{status}")
    public List<Long> getSecOrgRelatByOrgRelatId(@Param(value = "relatOrgId") long relatOrgId, @Param(value = "status") int status)throws Exception;

    @Select("select b.org_id as orgId,b.org_name as orgName from sec_org_relat a,sec_org b where a.org_id = b.org_id and a.relat_parent_org_id = #{relatOrgId} and a.status =#{status}")
    public List<Map> getSecOrgRelatNameByOrgRelatId(@Param(value = "relatOrgId") long relatOrgId, @Param(value = "status") int status)throws Exception;

    @Select("select org_id as orgId , org_name as orgName from sec_org where org_id = #{orgId} and status =#{status}")
    public Map getSecOrgByOrgId(@Param(value = "orgId") long orgId, @Param(value = "status") int status) throws Exception;

    @Select("select relat_parent_org_id from sec_org_relat where org_id = #{orgId} and status =#{status}")
    public Long getSecOrgRelatByOrgId(@Param(value = "orgId") long orgId, @Param(value = "status") int status) throws Exception;

    @Insert("insert into sec_org(" +
            "org_id," +
            "org_name," +
            "description," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status," +
            "ext," +
            "ext1)values(" +
            "#{orgId}," +
            "#{orgName}," +
            "#{description}," +
            "#{opId}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status}," +
            "#{ext}," +
            "#{ext1})")
    public void saveSecOrg(SecOrgDO secOrgDO)throws Exception;

    @Insert("insert into sec_org_relat(" +
            "org_id," +
            "relat_parent_org_id," +
            "description," +
            "status," +
            "op_id," +
            "create_time," +
            "update_time," +
            "ext," +
            "ext1)values(" +
            "#{orgId}," +
            "#{relatParentOrgId}," +
            "#{description}," +
            "#{status}," +
            "#{opId}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{ext}," +
            "#{ext1})")
    public void saveSecOrgRelat(SecOrgRelatDO secOrgRelatDO)throws Exception;

    @Update("update sec_org set update_time = CURRENT_TIMESTAMP,org_name = #{orgName} where org_id =#{orgId}")
    public void updateSecOrg(SecOrgDO secOrgDO)throws Exception;

    @Update("update sec_org set update_time = CURRENT_TIMESTAMP,status=0 where org_id =#{orgId}")
    public void delSecOrg(@Param(value = "orgId") long orgId)throws Exception;

    @Update("update sec_org_relat set update_time = CURRENT_TIMESTAMP,relat_parent_org_id = #{relatParentOrgId} where org_id = #{orgId}")
    public void updateSecOrgRelat(SecOrgRelatDO secOrgRelatDO)throws Exception;

    @Update("update sec_org_relat set update_time = CURRENT_TIMESTAMP,status=0 where relat_parent_org_id =#{relatParentOrgId}")
    public void delSecOrgRelat(@Param(value = "relatParentOrgId") long relatParentOrgId)throws Exception;
    @Select("select NEXTVAL(#{seqName})")
    public long getNewId(String sequence)throws Exception;
}
