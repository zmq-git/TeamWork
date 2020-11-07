package com.location.dao;

import com.location.bean.po.ProjectTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Repository
@Mapper
public interface StaticDataDao {
    @Select("<script>select code_name from static_data where code_type = 'INFO_NOTIFY_TYPE' and <if test='infoTypeValue!=null'>code_value = #{infoTypeValue,jdbcType=INTEGER}</if></script>")
    public String getNotifyTypeName(@Param("infoTypeValue") Integer infoTypeValue) throws Exception;
    @Select("select code_name from static_data where code_type = 'INFO_NOTIFY_TYPE'")
    public List<String> getAllNotifyTypeName() throws Exception;
    @Select("select code_name from static_data where code_type = 'INFO_NOTIFY_DEAL_STATE' and code_value = #{notifyDealStateValue}")
    public String getNotifyDealStateName(Integer notifyDealStateValue) throws Exception;
    @Select("select code_desc from static_data where code_type = 'INFO_NOTIFY_TYPE' and code_value = #{notifyTypeValue}")
    public String getNotifyProjectDesc(Integer notifyTypeValue) throws Exception;
    @Select("select code_value,code_name,code_desc from static_data where code_type = 'PROJECT_TYPE_STATE'")
    public List<ProjectTypePO> getProjectType() throws Exception;
    @Select("select code_name from static_data where code_type = 'PROJECT_TYPE_STATE' and code_value = #{projectTypeValue}")
    public String getProjectTypeName(Integer projectTypeValue) throws Exception;

}