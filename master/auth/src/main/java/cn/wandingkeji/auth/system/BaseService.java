package cn.wandingkeji.auth.system;

import cn.wandingkeji.auth.contants.ResultData;
import com.baomidou.mybatisplus.service.IService;

public interface BaseService<T> extends IService<T> {


    /**
     * 实体列表
     * @param t 实体条件
     */
    ResultData list(T t);

    /**
     * id查询
     * @param id 主键
     */
    ResultData findById(String id);

    /**
     * id删除
     * @param id 主键
     */
    ResultData delById(String id);

    /**
     * 新增 更新
     * @param t 操作实体
     */
    ResultData maintain(T t);

    /**
     * 条件查询
     * @param t 实体条件
     */
    ResultData findOne(T t);


}
