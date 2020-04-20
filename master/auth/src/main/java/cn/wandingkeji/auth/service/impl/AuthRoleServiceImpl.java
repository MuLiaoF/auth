package cn.wandingkeji.auth.service.impl;
import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.*;
import cn.wandingkeji.auth.mapper.*;
import cn.wandingkeji.auth.service.AuthRoleService;
import cn.wandingkeji.auth.system.BaseServiceImpl;
import cn.wandingkeji.auth.utils.ConstantUtils;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import cn.wandingkeji.auth.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthRoleServiceImpl  extends BaseServiceImpl<AuthRoleMapper, AuthRole> implements AuthRoleService {

    private static final Logger log = LoggerFactory.getLogger(AuthRoleServiceImpl.class);

    @Autowired
    private AuthDeptRoleMapper authDeptRoleMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public Set<String> getRoleListByLoginAct(String loginAct) throws Exception {

        AuthUser authUser = new AuthUser();
        authUser.setLoginAct(loginAct);

        AuthUser authUserSel = authUserMapper.selectOne(authUser);

        if(authUserSel == null) {
            return null;
        }

        EntityWrapper<AuthUserRole> entityWrapper = new EntityWrapper();
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(authUser.getId());
        entityWrapper.setEntity(authUserRole);

        List<AuthUserRole> authUserRoles = authUserRoleMapper.selectList(entityWrapper);


        Set<String> roleSet = new HashSet<>();

        for(AuthUserRole role : authUserRoles) {
            roleSet.add(role.getDeptRoleId());
        }

        return roleSet;
    }


    /**
     * 添加角色
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public ResultData addRole(AuthRole role) throws Exception {

        Integer roleCount = null;

        try {

            AuthRole oldRole = new AuthRole();
            oldRole.setName(role.getName());

            //根据name查询角色
            AuthRole authRole = authRoleMapper.selectOne(oldRole);

            if(authRole != null) {
                return ExceptionConstantsUtils.printErrorMessage(log,"该角色已经存在");
            }

            log.debug("插入角色", role);

            //没有该角色 向数据库中添加
            role.setId(StringUtils.GetUUID());
            roleCount = authRoleMapper.insert(role);

        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e ,"添加角色失败");
        }

        //如果添加成功，
        if(roleCount >= 0) {
            return ExceptionConstantsUtils.printSuccessMessage(log,"添加成功");
        } else {
            return ExceptionConstantsUtils.printErrorMessage(log,"失败");
        }
    }


    /**
     * 向角色中添加权限
     * @param id
     * @param perIds
     * @return
     */
    @Override
    public ResultData addRolePre(String id, String[] perIds) {

        //查询该角色是否存在
        AuthDeptRole authDeptRole = authDeptRoleMapper.selectById(id);


        if(authDeptRole == null) {
            return ExceptionConstantsUtils.printErrorMessage(log, "该部门下角色不存在");
        }


        List<String> perList = null;

        EntityWrapper<AuthPermission> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", perIds);

        //查询权限是否存在
        List<AuthPermission> authPermissions = authPermissionMapper.selectList(entityWrapper);

        if(authPermissions.size() == 0) {
            return ExceptionConstantsUtils.printErrorMessage(log, "权限不存在");
        }

        perList = new ArrayList<>();
        for(AuthPermission authPermission : authPermissions) {
            perList.add(authPermission.getId());
        }

        List<AuthRolePermission> perSelList = null;
        try {

            //查询该部门下的角色的权限是否存在
            perSelList = authRolePermissionMapper.selectRolePerByRoleId(id, perList);
        } catch (Exception e) {
            return ExceptionConstantsUtils.printErrorMessage(log, e, "查询权限失败");
        }

        try {
            log.info("权限" + perSelList);

            List<String> perHaveList = new ArrayList<>();

            for (int i = 0; i < perSelList.size(); i++) {
                perHaveList.add(perSelList.get(i).getPerId());
            }

            List<AuthRolePermission> roleAddList = new ArrayList<>();

            for (int i = 0; i < perIds.length; i++) {

                //如果传入的权限ID，和查出来的权限ID相同就跳过
                if(perHaveList.contains(perList.get(i))) {
                    continue;
                } else {
                    //如果传入的权限ID，和查出来的权限ID不相同，就向数据库中插入
                    AuthRolePermission authRolePermission = new AuthRolePermission();
                    authRolePermission.setId(StringUtils.GetUUID());
                    authRolePermission.setRoleId(authDeptRole.getRoleId());
                    authRolePermission.setPerId(perList.get(i));
                    authRolePermission.setDeptId(authDeptRole.getDeptId());
                    authRolePermission.setDeptRoleId(authDeptRole.getId());
                    roleAddList.add(authRolePermission);
                }
            }

            if(roleAddList.size() > 0) {
                authRolePermissionMapper.insertBatch(roleAddList);
            }

        } catch (Exception e) {

            return ExceptionConstantsUtils.printErrorMessage(log, e, "插入角色与权限的关系失败");
        }

        return ExceptionConstantsUtils.printSuccessMessage(log, "插入成功");
    }


}
