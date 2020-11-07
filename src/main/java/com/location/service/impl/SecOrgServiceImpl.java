package com.location.service.impl;

import com.location.bean.vo.SecOrgDO;
import com.location.dao.ISecOrgDAO;
import com.location.dao.UserInfoDao;
import com.location.service.SecOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SecOrgServiceImpl
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/12/6 0006下午 5:26
 * @Version V1.0
 **/
@Service
public class SecOrgServiceImpl implements SecOrgService {

    @Autowired
    ISecOrgDAO iSecOrgDAO;

    @Autowired
    UserInfoDao userInfoDao;


    /**
     * 未知层级深度，递归遍历组织层级
     * @param orgId
     * @return
     * @throws Exception
     */
    public void getChildrenOrg(Long orgId, List<SecOrgDO> childId) throws Exception{
        List<Long> secOrgRealChildId = iSecOrgDAO.getSecOrgRelatByOrgRelatId(orgId,1);
        if (secOrgRealChildId != null){
            for (Long childOrgId : secOrgRealChildId){
                SecOrgDO orgInfoByOrgId = userInfoDao.getOrgInfoByOrgId((int) childOrgId.longValue(), 1);
                if (orgInfoByOrgId != null){
                    childId.add(orgInfoByOrgId);
                }
                getChildrenOrg(childOrgId,childId);
            }
        }
    }

    /**
     * 返回所有公司和部门的名字
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,List<SecOrgDO>> getAllOrgName() throws Exception{
        Map<String,List<SecOrgDO>> map = new HashMap<>();
        List<Long> allSecOrgTopId = iSecOrgDAO.getAllSecOrgTopId();
        if (allSecOrgTopId != null && allSecOrgTopId.size() > 0){
            for (Long topId : allSecOrgTopId){
                List<SecOrgDO> childId = new ArrayList<>();
                getChildrenOrg(topId,childId);
                map.put(iSecOrgDAO.getSecOrgNameByOrgId(topId,1),childId);
            }
        }
        return map;
    }

    /**
     * 查询所有的组织树状信息
     * @param oid
     * @return
     * @throws Exception
     */
    @Override
    public Map getAllOrgInfo(Long oid) throws Exception {
        Map secOrgInfoMap = iSecOrgDAO.getSecOrgInfo(oid,1);
        if (secOrgInfoMap != null){
            Map map1 = getChildIds(oid);
            if(map1!=null){
                secOrgInfoMap.put("child",map1);
            }
        }
        return secOrgInfoMap;
    }


    //未知层级深度，递归遍历组装报文
    public Map getChildIds(Long id) throws Exception{
        Map map = new HashMap();
        List<Long> childIds = iSecOrgDAO.getSecOrgRelatByOrgRelatId(id, 1);
        if (childIds != null && !childIds.isEmpty()){
            List list = new ArrayList();
            map.put("result",list);
            for (Long cid : childIds){
                Map secOrgInfo = iSecOrgDAO.getSecOrgInfo(cid,1);
                if (secOrgInfo != null){
                    list.add(secOrgInfo);
                    Map childIds1 = getChildIds(cid);
                    if (childIds1 != null){
                        secOrgInfo.put("children",childIds1);
                    }
                }
            }
        }
        return map;
    }
}
