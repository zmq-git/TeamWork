package com.location.service.impl;

import com.location.common.utils.TimeUtils;
import com.location.bean.po.ItemRelatPO;
import com.location.bean.vo.ClassInfoVO;
import com.location.bean.vo.ItemRelatVO;
import com.location.bean.vo.NodeInfoVO;
import com.location.bean.vo.TemplateInfoVO;
import com.location.common.constants.TeamConst;
import com.location.dao.InterationTaskRelDao;
import com.location.dao.ItemRelatDao;
import com.location.dao.UserInfoDao;
import com.location.service.ItemRelatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName ItemRelatServiceImpl
 * @Description: 配置表进行操作
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Service
public class ItemRelatServiceImpl implements ItemRelatService {
    @Autowired
    ItemRelatDao itemRelatDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InterationTaskRelDao interationTaskRelDao;

    /**
     * 根据任务分类id获取任务分类树结构
     * @param classificationId
     * @return
     */
    @Override
    public Map getTaskClassificationById(BigDecimal classificationId) throws Exception{
        Map map =itemRelatDao.getItemRelatById(classificationId,1,"TASK_REL_TYPE");
        if(map!=null){
            Map map1 = getChildIds(classificationId);
            if(map1!=null){
                map.put("child",map1);
            }
        }
        return map;
    }

    /**
     * 获取所有分类
     * @param
     * @return
     */
    @Override
    public Map getTaskClassification() throws Exception{
        //父节点为0的TASK_REL_TYPE格式为根节点
        Map res = new HashMap();
        List list  =new ArrayList();
        List<BigDecimal> ids = itemRelatDao.getItemRelatByItemId(BigDecimal.ZERO,1,"TASK_REL_TYPE");
        if (ids!=null&&ids.size()>0) {
            for (BigDecimal id : ids) {
                Map map =itemRelatDao.getItemRelatById(id,1,"TASK_REL_TYPE");
                if(map!=null){
                    Map map1 = getChildIds(id);
                    if(map1!=null){
                        map.put("child",map1);
                    }
                }
                list.add(map);
            }
        }
        res.put("result",list);
        return res;
    }

    /**
     * 任务分类新增删除修改
     * @param itemRelatVO
     * @throws Exception
     */
    @Override
    public void taskClassificationDeal(ItemRelatVO itemRelatVO,BigDecimal userId) throws Exception{
        BigDecimal doneCode = itemRelatDao.getDoneCode();
        //新增
        if(itemRelatVO.getType()==0){
            BigDecimal id = getNewOrgId();
            ItemRelatPO itemRelatPO = new ItemRelatPO();
            itemRelatPO.setId(id);
            itemRelatPO.setCreater(userId);
            itemRelatPO.setItemId(itemRelatVO.getParentClassId());
            itemRelatPO.setDoneCode(doneCode);
            itemRelatPO.setStateName(itemRelatVO.getClassName());
            itemRelatPO.setState(1);
            itemRelatPO.setRelatType("TASK_REL_TYPE");
            itemRelatPO.setCreateDate(TimeUtils.getCurrentTime());
            itemRelatPO.setDoneDate(TimeUtils.getCurrentTime());
            itemRelatPO.setDescribe(itemRelatVO.getDescribe());
            itemRelatDao.saveItemRelat(itemRelatPO);
        }
        //删除
        if(itemRelatVO.getType()==1){
            //该组织对应的子组织全部失效
            delSecOrgRelatByOrgId(itemRelatVO.getClassId(),"TASK_REL_TYPE");
        }
        //修改
        if(itemRelatVO.getType()==2){
            if(itemRelatVO.getParentClassId()==BigDecimal.ZERO){
                //throw new Exception("不能使用这样的挂靠关系");
            }
            ItemRelatPO itemRelatPO = new ItemRelatPO();
            itemRelatPO.setId(itemRelatVO.getClassId());
            itemRelatPO.setCreater(userId);
            itemRelatPO.setItemId(itemRelatVO.getParentClassId());
            itemRelatPO.setDoneCode(doneCode);
            itemRelatPO.setDescribe(itemRelatVO.getDescribe());
            itemRelatPO.setStateName(itemRelatVO.getClassName());
            itemRelatDao.updateItemRelat(itemRelatPO);
        }
    }


    //未知层级深度，递归遍历组装报文，递归效率低，消耗大，慎用
    public Map getChildIds(BigDecimal id) throws Exception{
        Map retMap = new LinkedHashMap();
        List<BigDecimal> Ids = itemRelatDao.getItemRelatByItemId(id,1,"TASK_REL_TYPE");
        if(Ids != null && !Ids.isEmpty()){
            //retMap.put("classificationId",id);
            List li = new ArrayList();
            retMap.put("result",li);
            for(BigDecimal childId : Ids){
                Map map =itemRelatDao.getItemRelatById(childId,1,"TASK_REL_TYPE");
                if(map != null){
                    li.add(map);
                    Map map1 = getChildIds(childId);
                    if(map1!=null){
                        map.put("child",map1);
                    }
                }
            }
        }
        return retMap;
    }

    public BigDecimal getNewOrgId()throws Exception{
        return itemRelatDao.getNewId(TeamConst.ITEM_RELAT_ID_SEQUENCE);
    }

    @Override
    public String getStateNameByStateCode(BigDecimal taskRelId,Integer stateCode)throws Exception{
        BigDecimal realtId = itemRelatDao.getRelateIdById(taskRelId,TeamConst.ROLE_STATUS_VALID,TeamConst.TASK_REL_TYPE);
        String stateCodeName = itemRelatDao.getStateNameByStateCode(realtId,TeamConst.ROLE_STATUS_VALID,TeamConst.TYPE_NAME,stateCode);
        if(StringUtils.isNotBlank(stateCodeName)){
            return stateCodeName;
        }else {
            throw new Exception("状态类型："+taskRelId+" 对应的状态编码:"+stateCode+" 未配置或者已经被删除");
        }
    }

    /**
     *根据分类ID获取分类名称
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String queryItemNameById(BigDecimal id)throws Exception{
        String stateCodeName = itemRelatDao.getStateNameById(id,TeamConst.ROLE_STATUS_VALID,TeamConst.TASK_REL_TYPE);
        if(StringUtils.isNotBlank(stateCodeName)){
            return stateCodeName;
        }else {
            return String.valueOf(id);
        }
    }

    /**
     * 根据任务分类id获取任务分类信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ClassInfoVO queryClassInfoById(BigDecimal id)throws Exception{
        ClassInfoVO classInfoVO = new ClassInfoVO();
        String stateCodeName = itemRelatDao.getStateNameById(id,TeamConst.ROLE_STATUS_VALID,TeamConst.TASK_REL_TYPE);
        classInfoVO.setClassName(stateCodeName);
        classInfoVO.setId(id);
        BigDecimal tempId= itemRelatDao.getRelateIdById(id,TeamConst.ROLE_STATUS_VALID,TeamConst.TASK_REL_TYPE);
        String tempName = itemRelatDao.getStateNameById(tempId,TeamConst.ROLE_STATUS_VALID,TeamConst.TEMPLE_NAME);
        classInfoVO.setTempId(tempId);
        classInfoVO.setTempName(tempName);
        List<Map> stateList = itemRelatDao.getStateInfoById(tempId,TeamConst.ROLE_STATUS_VALID,TeamConst.TYPE_NAME);
        classInfoVO.setStateList(stateList);
        return classInfoVO;
    }

    public void delSecOrgRelatByOrgId(BigDecimal id,String relat_type)throws Exception{
        List<BigDecimal> childOrgList =itemRelatDao.getItemRelatByItemId(id,1,relat_type);
        itemRelatDao.delItemRelat(id,relat_type);
        //secOrgDAO.delSecOrgRelat(orgId);
        //对应模板信息全部失效
/*        List<Integer> ids =itemRelatDao.getSecStaffByOrgId(orgId);
        itemRelatDao.delSecStaffByOrgId(orgId);
        for (int staffId:ids){
            itemRelatDao.delSecUserByStaffId(staffId);
        }*/
        for (BigDecimal childOrgId:childOrgList){
            delSecOrgRelatByOrgId(childOrgId,relat_type);
        }
    }
    @Override
    public List <TemplateInfoVO> queryTemplate(Integer state)throws Exception{
        List <TemplateInfoVO> list = itemRelatDao.queryTemplate(null,state,TeamConst.TEMPLE_NAME);
        if(list!=null&&list.size()>0){
            for(TemplateInfoVO templateInfoVO:list){
                String userName = userInfoDao.getNameByUserId(templateInfoVO.getCreater(),1);
                templateInfoVO.setCreatorName(userName);
            }
        }
        return list;
    }

    @Override
    public void createTemplate(TemplateInfoVO templateInfoVO,BigDecimal userId) throws Exception{

        BigDecimal templateId = itemRelatDao.getNewId("item_relat_sequence");
        BigDecimal doneCode = itemRelatDao.getDoneCode();
        //先保存模板信息
        ItemRelatPO itemRelatPO = new ItemRelatPO();
        itemRelatPO.setId(templateId);
        itemRelatPO.setDescribe(templateInfoVO.getDes());
        itemRelatPO.setDoneCode(doneCode);
        itemRelatPO.setRelatType(TeamConst.TEMPLE_NAME);
        itemRelatPO.setState(1);
        itemRelatPO.setCreater(userId);
        itemRelatPO.setStateName(templateInfoVO.getTemplateName());
        itemRelatDao.saveItemRelat(itemRelatPO);
        //保存节点信息
        if(templateInfoVO.getNodeList()!=null&&templateInfoVO.getNodeList().size()>0){
            List<NodeInfoVO> list = templateInfoVO.getNodeList();
            for(NodeInfoVO nodeInfoVO:list){
                if(nodeInfoVO.getNum()!=null&&nodeInfoVO.getName()!=null){

                }
                ItemRelatPO node = new ItemRelatPO();
                BigDecimal nodeId = itemRelatDao.getNewId("item_relat_sequence");
                node.setId(nodeId);
                node.setDescribe(nodeInfoVO.getDescription());
                node.setDoneCode(doneCode);
                node.setRelatType(TeamConst.TYPE_NAME);
                node.setStateName(nodeInfoVO.getName());
                node.setRelatId(templateId);
                node.setState(1);
                node.setCreater(userId);
                node.setState_code(nodeInfoVO.getNum());
                //新增扭转关系保存在relat_item_id 字段中
                List<NodeInfoVO> reachNodeLists = nodeInfoVO.getReachNode();
                StringBuffer sb = new StringBuffer();
                if(reachNodeLists!=null&&reachNodeLists.size()>0){
                    for(NodeInfoVO nodeInfoVO1:reachNodeLists){
                        Integer num = nodeInfoVO1.getNum();
                        sb.append(num);
                        sb.append(",");
                    }
                }
                String relation = new String();
                if(sb.length()>0){
                    relation = sb.substring(0, sb.length()-1);
                }
                String torsionRelation = relation;
                node.setTorsionRelation(torsionRelation);
                itemRelatDao.saveItemRelat(node);
            }
        }
    }
    public TemplateInfoVO queryTemplateById (BigDecimal id)throws Exception{
        TemplateInfoVO res = new TemplateInfoVO();
        List <TemplateInfoVO> list = itemRelatDao.queryTemplate(id,1,TeamConst.TEMPLE_NAME);
        if(list!=null&&list.size()>0){
            res = list.get(0);
            for(TemplateInfoVO templateInfoVO:list){
                String userName = userInfoDao.getNameByUserId(templateInfoVO.getCreater(),1);
                templateInfoVO.setCreatorName(userName);
                List<Map> stateList = itemRelatDao.getStateInfoById(id,TeamConst.ROLE_STATUS_VALID,TeamConst.TYPE_NAME);
                List<NodeInfoVO> nodeList = new ArrayList<>();
                templateInfoVO.setNodeList(nodeList);
                if(stateList!=null&&stateList.size()>0){
                    for(Map state:stateList){
                        NodeInfoVO nodeInfoVO = new NodeInfoVO();
                        List<NodeInfoVO> reachNode = new ArrayList<NodeInfoVO>();
                        nodeInfoVO.setReachNode(reachNode);
                        mapToObeject(nodeInfoVO,state);
/*                        nodeInfoVO.setName(state.get("stateName")==null?null:(String) state.get("stateName"));
                        nodeInfoVO.setDescription( state.get("des")==null?null:(String) state.get("des"));
                        nodeInfoVO.setNum(state.get("stateCode")==null?null:(Integer) state.get("stateCode"));
                        nodeInfoVO.setId(state.get("id")==null?null:(BigDecimal) state.get("id"));*/
                        if(state.get("torsionRelation")!=null){
                            String torsionRelation = (String )state.get("torsionRelation");
                            if(torsionRelation.length()>0){
                                String[] nodes =torsionRelation.split(",");
                                for(String node:nodes){
                                    NodeInfoVO nodeInfoVOtr = new NodeInfoVO();
                                    Integer status_code = Integer.parseInt(node);
                                    Map nodeInfoVOMap=stateList.get(status_code);
                                    mapToObeject(nodeInfoVOtr,nodeInfoVOMap);
                                    //nodeInfoVOtr.setNum(Integer.parseInt(node));
                                    reachNode.add(nodeInfoVOtr);
                                }
                            }
                        }
                        nodeList.add(nodeInfoVO);
                    }
                }
            }
        }
        return res;
    }

    @Override
    public TemplateInfoVO queryTemplateByclassificationId (BigDecimal id)throws Exception{
        TemplateInfoVO res = new TemplateInfoVO();
        BigDecimal realtId = itemRelatDao.getRelateIdById(id,TeamConst.ROLE_STATUS_VALID,TeamConst.TASK_REL_TYPE);
        if(realtId!=null){
            res =queryTemplateById(realtId);
        }
        return res;
    }
    public void deleteTemplate(BigDecimal id)throws Exception{

        //删除前校验模板是否在使用
        List<BigDecimal> ids = itemRelatDao.getItemRelatByRelateId(id,1,TeamConst.TASK_REL_TYPE);
        if(ids!=null&&ids.size()>0){
            for (BigDecimal classId:ids){
                List<BigDecimal>taskIds = interationTaskRelDao.getTaskIdByClassId(classId,1);
                if(taskIds!=null&&taskIds.size()>0){
                    throw new Exception("该模板正在使用，不能被删除");
                }
            }
        }
        itemRelatDao.delelteRecord(id);
        itemRelatDao.delelteRelRecord(id);
    }
    public void updateClassTemplate(BigDecimal classId,BigDecimal templateId) throws Exception{
        List<BigDecimal>taskIds = interationTaskRelDao.getTaskIdByClassId(classId,1);
        if(taskIds!=null&&taskIds.size()>0){
            throw new Exception("改分类已经有任务生成，请先删除任务再进行修改");
        }
        itemRelatDao.updateClassTemplate(classId,templateId);

    }

    public NodeInfoVO mapToObeject(NodeInfoVO nodeInfoVO,Map state){
        nodeInfoVO.setName(state.get("stateName")==null?null:(String) state.get("stateName"));
        nodeInfoVO.setDescription( state.get("des")==null?null:(String) state.get("des"));
        nodeInfoVO.setNum(state.get("stateCode")==null?null:(Integer) state.get("stateCode"));
        nodeInfoVO.setId(state.get("id")==null?null:(BigDecimal) state.get("id"));
        return nodeInfoVO;
    }

}