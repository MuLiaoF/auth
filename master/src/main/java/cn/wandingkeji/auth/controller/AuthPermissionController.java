package cn.wandingkeji.auth.controller;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthPermission;
import cn.wandingkeji.auth.service.AuthPermissionService;
import cn.wandingkeji.auth.service.impl.AuthPermissionServiceImpl;
import cn.wandingkeji.auth.system.BaseController;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import cn.wandingkeji.auth.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/sys/auth/per")
public class AuthPermissionController extends BaseController<AuthPermission> {

    private static final Logger log = LoggerFactory.getLogger(AuthPermissionController.class);

    @Autowired
    private AuthPermissionService authPermissionService;


    @RequestMapping("/add")
    public ResultData addPer(AuthPermission authPermission) {
        ResultData resultData  = null;
        try {
            resultData = authPermissionService.addPer(authPermission);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e,null);
        }
        return resultData;
    }


    @RequestMapping("/find")
    public ResultData findAll(String topcode, String pcode, String menuName, Integer pageNum, Integer pageSize) {
        return authPermissionService.getAll(topcode, pcode, menuName, pageNum, pageSize);
    }


}
