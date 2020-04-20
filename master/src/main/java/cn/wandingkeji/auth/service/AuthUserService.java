package cn.wandingkeji.auth.service;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthUser;

public interface AuthUserService {


    /**
     * 通过账号查询用户
     * @param userName
     * @return
     */
    AuthUser getUserByLoginAct(String userName);


    /**
     * 添加用户
     * @param user
     */
    ResultData addUser(AuthUser user);

    /**
     * 添加用户权限
     * @param id
     * @param roleIds
     * @return
     */
    ResultData updateUserPer(String id, String[] roleIds);

    /**
     * 添加用户角色
     * @param id
     * @param roleIds
     * @return
     */
    ResultData updateUserRole(String id, String deptId, String[] roleIds);
}
