package com.ajaya.cashloan.manage.model;

import com.ajaya.cashloan.cl.model.ChannelModel;
import com.ajaya.cashloan.manage.domain.QuartzInfo;

/**
 *  定时任务详情Model
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017年3月27日 上午10:05:33
 *
 */
public class QuartzInfoModel extends QuartzInfo{
	
	private static final long serialVersionUID = 1L;
	
	/** 状态 - 启用 */
	public static final String STATE_ENABLE = "10";

	/** 状态 - 禁用 */
	public static final String STATE_DISABLE = "20";
	/**
     * 状态中文转换
     * 
     * @param state
     * @return
     */
    public static String stateConvert(String state) {
        String stateStr;
        if (ChannelModel.STATE_DISABLE.equals(state)) {
            stateStr = "禁用";
        } else {
            stateStr = "启用";
        }
        return stateStr;
    }
    
	
	/**
	 * 任务状态描述
	 */
	private String stateStr;
	
	/**
	 * 上次执行时间
	 */
	private String lastStartTime;

	/**
	 * 获取任务状态描述
	 * @return stateStr
	 */
	public String getStateStr() {
		this.stateStr = stateConvert(this.getState());
		return stateStr;
	}

	/**
	 * 设置任务状态描述
	 * @param stateStr
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	/**
	 * 获取上次执行时间
	 * 
	 * @return lastStartTime
	 */
	public String getLastStartTime() {
		return lastStartTime;
	}

	/**
	 * 设置上次执行时间
	 * 
	 * @param lastStartTime
	 */
	public void setLastStartTime(String lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	
	
	
	
	
	
}
