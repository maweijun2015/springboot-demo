package com.znjf33.external.api.provider.domain;


import java.util.Date;

public class ZnjfExceptionRecordResultDO {
    /**
     * id
     */
    private Integer id;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 浙农金服平台用户ID
     */
    private Integer userId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 渠道名
     */
    private String channelTypeName;

    /**
     * 处理失败响应信息
     */
    private String msg;
    /**
     * 添加时间
     */
    private Date addTime;

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}