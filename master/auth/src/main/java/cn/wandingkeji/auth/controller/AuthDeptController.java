package cn.wandingkeji.auth.controller;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthDept;
import cn.wandingkeji.auth.service.AuthDeptService;
import cn.wandingkeji.auth.system.BaseController;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sys/auth/dept")
public class AuthDeptController extends BaseController<AuthDept> {

    private static final Logger log = LoggerFactory.getLogger(AuthUserController.class);

    @Autowired
    private AuthDeptService authDeptService;

    @RequestMapping("/add")
    public ResultData addDept(AuthDept authDept) throws Exception {
        ResultData resultData  = null;
        try {
            resultData = authDeptService.addDept(authDept);
        } catch (Exception e) {
            e.printStackTrace();
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加失败");
        }
        return resultData;
    }


    @RequestMapping("/add/role")
    public ResultData addDeptPre(String id, String roleParId, String[] roleIds) {
        ResultData resultData = null;
        try {
            resultData = authDeptService.addDeptRole(id, roleParId, roleIds);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加失败");
        }
        return resultData;
    }

}
