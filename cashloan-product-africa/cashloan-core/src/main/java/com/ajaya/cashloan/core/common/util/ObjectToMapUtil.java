package com.ajaya.cashloan.core.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 功能说明：object转map
 *
 * @author yanzhiqiang
 * @since 2020-03-13 17:07
 **/
public class ObjectToMapUtil {

    /**
     * obj转map
     *
     * @param obj 实体对象
     * @return 返回map
     */
    public static HashMap convertObjToMap(Object obj) {
        HashMap<String, Object> reMap = new HashMap<>(8);
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                try {
                    Field f = obj.getClass().getDeclaredField(field.getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(field.getName(), o);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }
}
