package com.ajaya.cashloan.manage.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.system.domain.SysUser;


@Service
public class ManageSessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getSession() == null){
			return true;
		}
		
		Session session = SecurityUtils.getSubject().getSession();
		SysUser sysUser = (SysUser) session.getAttribute("SysUser");
		Object noSession = session.getAttribute("noSession");
	  	if(sysUser == null && noSession == null){
	  		//SecurityUtils.getSubject().getSession().setAttribute("noSession", true);
	  		
	  		Map<String,Object> result = new HashMap<String,Object>();
			result.put(Constant.RESPONSE_CODE, Constant.NOSESSION_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "You are not login or your login has expired.Please login first.");
			ServletUtils.writeToResponse(response,result);
			
	  		return false;
	  	}
	  	if(sysUser != null && sysUser.getStatus().equals("1")){
	  		Map<String,Object> result = new HashMap<String,Object>();
			result.put(Constant.RESPONSE_CODE, Constant.NOSESSION_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "Your account has been locked, please contact the administrator to unlock it");
			ServletUtils.writeToResponse(response,result);
	  	}
		return true;
	}
}
