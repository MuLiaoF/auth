package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthDept;
import cn.wandingkeji.auth.domain.AuthDeptRole;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AuthDeptMapper extends BaseDao<AuthDept> {

    /**
     * 统计当前
     * @param deptPreId
     * @return
     */
    Integer selectCountByPreId(String deptPreId) throws Exception;

    /**
     * 查询该部门下的角色
     * @param deptId
     * @param roleIds
     * @return
     */
    //AuthDeptRole selectRoleByDeptId(String deptId, String[] roleIds);
}
