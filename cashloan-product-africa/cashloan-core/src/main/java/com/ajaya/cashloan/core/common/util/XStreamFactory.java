package com.ajaya.cashloan.core.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 功能说明： xStream 工厂
 *
 * @author yanzhiqiang
 * @since 2020-04-08 18:01
 **/
public class XStreamFactory {

    private static class LazyHolder {
        private static final XStream xstream = new XStream(new DomDriver());
    }

    public static XStream getInstance() {
        return XStreamFactory.LazyHolder.xstream;
    }
}

