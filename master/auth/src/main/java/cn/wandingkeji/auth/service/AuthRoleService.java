package cn.wandingkeji.auth.service;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthRole;
import cn.wandingkeji.auth.domain.AuthUser;

import java.util.Set;

public interface AuthRoleService {

    /**
     * 根据账号获取角色
     * @param userName
     * @return
     */
    Set<String> getRoleListByLoginAct(String userName) throws Exception;


    /**
     * 添加角色
     * @param user
     * @return
     */
    ResultData addRole(AuthRole user) throws Exception;

    /**
     * 向角色中添加权限
     * @param id
     * @param perIds
     * @return
     */
    ResultData addRolePre(String id, String[] perIds);
}
