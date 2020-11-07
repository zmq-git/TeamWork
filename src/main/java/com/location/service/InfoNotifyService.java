package com.location.service;

import com.location.bean.vo.AddNotifyVO;
import com.location.bean.vo.InfoNotifyVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 针对info_notify表的service
 * @Author yjh
 * @Date 2019/12/18
 * @Version V1.0
 **/
@Service
public interface InfoNotifyService {
    public void addNotifyInfo(AddNotifyVO addNotifyVO) throws Exception;
    public List<InfoNotifyVO> getNotifyByAuditor(BigDecimal auditor, Integer state) throws Exception;
    public void updateNotifyInfoState(BigDecimal id, Integer auditState) throws Exception;
    public List<String> getInfoTypeName() throws Exception;
}
