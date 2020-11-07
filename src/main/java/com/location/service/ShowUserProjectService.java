package com.location.service;

import com.location.bean.vo.ProjectInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 查询用户已加入的项目
 * @Author yanjh
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Service
public interface ShowUserProjectService {
    /**
     * 根据登录用户查询该用户加入的项目
     * @param userAccount
     * @return
     * @throws Exception
     */
    public List<ProjectInfoVO> findUserAllProject(String userAccount) throws Exception;
}
