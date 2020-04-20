package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthUserRole;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthUserRoleMapper extends BaseDao<AuthUserRole> {

    /**
     * 用户和角色的绑定
     * @param id
     * @param roleList
     * @return
     */
    List<AuthUserRole> selectUserRoleByRoleId( @Param("userId")String id, @Param("items") List<String> roleList);

    /**
     * 批量添加
     * @param roleAddList
     */
    Integer insertBatch(List<AuthUserRole> roleAddList);
}
