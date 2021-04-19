package com.ajaya.cashloan.rc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.domain.SceneBusinessLog;
import com.ajaya.cashloan.rc.mapper.SceneBusinessLogMapper;
import com.ajaya.cashloan.rc.mapper.SceneBusinessMapper;
import com.ajaya.cashloan.rc.service.SceneBusinessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tool.util.StringUtil;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.rc.model.TppServiceInfoModel;

@Service("sceneBusinessLogService")
@Transactional(rollbackFor=Exception.class)
public class SceneBusinessLogServiceImpl extends BaseServiceImpl<SceneBusinessLog, Long> implements SceneBusinessLogService {
	
	public static final Logger logger = LoggerFactory.getLogger(SceneBusinessLogServiceImpl.class);

	@Resource
	private SceneBusinessLogMapper sceneBusinessLogMapper;
	@Resource
	private SceneBusinessMapper sceneBusinessMapper;
	
	@Override
	public BaseMapper<SceneBusinessLog, Long> getMapper() {
		return sceneBusinessLogMapper;
	}

	/**
	 * 按照get_way判断接口是否需要执行
	 * @param borrow
	 * @param info
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean needExcute(Long userId,Long busId,String getWay,String period) {
		try {
			if(TppServiceInfoModel.GET_WAY_EVERYTIMES.equals(getWay)){
				return true;
			}else if(TppServiceInfoModel.GET_WAY_CYCLE.equals(getWay)){
				if(StringUtil.isNotBlank(period)){
					int days = Integer.parseInt(period);
					SceneBusinessLog log = sceneBusinessLogMapper.findLastExcute(userId,busId);
					// 如果找不到记录，那么说明接口没有执行过，进行第一次执行，需要执行返回true
					if (log == null) {
						return true;
					}
					Calendar cl = Calendar.getInstance();
					cl.setTime(log.getCreateTime());
					cl.set(Calendar.DAY_OF_MONTH,cl.get(Calendar.DAY_OF_MONTH)+days);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date nextTime = sdf.parse(sdf.format(cl.getTime()));
					
					Date nowTime = sdf.parse(sdf.format(new Date()));
					if(nextTime.equals(nowTime) || nextTime.before(nowTime)){
						return true;
					}
				}
			} else if (TppServiceInfoModel.GET_WAY_ONCE.equals(getWay)){
				SceneBusinessLog log = sceneBusinessLogMapper.findLastExcute(userId,busId);
				if (log == null) {
					return true;
				}
			}
		} catch (ParseException e) {
			logger.error("数据接口执行错误");
		}
		return false;
	}
	
	@Override
	public boolean haveNeedExcuteService(Long borrowId) {
		int count = sceneBusinessLogMapper.countUnFinishLog(borrowId);
		return count > 0 ? true : false;
	}

	@Override
	public SceneBusinessLog findByBorrowIdAndBusNid(long borrowId, String busNid) {
		Map<String, Object> map = new HashMap<>();
		map.put("borrowId", borrowId);
		map.put("businessId", busNid);
		return sceneBusinessLogMapper.findSelective(map);
	}
	
}
