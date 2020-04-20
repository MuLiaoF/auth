package cn.wandingkeji.auth.mapper;


import cn.wandingkeji.auth.domain.AuthUserPermission;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthUserPermissionMapper extends BaseDao<AuthUserPermission> {

    /**
     * 查询用户权限  查询特殊权限以及角色权限
     * @param userId
     * @return
     */
    List<AuthUserPermission> selectUserPerById(String userId);

    /**
     * 批量插入
     * @param perAddList
     */
    Integer insertBatch(List<AuthUserPermission> perAddList);
}
