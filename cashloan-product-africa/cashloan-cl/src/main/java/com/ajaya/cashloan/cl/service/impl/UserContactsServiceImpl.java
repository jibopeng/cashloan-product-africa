package com.ajaya.cashloan.cl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.service.UserContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.UserContacts;
import com.ajaya.cashloan.cl.domain.UserEmerContacts;
import com.ajaya.cashloan.cl.mapper.UserContactsMapper;
import com.ajaya.cashloan.cl.mapper.UserEmerContactsMapper;
import com.ajaya.cashloan.cl.mapper.UserMessagesMapper;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ListUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;


/**
 * 用户资料--联系人ServiceImpl
 *
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:52:26
 */

@Service("clUserContactsService")
public class UserContactsServiceImpl extends BaseServiceImpl<UserContacts, Long> implements UserContactsService {

    private static final Logger logger = LoggerFactory.getLogger(UserContactsServiceImpl.class);

    @Resource
    private UserContactsMapper userContactsMapper;
    @Resource
	private UserMessagesMapper clUserMessagesMapper;

    @Override
    public BaseMapper<UserContacts, Long> getMapper() {
        return userContactsMapper;
    }


    @Override
    public Page<UserContacts> listContacts(long userId, int current, int pageSize) {
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }

        /**刘晓亮 start 返回通讯录的备注信息*/
        // 通讯录备注表  分表
        String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int remarkCountTable = userContactsMapper.countTable(remarkTableName);
        if (remarkCountTable == 0) {
            userContactsMapper.createContactRemarkTable(remarkTableName);
        }
        /**刘晓亮 end*/
        List<UserContacts> list = null;
        Long borrowId = userContactsMapper.findMaxBorrowidByUserid(tableName, userId);
        if(borrowId == null || borrowId == 0){
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            PageHelper.startPage(current, pageSize);
            list = userContactsMapper.listShardSelective(tableName, params);
           
        } else {
            PageHelper.startPage(current, pageSize);
            list = userContactsMapper.listShardByBorrowid(tableName, borrowId);
        
        }
        return (Page<UserContacts>) list;
    }
    
    
    public List<UserContacts> search(List<UserContacts> logsList){
        Collections.sort(logsList, new Comparator<UserContacts>() {
            @Override
            public int compare(UserContacts o1, UserContacts o2) {
                return Integer.parseInt(o2.getMsgTimes()) - Integer.parseInt(o1.getMsgTimes()) ;
            }
        });
        return logsList;
    }

    
    private static List<UserContacts> removeDuplicatePlan(List<UserContacts> planList) {
        Set<UserContacts> set = new TreeSet<UserContacts>(new Comparator<UserContacts>() {
            @Override
            public int compare(UserContacts a, UserContacts b) {
                // 字符串则按照asicc码升序排列
                return a.getPhone().replace(" ", "").compareTo(b.getPhone().replace(" ", ""));
            }
        });
        
        set.addAll(planList);
        return new ArrayList<UserContacts>(set);
    }

    @Resource
    private UserEmerContactsMapper userEmerContactsMapper;
    @Override
    public boolean deleteAndSave(List<Map<String, Object>> infos, long userId, String type) {
    	logger.info("开始保存用户ID： "+userId+" 的 type为 "+type+" 通讯录 ------");
        int msg = 0;
        String name = null;
        String phone = null;
        boolean flag = false;
        
        Integer typeInt = null;
        if(type != null && !"".equals(type.trim())){
        	typeInt = Integer.parseInt(type);
        }
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }
        // 删除更新操作
        userContactsMapper.deleteShardByUserId(tableName, userId, typeInt);
        
        Date createTime = new Date();
        for (Map<String, Object> map : infos) {
            logger.debug("用户 " + userId + " 保存通讯录，name：" + StringUtil.isNull(map.get("name")) + "，phone：" + StringUtil.isNull(map.get("phone")));
            logger.debug("用户 " + userId + " 保存通讯录，name___：" + name + "，phone___：" + phone);
            if(map.containsKey("name") && StringUtil.isNoneBlank(map.get("name").toString())){
            	name = map.get("name").toString();
            } else {
            	name = "";
            }
            if(map.containsKey("phone") && StringUtil.isNoneBlank(map.get("phone").toString())){
            	phone = map.get("phone").toString();
            } else {
            	phone = "";
            }
            
            try {
                UserContacts userContacts = new UserContacts();
                userContacts.setUserId(userId);
                userContacts.setName(name);
                userContacts.setPhone(phone);
                userContacts.setCreateTime(createTime);
                userContacts.setType(typeInt);
                msg += userContactsMapper.saveShard(tableName, userContacts);
            } catch (Exception e) {
                logger.error("用户 " + userId + " 保存通讯录异常， name： " + name + "， phone：" + phone);
            }
        }
        if (msg > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void addOrUpdateContactRemark(long userId, String contactPhone, String contactName,
                                         String contactRemark) {
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createContactRemarkTable(tableName);
        }
        //首先查询通讯录备注表中是否有
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String selectParamSql = "select count(1) from " + tableName + " where user_id = " + userId + " and contact_phone = '" + contactPhone + "'";
        Integer count = userContactsMapper.selectContactRemarkCountBySql(selectParamSql);
        if (count > 0) {
            //更新操作
            Date date = new Date();
            String updateTime = sdf.format(date);
            String updateParamSql = "update " + tableName + " set contact_remark = '" + contactRemark + "', update_time = '" + updateTime + "' where user_id = " + userId + " and contact_phone = '" + contactPhone + "'";
            userContactsMapper.updateContactRemarkBySql(updateParamSql);
        } else {
            //插入数据
            Date date = new Date();
            String createTime = sdf.format(date);
            String insertParamSql = "insert into " + tableName + " (user_id,contact_name,contact_phone,contact_remark,create_time) values (" + userId + ",'" + contactName + "','" + contactPhone + "','" + contactRemark + "','" + createTime + "')";
            userContactsMapper.saveContactRemarkBySql(insertParamSql);
        }
    }

	@Override
	public void updateContactBorrowidByUserid(long userId, long borrowId) {
		logger.info("更新用户ID： "+userId+", 订单ID： "+borrowId+" 的通讯录数据 ------");
		// 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }
        // 用户没有订单号的通讯录数据
		List<UserContacts> userContactsListNoBorrowid = userContactsMapper.findListNoBorrowidByUserid(tableName, userId);
		if(userContactsListNoBorrowid != null && userContactsListNoBorrowid.size() > 0){
			List<Long> ids = new ArrayList<>();
			Date updateTime = new Date();
			for (int i = 0; i < userContactsListNoBorrowid.size(); i++) {
				ids.add(userContactsListNoBorrowid.get(i).getId());
//				UserContacts userContactsNoBorrowid = userContactsListNoBorrowid.get(i);
//				userContactsNoBorrowid.setBorrowId(borrowId);
//				userContactsNoBorrowid.setUpdateTime(updateTime);
//				userContactsMapper.updateShard(tableName, userContactsNoBorrowid);
			}
			userContactsMapper.updateByIdsShard(tableName, borrowId, updateTime, ids);
		}
	}

	@Override
	public Page<UserContacts> listContactsXinShen(long userId, int current, int pageSize) {
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }

        /**刘晓亮 start 返回通讯录的备注信息*/
        // 通讯录备注表  分表
        String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int remarkCountTable = userContactsMapper.countTable(remarkTableName);
        if (remarkCountTable == 0) {
            userContactsMapper.createContactRemarkTable(remarkTableName);
        }
        /**刘晓亮 end*/

        PageHelper.startPage(current, pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<UserContacts> list = userContactsMapper.listShardSelective(tableName, params);

        /**刘晓亮 start*/
        if (list != null && list.size() > 0) {
            for (UserContacts userContacts : list) {
                String selectParamSql = "select contact_remark from " + remarkTableName + " where user_id = " + userId + " and contact_phone = '" + userContacts.getPhone() + "' limit 1";
                String remark = userContactsMapper.selectContactRemarkBySql(selectParamSql);
                if (StringUtil.isNotBlank(remark)) {
                    userContacts.setRemark(remark);
                }
            }
        }
        /**刘晓亮 end*/
        return (Page<UserContacts>) list;
    }

	@Override
	public void deleteShard(String tableName, long userId, Integer type) {
		userContactsMapper.deleteShardByUserId(tableName, userId, type);
	}

	@Override
	public int saveShard(String tableName, Map<String, Object> map, long userId, Date createTime, Integer type) {
        try {
        	String name = null;
        	String phone = null;
        	if(map == null || !map.containsKey("name") || !map.containsKey("phone") || map.get("name") == null || map.get("phone") == null){
        		return 0;
        	}
        	logger.debug("用户 " + userId + " 保存通讯录，name：" + StringUtil.isNull(map.get("name")) + "，phone：" + StringUtil.isNull(map.get("phone")));
        	logger.debug("用户 " + userId + " 保存通讯录，name___：" + name + "，phone___：" + phone);
        	if(map.containsKey("name") && StringUtil.isNoneBlank(map.get("name").toString())){
        		name = map.get("name").toString();
        	} else {
        		name = "";
        	}
        	if(map.containsKey("phone") && StringUtil.isNoneBlank(map.get("phone").toString())){
        		phone = map.get("phone").toString();
        	} else {
        		phone = "";
        	}
            UserContacts userContacts = new UserContacts();
            userContacts.setUserId(userId);
            userContacts.setName(name);
            userContacts.setPhone(phone);
            userContacts.setCreateTime(createTime);
            userContacts.setType(type);
            userContacts.setBorrowId(map.get("borrowId")==null ? null : Long.parseLong(map.get("borrowId").toString()));
            return userContactsMapper.saveShard(tableName, userContacts);
        } catch (Exception e) {
            logger.error("用户 " + userId + " 保存通讯录异常 ", e);
        }
		return 0;
	}

	@Override
	public String getTableName(long userId) {
		// 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }
		return tableName;
	}

	@Override
	public Page<UserContacts> listContactsLike(long userId, int current, int pageSize, String name, String phone) {
		// 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }

        /**刘晓亮 start 返回通讯录的备注信息*/
        // 通讯录备注表  分表
        String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int remarkCountTable = userContactsMapper.countTable(remarkTableName);
        if (remarkCountTable == 0) {
            userContactsMapper.createContactRemarkTable(remarkTableName);
        }
        /**刘晓亮 end*/

        PageHelper.startPage(current, pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("name", name);
        params.put("phone", phone);
        List<UserContacts> list = userContactsMapper.listShardSelectiveLike(tableName, params);

        /**刘晓亮 start*/
        if (list != null && list.size() > 0) {
            for (UserContacts userContacts : list) {
                String selectParamSql = "select contact_remark from " + remarkTableName + " where user_id = " + userId + " and contact_phone = '" + userContacts.getPhone() + "' limit 1";
                String remark = userContactsMapper.selectContactRemarkBySql(selectParamSql);
                if (StringUtil.isNotBlank(remark)) {
                    userContacts.setRemark(remark);
                }
            }
        }
        /**刘晓亮 end*/
        return (Page<UserContacts>) list;
	}
	
	
	@Override
    public Map<String, Object> listContactsTop(long userId, int current, int pageSize) {
    	List<UserContacts> result = new ArrayList<>();
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }

        /**刘晓亮 start 返回通讯录的备注信息*/
        // 通讯录备注表  分表
        String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int remarkCountTable = userContactsMapper.countTable(remarkTableName);
        if (remarkCountTable == 0) {
            userContactsMapper.createContactRemarkTable(remarkTableName);
        }
        /**刘晓亮 end*/

        
        List<UserContacts> list = null;
        Long borrowId = userContactsMapper.findMaxBorrowidByUserid(tableName, userId);
        if(borrowId == null || borrowId == 0){
        	Map<String, Object> params = new HashMap<>();
        	params.put("userId", userId);
        	//PageHelper.startPage(current, pageSize);
        	list = userContactsMapper.listShardSelective(tableName, params);
        } else {
        	//PageHelper.startPage(current, pageSize);
        	list = userContactsMapper.listShardByBorrowid(tableName, borrowId);
        }
        
        list = removeDuplicatePlan(list);
        
        List<UserContacts> resultContactRemarkList = new ArrayList<>();
        
        
        //----查询有备注功能的
        if(list != null && list.size() > 0){
			for (UserContacts userContacts : list) {
				String selectParamSql = "select contact_remark from " + remarkTableName + " where user_id = " + userId + " and contact_phone = '" + userContacts.getPhone() + "' limit 1";
				String remark = userContactsMapper.selectContactRemarkBySql(selectParamSql);
				if(StringUtil.isNotBlank(remark)){
					userContacts.setRemark(remark);
					resultContactRemarkList.add(userContacts);
				}
			}
		}
        
        
        //----查询短信联系人top前20
        Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		String tableNameMsg = ShardTableUtil.generateTableNameById("cl_user_messages", userId, 30000);
		int countTableMsg = clUserMessagesMapper.countTable(tableNameMsg);
		if (countTableMsg == 0) {
			clUserMessagesMapper.createTable(tableNameMsg);
		}
		
		//List<Map<String, Object>> msgTopMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>>  msgTopMap = clUserMessagesMapper.listShardByBorrowidTop20(tableNameMsg, borrowId);
        if(msgTopMap == null || msgTopMap.size() <= 0){
        	Date maxCreatetime = clUserMessagesMapper.findMaxCreatetimeByUserid(tableNameMsg, userId);
        	if(maxCreatetime == null){
        		msgTopMap = clUserMessagesMapper.listShardSelectiveTop20(tableNameMsg, paramMap);
            } else {
            	msgTopMap = clUserMessagesMapper.findListByUseridAndCreatetimeTop20(tableName, userId, maxCreatetime);
            }
        } else {
        	
        }
        Map<String, Object> msgTopMapRes = new HashMap<>();
        if(msgTopMap != null){
        	for (Map<String, Object> map : msgTopMap) {
        		msgTopMapRes.put(map.get("name").toString(), map.get("num"));
    		}
        }
        
        //---查询命中紧急联系人和工作联系人
        //查询紧急联系人
        List<UserContacts> resultEmerList = new ArrayList<>();
        List<UserEmerContacts> emerContacts = userEmerContactsMapper.listSelective(paramMap);
        List<String> emerNameList = new ArrayList<>();
        if(emerContacts != null && emerContacts.size() > 0){
        	for (UserEmerContacts userEmerContacts : emerContacts) {
        		emerNameList.add(userEmerContacts.getName());
        		UserContacts userContacts = new UserContacts();
        		userContacts.setUserId(userId);
        		userContacts.setName(userEmerContacts.getName());
        		userContacts.setPhone(userEmerContacts.getPhone());
        		userContacts.setMsgTimes(userEmerContacts.getRelation());
        		resultEmerList.add(userContacts);
			}
        }
        List<UserContacts> resultMsgTopList = new ArrayList<>();
        
        List<UserContacts> resultWorkList = new ArrayList<>();
        List<String> workModelList = ListUtil.parseConfigFileToList("workContactModel");
        List<UserContacts> resultContactSYList = new ArrayList<>();
        if(list != null && list.size() > 0){
			for (UserContacts userContacts : list) {
				if(msgTopMapRes.containsKey(userContacts.getName())){
					userContacts.setMsgTimes(msgTopMapRes.get(userContacts.getName()).toString());
					resultMsgTopList.add(userContacts);
				} else if(workModelList.contains(userContacts.getName())){
					userContacts.setMsgTimes("reference for work");
					resultWorkList.add(userContacts);
				} else if(StringUtil.isBlank(userContacts.getRemark())){
					resultContactSYList.add(userContacts);
				}
			}
		}
        //往后排的最先放
        result.addAll(resultEmerList);
        result.addAll(resultContactRemarkList);
        resultMsgTopList = search(resultMsgTopList);
        if(resultMsgTopList != null && resultMsgTopList.size() > 20){
        	result.addAll(resultMsgTopList.subList(0, 20));
        } else {
        	result.addAll(resultMsgTopList);
        }
        result.addAll(resultWorkList);
        result.addAll(resultContactSYList);
        Map<String, Object> resultMap = new HashMap<>();
        if(result != null && result.size() > 0 && "1".equals(Global.getValue("urge_contact_switch"))){
			int subSize = pageSize;
			logger.info("****subSize" +subSize);
			int subCount = result.size();
			logger.info("****subCount" +subCount);
			int subPageTotal = (subCount / subSize) + ((subCount % subSize > 0) ? 1 : 0);
			logger.info("****subPageTotal" +subPageTotal);
			int len = subPageTotal - 1;
			logger.info("****len" +len);
			int i = current - 1;
			logger.info("****i" +i);
			int fromIndex = i * subSize;
			logger.info("****fromIndex" +fromIndex);
			int toIndex = ((i == len) ? subCount : ((i + 1) * subSize));
			logger.info("toIndex--" + toIndex);
			if(toIndex > result.size()){
				toIndex = result.size();
			}
			logger.info("***toIndex--" + toIndex);
			logger.info("***fromIndex--" + fromIndex);
			
			List<UserContacts> subUserContacts = result.subList(fromIndex, toIndex);
			RdPage rdPage = new RdPage();
			rdPage.setCurrent(current);
			rdPage.setPages(subPageTotal);
			rdPage.setPageSize(pageSize);
			rdPage.setTotal(subCount);

			resultMap.put("list", subUserContacts);
			resultMap.put("rdPage", rdPage);
		} else {
        	resultMap.put("list", new ArrayList<UserContacts>());
        	resultMap.put("rdPage", new RdPage());
        	
        }
        return resultMap;
    }

	@Override
	public Long selectDataMaxBorrowIdBefore(Long borrowId, Long userId) {
		String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
        	userContactsMapper.createTable(tableName);
        }
        logger.info("borrowId:" + borrowId + ",userId:" + userId + ",tableName:" + tableName);
		return userContactsMapper.selectDataMaxBorrowIdBefore(userId,borrowId,tableName);
	}
	
	@Override
    public List<UserContacts> listContacts(long userId) {
        // 分表
        String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", userId, 30000);
        int countTable = userContactsMapper.countTable(tableName);
        if (countTable == 0) {
            userContactsMapper.createTable(tableName);
        }

        /**刘晓亮 start 返回通讯录的备注信息*/
        // 通讯录备注表  分表
        String remarkTableName = ShardTableUtil.generateTableNameById("cl_user_contacts_remark", userId, 30000);
        int remarkCountTable = userContactsMapper.countTable(remarkTableName);
        if (remarkCountTable == 0) {
            userContactsMapper.createContactRemarkTable(remarkTableName);
        }
        /**刘晓亮 end*/
        List<UserContacts> list = null;
        Long borrowId = userContactsMapper.findMaxBorrowidByUserid(tableName, userId);
        if(borrowId == null || borrowId == 0){
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            list = userContactsMapper.listShardSelective(tableName, params);
           
        } else {
            list = userContactsMapper.listShardByBorrowid(tableName, borrowId);
        
        }
        return list;
    }
}