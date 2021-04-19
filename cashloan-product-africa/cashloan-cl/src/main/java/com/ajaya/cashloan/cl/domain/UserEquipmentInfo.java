package com.ajaya.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户设备信息表实体
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-08-27 13:04:39
 */
public class UserEquipmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 设备指纹
     */
    private String blackBox;

    /**
     * 操作系统
     */
    private String operatingSystem;

    /**
     * 系统版本
     */
    private String systemVersions;

    /**
     * 手机型号
     */
    private String phoneType;

    /**
     * 手机品牌
     */
    private String phoneBrand;

    /**
     * 手机设备标识
     */
    private String phoneMark;

    /**
     * mac网络接口地址
     */
    private String mac;

    /**
     * imei设备标识
     */
    private String imei;

    /**
     * 应用版本号
     */
    private String versionName;

    /**
     * 应用build号
     */
    private String versionCode;

    /**
     * APP安装时间
     */
    private Date appInstallTime;

    /**
     * 用户adjust标识
     */
    private String androidId;

    /**
     * APP应用市场
     */
    private String appMarket;

    /**
     * 用户adjust标识
     */
    private String gpsadId;

    /**
     * 分辨率宽
     */
    private String deviceWidth;

    /**
     * 分辨率高
     */
    private String deviceHeight;

    /**
     * 运营商
     */
    private String telephony;

    /**
     * 默认语言
     */
    private String defaultLanguage;

    /**
     * 安全更新日期
     */
    private String securityPatch;

    /**
     * sdk版本
     */
    private String sdkVersion;

    /**
     * 是否root
     */
    private String rooted;

    /**
     * 出厂日期
     */
    private String productionDate;

    /**
     * 硬件设备序列号
     */
    private String serial;

    /**
     * 是否有内置sd卡
     */
    private String containSd;

    /**
     * ram可用
     */
    private String ramCanUse;

    /**
     * ram总共
     */
    private String ramTotal;

    /**
     * 内存可用
     */
    private String cashCanUse;

    /**
     * 内存总共
     */
    private String cashTotal;

    /**
     * 是否有外置SD卡
     */
    private String extraSd;


    /**
     * 获取主键Id
     *
     * @return id
     */
    public Long getId(){
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param 要设置的主键Id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Long getUserId(){
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 要设置的用户id
     */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
     * 获取设备指纹
     *
     * @return 设备指纹
     */
    public String getBlackBox(){
        return blackBox;
    }

    /**
     * 设置设备指纹
     *
     * @param blackBox 要设置的设备指纹
     */
    public void setBlackBox(String blackBox){
        this.blackBox = blackBox;
    }

    /**
     * 获取操作系统
     *
     * @return 操作系统
     */
    public String getOperatingSystem(){
        return operatingSystem;
    }

    /**
     * 设置操作系统
     *
     * @param operatingSystem 要设置的操作系统
     */
    public void setOperatingSystem(String operatingSystem){
        this.operatingSystem = operatingSystem;
    }

    /**
     * 获取系统版本
     *
     * @return 系统版本
     */
    public String getSystemVersions(){
        return systemVersions;
    }

    /**
     * 设置系统版本
     *
     * @param systemVersions 要设置的系统版本
     */
    public void setSystemVersions(String systemVersions){
        this.systemVersions = systemVersions;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public String getPhoneType(){
        return phoneType;
    }

    /**
     * 设置手机型号
     *
     * @param phoneType 要设置的手机型号
     */
    public void setPhoneType(String phoneType){
        this.phoneType = phoneType;
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     */
    public String getPhoneBrand(){
        return phoneBrand;
    }

    /**
     * 设置手机品牌
     *
     * @param phoneBrand 要设置的手机品牌
     */
    public void setPhoneBrand(String phoneBrand){
        this.phoneBrand = phoneBrand;
    }

    /**
     * 获取手机设备标识
     *
     * @return 手机设备标识
     */
    public String getPhoneMark(){
        return phoneMark;
    }

    /**
     * 设置手机设备标识
     *
     * @param phoneMark 要设置的手机设备标识
     */
    public void setPhoneMark(String phoneMark){
        this.phoneMark = phoneMark;
    }

    /**
     * 获取mac网络接口地址
     *
     * @return mac网络接口地址
     */
    public String getMac(){
        return mac;
    }

    /**
     * 设置mac网络接口地址
     *
     * @param mac 要设置的mac网络接口地址
     */
    public void setMac(String mac){
        this.mac = mac;
    }

    /**
     * 获取imei设备标识
     *
     * @return imei设备标识
     */
    public String getImei(){
        return imei;
    }

    /**
     * 设置imei设备标识
     *
     * @param imei 要设置的imei设备标识
     */
    public void setImei(String imei){
        this.imei = imei;
    }

    /**
     * 获取应用版本号
     *
     * @return 应用版本号
     */
    public String getVersionName(){
        return versionName;
    }

    /**
     * 设置应用版本号
     *
     * @param versionName 要设置的应用版本号
     */
    public void setVersionName(String versionName){
        this.versionName = versionName;
    }

    /**
     * 获取应用build号
     *
     * @return 应用build号
     */
    public String getVersionCode(){
        return versionCode;
    }

    /**
     * 设置应用build号
     *
     * @param versionCode 要设置的应用build号
     */
    public void setVersionCode(String versionCode){
        this.versionCode = versionCode;
    }

    /**
     * 获取APP安装时间
     *
     * @return APP安装时间
     */
    public Date getAppInstallTime(){
        return appInstallTime;
    }

    /**
     * 设置APP安装时间
     *
     * @param appInstallTime 要设置的APP安装时间
     */
    public void setAppInstallTime(Date appInstallTime){
        this.appInstallTime = appInstallTime;
    }

    /**
     * 获取用户adjust标识
     *
     * @return 用户adjust标识
     */
    public String getAndroidId(){
        return androidId;
    }

    /**
     * 设置用户adjust标识
     *
     * @param androidId 要设置的用户adjust标识
     */
    public void setAndroidId(String androidId){
        this.androidId = androidId;
    }

    /**
     * 获取APP应用市场
     *
     * @return APP应用市场
     */
    public String getAppMarket(){
        return appMarket;
    }

    /**
     * 设置APP应用市场
     *
     * @param appMarket 要设置的APP应用市场
     */
    public void setAppMarket(String appMarket){
        this.appMarket = appMarket;
    }

    /**
     * 获取用户adjust标识
     *
     * @return 用户adjust标识
     */
    public String getGpsadId(){
        return gpsadId;
    }

    /**
     * 设置用户adjust标识
     *
     * @param gpsadId 要设置的用户adjust标识
     */
    public void setGpsadId(String gpsadId){
        this.gpsadId = gpsadId;
    }

    /**
     * 获取分辨率宽
     *
     * @return 分辨率宽
     */
    public String getDeviceWidth(){
        return deviceWidth;
    }

    /**
     * 设置分辨率宽
     *
     * @param deviceWidth 要设置的分辨率宽
     */
    public void setDeviceWidth(String deviceWidth){
        this.deviceWidth = deviceWidth;
    }

    /**
     * 获取分辨率高
     *
     * @return 分辨率高
     */
    public String getDeviceHeight(){
        return deviceHeight;
    }

    /**
     * 设置分辨率高
     *
     * @param deviceHeight 要设置的分辨率高
     */
    public void setDeviceHeight(String deviceHeight){
        this.deviceHeight = deviceHeight;
    }

    /**
     * 获取运营商
     *
     * @return 运营商
     */
    public String getTelephony(){
        return telephony;
    }

    /**
     * 设置运营商
     *
     * @param telephony 要设置的运营商
     */
    public void setTelephony(String telephony){
        this.telephony = telephony;
    }

    /**
     * 获取默认语言
     *
     * @return 默认语言
     */
    public String getDefaultLanguage(){
        return defaultLanguage;
    }

    /**
     * 设置默认语言
     *
     * @param defaultLanguage 要设置的默认语言
     */
    public void setDefaultLanguage(String defaultLanguage){
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * 获取安全更新日期
     *
     * @return 安全更新日期
     */
    public String getSecurityPatch(){
        return securityPatch;
    }

    /**
     * 设置安全更新日期
     *
     * @param securityPatch 要设置的安全更新日期
     */
    public void setSecurityPatch(String securityPatch){
        this.securityPatch = securityPatch;
    }

    /**
     * 获取sdk版本
     *
     * @return sdk版本
     */
    public String getSdkVersion(){
        return sdkVersion;
    }

    /**
     * 设置sdk版本
     *
     * @param sdkVersion 要设置的sdk版本
     */
    public void setSdkVersion(String sdkVersion){
        this.sdkVersion = sdkVersion;
    }

    /**
     * 获取是否root
     *
     * @return 是否root
     */
    public String getRooted(){
        return rooted;
    }

    /**
     * 设置是否root
     *
     * @param rooted 要设置的是否root
     */
    public void setRooted(String rooted){
        this.rooted = rooted;
    }

    /**
     * 获取出厂日期
     *
     * @return 出厂日期
     */
    public String getProductionDate(){
        return productionDate;
    }

    /**
     * 设置出厂日期
     *
     * @param productionDate 要设置的出厂日期
     */
    public void setProductionDate(String productionDate){
        this.productionDate = productionDate;
    }

    /**
     * 获取硬件设备序列号
     *
     * @return 硬件设备序列号
     */
    public String getSerial(){
        return serial;
    }

    /**
     * 设置硬件设备序列号
     *
     * @param serial 要设置的硬件设备序列号
     */
    public void setSerial(String serial){
        this.serial = serial;
    }

    /**
     * 获取是否有内置sd卡
     *
     * @return 是否有内置sd卡
     */
    public String getContainSd(){
        return containSd;
    }

    /**
     * 设置是否有内置sd卡
     *
     * @param containSd 要设置的是否有内置sd卡
     */
    public void setContainSd(String containSd){
        this.containSd = containSd;
    }

    /**
     * 获取ram可用
     *
     * @return ram可用
     */
    public String getRamCanUse(){
        return ramCanUse;
    }

    /**
     * 设置ram可用
     *
     * @param ramCanUse 要设置的ram可用
     */
    public void setRamCanUse(String ramCanUse){
        this.ramCanUse = ramCanUse;
    }

    /**
     * 获取ram总共
     *
     * @return ram总共
     */
    public String getRamTotal(){
        return ramTotal;
    }

    /**
     * 设置ram总共
     *
     * @param ramTotal 要设置的ram总共
     */
    public void setRamTotal(String ramTotal){
        this.ramTotal = ramTotal;
    }

    /**
     * 获取内存可用
     *
     * @return 内存可用
     */
    public String getCashCanUse(){
        return cashCanUse;
    }

    /**
     * 设置内存可用
     *
     * @param cashCanUse 要设置的内存可用
     */
    public void setCashCanUse(String cashCanUse){
        this.cashCanUse = cashCanUse;
    }

    /**
     * 获取内存总共
     *
     * @return 内存总共
     */
    public String getCashTotal(){
        return cashTotal;
    }

    /**
     * 设置内存总共
     *
     * @param cashTotal 要设置的内存总共
     */
    public void setCashTotal(String cashTotal){
        this.cashTotal = cashTotal;
    }

    /**
     * 获取是否有外置SD卡
     *
     * @return 是否有外置SD卡
     */
    public String getExtraSd(){
        return extraSd;
    }

    /**
     * 设置是否有外置SD卡
     *
     * @param extraSd 要设置的是否有外置SD卡
     */
    public void setExtraSd(String extraSd){
        this.extraSd = extraSd;
    }

}