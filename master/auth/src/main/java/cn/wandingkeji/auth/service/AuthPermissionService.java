package cn.wandingkeji.auth.service;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthPermission;

import java.util.List;
import java.util.Set;

public interface AuthPermissionService {

    /**
     * 系统中是否包含权限路径
     * @param requestURI
     * @return
     */
    boolean containPermissionPath(String requestURI);

    /**
     * 当前用户所拥有的权限
     * @param userName
     * @return
     */
    Set<String> haveUserPermission(String userName);

    /**
     * 遍历权限
     * @return
     */
    List<AuthPermission> list();

    /**
     * 通过用户名查询权限
     * @param userName
     * @return
     */
    Set<String> getPermissionByLoginAct(String userName);

    /**
     * 添加权限
     * @param authPermission
     * @return
     */
    ResultData addPer(AuthPermission authPermission);

    /**
     * 分页查询所有权限
     * @return
     */
    ResultData getAll(String topcode, String pcode, String menuName, Integer pageNum, Integer pageSize);
}
