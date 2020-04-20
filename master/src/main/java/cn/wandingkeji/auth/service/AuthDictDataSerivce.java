package cn.wandingkeji.auth.service;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthDictData;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

@Service
public interface AuthDictDataSerivce {

    /**
     * 获取字典列表分页
     *
     * @param simpleName 字典名称
     */
    ResultData findDictPage(Page<AuthDictData> page, String simpleName);


    ResultData findDicttAll();

    ResultData findById(String id);

    ResultData maintain(AuthDictData pSysDict);

    ResultData delDict(String id);

}
