package cn.wandingkeji.auth.service.impl;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.*;
import cn.wandingkeji.auth.mapper.*;
import cn.wandingkeji.auth.service.AuthUserService;
import cn.wandingkeji.auth.system.BaseServiceImpl;
import cn.wandingkeji.auth.utils.ConstantUtils;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import cn.wandingkeji.auth.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUserMapper,AuthUser> implements AuthUserService {

    private static final Logger log = LoggerFactory.getLogger(AuthUserServiceImpl.class);

    //@Autowired
    //private AuthUserPermissionService authPermissionService;

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    private AuthUserPermissionMapper authUserPermissionMapper;

    @Autowired
    private AuthDeptRoleMapper authDeptRoleMapper;

    @Override
    public AuthUser getUserByLoginAct(String userName) {
        AuthUser authUser = new AuthUser();
        authUser.setLoginAct(userName);
        return authUserMapper.selectOne(authUser);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public ResultData addUser(AuthUser user) {

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = ConstantUtils.MD5_TYPE;
        String encodedPassword = new SimpleHash(algorithmName, user.getLoginPwd(), salt, times).toString();

        AuthUser authUser = null;
        String id = user.getId();
        String userId = user.getUserId();
        if(id != null || userId != null) {
            authUser.setId(id);
            authUser.setUserId(userId);
            authUser = authUserMapper.selectById(id);
        }

        //如果该用户已经注册过
        if(authUser != null) {
            return ExceptionConstantsUtils.printErrorMessage(log, "该用户已经注册过了");
        }

        AuthUser u = new AuthUser();
        u.setUserId(user.getUserId());
        u.setName(user.getName());
        u.setLoginAct(user.getLoginAct());
        u.setLoginPwd(encodedPassword);
        u.setSalt(salt);
        u.setId(StringUtils.GetUUID());
        Integer insert = null;
        try {
            insert = baseMapper.insert(u);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e,"添加错误");
        }

        return ExceptionConstantsUtils.printSuccessMessage(log, "添加成功");
    }

    /**
     * 修改用户权限
     * @param id
     * @param perIds
     * @return
     */
    @Override
    public ResultData updateUserPer(String id, String[] perIds) {

        AuthUser authUser = authUserMapper.selectById(id);

        if(authUser == null) {
            return ExceptionConstantsUtils.printErrorMessage(log,"该用户不存在");
        }

        List<String> perList = Arrays.asList(perIds);

        //查询权限是否存在
        List<String> perSelList = authPermissionMapper.selectPerList(perList);

        if(perSelList.size() <= 0) {
            return ExceptionConstantsUtils.printErrorMessage(log, "权限查询失败");
        }


        try {

            //查询用户权限  查询特殊权限以及角色权限
            List<AuthUserPermission> userPerList = authUserPermissionMapper.selectUserPerById(authUser.getId());

            List<String> perHaveList = new ArrayList<>();
            for (int i = 0; i < userPerList.size(); i++) {
                perHaveList.add(userPerList.get(i).getPerId());
            }

            log.info("已拥有的权限"+ perHaveList);
            log.info("权限" + perList);

            List<AuthUserPermission> perAddList = new ArrayList<>();

            for (int i = 0; i < perIds.length; i++) {

                //如果传入的权限ID，和查出来的权限ID相同就跳过
                if(perHaveList.contains(perList.get(i))) {
                    continue;
                } else {
                    //如果传入的权限ID，和查出来的权限ID不相同，就向数据库中插入
                    AuthUserPermission authUserPermission = new AuthUserPermission();
                    authUserPermission.setPerId(perList.get(i));
                    authUserPermission.setId(StringUtils.GetUUID());
                    authUserPermission.setUserId(id);

                    perAddList.add(authUserPermission);
                }
            }

            //批量添加

            if (perAddList.size() > 0)
                authUserPermissionMapper.insertBatch(perAddList);

            //authPermissionService.insertBatch(perAddList);

        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加用户权限失败");
        }

        return ExceptionConstantsUtils.printSuccessMessage(log, "添加用户权限成功");
    }

    /**
     * 给用户添加角色
     * @param id
     * @param roleIds
     * @return
     */
    @Override
    public ResultData updateUserRole(String id, String deptId, String[] roleIds) {

        AuthUser authUser = authUserMapper.selectById(id);


        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.in("id", roleIds);

        List<AuthDeptRole> authDeptRoleList = authDeptRoleMapper.selectList(entityWrapper);

        if(authUser == null) {
            return ExceptionConstantsUtils.printErrorMessage(log,"该用户不存在");
        }

        if(authDeptRoleList.size() <= 0) {
            return ExceptionConstantsUtils.printErrorMessage(log,"该部门下没有该角色");
        }

        List<String> roleList = new ArrayList<>();

        for (int i = 0; i < authDeptRoleList.size(); i++) {
            roleList.add(authDeptRoleList.get(i).getId());
        }

        try {

            //查询该用户下的角色
            List<AuthUserRole> roleSelList = authUserRoleMapper.selectUserRoleByRoleId(authUser.getId(), roleList);

            log.info("权限" + roleSelList);

            List<String> roleHaveList = new ArrayList<>();
            for (int i = 0; i < roleSelList.size(); i++) {
                roleHaveList.add(roleSelList.get(i).getDeptRoleId());
            }

            List<AuthUserRole> roleAddList = new ArrayList<>();

            for (int i = 0; i < roleIds.length; i++) {

                //如果传入的角色ID，和查出来的角色ID相同就跳过
                if(roleHaveList.contains(roleList.get(i))) {
                    continue;
                } else {
                    //如果传入的角色ID，和查出来的角色ID不相同，就向数据库中插入
                    AuthUserRole authUserRole = new AuthUserRole();
                    authUserRole.setId(StringUtils.GetUUID());
                    authUserRole.setDeptRoleId(roleList.get(i));
                    authUserRole.setUserId(id);
                    roleAddList.add(authUserRole);
                }

            }

            if(roleAddList.size() > 0) {
                authUserRoleMapper.insertBatch(roleAddList);
            }


        } catch (Exception e) {

            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加用角色失败");
        }

        return ExceptionConstantsUtils.printSuccessMessage(log,"添加用角色成功");
    }
}
