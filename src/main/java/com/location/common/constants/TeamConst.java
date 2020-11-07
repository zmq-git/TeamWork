package com.location.common.constants;


public class TeamConst {
    /**
     * 用户状态值
     * 0：失效
     * 1：生效
     * 2：待审核
     */
    public static final byte USER_STATUS_INVALID = 0;
    public static final byte USER_STATUS_VALID = 1;
    public static final byte USER_STATUS_AUDIT = 2;
    public static final String ITEM_RELAT_ID_SEQUENCE = "item_relat_sequence";
    public static final String TYPE_NAME = "TYPE_NAME";//节点标识
    public static final String TASK_REL_TYPE = "TASK_REL_TYPE";//分类标识
    public static final String TEMPLE_NAME = "TEMPLE_NAME";//模板标识
    public static final String STATE_CHANGE_REQUIREMENT = "STATE_CHANGE_REQUIREMENT";//模板标识
    public static final String DEFAULT_PASSWORD = "123456";
    public static final int ROLE_STATUS_VALID =1;
    public static final int NOTIFY_DEAL_STATE_INVALID = 0;//消息处理状态 0：审核拒绝,1：待审核,2：审核通过,3:已阅读
    public static final int NOTIFY_DEAL_STATE_AUDIT = 1;
    public static final int NOTIFY_DEAL_STATE_VALID = 2;
    public static final int NOTIFY_DEAL_STATE_READ = 3;
    public static final int INFO_NOTIFY_TYPE_COMMON = 1;//消息类型 1：普通消息 2：注册申请消息 3：任务分配消息
    public static final int INFO_NOTIFY_TYPE_REGISTER = 2;
    public static final int INFO_NOTIFY_TYPE_TASK = 3;
}
