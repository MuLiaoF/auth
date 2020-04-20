package cn.wandingkeji.auth.service.impl;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.contants.ResultEnum;
import cn.wandingkeji.auth.domain.AuthDictData;
import cn.wandingkeji.auth.mapper.AuthDictDataMapper;
import cn.wandingkeji.auth.service.AuthDictDataSerivce;
import cn.wandingkeji.auth.system.BaseServiceImpl;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AuthDictDataServiceImpl  extends BaseServiceImpl<AuthDictDataMapper, AuthDictData> implements AuthDictDataSerivce {


    private static final Logger log = LoggerFactory.getLogger(AuthDictDataServiceImpl.class);

    @Autowired
    private AuthDictDataMapper authDictDataMapper;

    @Override
    public ResultData findDictPage(Page<AuthDictData> page, String simpleName) {
        List<AuthDictData> sysDeptList = authDictDataMapper.selectPage(page, new EntityWrapper<>(new AuthDictData())
                .like(!StringUtils.isEmpty(simpleName), "simple_name", simpleName)
                .orderBy("num", true));
        page.setRecords(sysDeptList);
        return ExceptionConstantsUtils.printSuccessMessage(log, "查询成功");
    }

    @Override
    public ResultData findDicttAll() {
        List<AuthDictData> sysDictList = authDictDataMapper.selectList(new EntityWrapper<>(new AuthDictData()).orderBy("num", true));
        if (CollectionUtils.isEmpty(sysDictList)) {
            return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.LIST_EMPTY.getMsg());
        }
        return ExceptionConstantsUtils.printSuccessMessage(log, "查询成功" ,sysDictList);
    }

    @Override
    public ResultData findById(String id) {
        AuthDictData sysDict = authDictDataMapper.selectById(id);
        if (sysDict != null) {
            return ExceptionConstantsUtils.printSuccessMessage(log, "查询成功" ,sysDict);
        } else {
            return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.HANDLE_ERROR.getMsg());
        }
    }


    @Override
    @Transactional
    public ResultData maintain(AuthDictData sysDict) {
        if (org.springframework.util.StringUtils.isEmpty(sysDict.getDictCode())) {
            AuthDictData tempSysDict = authDictDataMapper.findByDictType(sysDict.getDictType(), null);
            if (tempSysDict != null) {
                return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.DEPT_NAME_REPEAT_ERROR.getMsg());
            }
            int sign = authDictDataMapper.insert(sysDict);
            if (sign <= 0) {
                return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.HANDLE_ERROR.getMsg());
            }
        } else {
            AuthDictData sysDictPersis = authDictDataMapper.selectById(sysDict.getDictCode());
            if (sysDictPersis == null) {
                return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.HANDLE_ERROR.getMsg());
            }
            /*AuthDictData tempSysDept = authDictDataMapper.findByDictType(sysDict.getDictType(), sysDictPersis.getDictCode());
            if (tempSysDept != null) {
                return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.DEPT_NAME_REPEAT_ERROR.getMsg());
            }*/
            BeanUtils.copyProperties(sysDict, sysDictPersis);
            int sign = authDictDataMapper.update(sysDictPersis ,null);
            if (sign <= 0) {
                return ExceptionConstantsUtils.printErrorMessage(log, ResultEnum.HANDLE_ERROR.getMsg());
            }
        }
        return ExceptionConstantsUtils.printSuccessMessage(log, ResultEnum.HANDLE_SUCCESS.getMsg());
    }

    @Override
    @Transactional
    public ResultData delDict(String id) {
        authDictDataMapper.deleteById(id);
        return ExceptionConstantsUtils.printSuccessMessage(log, ResultEnum.HANDLE_SUCCESS.getMsg());

    }

}
