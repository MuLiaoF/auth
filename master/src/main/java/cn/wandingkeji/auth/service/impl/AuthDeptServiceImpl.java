package cn.wandingkeji.auth.service.impl;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthDept;
import cn.wandingkeji.auth.domain.AuthDeptRole;
import cn.wandingkeji.auth.domain.AuthDictData;
import cn.wandingkeji.auth.domain.AuthRole;
import cn.wandingkeji.auth.mapper.AuthDeptMapper;
import cn.wandingkeji.auth.mapper.AuthDeptRoleMapper;
import cn.wandingkeji.auth.mapper.AuthDictDataMapper;
import cn.wandingkeji.auth.mapper.AuthRoleMapper;
import cn.wandingkeji.auth.service.AuthDeptService;
import cn.wandingkeji.auth.system.BaseServiceImpl;
import cn.wandingkeji.auth.utils.ConstantUtils;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import cn.wandingkeji.auth.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AuthDeptServiceImpl extends BaseServiceImpl<AuthDeptMapper, AuthDept> implements AuthDeptService {

    private static final Logger log = LoggerFactory.getLogger(AuthDeptServiceImpl.class);

    @Autowired
    private AuthDictDataMapper authDictDataMapper;

    @Autowired
    private AuthDeptMapper authDeptMapper;

    @Autowired
    private AuthDeptRoleMapper authDeptRoleMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;
    /**
     * 添加部门
     * @param authDept
     * @return
     */
    @Override
    public ResultData addDept(AuthDept authDept) {

        //如果有上级
        AuthDept dept = null;

        //如果传入了归属部门，没有查到
        if(authDept.getPid() != null) {
            dept = new AuthDept();
            dept.setId(authDept.getPid());
            dept = authDeptMapper.selectOne(dept);
            if(dept == null) {
                return ExceptionConstantsUtils.printErrorMessage(log, "该父级部门不存在");
            }

            dept.setPid(authDept.getPid());
            dept.setDeptName(authDept.getDeptName());
            dept.setId(null);

            AuthDept authDeptOld = authDeptMapper.selectOne(dept);

            if(authDeptOld != null) {
                return ExceptionConstantsUtils.printErrorMessage(log, "已存在该部门");
            }

        }


        if(dept == null) {
            //dept.setDeptName();
            EntityWrapper<AuthDept> entityWrapper = new EntityWrapper();
            entityWrapper.where("pid is null");
            entityWrapper.where("dept_name={0}", authDept.getDeptName());
            List<AuthDept> authDepts = authDeptMapper.selectList(entityWrapper);


            if(authDepts.size() >= 0 || authDepts == null) {
                return ExceptionConstantsUtils.printErrorMessage(log, "该部门已存在");
            }
        }


        //如果上级部门下没有该部门
        Integer count = null;
        try {
            authDept.setId(StringUtils.GetUUID());
         //   authDictDataMapper.insert();
            count = authDeptMapper.insert(authDept);
            log.info("插入" + count);
        } catch (Exception e) {
            e.printStackTrace();
            return ExceptionConstantsUtils.printErrorMessage(log, e, "添加部门失败");
        }
        return ExceptionConstantsUtils.printSuccessMessage(log, "添加部门成功");
    }

    /**
     * 向部门下添加角色
     * @param id
     * @param roleIds
     * @return
     */
    @Override
    public ResultData addDeptRole(String id, String roleParId, String[] roleIds) {

        AuthDeptRole authDeptRoleOld = new AuthDeptRole();
        AuthDeptRole deptRole = null;
                //查询部门
        AuthDept dept = authDeptMapper.selectById(id);

        if(dept == null) {
            return ExceptionConstantsUtils.printErrorMessage(log, "该部门不存在");
        }

        AuthRole authRole = null;

        //如果角色上司传递了
        if(roleParId != null) {

            //查询该上司角色是否存在
            authRole = authRoleMapper.selectById(roleParId);

            if(authRole == null) {
                return ExceptionConstantsUtils.printErrorMessage(log, "该上司角色不存在");
            }

            authDeptRoleOld.setRoleId(roleParId);
            authDeptRoleOld.setDeptId(id);

            //查询该部门下的上司角色是否存在
            deptRole = authDeptRoleMapper.selectOne(authDeptRoleOld);

            if(deptRole == null) {
                return ExceptionConstantsUtils.printErrorMessage(log, "该角色的上司不在其所在部门下");
            }
        }

        //将数组装换成集合
        List<String> roleList = Arrays.asList(roleIds);

        //查询该角色是否存在
        roleList = authRoleMapper.selectRoleList(roleList);

        try {

            //查询该部门下是否有这个角色
            List<AuthDeptRole> roleSelList = authDeptRoleMapper.selectDeptRoleByRoleId(id, roleList);

            log.info("角色" + roleSelList.size());

            //创建集合存放  已经存在的角色的ID
            List<String> roleHaveList = new ArrayList<>();
            for (int i = 0; i < roleSelList.size(); i++) {
                roleHaveList.add(roleSelList.get(i).getRoleId());
            }

            //需要添加的角色
            List<AuthDeptRole> roleAddList = new ArrayList<>();

            for (int i = 0; i < roleIds.length; i++) {

                //如果传入的角色ID，和查出来的角色ID相同就跳过
                if(roleHaveList.contains(roleList.get(i))) {
                    if(deptRole != null) {
                        AuthDeptRole authDeptRole = new AuthDeptRole();
                        authDeptRole.setId(StringUtils.GetUUID());
                        authDeptRole.setRoleId(roleList.get(i));
                        authDeptRole.setDeptId(id);
                        authDeptRole.setPid(roleParId);
                        roleAddList.add(authDeptRole);
                    }
                } else {
                    //如果传入的角色ID，和查出来的角色ID不相同，就向数据库中插入
                    AuthDeptRole authDeptRole = new AuthDeptRole();
                    authDeptRole.setId(StringUtils.GetUUID());
                    authDeptRole.setRoleId(roleList.get(i));
                    authDeptRole.setDeptId(id);
                    //如果上司角色不为空
                    if(deptRole != null) {
                        authDeptRole.setPid(roleParId);
                    }
                    roleAddList.add(authDeptRole);
                }
            }


            if (roleAddList.size() > 0) {
                //批量插入
                authDeptRoleMapper.insertBatch(roleAddList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ExceptionConstantsUtils.printErrorMessage(log , e , "查询角色失败");
        }

        return ExceptionConstantsUtils.printSuccessMessage(log, "添加成功");
    }




}
