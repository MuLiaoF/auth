package cn.wandingkeji.auth.system;

import cn.wandingkeji.auth.contants.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public abstract class BaseController<T> {

    @Autowired
    private BaseService<T> baseService;

    @PostMapping("/list")
    public ResultData list(T t) {
        return baseService.list(t);
    }

    /**
     * id查询
     */
    @PostMapping("/findById")
    public ResultData findById(String id) {
        return baseService.findById(id);
    }

    /**
     * id删除
     */
    @PostMapping("/deleteById")
    public ResultData deleteById(String id) {
        return baseService.delById(id);
    }

    /**
     * 新增更新
     */
    @PostMapping("/maintain")
    public ResultData maintain(T t) {
        return baseService.maintain(t);
    }

    /**
     * 条件查询单个实体
     */
    @PostMapping("/findOne")
    public ResultData findOne(T t) {
        return baseService.findOne(t);
    }


}
