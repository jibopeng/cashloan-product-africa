package com.ajaya.cashloan.api.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.CacheUtil;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.system.domain.SysConfig;
import com.ajaya.cashloan.system.permission.annotation.RequiresPermission;
import com.ajaya.cashloan.system.service.SysConfigService;

/**
 * API System Controller
 *
 * @author gc
 * @version 1.0.0
 * @date 2017年4月29日 下午3:47:24
 */

@Controller
@Scope("prototype")
public class SysController extends BaseController {

    private static final Logger logger = LoggerFactory
            .getLogger(SysController.class);
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 重加载系统配置数据
     *
     * @throws Exception
     */
    @RequestMapping("/system/config/reload.htm")
    @RequiresPermission(code = "system:config:reload", name = "系统管理-系统参数设置-缓存数据重加载")
    public void reload() throws Exception {

        // 调用缓存辅助类 重加载系统配置数据
        CacheUtil.initSysConfig();
        try {
            String s = File.separator;
            String path = request.getSession().getServletContext().getRealPath("/") + s + "static" + s + "js" + s + "config.js";
            File file = new File(path);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            byte[] buff = new byte[(int) file.length()];
            bis.read(buff);
            FileOutputStream fos = new FileOutputStream(file);
            String[] lines = (new String(buff)).split("\n");

            Map<String, Object> param = new HashMap<>();
            param.put("status", 1);
            List<SysConfig> list = sysConfigService.getList(param);
            for (int j = 0; j < lines.length; j++) {
                if ((lines[j] + "\n").length() != 1) {
                    logger.info("lines[j]=" + (lines[j] + "\n"));
                    fos.write((lines[j] + "\n").getBytes());

                }
                String a = lines[j].toLowerCase();
                for (SysConfig sysConfig : list) {
                    if (a.contains(sysConfig.getCode())) {
                        String jieguo = lines[j + 1].substring(lines[j + 1].indexOf('"') + 1, lines[j + 1].indexOf(";"));
                        if (!jieguo.equals(sysConfig.getValue())) {
                            fos.write((lines[j + 1].replace(lines[j + 1], "  return " + '"' + sysConfig.getValue() + '"' + ";") + "\n").getBytes());
                            lines[j + 1] = "\n";
                        }
                    }
                }
            }
            fos.flush();
            fos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> returnMap = new HashMap<>(8);
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, returnMap);
    }
}
