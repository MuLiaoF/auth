package cn.wandingkeji.auth.service.impl;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.domain.AuthPermission;
import cn.wandingkeji.auth.domain.AuthUser;
import cn.wandingkeji.auth.domain.AuthUserPermission;
import cn.wandingkeji.auth.mapper.AuthPermissionMapper;
import cn.wandingkeji.auth.mapper.AuthUserMapper;
import cn.wandingkeji.auth.mapper.AuthUserPermissionMapper;
import cn.wandingkeji.auth.service.AuthPermissionService;
import cn.wandingkeji.auth.system.BaseServiceImpl;
import cn.wandingkeji.auth.utils.ExceptionConstantsUtils;
import cn.wandingkeji.auth.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthPermissionServiceImpl extends BaseServiceImpl<AuthPermissionMapper, AuthPermission> implements AuthPermissionService {


    private static final Logger log = LoggerFactory.getLogger(AuthPermissionServiceImpl.class);

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserPermissionMapper authUserPermissionMapper;

    @Override
    public boolean containPermissionPath(String requestURI) {
        AuthPermission authPermission = new AuthPermission();
        authPermission.setUrl(requestURI);
        AuthPermission authPermissionSel = authPermissionMapper.selectOne(authPermission);

        if(authPermissionSel == null) {
            return false;
        }
        return true;
    }

    @Override
    public Set<String> haveUserPermission(String userName) {
        return null;
    }

    @Override
    public List<AuthPermission> list() {
        //EntityWrapper<AuthPermission> entityWrapper = new EntityWrapper();
        return authPermissionMapper.selectAll();
    }

    @Override
    public Set<String> getPermissionByLoginAct(String loginAct) {

        AuthUser authUser = new AuthUser();
        authUser.setLoginAct(loginAct);
        authUser = authUserMapper.selectOne(authUser);

        List<AuthUserPermission> authUserPermissions = authUserPermissionMapper.selectUserPerById(authUser.getId());

        Set<String> perSet = new HashSet<>();

        for (AuthUserPermission authUserPermission:authUserPermissions) {
            perSet.add(authUserPermission.getPerId());
        }

        return perSet;
    }


    /**
     * 添加权限
     * @param authPermission
     * @return
     */
    @Override
    public ResultData addPer(AuthPermission authPermission) {

        AuthPermission authPermissionOld = authPermissionMapper.selectOne(authPermission);

        if(authPermissionOld != null) {
            return ExceptionConstantsUtils.printErrorMessage(log, "该权限已存在，添加失败");
        }
        authPermission.setId(StringUtils.GetUUID());
        Integer count = authPermissionMapper.insert(authPermission);

        return ExceptionConstantsUtils.printSuccessMessage(log, "添加成功");
    }

    /**
     * 分页查询
     * @param topcode
     * @param pcode
     * @param menuName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultData getAll(String topcode, String pcode, String menuName, Integer pageNum, Integer pageSize) {
        Page<AuthPermission> page = new Page<>(pageNum == null ? 1 : pageNum,
                pageSize == null ? 2 : pageSize);
        List<AuthPermission> authPermissions = authPermissionMapper.selectPerPage(topcode, page, pcode, menuName);
        page.setRecords(authPermissions);
        return ExceptionConstantsUtils.printSuccessMessage(log, "查询权限成功", page);
    }
}
