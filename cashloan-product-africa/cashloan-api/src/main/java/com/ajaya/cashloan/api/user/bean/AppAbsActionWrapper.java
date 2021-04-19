package com.ajaya.cashloan.api.user.bean;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.MapUtil;

/**
 * Created by lsk on 2016/8/16.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AppAbsActionWrapper {
    private Logger logger = LoggerFactory.getLogger(AppAbsActionWrapper.class);
    protected HttpServletRequest _req;
    protected HttpServletResponse _resp;
    protected boolean writeJson = true;


    public AppAbsActionWrapper(HttpServletResponse _resp) {
        this(_resp, null);
    }


    public static String toUnderLine(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c >= 65 && c <= 90) {
                c += 32;
                sb.append("_");
            }
            sb.append(c);
        }
        return sb.toString();
    }


    public AppAbsActionWrapper(HttpServletResponse _resp, HttpServletRequest _req) {
        this._resp = _resp;
        this._req = _req;

        Object result = null;
        try {
            result = doAction();
            if (result == null) {
                result = new Object[][]{
                    {"success", true},
                    {"msg", "操作成功"}
                };
            }

            if (result instanceof Object[][]) {
                result = MapUtil.array2Map((Object[][]) result);
            }
            if (result instanceof Map) {
                Map _result = (Map) result;
                if (!_result.containsKey("success")) {
                    _result.put("success", true);
                }
                if (!_result.containsKey("code")) {
                    boolean success = (Boolean) _result.get("success");
                    _result.put("code", success ? 200 : 400);
                }


                Boolean success = (Boolean) _result.get("success");
                if (success != null) {
                    _result.remove("success");
                    if (!_result.containsKey("code")) {
                        _result.put("code", success ? 200 : 400);
                    }
                }


                Object data = ((Map) result).get("data");
                if (data instanceof Map) {
                    MapUtil.replaceNullValue2EmptyStr((Map) data);
                }
            }

        } catch (Exception e) {
            logger.warn("{}", e);
            result = MapUtil.array2Map(new Object[][]{
                    {"code", 500},
                {"msg", "System exception, please try again later, or contact your system administrator"}
            });
        }
        if (writeJson) {
            JsonUtil.writeJson(result, _resp);
        }
    }

    public abstract Object doAction() ;
}
