package com.location.dao;

import com.location.bean.po.InterationItemPO;
import com.location.bean.vo.InterationInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/19
 * @Version V1.0
 **/
@Repository
@Mapper
public interface IterationItemDao {
    @Select("select * from INTERATION_ITEM where INTERATION_ID = #{id} and STATE =#{state}")
    public Map getIterInfoBytId(@Param(value = "id") BigDecimal id, @Param(value = "state") Integer state) throws Exception;
    @Select("select INTERATION_ID as interationId,INTERATION_NAME as interationName,INTERATION_DESC as interationDesc,CREATER as creater,INTERATION_PICTURE as interationPicture,CREATE_DATE as createDate,START_DATE as startDate,END_DATE as endDate from INTERATION_ITEM where INTERATION_ID=#{id}")
    public InterationInfoVO getIterInfoDescById(BigDecimal id) throws Exception;
    @Select("select NEXTVAL('interation_id_seq')")
    public BigDecimal getNewInterationId()throws Exception;

    @Insert("insert into INTERATION_ITEM(INTERATION_ID,INTERATION_NAME," +
            "INTERATION_DESC," +
            "CREATER," +
            "INTERATION_PICTURE," +
            "DONE_CODE," +
            "CREATE_DATE," +
            "DONE_DATE," +
            "START_DATE," +
            "END_DATE" +
            " ) values (#{interationId},#{interationName},#{interationDesc},#{creater},#{interationPicture},#{doneCode}" +
            ",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{startDate,jdbcType=TIMESTAMP}," +
            "#{endDate,jdbcType=TIMESTAMP})")
    public int saveIterationItem(InterationItemPO interationItemPO)throws Exception;

}