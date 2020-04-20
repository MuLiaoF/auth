package cn.wandingkeji.auth.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> extends BaseMapper<T> {

    List<T> page(T t, Pagination page);

    List<Map<String, Object>> mapPage(T t, Pagination page);

    List<T> joinPage(@Param("map")Map<String, Object> map, Pagination page);

    List<Map<String, Object>> joinMapPage(@Param("map") Map<String, Object> map, Pagination page);

}
