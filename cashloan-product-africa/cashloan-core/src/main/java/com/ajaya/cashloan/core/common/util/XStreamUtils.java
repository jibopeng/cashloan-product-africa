package com.ajaya.cashloan.core.common.util;

import com.thoughtworks.xstream.XStream;

/**
 * 功能说明：xStream工具类
 *
 * @author yanzhiqiang
 * @since 2020-04-08 18:03
 **/
public class XStreamUtils {
    /**
     * java 转换成xml
     *
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj) {
        XStream xstream = new XStream();
        //如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass());
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xstream.toXML(obj);
    }

    /**
     * 将传入xml文本转换成Java对象
     *
     * @param xmlStr xml文本
     * @param cls    xml对应的class类
     * @return T   xml对应的class类的实例对象
     */
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = XStreamFactory.getInstance();
        //忽略多余节点
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xmlStr);
    }
}
