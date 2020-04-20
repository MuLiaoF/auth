package cn.wandingkeji.auth.contants;

import lombok.Data;

@Data
public enum ResultEnum {


    USER_EMPTY("11001", "用户不存在"),
    LOGIN_ERROR("11002", "登陆失败，用户名或密码错误"),

    SYS_USER_EXIST("40000", "用户已存在"),
    DEPT_NAME_REPEAT_ERROR("40001", "组织名称已存在"),
    ROLE_NAME_REPEAT_ERROR("40002", "角色名称已存在"),
    MENU_EXIST("40003", "菜单已存在"),
    PARENT_MENU_NOT_EXIST("40004", "父菜单不存在"),
    PWD_ERROR("40005", "旧密码错误"),
    MENU_EMPTY("50001", "暂无权限"),

    HANDLE_SUCCESS("0", "操作成功"),
    HANDLE_ERROR("1", "操作失败"),
    HANDLE_SQLERROR("2", "SQL异常"),
    LIST_EMPTY("2", "列表为空"),
    PARAMS_ERROR("-1", "参数错误"),
    OBJ_EXIST_ERROR("3", "对象已存在"),
    OBJ_NOT_EXIST_ERROR("4", "对象不存在"),


    /******* oss 图片 *******/
    OSS_IMAGE_EMPTY("4001","图片不存在"),
    OSS_IMAGE_ERROR("4002","图片上传失败"),
    OSS_IMAGE_MAX("4003","图片过大"),
    OSS_IMAGE_SIZE("4004","url路径参数异常"),


    /***************** 超类返回集 *****************/
    SUPER_ERROR_SELECT("01","单条为空"),
    SUPER_ERROR_INSERT("02","插入失败"),
    SUPER_ERROR_UPDATE("03","更新失败"),
    SUPER_ERROR_DELETE("04","删除失败");

    /***************** 超类返回集 *****************/

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
