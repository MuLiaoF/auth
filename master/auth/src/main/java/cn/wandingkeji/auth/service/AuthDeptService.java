package cn.wandingkeji.auth.service;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthDept;

public interface AuthDeptService {

    /**
     * 添加部门
     * @param authDept
     * @return
     */
    ResultData addDept(AuthDept authDept) throws Exception;

    /**
     * 向部门中添加角色
     * @param id
     * @param roleIds
     * @return
     */
    ResultData addDeptRole(String id, String roleParId, String[] roleIds);
}
