package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthDeptRole;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthDeptRoleMapper extends BaseDao<AuthDeptRole> {

    /**
     * 查询部门下是否存在该角色
     * @param id
     * @param roleList
     * @return
     */
    List<AuthDeptRole> selectDeptRoleByRoleId(@Param("deptId") String id, @Param("items") List<String> roleList);

    /**
     * 批量添加
     * @param roleAddList
     * @return
     */
    Integer insertBatch(List<AuthDeptRole> roleAddList);


    /**
     *
     * @param deptId
     * @param roleIds
     * @return
     */
    //AuthDeptRole selectRoleByDeptId(String deptId, String[] roleIds);
}
