package com.znjf33.external.api.provider.domain;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
public class SupplyChainLmjResultDO {
    /**
     * fundId
     */
    private Long fundId;
    /**
     * 标id
     */
    private Long borrowId;
    /**
     * 交易号
     */
    private String dealNo;
    /**
     * 手机号
     */
    private String mobilePhone;
    /** 真实姓名 */
    private String realName;
    /**
     * 法人代表姓名
     */
    private String frdbName;
    /**
     * 外部用户id
     */
    private Integer extUserId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 信用额度
     */
    private Float creditAmount;

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

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public Float getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Float creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(Integer extUserId) {
        this.extUserId = extUserId;
    }
}
