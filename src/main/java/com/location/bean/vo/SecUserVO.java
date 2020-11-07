package com.location.bean.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@ApiModel("用户登录返回信息")
@Data
public class SecUserVO implements Serializable {

    private static final long serialVersionUID = -8941768685465188198L;

    @ApiModelProperty("id")
    private int id;
    @ApiModelProperty("渠道")
    private int domainId;
    @ApiModelProperty("渠道名称")
    private String domainName;
    @ApiModelProperty("员工编号")
    private int staffId;
    @ApiModelProperty("员工姓名")
    private String staffName;
    @ApiModelProperty(dataType = "String", name = "账号", value = "账号")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("1:是超级管理员，0：不是超级管理员")
    private int isAdmin;
    @ApiModelProperty("创建人")
    private int creator;
    @ApiModelProperty("创建时间")
        private Timestamp createTime;
    @ApiModelProperty("修改人")
    private int modifier;
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;
    @ApiModelProperty("数据状态 1:生效 0：失效")
    private byte status;
    @ApiModelProperty("组织编号")
    private int orgId;
    @ApiModelProperty("组织名称")
    private String orgName;
    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("sessionId")
    private String sessionId;
    @ApiModelProperty("权限列表")
    private SecMenuVO secMenuVO;
    @ApiModelProperty("权限列表")
    private List<Map> secMenusList;
    @ApiModelProperty("公司id")
    public int companyId;
    @ApiModelProperty("公司名字")
    public String companyName;
    @ApiModelProperty("邮箱")
    public String mail;
    @ApiModelProperty("电话")
    public String phoneNum;
    @ApiModelProperty("用户头像")
    public String portraitAddress;







    public static Builder build() {
        return new Builder();
    }

    public static class Builder {
        private String domainName;
        private String staffName;
        private int orgId;
        private String orgName;
        private int id;
        private int domainId;
        private int staffId;
        private String username;
        /*private String password;*/
        private int isAdmin;
        private int creator;
        private Timestamp createTime;
        private int modifier;
        private Timestamp updateTime;
        private byte status;

        public Builder domainName(String domainName) {
            this.domainName = domainName;
            return this;
        }

        public Builder staffName(String staffName) {
            this.staffName = staffName;
            return this;
        }

        public Builder orgId(int orgId) {
            this.orgId = orgId;
            return this;
        }

        public Builder orgName(String orgName) {
            this.orgName = orgName;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder domainId(int domainId) {
            this.domainId = domainId;
            return this;
        }

        public Builder staffId(int staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder username(String username) {
            this.username  = username;
            return this;
        }

      /*  public Builder password(String password) {
            this.password = password;
            return this;
        }*/

        public Builder isAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder creator(int creator) {
            this.creator = creator;
            return this;
        }

        public Builder createTime(Timestamp createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder modifier(int modifier) {
            this.modifier = modifier;
            return this;
        }

        public Builder updateTime(Timestamp updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder status(byte status) {
            this.status = status;
            return this;
        }


        public SecUserVO build() {
            SecUserVO vo = new SecUserVO();
            vo.setDomainName(this.domainName);
            vo.setStaffName(this.staffName);
            vo.setOrgId(this.orgId);
            vo.setOrgName(this.orgName);
            vo.setId(this.id);
            vo.setDomainId(this.domainId);
            vo.setStaffId(this.staffId);
            vo.setUsername(this.username);
          /*  vo.setPassword(this.password);*/
            vo.setIsAdmin(this.isAdmin);
            vo.setCreator(this.creator);
            vo.setCreateTime(this.createTime);
            vo.setModifier(this.modifier);
            vo.setUpdateTime(this.updateTime);
            vo.setStatus(this.status);
            return vo;
        }
    }
}
