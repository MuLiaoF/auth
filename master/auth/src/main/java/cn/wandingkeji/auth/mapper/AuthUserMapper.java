package cn.wandingkeji.auth.mapper;


import cn.wandingkeji.auth.domain.AuthPermission;
import cn.wandingkeji.auth.domain.AuthUser;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthUserMapper  extends BaseDao<AuthUser> {

    /**
     * 插入用户
     * @param u
     * @return
     */
   /* Boolean insertUser(AuthUser u) throws Exception ;*/


    /**
     * 查询用户下角色的权限
     * @param id
     * @return
     */
    List<AuthPermission> selectUserRolePerByUserId(String id);
}
