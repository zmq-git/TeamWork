package com.location.dao;

import com.location.bean.vo.SecMenuDO;
import com.location.bean.vo.SecMenusDO;
import com.location.bean.vo.SecMenusSaveVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wangcl on 2019/8/27.
 */
@Repository
@Mapper
public interface SecMenuDAO {
    @Select("select * from sec_menus where id = #{id} and status = 1")
    public SecMenusDO getSecMenusById(@Param("id") long id);

    @Select("select * from sec_menus where business_id = #{businessId} and status=1")
    public List<SecMenuDO> getSecMenusByBusinessId(@Param("businessId") long businessId);

    @Select("Select nextval('business_id_sequence')")
    public long getBusinessId();

    @Select("select menu_name from sec_menu where id = 1")
    public String getMenuNameById();
    @Select("select * from sec_menu where id = #{id}")
    public SecMenuDO getMenuById(@Param("id") Integer id);
    @Select("select * from sec_menu where parent_id = #{id}")
    public List<SecMenuDO> getMenuByParenId(@Param("id") Integer id);
    @Select("select id,menu_type as menuType,menu_name as menuName,parent_id as parentId,business_id as businessId,menu_link as menuLink,menu_level as menuLevel from sec_menus where status = #{status}")
    public List<Map> getSecMenus(@Param(value = "status") int status)throws Exception;

    @Insert("insert into sec_menus(" +
            "menu_type," +
            "menu_name," +
            "parent_id," +
            "business_id," +
            "menu_level," +
            "menu_link," +
            "menu_icon," +
            "description," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status," +
            "ext," +
            "ext1)values(#{menuType},#{menuName},#{parentId},#{businessId},#{menuLevel},#{menuLink},#{menuIcon},#{description}," +
            "#{opId},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP},#{status},#{ext},#{ext1})")
    public void saveMenus(SecMenusDO secMenusDO)throws Exception;

    @Update("update sec_menus set status = 0,update_time = CURRENT_TIMESTAMP where id = #{id}")
    public void delMenus(long id)throws Exception;

    @Update("<script>update sec_menus set update_time =CURRENT_TIMESTAMP " +
            "<if test='menuType!=null'>,menu_Type = #{menuType}</if>" +
            "<if test='menuName!=null'>,menu_name = #{menuName}</if>" +
            //"<if test='businessId!=null'>,business_id = #{businessId}</if>" +
            "<if test='menuLink!=null'>,menu_link=#{menuLink}</if>" +
            "<if test='parentId!=null'>,parent_id=#{parentId}</if> where id =#{menuId}</script>")
    public void updateMenus(SecMenusSaveVO secMenusSaveVO)throws Exception;

    @Select("<script>select * from sec_menus where id in <foreach item='item' index='index' collection='ids' open=\"(\" separator=\",\" close=\")\">#{item}</foreach> and status = #{status}</script>")
    public List<SecMenusDO> getMenuInfoByIds(@Param(value = "ids") List list, @Param(value = "status") int status)throws Exception;
    @Select("select id from sec_menus where business_id = #{businessId} and status = 1")
    public Long checkMenuByBusssinessId(long businessId);
}
