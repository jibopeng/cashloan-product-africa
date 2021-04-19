package com.ajaya.cashloan.core.common.util;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReqToJsonUtil {

	public static String parseRequestToJson(ServletInputStream inputStream){
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}
			in.close();
			inputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sb.toString();
	}
}
	
