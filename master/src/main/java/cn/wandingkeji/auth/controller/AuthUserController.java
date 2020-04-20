package cn.wandingkeji.auth.controller;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthUser;
import cn.wandingkeji.auth.service.AuthUserService;
import cn.wandingkeji.auth.system.BaseController;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sys/auth/user")
public class AuthUserController extends BaseController<AuthUser> {

    private static final Logger log = LoggerFactory.getLogger(AuthUserController.class);

    @Autowired
    private AuthUserService authUserService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public ResultData addUser(AuthUser user) {
        ResultData resultData = null;
        try {
            resultData = authUserService.addUser(user);

        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加失败");
        }
        return resultData;
    }

    /**
     * 向用户添加权限
     * @param id
     * @param perIds
     * @return
     */
    @RequestMapping("/add/per")
    public ResultData updateUserPer(String id, String[] perIds) {

        ResultData resultData = null;
        try {
            resultData = authUserService.updateUserPer(id,  perIds);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加失败");
        }
        return resultData;
    }

    /**
     * 向用户添加角色
     * @param id
     * @param roleIds
     * @return
     */
    @RequestMapping("/add/role")
    public ResultData updateUserRole(String id, String deptId, String[] roleIds) {

        ResultData resultData = null;
        try {
            resultData = authUserService.updateUserRole(id, deptId, roleIds);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e , "添加失败");
        }
        return resultData;
    }




}
