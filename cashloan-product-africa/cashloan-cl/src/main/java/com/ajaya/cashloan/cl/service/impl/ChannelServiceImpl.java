package com.ajaya.cashloan.cl.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.service.ChannelService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.Channel;
import com.ajaya.cashloan.cl.mapper.ChannelMapper;
import com.ajaya.cashloan.cl.model.ChannelCountModel;
import com.ajaya.cashloan.cl.model.ChannelModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;

@Service("channelService")
public class ChannelServiceImpl extends BaseServiceImpl<Channel, Long> implements ChannelService {
	
    @Resource
    private ChannelMapper channelMapper;
    
    @Override
	public BaseMapper<Channel, Long> getMapper() {
		return channelMapper;
	}

	@Override
	public boolean save(Channel channel) {
		channel.setCreateTime(new Date());
		channel.setState(ChannelModel.STATE_ENABLE);
		int result = channelMapper.save(channel);
		if(result >0 ){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean update(Map<String, Object> paramMap) {
		int result = channelMapper.updateSelective(paramMap);
		if (result > 0) {
			return true;
		}
		return false;
	}

	
	@Override
	public Channel findByCode(String code) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", code);
		return channelMapper.findSelective(paramMap);
	}

	@Override
	public Page<ChannelModel> page(int current, int pageSize,
			Map<String, Object> searchMap) {
		PageHelper.startPage(current, pageSize);
		Page<ChannelModel> page = (Page<ChannelModel>) channelMapper
				.page(searchMap);
		return page;
	}

	@Override
	public Page<ChannelCountModel> channelUserList(int current, int pageSize,
			Map<String, Object> searchMap) {
		PageHelper.startPage(current, pageSize);
		Page<ChannelCountModel> page = (Page<ChannelCountModel>) channelMapper.channelUserList(searchMap);
		return page;
	}

	@Override
	public List<Channel> listChannel() {
		return channelMapper.listChannel();
	}

	@Override
	public List<Channel> listChannelWithoutApp() {
		return channelMapper.listChannelWithoutApp();
	}

	@Override
	public List<Map<String, Object>> channelUserCount(Map<String, Object> paramMap,int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		Page<Map<String, Object>> result = (Page<Map<String, Object>>) channelMapper.countOne(paramMap);
		List<Map<String, Object>> one = result.getResult();
		
		//关联的是渠道id，传进来的搜索条件是渠道名称，这边转换下
		for (Map<String, Object> map : one) {
			if(paramMap.get("name")!=null&&paramMap.get("name").equals(map.get("name"))){
				paramMap.put("channelId", map.get("channelId"));
				break;
			}
		}
		List<Map<String, Object>> two=channelMapper.countTwo(paramMap);
		List<Map<String, Object>> three=channelMapper.countThree(paramMap);
		List<Map<String, Object>> four=channelMapper.countFour(paramMap);
		List<Map<String, Object>> five=channelMapper.countFive(paramMap);
		List<Map<String, Object>> six=channelMapper.countSix(paramMap);
		List<Map<String, Object>> seven=channelMapper.countSeven(paramMap);
		List<Map<String, Object>> eight=channelMapper.countEight(paramMap);
		List<Map<String, Object>> nine=channelMapper.countNine(paramMap);
		for (int i = 0; i < one.size(); i++) {
			//渠道标识，渠道名称
			//注册人数
			count(one.get(i), two, "registerCount", "countTwo");
			//借款人数
			count(one.get(i), three, "borrowMember", "countThree");
			//借款次数
			count(one.get(i), four, "borrowCount", "countFour");
			//借款金额
			count(one.get(i), five, "borrowAmout", "countFive");
			//新增放款笔数
			count(one.get(i), six, "newPayCount", "countSix");
			//复借放款笔数
			count(one.get(i), seven, "repeatPayCount", "countSeven");
			//放款成功金额
			count(one.get(i), eight, "payAccount", "countEight");
			//通过次数
			count(one.get(i), nine, "passCount", "countNine");
		}
		return result;
	}
	
	private void count(Map<String,Object> oneMap,List<Map<String,Object>> listMap,String remark,String keyWord){
		String s;
		for (Map<String, Object> map : listMap) {
			s=map.get("channelId")+"";
			if(s!=null&&!s.equals("")&&!s.equals("null")&&s.equals(oneMap.get("channelId")+"")){
				oneMap.put(remark, map.get(keyWord)==null?"0":map.get(keyWord));
				break;
			}
		}
		if(oneMap.get(remark)==null||oneMap.get(remark).equals("")){
			oneMap.put(remark,"0");
		}
	}
}