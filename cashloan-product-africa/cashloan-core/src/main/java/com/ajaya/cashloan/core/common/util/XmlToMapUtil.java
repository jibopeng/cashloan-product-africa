package com.ajaya.cashloan.core.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlToMapUtil {

	private final static Logger logger = LoggerFactory.getLogger(XmlToMapUtil.class);
	
	public static List<Map<String,String>> getListByXml(String xml,String targetElement){
		
        List<Map<String, String>> resultList = null;
		try {
			resultList = new ArrayList<Map<String, String>>();
			Document document = null;
			document = DocumentHelper.parseText(xml);
			//根据标签获取标签下的所有子标签
			List<Element> condition = document.getRootElement().elements(targetElement);
			for(Element element : condition){
			    Map<String,String> resultMap = new HashMap<String,String>();
			    List<Attribute> attributes = element.attributes();
			    if(attributes!=null&&attributes.size()>0){
			        for(Attribute attribute : attributes){
			            resultMap.put(attribute.getName(), attribute.getText().replace(" ", ""));
			        }
			    }
			    List<Element> elements = element.elements();
			    if(elements!=null&&elements.size()>0){
			        for(Element e : elements){
			            resultMap.put(e.getName(), e.getText().replace(" ", ""));
			        }
			    }
			    resultList.add(resultMap);
			}
		} catch (DocumentException e) {
			logger.error("Xml解析List,报错," , e);
		}
        return resultList;
    }
}
