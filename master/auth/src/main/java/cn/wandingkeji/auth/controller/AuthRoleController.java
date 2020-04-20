package cn.wandingkeji.auth.controller;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthRole;
import cn.wandingkeji.auth.service.AuthRoleService;
import cn.wandingkeji.auth.system.BaseController;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/auth/role")
public class AuthRoleController extends BaseController<AuthRole> {


    private static final Logger log = LoggerFactory.getLogger(AuthRoleController.class);

    @Autowired
    private AuthRoleService authRoleService;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/add")
    public ResultData addRole(AuthRole role) {

        ResultData resultData = null;
        try {
            resultData = authRoleService.addRole(role);

        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加角色失败");
        }

        return resultData;

    }

    /**
     * 向角色中添加权限
     * @param id
     * @param perIds
     * @return
     */
    @RequestMapping("/add/pre")
    public ResultData addRolePer(String id,
                                 String[] perIds) {

        ResultData resultData = null;
        try {
            resultData = authRoleService.addRolePre(id, perIds);

        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加角色失败");
        }

        return resultData;
    }


}
