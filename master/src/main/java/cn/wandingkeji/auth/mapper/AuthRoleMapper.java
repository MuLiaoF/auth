package cn.wandingkeji.auth.mapper;


import cn.wandingkeji.auth.domain.AuthRole;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthRoleMapper extends BaseDao<AuthRole> {

    /**
     * 查询该角色是否存在
     * @param roleList
     * @return
     */
    List<String> selectRoleList(@Param("items") List<String> roleList);
}
