package cn.wandingkeji.auth.controller;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthDictData;
import cn.wandingkeji.auth.service.AuthDictDataSerivce;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthDictDataController {


    @Autowired
    private AuthDictDataSerivce authsDictDataService;

    @PostMapping("/page")
    public ResultData findDeptPage(String simpleName, Integer pageNum, Integer pageSize) {
        Page<AuthDictData> page = new Page<>(pageNum == null ? 1 : pageNum,
                pageSize == null ? 10 : pageSize);
        return authsDictDataService.findDictPage(page, simpleName);
    }

    @PostMapping("/list")
    public ResultData findDictLIst() {
        return authsDictDataService.findDicttAll();
    }

    @PostMapping("/findById")
    public ResultData findById(String id) {
        return authsDictDataService.findById(id);
    }

    /**
     * 更新或新增
     * @param pSysDict
     * @return
     */
    @PostMapping("/maintain")
    public ResultData maintain(AuthDictData pSysDict) {
        return authsDictDataService.maintain(pSysDict);
    }

    @PostMapping("/deleteById")
    public ResultData deleteById(String id) {
        return authsDictDataService.delDict(id);
    }


}
