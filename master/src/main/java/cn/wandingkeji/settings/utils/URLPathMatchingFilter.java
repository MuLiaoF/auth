package cn.wandingkeji.settings.utils;

import cn.wandingkeji.auth.service.AuthPermissionService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {

	@Autowired
	private AuthPermissionService permissionService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		if(null==permissionService) 
			permissionService = SpringContextUtils.getContext().getBean(AuthPermissionService.class);

		HttpServletResponse res = (HttpServletResponse) response;

		res.setContentType("application/json;charset=utf-8");

		String requestURI = getPathWithinApplication(request);
		System.out.println("requestURI:" + requestURI);

		Subject subject = SecurityUtils.getSubject();
		// 如果没有登录，就跳转到登录页面
		if (!subject.isAuthenticated()) {
			//WebUtils.issueRedirect(request, response, "/login");
			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("msg", "请先登录");
			res.getWriter().println(JSONObject.toJSON(returnMap).toString());
			return false;
		}

		// 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
		boolean needInterceptor = permissionService.containPermissionPath(requestURI);
		if (!needInterceptor) {
			return true;
		} else {
			boolean hasPermission = false;
			String loginAct = subject.getPrincipal().toString();
			Set<String> permissionUrls = permissionService.getPermissionByLoginAct(loginAct);
			//遍历改用户当下的角色所拥有的所有权限
			for (String url : permissionUrls) {
				// 这就表示当前用户有这个权限
				if (url.equals(requestURI)) {
					hasPermission = true;
					break;
				}
			}

			if (hasPermission)
				return true;
			else {
				UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");

				subject.getSession().setAttribute("ex", ex);

				Map<String, Object> returnMap = new HashMap<>();
				returnMap.put("msg", "当前用户没有访问路径 " + requestURI + " 的权限");
				res.getWriter().println(JSONObject.toJSON(returnMap).toString());
				return false;
			}

		}

	}
}