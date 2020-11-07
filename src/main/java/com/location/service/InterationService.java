package com.location.service;

import com.location.bean.vo.InterationInfoVO;
import com.location.bean.vo.UserStaffVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @Description: 迭代相关的service
 * @Author wanggb
 * @Date 2019/11/20
 * @Version V1.0
 **/
@Service
public interface InterationService {
    public Map findInterInfoByUserId(BigDecimal userId) throws Exception;
    public void addInterInfo(InterationInfoVO interationInfoVO, BigDecimal userId) throws Exception;

    public void deleteInterInfo(BigDecimal interationId, BigDecimal userId) throws Exception;

    public List<InterationInfoVO> findInterInfoByProjectId(BigDecimal projectId) throws Exception;
    public Boolean getInterInfo(String interationName, BigDecimal projectId) throws Exception;
    public Boolean checkInterEndPeriod(Timestamp endDate) throws Exception;

    public List<UserStaffVO> getUsersByInterationId(BigDecimal interationId) throws Exception;

}