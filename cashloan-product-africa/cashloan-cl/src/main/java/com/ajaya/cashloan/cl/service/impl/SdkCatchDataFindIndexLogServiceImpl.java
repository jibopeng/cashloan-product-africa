package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.SdkCatchDataCallBackLog;
import com.ajaya.cashloan.cl.domain.SdkCatchDataFindIndexLog;
import com.ajaya.cashloan.cl.mapper.SdkCatchDataCallBackLogMapper;
import com.ajaya.cashloan.cl.mapper.SdkCatchDataFindIndexLogMapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.SdkCatchDataFindIndexLogService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.DateUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * sdk抓取数据findindex控制记录日志表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-12-24 10:52:14
 */

@Service("sdkCatchDataFindIndexLogService")
public class SdkCatchDataFindIndexLogServiceImpl extends BaseServiceImpl<SdkCatchDataFindIndexLog, Long> implements SdkCatchDataFindIndexLogService {

    private static final Logger logger = LoggerFactory.getLogger(SdkCatchDataFindIndexLogServiceImpl.class);

    @Resource
    private SdkCatchDataFindIndexLogMapper sdkCatchDataFindIndexLogMapper;
    @Resource
    private SdkCatchDataCallBackLogMapper sdkCatchDataCallBackLogMapper;
    @Resource
    private ClBorrowService clBorrowService;

    @Override
    public BaseMapper<SdkCatchDataFindIndexLog, Long> getMapper() {
        return sdkCatchDataFindIndexLogMapper;
    }

    @Override
    public boolean needCatchData(Borrow borrow) {
        //默认失效时间，超过默认时间，则重新抓取。
        int time = 10;
        //判断订单状态，如果不是10状态则无需进行数据抓取；
        if (!BorrowModel.STATE_PRE.equals(borrow.getState())) {
            return false;
        }
        //判断复贷是否需要进行首页抓取
        boolean isRefinance = clBorrowService.isRefinanceBeforeCreateBorroworder(borrow.getUserId());
        String catchFlag = "true";
        String oldUserCatch = Global.getValue("sdk_model_score_old_user_catch");
        if (isRefinance && !catchFlag.equals(oldUserCatch)) {
            return false;
        }
        //判断epoch SDK是否成功翻抓取过数据,如果成功则不再进行抓取
        HashMap<String, Object> params = new HashMap<>(8);
        params.put("borrowId", borrow.getId());
        params.put("userId", borrow.getUserId());
        params.put("catchDataState", "1");
        List<SdkCatchDataCallBackLog> catchDataSyncLogs = sdkCatchDataCallBackLogMapper.listSelective(params);
        if (catchDataSyncLogs.size() > 0) {
            return false;
        }
        //判断订单创建时间是否在当前时间十分钟之内，如果是则不进行重新抓取
        Date createTime = borrow.getCreateTime();
        Date nowTime = new Date();
        int minuteBetween = DateUtil.minuteBetween(createTime, nowTime);
        if (minuteBetween < time) {
            return false;
        }
        //判断抓取次数是否超过三次，如果超过三次不再进行抓取
        int countFlag = 3;
        int count = sdkCatchDataFindIndexLogMapper.countByBorrowId(borrow.getId());
        if (count >= countFlag) {
            return false;
        }
        //判断上一次发起抓取数据时间是否超过默认时间
        SdkCatchDataFindIndexLog lastLog = sdkCatchDataFindIndexLogMapper.findLastLog(borrow.getId());
        if (lastLog != null) {
            Date lastLogCreateTime = lastLog.getCreateTime();
            int minuteBetween1 = DateUtil.minuteBetween(lastLogCreateTime, nowTime);
            if (minuteBetween1 < time) {
                return false;
            }
        }
        //保存这次抓取记录
        SdkCatchDataFindIndexLog sdkCatchDataFindIndexLog = new SdkCatchDataFindIndexLog();
        sdkCatchDataFindIndexLog.setCreateTime(new Date());
        sdkCatchDataFindIndexLog.setBorrowId(borrow.getId());
        sdkCatchDataFindIndexLog.setUserId(borrow.getUserId());
        sdkCatchDataFindIndexLog.setCreateTime(nowTime);
        int save = sdkCatchDataFindIndexLogMapper.save(sdkCatchDataFindIndexLog);
        return save > 0;
    }
}