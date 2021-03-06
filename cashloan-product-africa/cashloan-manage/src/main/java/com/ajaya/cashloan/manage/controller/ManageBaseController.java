package com.ajaya.cashloan.manage.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import tool.util.NumberUtil;
import tool.util.StringUtil;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.exception.BussinessException;
import com.ajaya.cashloan.core.common.exception.ParamValideException;
import com.ajaya.cashloan.core.common.exception.ServiceException;
import com.ajaya.cashloan.core.common.model.URLConfig;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.ValidateCode;
import com.ajaya.cashloan.core.common.web.controller.AbstractController;
import com.ajaya.cashloan.system.domain.SysRole;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.security.authentication.handler.SaveLoginInfoAuthenticationSuccessHandler;
import com.ajaya.cashloan.system.security.userdetails.UserFunction;
import com.ajaya.cashloan.system.security.userdetails.UserRole;
import com.ajaya.cashloan.system.service.SysRoleService;
import com.ajaya.cashloan.system.service.SysUserService;

/**
 * 
 * ??????action
 * @version 1.0
 * @author ?????????
 * @created 2014???9???23??? ??????1:48:28
 */
@Controller
public abstract class ManageBaseController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ManageBaseController.class);
	
	protected  HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;  

	@Resource
	private URLConfig mlmsAppServerConfig;
	@Resource
	private SysUserService sysUserService;
	@Resource
    private SysRoleService roleService;

    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }  

	/**
	 * ???????????????
	 * 
	 * @param binder
	 */
	@InitBinder
	protected final void initBinderInternal(WebDataBinder binder) {
		registerDefaultCustomDateEditor(binder);
		registerDefaultCustomNumberEditor(binder);
		initBinder(binder);
	}

	private void registerDefaultCustomNumberEditor(WebDataBinder binder) {
		// ????????????????????????????????????: #0.00
		NumberFormat numberFormat = new DecimalFormat("#0.00");
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, numberFormat, true));
	}

	protected void registerDefaultCustomDateEditor(WebDataBinder binder) {
		// ????????????????????????????????????: yyyy-MM-dd
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * ???????????????????????????, ???????????????????????????????????????
	 * 
	 * @param binder
	 */
	protected void initBinder(WebDataBinder binder) {
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param request
	 * @return
	 * @see SaveLoginInfoAuthenticationSuccessHandler
	 */
	protected SysUser getLoginUser(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("SysUser");
		if(obj != null){
			return (SysUser) obj;
		}
		return null;
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return SystemUser
	 * @throws ServiceException
	 */
	protected SysUser getSysUser() throws ServiceException {
		// ????????????????????????
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SysUser sysUser = sysUserService.getUserByUserName(user.getUsername());
		return sysUser;
	}

	/**
	 * ????????????Token ??? ??????????????????<br/>
	 * ????????????????????????????????????tokenName?????? ???session???????????????????????????"true",????????????????????????????????????????????????
	 * isTonten????????????session?????????
	 * 
	 * @param tokenName
	 *            ?????????Token??????
	 * @param request
	 */
	protected void setToken(String tokenName, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		session.setAttribute(tokenName, "true");
	}

	/**
	 * ??????session???Token??????<br/>
	 * ???????????????tokenName??????session???????????????????????????????????????????????????session??????????????????"" <br/>
	 * ?????????????????????????????? ????????????
	 * 
	 * @param tokenName
	 *            request
	 * @param request
	 * @return ??????????????????true??? ????????????false
	 */
	protected String isToken(String tokenName, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		String tokenValue = (String) session.getAttribute(tokenName);
		String paramValue = (String) request.getParameter(tokenName);

		// ?????????session????????????token???????????????
		if (StringUtil.isBlank(paramValue) && StringUtil.isBlank(tokenValue)) {
			return "??????Token????????????";
		} else if (StringUtil.isBlank(paramValue)
				&& !StringUtil.isBlank(tokenValue)) {
			return "??????Token????????????";
		} else if (paramValue.equals(tokenValue)
				&& !StringUtil.isBlank(tokenValue) && "true".equals(tokenValue)) { // session??????token,????????????????????????
			session.setAttribute(tokenName, "false");
			return "";
		} else {
			return "?????????????????????";
		}
	}

	protected void message(HttpServletResponse response) throws IOException {
		this.message(response, "", true);
	}

	/**
	 * ??????????????????
	 * 
	 * @param msg
	 *            ??????
	 */
	protected void message(HttpServletResponse response, String msg)
			throws IOException {
		this.message(response, msg, true);
	}

	/**
	 * ????????????????????????
	 * 
	 * @param msg
	 *            ??????
	 */
	protected void message(HttpServletResponse response, String msg,
			boolean result) throws IOException {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("success", result);
		data.put("msg", msg);
		this.jsonResponse(response, data);
	}

	protected Integer paramInt(HttpServletRequest request, String str) {
		return NumberUtil.getInt(request.getParameter(str));
	}

	protected Long paramLong(HttpServletRequest request, String str) {
		return NumberUtil.getLong(request.getParameter(str));
	}

	protected String paramString(HttpServletRequest request, String str) {
		return StringUtil.isNull(request.getParameter(str));
	}

	protected String redirect(String url) {
		return "redirect:" + mlmsAppServerConfig + url;
	}

	protected String redirectLogin() {
		return redirect("/modules/login.htm");
	}

	protected String success() {
		return redirect("/success.htm");
	}

	protected String error() {
		return redirect("/error.htm");
	}

	protected String success(ModelMap model) {
		return "success";
	}

	protected String error(ModelMap model) {
		return "error";
	}

	/**
	 * ??????URL????????????????????????????????????
	 * 
	 * @param url
	 *            ?????????URL
	 * @return ????????????
	 */
	protected boolean isAllowAccess(String url) {
		Map<String, UserFunction> functionMap = ((UserRole) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal())
				.getFunctionMap();
		if (functionMap.containsKey(url)) {
			return true;
		}
		return false;
	}
	
	/**
	 * shiro ????????????
	 * @param e
	 * @param response
	 */
	@ExceptionHandler({AuthorizationException.class})
	public void authorizationExceptionHandler(AuthorizationException e, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.PERM_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, "??????????????????????????????");
		ServletUtils.writeToResponse(response, res);
	}
	
	
	@ExceptionHandler({Exception.class})
	public void exceptionHandler(Exception e, HttpServletResponse response) {
		 Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "400");
		res.put(Constant.RESPONSE_CODE_MSG, "?????????????????????????????????????????????");
		logger.error("Exception:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public void notNullException(MethodArgumentNotValidException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "400");
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			res.put(Constant.RESPONSE_CODE_MSG, msg);
		} else {
			res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		}
		logger.error("MethodArgumentNotValidException:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	@ExceptionHandler({ParamValideException.class})
	public void paramValideException(ParamValideException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "404");
		res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		logger.error("MethodArgumentNotValidException:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	@ExceptionHandler({ ServiceException.class })
	public void excptionDispose(ServiceException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, e.getCode());
		res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());

		logger.error("ServiceException:", e);

		ServletUtils.writeToResponse(response, res);
	}

    @ExceptionHandler({RuntimeException.class})
    public void runtimeExcptionDispose(RuntimeException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, "???????????????");

		logger.error("RuntimeException", e);

        ServletUtils.writeToResponse(response, res);
    }
    
    
    @ExceptionHandler({BussinessException.class})
    public void bussinessException(BussinessException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(e.getCode())) {
        	res.put(Constant.RESPONSE_CODE, e.getCode());
        } else {
        	 res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        }
        res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
	
		logger.error("BussinessException", e);

        ServletUtils.writeToResponse(response, res);
    }
    
    @ExceptionHandler({BindException.class})
    public void bindException(BindException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, "????????????????????????????????????");
    
		logger.error("??????????????????"+ e.getFieldError().getDefaultMessage(), e);

        ServletUtils.writeToResponse(response, res);
    }
    
	/**
	 * ????????????session ???????????????
	 * 
	 * @param request
	 * @return
	 */
	public List<Long> getRole(HttpServletRequest request) {

		List<Long> roles = new ArrayList<Long>();
		HttpSession session = request.getSession();
		Long role = (Long) session.getAttribute(Constant.ROLEID);
		roles.add(role);

		return roles;

	}

    public SysRole getRoleForLoginUser(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        Long roleId = (Long) session.getAttribute(Constant.ROLEID);
        if (null==roleId) {
			return new SysRole();
		}
        SysRole role = roleService.getRoleById(roleId);

        return role;

    }
    
    public String getLoginUserName(HttpServletRequest request) throws ServiceException {
    	SysUser loginUser = getLoginUser(request);

        return loginUser.getUserName();

    }
    public List<String> getCoverdOffices(SysUser loginUser) {
        ArrayList<String> list = new ArrayList<String>();
        String coverdOfficeStr = loginUser.getOfficeOver();
        if(!org.springframework.util.StringUtils.hasLength(coverdOfficeStr)){
            coverdOfficeStr="null";
        }
        StringTokenizer stringTokenizer = new StringTokenizer(coverdOfficeStr, ",");
        while(stringTokenizer.hasMoreElements()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

	public Map<String, Object> getRequestFormMap(HttpServletRequest request) throws UnsupportedEncodingException{
        String str = getRequestParams(request);
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  
  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = paramValues[0];  
                if (paramValue.length() != 0) {  
                	params.put(paramName, paramValue);  
                }  
            }  
        }  
		if(StringUtil.isNotBlank(str) && params.size()==0){
			String str1 = java.net.URLDecoder.decode(str, "UTF-8");
			String[] strs = str.split("name=");
			String[] strs1 = str1.split("&");
			for (int i = 1; i < strs.length; i++) {
				String temp = strs[i].substring(0, strs[i].indexOf("------"));
				int index = temp.indexOf("\"", 1);
				index = index + 1;
				String key = temp.substring(0, index);
				String value = temp.substring(index, temp.length());
				params.put(key, value);
			}
			for (int i = 0; i < strs1.length; i++) {
				String[] temp = strs1[i].split("=");
				params.put(temp[0], temp[1]);
			}
		}
        return params;
    }
    
	/**
	 * ?????? request.getInputStream()?????????????????????
	 * 
	 * @param request
	 * @return
	 */
	public String getRequestParams(HttpServletRequest request) {
		String params = "";
		try {
			request.setCharacterEncoding("UTF-8");
			InputStream in = request.getInputStream();
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			params = sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return params;
	}
	
	/**
	 * ?????????????????????
	 * @throws Exception
	 */
	protected void generateImgCode() throws Exception{
	    // ??????????????????????????????????????????  
	    response.setContentType("image/jpeg");
	    //?????????????????????  
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    
	    HttpSession session = request.getSession();
	  
	    ValidateCode vCode = new ValidateCode(120,40,4,50);
	    session.setAttribute("code", vCode.getCode());
	    vCode.write(response.getOutputStream());
	    response.getOutputStream().flush();
	}
}
