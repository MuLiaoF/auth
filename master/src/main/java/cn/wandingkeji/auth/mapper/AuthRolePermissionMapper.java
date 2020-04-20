package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthRolePermission;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthRolePermissionMapper extends BaseDao<AuthRolePermission> {

    /**
     * 查询该角色下的权限是否存在
     * @param id
     * @param perList
     * @return
     */
    List<AuthRolePermission> selectRolePerByRoleId(@Param("deptRoleId") String deptRoleId, @Param("items") List<String> perList);

    /**
     * 批量添加角色的权限
     * @param roleAddList
     * @return
     */
    Integer insertBatch(List<AuthRolePermission> roleAddList);
}
