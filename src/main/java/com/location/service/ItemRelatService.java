package com.location.service;

import com.location.bean.vo.ClassInfoVO;
import com.location.bean.vo.ItemRelatVO;
import com.location.bean.vo.TemplateInfoVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 配置表service
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Service
public interface ItemRelatService {
    public Map getTaskClassificationById(BigDecimal taskId) throws Exception;
    public Map getTaskClassification() throws Exception;
    public void taskClassificationDeal(ItemRelatVO itemRelatVO, BigDecimal userId) throws Exception;
    public String getStateNameByStateCode(BigDecimal taskRelId, Integer stateCode)throws Exception;
    public String queryItemNameById(BigDecimal id)throws Exception;
    public ClassInfoVO queryClassInfoById(BigDecimal id)throws Exception;
    public List <TemplateInfoVO> queryTemplate(Integer state)throws Exception;
    public void createTemplate(TemplateInfoVO templateInfoVO, BigDecimal userId) throws Exception;
    public TemplateInfoVO queryTemplateById(BigDecimal id)throws Exception;
    public TemplateInfoVO queryTemplateByclassificationId(BigDecimal id)throws Exception;
    public void deleteTemplate(BigDecimal id)throws Exception;
    public void updateClassTemplate(BigDecimal classId, BigDecimal templateId) throws Exception;
    }