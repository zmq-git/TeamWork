package com.location.bean.vo;

import com.location.bean.vo.SecMenusDO;
import com.location.bean.vo.SecOperateDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SecMenuVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/9/24
 * @Version V1.0
 **/
@Setter
@Getter
@ApiModel("用户登录用户名权限返回结果")
public class SecMenuVO implements Serializable {
    private static final long serialVersionUID =1L;
    @ApiModelProperty("菜单权限")
    private List <SecMenusDO> secMenuDOS;
    @ApiModelProperty("操作权限")
    private List<SecOperateDO> secOperateDOS;
    @ApiModelProperty("其他操作权限（保留字段）")
    private List authCode;
}