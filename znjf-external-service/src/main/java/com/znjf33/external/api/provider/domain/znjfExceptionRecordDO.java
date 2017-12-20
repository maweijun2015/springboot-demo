package com.znjf33.external.api.provider.domain;

import java.util.Date;

public class znjfExceptionRecordDO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 0-处理中；1-处理成功；2-处理失败
     */
    private Integer status;

    /**
     * 浙农金服平台用户ID
     */
    private Integer userId;

    /**
     * 处理失败响应信息
     */
    private String msg;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 版本控制
     */
    private Integer version;

    /**
     * 重发次数
     */
    private Integer retryNum;

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 订单号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性订单号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 渠道类型
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性渠道类型
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 0-处理中；1-处理成功；2-处理失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性0-处理中；1-处理成功；2-处理失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 浙农金服平台用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性浙农金服平台用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 处理失败响应信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性处理失败响应信息
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 版本控制
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性版本控制
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取 [ZNJF_EXCEPTION_RECORD] 的属性 重发次数
     */
    public Integer getRetryNum() {
        return retryNum;
    }

    /**
     * 设置[ZNJF_EXCEPTION_RECORD]的属性重发次数
     */
    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }
}