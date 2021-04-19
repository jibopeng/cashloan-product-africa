package com.ajaya.cashloan.core.common.util;

import com.ajaya.cashloan.core.common.context.Global;

import java.util.*;

/**
 * 功能说明：订单分流器
 *
 * @author yanzhiqiang
 * @since 2020-10-29 15:04
 **/
public class DiversionUtils {


    /**
     * 待分配的分流组，key为组名，value为分组的权重
     */
    private static HashMap<String, Integer> diversionWeightMap = new HashMap<>();

    //七个姓名分组
    private static HashMap<String, Integer> diversionWeightSevenNameMap = new HashMap<>();

    private static Integer pos = 0;
    //七个姓名分组
    private static Integer posSevenName = 0;

    public static void getInstanceMap() {
        diversionWeightMap.clear();
        String serverHost = Global.getValue("diversion_grouping");
        String[] groups = serverHost.split(",");
        for (int i = 0; i < groups.length; i++) {
            String[] group = groups[i].split("-");
            diversionWeightMap.put(group[0], Integer.parseInt(group[1]));
        }
    }
    
    public static void getInstanceSevenNameMap() {
        diversionWeightSevenNameMap.clear();
        String serverHost = Global.getValue("diversion_grouping_seven_name");
        String[] groups = serverHost.split(",");
        for (int i = 0; i < groups.length; i++) {
            String[] group = groups[i].split("-");
            diversionWeightSevenNameMap.put(group[0], Integer.parseInt(group[1]));
        }
    }

    public static String getDiversion() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> diversionMap = new HashMap<>(8);
        diversionMap.putAll(DiversionUtils.diversionWeightMap);

        // 取得分组List
        Set<String> keySet = diversionMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> diversionList = new ArrayList<>();
        while (iterator.hasNext()) {
            String diversion = iterator.next();
            int weight = diversionMap.get(diversion);
            for (int i = 0; i < weight; i++) {
                diversionList.add(diversion);
            }
        }
        String diversion = null;
        synchronized (DiversionUtils.class) {
            if (pos >= diversionList.size()) {
                pos = 0;
            }
            diversion = diversionList.get(pos);
            pos++;
        }
        return diversion;
    }
    
    
    public static String getSevenNameDiversion() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> diversionMap = new HashMap<>(8);
        diversionMap.putAll(DiversionUtils.diversionWeightSevenNameMap);

        // 取得分组List
        Set<String> keySet = diversionMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> diversionList = new ArrayList<>();
        while (iterator.hasNext()) {
            String diversion = iterator.next();
            int weight = diversionMap.get(diversion);
            for (int i = 0; i < weight; i++) {
                diversionList.add(diversion);
            }
        }
        String diversion = null;
        synchronized (DiversionUtils.class) {
            if (posSevenName >= diversionList.size()) {
            	posSevenName = 0;
            }
            diversion = diversionList.get(posSevenName);
            posSevenName++;
        }
        return diversion;
    }

}
