package com.location.service.impl;

import com.location.bean.po.InfoNotifyPO;
import com.location.bean.vo.AddNotifyVO;
import com.location.bean.vo.InfoNotifyVO;
import com.location.common.constants.TeamConst;
import com.location.dao.InfoNotifyDao;
import com.location.dao.ProjectInfoDao;
import com.location.dao.StaticDataDao;
import com.location.dao.UserInfoDao;
import com.location.service.InfoNotifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName InfoNotifyServiceImpl
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/12/18 0018上午 9:40
 * @Version V1.0
 **/
@Service
public class InfoNotifyServiceImpl implements InfoNotifyService {

    @Autowired
    InfoNotifyDao infoNotifyDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    StaticDataDao staticDataDao;

    @Autowired
    ProjectInfoDao projectInfoDao;

    /**
     * 消息新增
     * @param addNotifyVO
     * @throws Exception
     */
    @Override
    public void addNotifyInfo(AddNotifyVO addNotifyVO) throws Exception {
        InfoNotifyPO infoNotifyPO = new InfoNotifyPO();
        infoNotifyPO.setId(infoNotifyDao.getNotifyId());
        infoNotifyPO.setState(1);//消息状态：1 表示未读
        infoNotifyPO.setDealState(TeamConst.NOTIFY_DEAL_STATE_AUDIT);
        if (addNotifyVO.getInfoType() == TeamConst.INFO_NOTIFY_TYPE_REGISTER){
            infoNotifyPO.setInfoType(addNotifyVO.getInfoType());
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //       infoNotifyPO.setSubject(registerVO.getStaffName()+"在"+ f.format(new Date()) +"提交申请,请审核");
            String notifyProjectDesc = staticDataDao.getNotifyProjectDesc(TeamConst.INFO_NOTIFY_TYPE_REGISTER);
            infoNotifyPO.setSubject(notifyProjectDesc.replace("applicant",userInfoDao.getNameByUserId(new BigDecimal(addNotifyVO.getApplicant()),2)));
            infoNotifyPO.setApplicant(new BigDecimal(addNotifyVO.getApplicant()));
            infoNotifyPO.setAuditor(new BigDecimal(addNotifyVO.getAuditor()));
            infoNotifyPO.setUserId(new BigDecimal(addNotifyVO.getAuditor()));
        }else if (addNotifyVO.getInfoType() == TeamConst.INFO_NOTIFY_TYPE_COMMON){
            infoNotifyPO.setInfoType(addNotifyVO.getInfoType());
            String notifyProjectDesc = staticDataDao.getNotifyProjectDesc(TeamConst.INFO_NOTIFY_TYPE_COMMON);
            infoNotifyPO.setSubject(MessageFormat.format(notifyProjectDesc,userInfoDao.getNameByUserId(new BigDecimal(addNotifyVO.getApplicant()),1),projectInfoDao.getProjectInfoByProjectId(addNotifyVO.getProjectId(),1).getProjectName()));
            infoNotifyPO.setApplicant(new BigDecimal(addNotifyVO.getApplicant()));
            infoNotifyPO.setAuditor(new BigDecimal(addNotifyVO.getAuditor()));
            infoNotifyPO.setUserId(new BigDecimal(addNotifyVO.getAuditor()));
        }else if (addNotifyVO.getInfoType() == TeamConst.INFO_NOTIFY_TYPE_TASK){
            infoNotifyPO.setInfoType(addNotifyVO.getInfoType());
            String notifyProjectDesc = staticDataDao.getNotifyProjectDesc(TeamConst.INFO_NOTIFY_TYPE_TASK);
            infoNotifyPO.setSubject(MessageFormat.format(notifyProjectDesc,userInfoDao.getNameByUserId(new BigDecimal(addNotifyVO.getApplicant()),1),projectInfoDao.getProjectInfoByProjectId(addNotifyVO.getProjectId(),1).getProjectName(),addNotifyVO.getTaskName()));
            infoNotifyPO.setApplicant(new BigDecimal(addNotifyVO.getApplicant()));
            infoNotifyPO.setAuditor(new BigDecimal(addNotifyVO.getAuditor()));
            infoNotifyPO.setUserId(new BigDecimal(addNotifyVO.getAuditor()));
        }
        infoNotifyDao.addNotifyInfo(infoNotifyPO);
    }

    /**
     * 根据审核人查询消息内容
     * @param auditor
     * @return
     * @throws Exception
     */
    @Override
    public List<InfoNotifyVO> getNotifyByAuditor(BigDecimal auditor, Integer state) throws Exception {
        List<InfoNotifyVO> list = new ArrayList<>();
        List<InfoNotifyPO> notifyInfoByAuditor = infoNotifyDao.getNotifyInfoByAuditor(auditor,state);
        if (notifyInfoByAuditor!=null && notifyInfoByAuditor.size()>0){
            for (InfoNotifyPO infoNotifyPO : notifyInfoByAuditor){
                InfoNotifyVO infoNotifyVO = new InfoNotifyVO();
                BeanUtils.copyProperties(infoNotifyPO,infoNotifyVO);
                if (state == 1){
                    if (infoNotifyPO.getInfoType() == 2){
                        infoNotifyVO.setApplicantName(userInfoDao.getNameByUserId(infoNotifyPO.getApplicant(),2));
                    }else {
                        infoNotifyVO.setApplicantName(userInfoDao.getNameByUserId(infoNotifyPO.getApplicant(),1));
                    }
                    infoNotifyVO.setDealState(staticDataDao.getNotifyDealStateName(TeamConst.NOTIFY_DEAL_STATE_AUDIT));
                }else if (state == 2){
                    infoNotifyVO.setApplicantName(userInfoDao.getNameByUserId(infoNotifyPO.getApplicant(),1));
                    if (infoNotifyPO.getDealState() == 0){
                        infoNotifyVO.setDealState(staticDataDao.getNotifyDealStateName(TeamConst.NOTIFY_DEAL_STATE_INVALID));
                    }else if (infoNotifyPO.getDealState() == 2){
                        infoNotifyVO.setDealState(staticDataDao.getNotifyDealStateName(TeamConst.NOTIFY_DEAL_STATE_VALID));
                    }else if (infoNotifyPO.getDealState() == 3){
                        infoNotifyVO.setDealState(staticDataDao.getNotifyDealStateName(TeamConst.NOTIFY_DEAL_STATE_READ));
                    }
                }
                infoNotifyVO.setAuditorName(userInfoDao.getNameByUserId(infoNotifyPO.getAuditor(),1));
                infoNotifyVO.setUserName(userInfoDao.getNameByUserId(infoNotifyPO.getUserId(),1));
                infoNotifyVO.setInfoTypeName(staticDataDao.getNotifyTypeName(infoNotifyPO.getInfoType()));
                infoNotifyVO.setInfoType(infoNotifyPO.getInfoType());
                list.add(infoNotifyVO);
            }
            return list;
        }
        return null;
    }

    /**
     * 消息更新（审核通过/审核拒绝）
     * @param id
     * @param auditState
     * @throws Exception
     */
    @Override
    public void updateNotifyInfoState(BigDecimal id, Integer auditState) throws Exception {
        InfoNotifyPO notifyInfoById = infoNotifyDao.getNotifyInfoById(id);
        Integer secUserId = userInfoDao.getSecUserIdByStaffId(notifyInfoById.getApplicant(), 2);
        Integer secUserRoleRelId = userInfoDao.getSecUserRoleIDByUserId(notifyInfoById.getApplicant(), 2);
        //我知道了：3,审核通过：2,审核拒绝：1,消息删除：0
        if (auditState == 2){
            if(notifyInfoById.getInfoType() == 2){
                userInfoDao.updateSecStaffInfoState(notifyInfoById.getApplicant(),1);
                userInfoDao.updateSecUserInfoState(secUserId,1);
                userInfoDao.updateSecUserRoleRelState(secUserRoleRelId,1);
            }
            infoNotifyDao.updateNotifyState(id,TeamConst.NOTIFY_DEAL_STATE_VALID);
        }else if (auditState == 1){
            if(notifyInfoById.getInfoType() == 2) {
                userInfoDao.updateSecStaffInfoState(notifyInfoById.getApplicant(), 0);
                userInfoDao.updateSecUserInfoState(secUserId, 0);
                userInfoDao.updateSecUserRoleRelState(secUserRoleRelId, 0);
            }
            infoNotifyDao.updateNotifyState(id,TeamConst.NOTIFY_DEAL_STATE_INVALID);
        }else if (auditState == 0){
            infoNotifyDao.deleteNotifyInfo(id);
        }else if (auditState == 3){
            infoNotifyDao.updateNotifyState(id,TeamConst.NOTIFY_DEAL_STATE_READ);
        }
    }

    /**
     * 查询所有的消息类型
     * @throws Exception
     */
    @Override
    public List<String> getInfoTypeName() throws Exception {
        List<String> allNotifyTypeName = staticDataDao.getAllNotifyTypeName();
        if (allNotifyTypeName!=null && allNotifyTypeName.size()>0){
            return allNotifyTypeName;
        }
        return null;
    }


}
