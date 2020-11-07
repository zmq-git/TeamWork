package com.location.common.enums;

/**
 * 异常编码枚举
 * Created by bawy on 2018/7/6 9:46.
 */
public enum ResultCodeEnum {

    /**
     * 公用枚举
     */
    SUCCESS("0000","成功"),
    PARAM_ERROR("0001","参数校验错误,{0}"),
    DUP_SUBMIT_ERROR("0002","非法提交或重复提交"),
    NOT_LOGIN_ERROR("0003","用户{0}未登录  "),
    SESSION_TIMEOUT_ERROR("0004","会话超时，请重新登录"),
    DATA_ERROR("0005", "数据异常，{0}"),
    USER_NOT_EXIST("0006","用户不存在:{0}"),
    USER_COMMON_ERROR("0007","{0}"),
    USER_NO_PERMISSION_ERROR("0008","当前登录用户无此操作权限"),
    ILLEGAL_OPERATION_ERROR("0110", "非法操作，{0}"),

    /**
     * 系统模块ES0001开始
     */
    SYSTEM_COMMON_ERROR("ES0001","{0}"),
    USERNAME_OR_PASSWORD_ERROR("ES0002","用户名或密码错误"),
    EMAIL_IS_USE("ES0003", "邮箱已经注册"),
    USERNAME_IS_USE("ES0004", "用户名已经被使用"),
    USER_STATUS_ABNORMAL("ES0005", "用户状态异常,{0}"),
    OLD_PASSWORD_ERROR("ES0006", "原密码输入错误"),
    SELECT_KEY_ERROR("ES0007", "下拉列表数据对应Key错误，无法获取对应下拉列表数据"),

    /**
     * 小区模块EC0001开始
     */
    COMMUNITY_COMMON_ERROR("EC0001","{0}"),

    /**
     * 摄像头交互部分异常编码
     */
    CAMERA_IMG_PARAM_ERROR("CC0001","请求入参异常"),
    CAMERA_IMG_NOT_FOUND_ERROR("CC0002","图片编号{0}找不到图片信息"),
    CAMERA_NOT_FOUND_ERROR("CC0003","摄像头编号{0}找不到"),
    CAMERA_UPLOAD_IMG_ERROR("CC0004","图片{0}上传到摄像头{1}失败：{2}"),
    CAMERA_IMG_NAME_NOT_FOUND_ERROR("CC0005","图片名{0}找不到图片信息"),

    CAMERA_IMG_INFO_ERROR("CC0006","图片信息不全:库索引:{0},文件索引:{1},图片名称:{2}"),
    CAMERA_DELETE_IMG_ERROR("CC0007","摄像头{0}删除图片{1}失败：{2}"),
    CAMERA_GET_IMG_ERROR("CC0008","摄像头{0}获取图片{1}失败：{2}"),
    CAMERA_GET_LIST_IMG_ERROR("CC0008","摄像头{0}获取图片失败：{1}"),


    UNDEFINE_ERROR("9999","未知错误");

    private String errorCode;
    private String errorMsg;

    ResultCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }



    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
