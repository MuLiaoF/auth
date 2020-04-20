package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthPermission;
import cn.wandingkeji.auth.system.BaseDao;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthPermissionMapper extends BaseDao<AuthPermission> {

    /**
     * 查询权限是否存在
     * @param perList
     * @return
     */
    List<String> selectPerList(@Param("items") List<String> perList);

    /**
     * 分页查询权限
     * @param topcode
     * @param page
     * @param pcode
     * @param menuName
     * @return
     */
    List<AuthPermission> selectPerPage(@Param("topcode") String topcode,
                                       Pagination page,
                                       @Param("pcode") String pcode,
                                       @Param("menuName") String menuName);

    List<AuthPermission> selectAll();
}
