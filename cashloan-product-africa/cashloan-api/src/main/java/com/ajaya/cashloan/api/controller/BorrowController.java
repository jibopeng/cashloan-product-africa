package com.ajaya.cashloan.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.model.*;
import com.ajaya.cashloan.cl.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 借款申请风控接口执行记录
 *
 * @author yanzhiqiang
 * @since 2021-01-21 14:54
 */
@Scope("prototype")
@Controller
public class BorrowController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(BorrowController.class);
    @Resource
    private ClBorrowService clBorrowService;
    @Resource
    private AdJustEnventMonitoService adJustEnventMonitoServiceImpl;
	@Resource
    private UserLoginVersionLogService userLoginVersionLogServiceImpl;
	@Resource
	private ClBorrowRiskBusinessService clBorrowRiskBusinessService;
	@Resource
    private UserAppFlyerLogService userAppFlyerLogService;
    /**
     * 首页信息查询
     *
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/borrow/findIndex.htm")
    public void findIndex() throws Exception {
        String userId = request.getParameter("userId");
        String versionNumber = request.getParameter("versionNumber");
        String appFlag = request.getParameter("appFlag");
        //插入用户登录版本统计
        userLoginVersionLogServiceImpl.saveUserLoginInfo(userId,versionNumber,appFlag);
        Map<String, Object> data = clBorrowService.findIndex(userId);
        String title2 = Global.getValue("title2");
        if (title2.equals(appFlag)){
            data.put("title", title2);
        }
        Map<String, Object> result = new HashMap<>(8);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "The query succeeded.");

        ServletUtils.writeToResponse(response, result);
    }
    /**
     * 借款申请
     *
     * @param client     客户端
     * @param address    借款地址
     * @param imei       imei地址
     * @param appRunTime app启动时间
     * @param coordinate 经纬度
     * @param ip         ip
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/act/borrow/save.htm")
    public void save(@RequestParam(value = "client") String client, @RequestParam(value = "address", required = false) String address,
                     @RequestParam(value = "coordinate", required = false) String coordinate, @RequestParam(value = "ip", required = false) String ip,
                     @RequestParam(value = "imei") String imei, @RequestParam(value = "appRunTime") long appRunTime,
                     @RequestParam(value = "userId") Long userId,@RequestParam(value = "appFlag") String appFlag,
                     @RequestParam(value = "mobileType") String mobileType) throws Exception {
        if (address == null) {
            address = "Unknown";
        }
        Map<String, Object> result = new HashMap<>(8);
        Borrow borrow = new Borrow(userId, 0D, "0", client, address, coordinate, ip, appFlag);
        ClBorrowModel borrowModel = clBorrowService.rcBorrowApply(borrow, mobileType, imei, appRunTime);
        // 所有订单都需要进行抓取原始数据
        result.put("needCatch", true);
        result.put("needCatchWaitingTime", Global.getValue("need_catch_waiting_time"));
        if (borrowModel.getId() > 0) {
            result.put("borrowId", borrowModel.getId());
            //申请成功
            //adjust申请成功事件统计(暂不使用，代码保留)
//            adJustEnventMonitoServiceImpl.adjustEnventMonito(borrow, 2);
            //appFlyer统计申请成功事件
            userAppFlyerLogService.appFlyerEnventMonito(borrowModel, 2);
            ServletUtils.writeToResponse(response, ResponseWrapper.success("The application was successful.", result));
            //保存风控相关业务表
            clBorrowRiskBusinessService.saveBorrowRiskBusiness(borrowModel.getId());
        } else {
            //申请失败了
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The application failed."));
        }
    }

    /**
     * 查询借款列表（放款、被拒、完成还款、逾期）
     *
     * @param userId   用户id
     * @param current  当前页码
     * @param pageSize 当前页大小
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/act/mine/borrow/pageList.htm", method = RequestMethod.GET)
    public void page(
            @RequestParam(value = "userId") long userId,
            @RequestParam(value = "current") int current,
            @RequestParam(value = "pageSize") int pageSize) throws Exception {
        Map<String, Object> searchMap = new HashMap<>(8);
        searchMap.put("userId", userId);
        Page<BorrowListModel> page = clBorrowService.pageList(userId, current, pageSize);
        Map<String, Object> data = new HashMap<>(8);
        data.put("list", page.getResult());
        Map<String, Object> result = new HashMap<>(8);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "The query succeeded.");
        ServletUtils.writeToResponse(response, result);
    }
    /**
     * 查询借款详情
     *
     * @param borrowId 借款id
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/act/borrow/findBorrowDetail.htm", method = RequestMethod.GET)
    public void findBorrowDetail(@RequestParam(value = "borrowId") Long borrowId) throws Exception {
        BorrowDetailModel clBorrowRecordModel = clBorrowService.findBorrowDetailById(borrowId);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("The query succeeded.", clBorrowRecordModel));
    }


    /**
     * 借款信息确认
     *
     * @param borrowId 借款id
     * @throws Exception 异常
     */
    @RequestMapping(value = "/api/act/borrow/findBorrowConfirm.htm", method = RequestMethod.GET)
    public void findBorrowConfirm(@RequestParam(value = "borrowId") Long borrowId) throws Exception {
        //额度变更
        clBorrowService.changeAmountHalf(borrowId);
        BorrowConfirmModel borrowConfirm = clBorrowService.findBorrowConfirm(borrowId);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("The query succeeded.", borrowConfirm));
    }
}
