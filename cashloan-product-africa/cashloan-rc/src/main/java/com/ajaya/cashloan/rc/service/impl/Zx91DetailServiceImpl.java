package com.ajaya.cashloan.rc.service.impl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import com.ajaya.cashloan.rc.domain.Zx91ReqLog;
import com.ajaya.cashloan.rc.mapper.Zx91ReqLogMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import tool.util.BigDecimalUtil;
import tool.util.OrderNoUtil;
import tool.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rc.mapper.Simplezx91CountMapper;
import com.ajaya.cashloan.rc.mapper.Zx91DetailMapper;
import com.ajaya.cashloan.rc.model.zx91.JsonSerializer;
import com.ajaya.cashloan.rc.model.zx91.LoanInfo;
import com.ajaya.cashloan.rc.model.zx91.Pkg1003;
import com.ajaya.cashloan.rc.model.zx91.Pkg2003;
import com.ajaya.cashloan.rc.model.zx91.PkgHeader;
import com.ajaya.cashloan.rc.domain.Simplezx91Count;
import com.ajaya.cashloan.rc.domain.TppBusiness;
import com.ajaya.cashloan.rc.domain.Zx91Detail;
import com.ajaya.cashloan.rc.service.Zx91DetailService;


/**
 * 风控数据统计-（简）通话记录统计ServiceImpl
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:52:59
 */
 
@Service("zx91DetailService")
public class Zx91DetailServiceImpl extends BaseServiceImpl<Zx91Detail, Long> implements Zx91DetailService {
	
   
    @Resource
    private Zx91DetailMapper zx91DetailMapper;
    @Resource
    private Zx91ReqLogMapper zx91ReqLogMapper;
    @Resource
    private Simplezx91CountMapper simplezx91CountMapper;
    
    static Map<String,String> amtMap = new HashMap<String, String>();
    
    static{
		amtMap.put("-7", "0,0.1");
		amtMap.put("-6", "0.1,0.2");
		amtMap.put("-5", "0.2,0.3");
		amtMap.put("-4", "0.3,0.4");
		amtMap.put("-3", "0.4,0.6");
		amtMap.put("-2", "0.6,0.8");
		amtMap.put("-1", "0.8,1");
		amtMap.put("1", "1,2");
    }

	@Override
	public BaseMapper<Zx91Detail, Long> getMapper() {
		return zx91DetailMapper;
	}

	@Override
	public int query91zx1003(String idNo,String realName,Long userId, TppBusiness business) {
//		idNo = "32020219790403401X";
//		realName = "吴慕恩";
		Pkg2003 pkg2003 = new Pkg2003();
//		String companyCode = "P21T2CF1103364550";
		String serCode = "1003";
//		String sign = "5594553F9C34037CE053580213ACB03F";
//		String url = "http://210.72.229.172:8181/jyzx/zxservice.do";
		
		JSONObject apiParamJson = JSONObject.parseObject(business.getExtend());
		final String companyCode = apiParamJson.getString("companyCode");
		final String sign = apiParamJson.getString("sign");
		final String url = business.getUrl();
		
		Zx91ReqLog reqLog = new Zx91ReqLog();
		reqLog.setReqOrder(OrderNoUtil.getSerialNumber());
		reqLog.setUserId(userId);
		reqLog.setCreateTime(new Date());
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("idNo", idNo);
		params.put("realName", realName);
		reqLog.setReqParams(JSONObject.toJSONString(params));
		zx91ReqLogMapper.save(reqLog);
		
		PkgHeader rspPkg = query1003(idNo, companyCode, realName, serCode, sign, url);
		if(StringUtil.isNotBlank(rspPkg.getRetCode())){
			reqLog.setRespCode(rspPkg.getRetCode());
		}
		
		if (rspPkg.getRetCode().equals("0000")) {
			System.out.println("响应消息:" + rspPkg.getRetMsg());
			pkg2003 = (Pkg2003) JsonSerializer.deserializer(rspPkg.getMsgBody(), new TypeReference<Pkg2003>() {});
			System.out.println("查询交易代码:" + pkg2003.getTrxNo());
			reqLog.setRespOrderNo(pkg2003.getTrxNo());
			reqLog.setRespParams(JSONObject.toJSONString(pkg2003));
		} else {
			System.out.println("响应消息:" + rspPkg.getRetMsg());
			reqLog.setRespParams(rspPkg.getRetMsg());
		}
		reqLog.setRespTime(new Date());
		zx91ReqLogMapper.update(reqLog);
		
		int rtValue = 0;
		double amtMin = 0.00;
		double amtMax = 0.00;
		int borrowCount = 0;
		Double overdueAmt = 0.00;
		if(pkg2003.getLoanInfos()!=null && pkg2003.getLoanInfos().size()>0){
			List<LoanInfo> infoList =  pkg2003.getLoanInfos();
			for(int i=0;i<infoList.size();i++){
				LoanInfo info = infoList.get(i);
				Zx91Detail detail = new Zx91Detail();
				detail.setUserId(userId);
				detail.setTrxNo(pkg2003.getTrxNo());
				if(info.getArrearsAmount()>0){
					double overdue = BigDecimalUtil.decimal(info.getArrearsAmount()/100000, 2);
					overdueAmt += overdue;
					detail.setArrearsAmount(overdue);
				}else{
					detail.setArrearsAmount(0.00);
				}
				
				
				int borrowAmt = Integer.parseInt(info.getBorrowAmount());
				if(borrowAmt<2){
					String amtStr = amtMap.get(info.getBorrowAmount());
					String[] amts = amtStr.split(",");
					if(amts.length==2){
						amtMin += BigDecimalUtil.round(amts[0], 2);
						amtMax += BigDecimalUtil.round(amts[1], 2);
						detail.setBorrowAmtMin(BigDecimalUtil.round(amts[0], 2));
						detail.setBorrowAmtMax(BigDecimalUtil.round(amts[1], 2));
					}
				}else{
					amtMin += BigDecimalUtil.round(borrowAmt*2 - 2, 2); ;
					amtMax += borrowAmt*2;
				}
				
				if(info.getBorrowState().intValue()==2){
					borrowCount++;
				}
				detail.setBorrowState(info.getBorrowState());
				detail.setBorrowType(info.getBorrowType());
				detail.setCompanyCode(info.getCompanyCode());
				detail.setContractDate(info.getContractDate());
				detail.setLoanPeriod(info.getLoanPeriod());
				detail.setRepayState(info.getRepayState());
				rtValue += zx91DetailMapper.save(detail);
			}
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			Simplezx91Count count = simplezx91CountMapper.findSelective(paramMap);
			if(count==null){
				count = new Simplezx91Count();
				count.setUserId(userId);
				count.setCreateTime(new Date());
			}
			
			count.setAmtMax(amtMax);
			count.setAmtMin(amtMin);
			count.setBorrowCount(borrowCount);
			count.setOverdueAmt(overdueAmt);
			
			if(count.getId()!=null && count.getId()>0){
				simplezx91CountMapper.update(count);
			}else{
				simplezx91CountMapper.save(count);
			}
			
		}
		return rtValue;
	}
	

	public PkgHeader query1003(String idNo,String companyCode,String realName,String serCode,String sign,String url) {
		Pkg1003 pkg1001 = new Pkg1003();
		pkg1001.setIdCard(idNo);
		pkg1001.setRealName(realName);

		String msgBody = JsonSerializer.serializer(pkg1001);

		PkgHeader reqPkg = new PkgHeader();
		PkgHeader rspPkg = new PkgHeader();
		reqPkg.setVersion("01"); // 默认01
		reqPkg.setCustNo(companyCode); // 公司代码
		reqPkg.setEncode("01"); // 01.UTF8 02.GBK
		reqPkg.setTrxCode(serCode); // 报文编号 默认四位 例:0001
		reqPkg.setEncryptType("01"); // 加密类型 01.不加密 02.RSA
		reqPkg.setMsgType("01"); // 01.JSON 02.XML 03.Protobuf
		reqPkg.setMsgBody(msgBody); // 报文主体
		reqPkg.setSign(sign); // 签名

		CloseableHttpClient httpclient = createSSLClientDefault();
		HttpPost post = new HttpPost(url);
		ByteArrayEntity reqEntity = new ByteArrayEntity(reqPkg.toPkgBytes("UTF-8"));
		post.setEntity(reqEntity);

		CloseableHttpResponse response;
		String result = "";
		try {
			response = httpclient.execute(post);
			HttpEntity rspEntity = response.getEntity();

			if (rspEntity != null) {
				result = EntityUtils.toString(rspEntity);
			}
			rspPkg.parseFromString(result); // 解析报文头
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rspPkg;
	}

	private static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	
}