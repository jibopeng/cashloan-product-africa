package com.ajaya.cashloan.api.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ajaya.cashloan.cl.service.ClSmsService;
import org.apache.shiro.codec.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ajaya.cashloan.api.user.bean.AppDbSession;
import com.ajaya.cashloan.api.user.bean.AppSessionBean;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.MapUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;

/**
 * Created by lsk on 2017/2/14.
 */
@SuppressWarnings({ "rawtypes" })
public class ApiInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(ApiInterceptor.class);

	@Autowired
	private AppDbSession session;
	@Autowired
	private ThreadPoolTaskExecutor executor;
	@Resource
	private ClSmsService clSmsService;

	public static Map<String,Object> getParams(HttpServletRequest request) {
		Map<String, String[]> rec = request.getParameterMap();
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		for (Map.Entry<String, String[]> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue()[0];
			result.put(name, value);
		}
		return result;
	}

	public static String paramsString(Map<String,Object> map) {
		Map<String, Object> rec = MapUtil.simpleSort(map);
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Object> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			sb.append(name + "=" + value).append("|");
		}

		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		logger.debug("签名验签" + sb.toString());
		return sb.toString();
	}

	private static String md5(String data) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		return Hex.encodeToString(MessageDigest.getInstance("MD5").digest(
				data.getBytes("utf8")));

	}

	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
//			br.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return str;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		Map< String, Object>  requestMap = getParams(request);

		// 系统类型
		String mobileType = StringUtil.isNull(requestMap.get("mobileType"));

		String versionNumber = StringUtil.isNull(requestMap.get("versionNumber"));

		String user_version = StringUtil.isBlank(versionNumber) ? "1.0.0" : versionNumber;
		//版本控制 安卓版本号
		String sys_version = Global.getValue("check_version");
		String ios_sys_version = Global.getValue("check_ios_version");
		//苹果版本号
		//	logger.info("mobileType---------" + mobileType);
		if (sys_version != null && "2".equals(mobileType)) {
				//		logger.info("用户安卓版本号：check_version---------" + sys_version);
				int result = StringUtil.compareVersion(sys_version,user_version);
				if (result > 0) {
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("code", 414);
					m.put("msg","Please update to the latest version");
					m.put("downloadUrl", Global.getValue("app_android_downloadurl"));
					JsonUtil.writeJson(m, response);
					return false;
			}
		} else if(ios_sys_version != null && ("1".equals(mobileType) || "3".equals(mobileType) || "4".equals(mobileType))){
			logger.info("用户苹果版本号：ios_sys_version---------" + ios_sys_version);
			int result = StringUtil.compareVersion(ios_sys_version,user_version);
			if (result > 0) {
				Map<String, Object> m = new HashMap<String,Object>();
				m.put("code", 414);
				m.put("msg","版本过低，下载地址https://dd.duandai.com/h5/ios_index.jsp");
				Map<String, Object> map =  new HashMap<>();
				map.put("downloadUrl", Global.getValue("ios_jump_url"));
				m.put("data", map);
				JsonUtil.writeJson(m, response);
				return false;
			}
		}

		//版本控制结束
		String token = request.getHeader("token");
		String signMsg = request.getHeader("signMsg");

		Map<String, Object> rec = new LinkedHashMap<String, Object>();
		String _signMsg;
		// 登录后的请求地址都带有/act/
		boolean flag;
		if (uri.contains("/act/")) {
			if (StringUtil.isEmpty(token) || StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "sign error!");
				JsonUtil.writeJson(rec, response);
				return false;
			}

			_signMsg = md5(Global.getValue("app_key") + token + paramsString(requestMap));
			flag = _signMsg.equalsIgnoreCase(signMsg);

			// 不需要登录的地址可能没有token
		} else {
			if (StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "sign error.");
				JsonUtil.writeJson(rec, response);
				return false;
			}
			String a=Global.getValue("app_key");
			String b=token;
			String c=paramsString(requestMap);
			_signMsg = md5(Global.getValue("app_key") + (token == null ? "" : token) + paramsString(requestMap));
			flag = _signMsg.equalsIgnoreCase(signMsg);
		}

		// 根据地址是否带/act/生成的_signMsg，校验
		if (!flag) {
			logger.info("验签不通过，方法 {}",uri);
			logger.info("验签不通过，参数 {}",paramsString(requestMap));
			rec.put("code", 400);
			rec.put("msg", "sign error");
			JsonUtil.writeJson(rec, response);
			return false;
		}

		// 如果带有token，则说明已经登陆，将用户数据放入session中
		if (StringUtil.isNotBlank(token) && uri.contains("/act/")) {
			Object result = session.access(token);
			if (result instanceof AppSessionBean) {
				AppSessionBean sessionBean = (AppSessionBean) result;
				logger.debug("userData:"+sessionBean.getSession()+"userId:"+sessionBean.getUserId());
				request.getSession().setAttribute("userData",sessionBean.getSession());
				request.getSession().setAttribute("userId",sessionBean.getUserId());
			} else {
				Map json = (result instanceof Map) ? (Map) result : MapUtil.array2Map((Object[][]) result);
				JsonUtil.writeJson(json, response);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {


	}
}
