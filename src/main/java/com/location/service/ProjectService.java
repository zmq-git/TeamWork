package com.location.service;

import com.location.bean.po.ProjectTypePO;
import com.location.bean.vo.ProjectInfoVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 针对projectInfo表的service
 * @Author yanjh
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Service
public interface ProjectService {
    /**
     * 保存项目信息
     * @param projectAddVO
     * @return
     * @throws Exception
     */
    public void addProjectInfo(ProjectInfoVO projectAddVO, BigDecimal userId) throws Exception;

    /**
     * 根据项目名称精准查询项目是否存在
     * @param projectName
     * @return
     * @throws Exception
     */
    public ProjectInfoVO getProjectInfo(String projectName) throws Exception;

    /**
     * 根据项目名称模糊查询
     * @param projectName
     * @return
     * @throws Exception
     */
    public List<ProjectInfoVO> getProjectInfoByProjectName(String projectName, BigDecimal userId) throws Exception;

    /**
     * 查询所有项目
     * @return
     * @throws Exception
     */
    public List<ProjectInfoVO> getProjectAll(Integer pageSize, Integer current, Integer userId, Integer projectPeriod) throws Exception;

    /**
     * 删除项目
     * @param projectId
     * @throws Exception
     */
    public void deleteProjectInfo(BigDecimal projectId) throws Exception;

    /**
     * 修改项目信息
     * @param projectAddVO
     * @return
     * @throws Exception
     */
    public void updateProjectInfo(ProjectInfoVO projectAddVO) throws Exception;

    /**
     * 查询项目数量
     * @return
     * @throws Exception
     */
    public Integer getProjectNumber(Integer userId, Integer projectPeriod) throws Exception;

    /**
     * 查询项目类型
     * @return
     * @throws Exception
     */
    public List<ProjectTypePO> getProjectTypeInfo() throws Exception;

    /**
     * 修改项目状态
     * @throws Exception
     */
    public void updateProjectPeriod(Integer userId) throws Exception;

    public Integer queryProjectRole(BigDecimal projectId,BigDecimal userId)throws Exception;

}
