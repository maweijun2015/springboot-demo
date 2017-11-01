package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
public class SupplyChainLmjResultDTO implements Serializable {
    private static final long serialVersionUID = 3073451196909322903L;
    /**
     * code
     */
    private String code;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 法人代表姓名
     */
    private String frdbName;
    /**
     * 今日签约数
     */
    private Integer countFundsToday;
    /**
     * 垫资金额
     */
    private Double amountApplied;
    /**
     * 借款天数
     */
    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getAmountApplied() {
        return amountApplied;
    }

    public void setAmountApplied(Double amountApplied) {
        this.amountApplied = amountApplied;
    }

    public Integer getCountFundsToday() {
        return countFundsToday;
    }

    public void setCountFundsToday(Integer countFundsToday) {
        this.countFundsToday = countFundsToday;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFrdbName() {
        return frdbName;
    }

    public void setFrdbName(String frdbName) {
        this.frdbName = frdbName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
