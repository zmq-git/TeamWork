package com.location.service;

import com.location.bean.vo.SecOrgDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 针对sec_org表的service
 * @Author yjh
 * @Date 2019/12/6
 * @Version V1.0
 **/
@Service
public interface SecOrgService {

    public Map<String,List<SecOrgDO>> getAllOrgName() throws Exception;
    public Map getAllOrgInfo(Long oid) throws Exception;
}
