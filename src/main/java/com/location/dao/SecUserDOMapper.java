package com.location.dao;

import com.location.bean.vo.SecUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SecUserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecUserDO record);

    int insertSelective(SecUserDO record);

    SecUserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecUserDO record);

    int updateByPrimaryKey(SecUserDO record);
}