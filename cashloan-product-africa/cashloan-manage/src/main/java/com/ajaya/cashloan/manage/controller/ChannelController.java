package com.ajaya.cashloan.manage.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.Channel;
import com.ajaya.cashloan.cl.model.ChannelCountModel;
import com.ajaya.cashloan.cl.model.ChannelModel;
import com.ajaya.cashloan.cl.service.ChannelService;
import com.ajaya.cashloan.cl.service.UrgeOrderPushLogService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import com.ajaya.cashloan.cl.service.UserFirstCentCreditReportService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.ListUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.github.pagehelper.Page;

/**
* 渠道信息Controller
*
* @author gc
* @version 1.0.0
* @date 2017-03-03 10:52:07
*/
@Scope("prototype")
@Controller
public class ChannelController extends ManageBaseController {
	
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

   @Resource
   private ChannelService channelService;

   /**
    * 保存
    * @param code
    * @param name
    * @param linker
    * @param phone
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/save.htm", method = RequestMethod.POST)
   public void save(@RequestParam(value="code") String code,
           @RequestParam(value="name") String name,
           @RequestParam(value="linker") String linker,
           @RequestParam(value="phone") String phone) throws Exception {
       Channel channel=new Channel();
       channel.setCode(code);
       channel.setLinker(linker);
       channel.setName(name);
       channel.setPhone(phone);
       boolean flag = channelService.save(channel);

       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
       }
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 查询所有渠道信息
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/listChannel.htm")
   public void listChannel() throws Exception {
       List<Channel> list = channelService.listChannel();

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, list);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   @RequestMapping(value = "/modules/manage/promotion/channel/getIndiaJson.htm", method = RequestMethod.POST)
   public void getIndiaJson() throws Exception {

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       String pathname = ListUtil.class.getResource("/config/nga.json").getFile();
       BufferedReader in = new BufferedReader(new FileReader(pathname));
       String key = "";
       StringBuffer data = new StringBuffer();
       while (StringUtil.isNoneBlank(key = in.readLine())) {
           // System.out.println(key);
           data.append(key);
       }
       Map<String, Object> map = new HashMap();
       map.put("india", data.toString());
       result.put("data", data.toString());
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 渠道信息列表页查看
    *
    * @param searchParams 搜索条件
    * @param current 当前页码
    * @param pageSize 页码大小
    * @throws Exception 异常
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/promotion/channel/page.htm", method = {RequestMethod.POST,RequestMethod.GET})
   public void page(
           @RequestParam(value="searchParams",required=false) String searchParams,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String, Object> searchMap = new HashMap<>();
       if (!StringUtils.isEmpty(searchParams)) {
           searchMap = JsonUtil.parse(searchParams, Map.class);
       }

       Page<ChannelModel> page = channelService.page(current, pageSize,searchMap);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 修改
    * @param id
    * @param code
    * @param name
    * @param linker
    * @param phone
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/update.htm", method = RequestMethod.POST)
   public void update(
           @RequestParam(value="id") Long id,
           @RequestParam(value="code") String code,
           @RequestParam(value="name") String name,
           @RequestParam(value="linker") String linker,
           @RequestParam(value="phone") String phone) throws Exception {
       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("id", id);
       paramMap.put("code", code);
       paramMap.put("name", name);
       paramMap.put("linker", linker);
       paramMap.put("phone", phone);
       boolean flag = channelService.update(paramMap);
       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
       }
       ServletUtils.writeToResponse(response, result);
   }


   /**
    * 渠道信息修改状态
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/updateState.htm", method = RequestMethod.POST)
   public void updateState(@RequestParam(value="id") Long id,
                   @RequestParam(value="state") String state) throws Exception {
       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("id", id);
       paramMap.put("state", state);
       boolean flag = channelService.update(paramMap);
       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
       }
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 统计渠道用户信息
    *
    * @param searchParams
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/promotion/channel/channelUserList.htm", method = {RequestMethod.POST,RequestMethod.GET})
   public void channelUserList(
           @RequestParam(value="searchParams",required=false) String searchParams,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String, Object> searchMap = new HashMap<>();
       if (!StringUtils.isEmpty(searchParams)) {
           searchMap = JsonUtil.parse(searchParams, Map.class);
       }
       Page<ChannelCountModel> page = channelService.channelUserList(current, pageSize,searchMap);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 统计渠道用户信息
    * chenxy
    * 2017年7月19日 20:49:47
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/promotion/channel/channelUserCount.htm", method = {RequestMethod.POST,RequestMethod.GET})
   public void channelUserCount(
           @RequestParam(value="searchParams",required=false) String searchParams,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String, Object> searchMap = new HashMap<>();
       if (!StringUtils.isEmpty(searchParams)) {
           searchMap = JsonUtil.parse(searchParams, Map.class);
       }
       Page<Map<String,Object>> page = (Page<Map<String, Object>>) channelService.channelUserCount(searchMap,current,pageSize);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }
    @Resource
    UrgeOrderPushLogService UrgeOrderPushLogService;
    @Resource
    UrgeRepayOrderService urgeRepayOrderService;


    /**
     * 根据配置文件推送逾期数据
     * @throws Exception
     */
    @RequestMapping("/urge/pushOrder.htm")
    public void pushOrder() throws Exception {
        String pathname = ListUtil.class.getResource("/config/file/borrowIds.txt").getFile();
        File filename = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        while ((line = br.readLine()) != null) {
            long borrowId = Long.parseLong(line);
            urgeRepayOrderService.saveOrder(borrowId);
            UrgeOrderPushLogService.pushUrgeOrder(borrowId);
        }
    }

    /**
     * 根据配置文件推送还款数据
     * @throws Exception
     */
    @RequestMapping("/urge/pushRepayment.htm")
    public void pushRepayment() throws Exception {
        System.out.println("*******************");
        String pathname = ListUtil.class.getResource("/config/file/repayBorrowIds.txt").getFile();
        File filename = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        while ((line = br.readLine()) != null) {
            long borrowId = Long.parseLong(line);
            UrgeOrderPushLogService.pushRepaymet(borrowId);
        }
    }

    /**
     * 根据订单id推送逾期还款数据
     * @param borrowId 订单id
     * @throws Exception
     */
    @RequestMapping("/urge/pushRepaymentByBorrowId.htm")
    public void pushRepayment(Long borrowId) throws Exception {
        UrgeOrderPushLogService.pushRepaymet(borrowId);
    }

    /**
     * 根据订单id推送逾期数据
     * @param borrowId 订单id
     * @throws Exception
     */
    @Resource
    private UserFirstCentCreditReportService userFirstCentCreditReportService;
    @RequestMapping("/urge/pushOrderByBorrowId.htm")
    public void pushOrder(Long borrowId) throws Exception {
        //UrgeOrderPushLogService.pushUrgeOrder(borrowId);
        userFirstCentCreditReportService.saveFirstCentCreditReport(borrowId);
    }
}
