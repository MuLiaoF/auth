package cn.wandingkeji.auth.mapper;

import cn.wandingkeji.auth.domain.AuthDictData;
import cn.wandingkeji.auth.system.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDictDataMapper extends BaseDao<AuthDictData> {

    /**
     * 根据字典类型查找数据
     * @param dictType
     * @param o
     * @return
     */
    AuthDictData findByDictType(String dictType, Object o);


}
