package com.ajaya.cashloan.cl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.BankAccount;
import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.domain.BorrowRepayLog;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.model.BorrowRepayModel;
import com.ajaya.cashloan.cl.model.ManageBorrowProgressModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;
import tool.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.model.ClBorrowModel;
import com.ajaya.cashloan.cl.model.ManageBorrowModel;
import com.ajaya.cashloan.cl.service.BorrowProgressService;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.mapper.UserBaseInfoMapper;


/**
 * 借款进度表ServiceImpl
 * 
 * @author yanzhiqiang
 * @since 201-02-28 10:33:38
 */
 
@Service("borrowProgressService")
public class BorrowProgressServiceImpl extends BaseServiceImpl<BorrowProgress, Long> implements BorrowProgressService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BorrowProgressServiceImpl.class);
   
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;

    @Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private BankAccountMapper bankAccountMapper;
	@Override
	public BaseMapper<BorrowProgress, Long> getMapper() {
		return borrowProgressMapper;
	}

	
	@Override
	public Map<String,Object> result(Borrow borrow) {
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("borrowId", borrow.getId());
		BorrowRepayLog log = borrowRepayLogMapper.findSelective(searchMap);
		
		List<BorrowRepayModel> repay = borrowRepayMapper.listSelModel(searchMap);
		Map<String,Object> result = new HashMap<>();
		ClBorrowModel clBorrowModel = new ClBorrowModel();
		BeanUtils.copyProperties(borrow, clBorrowModel);
		clBorrowModel.setCreditTimeStr(DateUtil.dateStr(clBorrowModel.getCreateTime(),"yyyy-M-d"));
		clBorrowModel.setPenalty("20");
		if (!repay.isEmpty()) {
			clBorrowModel.setPenaltyAmount(repay.get(0).getPenaltyAmout());
			if (repay.get(0).getPenaltyAmout()>0) {
				clBorrowModel.setPenalty("10");
				clBorrowModel.setOverdueAmount(String.valueOf(clBorrowModel.getAmount()+clBorrowModel.getPenaltyAmount()));
			}
		}else{
			clBorrowModel.setOverdueAmount(String.valueOf(clBorrowModel.getAmount()));
		}
		searchMap.clear();
		searchMap.put("userId", borrow.getUserId());
		searchMap.put("status", "1");
		searchMap.put("verifiedCode", "1");
		BankAccount card = bankAccountMapper.findSelective(searchMap);
		if(StringUtil.isNotBlank(searchMap)){
			clBorrowModel.setCardNo(card.getAccountNumber());
			clBorrowModel.setBank(card.getBankName());
		}
		
		List<ClBorrowModel> brList = new ArrayList<ClBorrowModel>();
		brList.add(clBorrowModel);
		result.put("borrow", brList);
		for (BorrowRepayModel borrowRepayModel : repay) {
			borrowRepayModel.setRepayTimeStr(DateUtil.dateStr(borrowRepayModel.getRepayTime(),"yyyy-M-d"));
			borrowRepayModel.setAmount(borrowRepayModel.getAmount()+borrowRepayModel.getPenaltyAmout());
		}
		if (StringUtil.isNotBlank(log)) {
			for (BorrowRepayModel repayModel : repay) {
				repayModel.setRealRepayTime(DateUtil.dateStr(log.getRepayTime(),"yyyy-M-d"));
				repayModel.setRealRepayAmount(String.valueOf(log.getAmount()+log.getPenaltyAmout()));
			}
		}
		result.put("repay", repay);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public  Page<ManageBorrowProgressModel> listModel(Map<String, Object> params, int currentPage,
                                                      int pageSize) {
		Map<String, Object> bparams=new HashMap<String, Object>();
		if(StringUtil.isNotBlank(params)){
			if(params.containsKey("realName")){
				bparams.put("realName", params.get("realName"));
			}
			if(params.containsKey("phone")){
				bparams.put("phone", params.get("phone"));
			}
			if(params.containsKey("orderNo")){
				bparams.put("orderNo", params.get("orderNo"));
			}
			List<ManageBorrowModel> borrowList = clBorrowMapper.listModel(bparams);
			
			if(StringUtil.isNotBlank(params)&&StringUtil.isNotBlank(bparams)&&StringUtil.isNotBlank(borrowList)){
				bparams=new HashMap<String, Object>();
				List borrowIds=new ArrayList();
				if(borrowList.size()>0){
					for(ManageBorrowModel b:borrowList){
						borrowIds.add(b.getId());
					}
				}else{
					borrowIds.add("0");
				}
				if(StringUtil.isNotBlank(borrowIds)){
				    params.put("borrowIds", borrowIds);
				}
			}
		}
		
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBorrowProgressModel> list = borrowProgressMapper.listModel(params);
		if (StringUtil.isNotBlank(list)) {
			for (int i = 0; i < list.size(); i++) {
				ManageBorrowProgressModel model = list.get(i);
				Borrow b = clBorrowMapper.findByPrimary(model.getBorrowId());
				if(b != null){
					list.get(i).setAmount(b.getAmount());
					list.get(i).setOrderNo(b.getOrderNo());
					UserBaseInfo info = userBaseInfoMapper.findByUserId(model.getUserId());
					if (StringUtil.isNotBlank(info)) {
						//list.get(i).setPhone(info.getPhone());
						list.get(i).setRealName(info.getRealName());
					}
				}
			}
		}
		
		return (Page<ManageBorrowProgressModel>)list;
	}

	@Override
	public boolean save(BorrowProgress borrowProgress){
		int result = borrowProgressMapper.save(borrowProgress);
		logger.info("修改borrowProgress状态, borrowId  " + borrowProgress.getBorrowId() + "处理, 更新值  " + result);
		if(result > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<BorrowProgress> listSeletetiv(Map<String, Object> map) {
		return borrowProgressMapper.listSelective(map);
	}
	@Override
	public int isNormalBorrowProgress(long borrowId) {
		return borrowProgressMapper.isNormalBorrowProgress(borrowId);
	}
	@Override
	public BorrowProgress selectSanctionDate(Long borrowId) {
		return borrowProgressMapper.selectSanctionDate(borrowId);
	}
}
