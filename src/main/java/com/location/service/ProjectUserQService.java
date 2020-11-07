package com.location.service;

import com.location.bean.vo.ProjectUserAddVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/7
 * @Version V1.0
 **/
@Service
public interface ProjectUserQService {
    public List<String> addProjectMember(ProjectUserAddVO projectUserAddVO) throws Exception;
    public Map getIterInfoByUserId(BigDecimal userId)throws Exception;
    public void deleteProjectMember(Integer projectId, List<Integer> userIdList) throws Exception;
    public void updateProjectMenberRole(Integer projectId, Integer userId, List<Integer> roleIdList, Integer operator) throws Exception;
}