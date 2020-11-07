package com.location.service.impl;

import com.location.common.utils.StringUtil;
import com.location.bean.vo.SecMenuDO;
import com.location.bean.vo.SecMenusDO;
import com.location.bean.vo.SecMenusSaveVO;
import com.location.dao.SecMenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by wangcl on 2019/8/27.
 */
@Service
public class SecMenuService {

    @Autowired
    SecMenuDAO secMenuDAO;

    public String getMenuNameById() {
        return secMenuDAO.getMenuNameById();
    }

    public long generateBusinessId()throws Exception{
        long businessId = secMenuDAO.getBusinessId();
        while(true){
            List<SecMenuDO> list = secMenuDAO.getSecMenusByBusinessId(businessId);
            if(list!=null && list.size()>0){
                businessId = secMenuDAO.getBusinessId();
            }else{
                break;
            }
        }
        return businessId;
    }

    public List<Map> getSecMenus()throws Exception{
        List<Map> list =secMenuDAO.getSecMenus(1);
        List<Map> retList = new ArrayList<>();
        //递归组装list层级关系
        if(list!=null&&list.size()>0){
            //先遍历父节点
            for (Map map:list){
                Map resultMap = new LinkedHashMap();
                if(StringUtil.getAsString(map,"parentId").equals("0")){
                    resultMap.put("parentId",map.get("parentId"));
                    resultMap.put("menuId",map.get("id"));
                    resultMap.put("menuType",map.get("menuType"));
                    resultMap.put("menuName",map.get("menuName"));
                    resultMap.put("businessId",map.get("businessId"));
                    resultMap.put("menuLink",map.get("menuLink"));
                    resultMap.put("menuLevel",map.get("menuLevel"));
                    resultMap.put("child",getSecMenusChild(list,StringUtil.getAsString(map,"id")));
                    retList.add(resultMap);
                }
            }
        }
        return retList;
    }
    public List<Map> getSecMenusChild(List<Map> list,String id){
        List<Map> retList= new ArrayList<>();
        for (Map map : list){
            Map resultMap = new LinkedHashMap();
            if(StringUtil.getAsString(map,"parentId").equals(id)){
                resultMap.put("parentId",map.get("parentId"));
                resultMap.put("menuId",map.get("id"));
                resultMap.put("menuType",map.get("menuType"));
                resultMap.put("menuName",map.get("menuName"));
                resultMap.put("businessId",map.get("businessId"));
                resultMap.put("menuLink",map.get("menuLink"));
                resultMap.put("menuLevel",map.get("menuLevel"));
                resultMap.put("child",getSecMenusChild(list,StringUtil.getAsString(map,"id")));
                retList.add(resultMap);
            }
        }
        return retList;
    }

    public void saveSecMenus(SecMenusSaveVO secMenusSaveVO,int opId)throws Exception{
        if(secMenusSaveVO.getType()==0){
            SecMenusDO secMenusDO = new SecMenusDO();
            secMenusDO.setCreateTime(new Timestamp(new Date().getTime()));
            secMenusDO.setUpdateTime(new Timestamp(new Date().getTime()));
            secMenusDO.setStatus(1);
            secMenusDO.setOpId(opId);
            secMenusDO.setMenuName(secMenusSaveVO.getMenuName());
            secMenusDO.setParentId(secMenusSaveVO.getParentId());
            if(secMenusSaveVO.getMenuType()==0){
                //页面权限新增
                secMenusDO.setMenuType(0);
                secMenusDO.setMenuLink(secMenusSaveVO.getMenuLink());
                //添加菜单层级、描述
                SecMenusDO parentSecMenus = secMenuDAO.getSecMenusById(secMenusSaveVO.getParentId());
                int level =0;
                if(parentSecMenus!=null){
                    level = parentSecMenus.getMenuLevel()+1;
                }else{
                    level = 1;
                }
                secMenusDO.setMenuLevel(level);
                secMenusDO.setDescription(level+"级菜单");
            }else if(secMenusSaveVO.getMenuType()==1){
                //操作权限新增
                secMenusDO.setMenuType(1);
                Long id = secMenuDAO.checkMenuByBusssinessId(secMenusSaveVO.getBusinessId());
                if(id!=null){
                    throw new Exception("操作编码已经存在");
                }
                secMenusDO.setBusinessId(secMenusSaveVO.getBusinessId());
            }



            //新增
            secMenuDAO.saveMenus(secMenusDO);
        }else if(secMenusSaveVO.getType()==1){
            //逻辑删除
            secMenuDAO.delMenus(secMenusSaveVO.getMenuId());
        }else if(secMenusSaveVO.getType()==2){
            //修改
            secMenuDAO.updateMenus(secMenusSaveVO);
        }
    }
}
