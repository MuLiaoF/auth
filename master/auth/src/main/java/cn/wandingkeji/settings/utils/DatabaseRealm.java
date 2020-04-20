package cn.wandingkeji.settings.utils;

import cn.wandingkeji.auth.domain.AuthUser;
import cn.wandingkeji.auth.service.AuthPermissionService;
import cn.wandingkeji.auth.service.AuthRoleService;
import cn.wandingkeji.auth.service.AuthUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {

	@Autowired
	private AuthUserService userService;
	@Autowired
	private AuthRoleService roleService;
	@Autowired
	private AuthPermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 能进入到这里，表示账号已经通过验证了
		String LoginAct = (String) principalCollection.getPrimaryPrincipal();
		// 通过service获取角色和权限
		Set<String> permissions = permissionService.getPermissionByLoginAct(LoginAct);
		Set<String> roles = null;
		try {
			roles = roleService.getRoleListByLoginAct(LoginAct);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 授权对象
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		// 把通过service获取到的角色和权限放进去
		s.setStringPermissions(permissions);
		s.setRoles(roles);
		return s;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取账号密码
		UsernamePasswordToken t = (UsernamePasswordToken) token;
		String userName = token.getPrincipal().toString();
		// 获取数据库中的密码
		AuthUser user = userService.getUserByLoginAct(userName);
		String passwordInDB = user.getLoginPwd();
		String salt = user.getSalt();
		// 认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
		// 盐也放进去
		// 这样通过applicationContext-shiro.xml里配置的 HashedCredentialsMatcher 进行自动校验
		SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt),
				getName());
		return a;
	}

}