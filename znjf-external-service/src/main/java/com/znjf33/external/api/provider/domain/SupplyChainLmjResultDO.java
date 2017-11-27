package com.znjf33.external.api.provider.domain;

import java.util.Date;

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
     * 借款天数
     */
    private Integer duration;
    /**
     * 信用额度
     */
    private Float creditAmount;
    /**
     * 垫资金额
     */
    private Double amountApplied;
    /**
     * 商户号
     */
    private String extMerchants;
    /**
     * 支用唯一编号
     */
    private String loanDrawUuid;
    /**
     * 贷款唯一编号
     */
    private String loanAppUuid;
    /**
     * 交易金额
     */
    private Long transactionAmount;
    /**
     * 预计还款时间
     */
    private Date repaymentTime;
    /**
     * 预还金额
     */
    private double repaymentAccount;
    /**
     * 本金
     */
    private double capital;
    /**
     * 利息
     */
    private double interest;
    /**
     * 服务费
     */
    private double manageFee;

    public double getManageFee() {
        return manageFee;
    }

    public void setManageFee(double manageFee) {
        this.manageFee = manageFee;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getRepaymentAccount() {
        return repaymentAccount;
    }

    public void setRepaymentAccount(double repaymentAccount) {
        this.repaymentAccount = repaymentAccount;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getLoanAppUuid() {
        return loanAppUuid;
    }

    public void setLoanAppUuid(String loanAppUuid) {
        this.loanAppUuid = loanAppUuid;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getLoanDrawUuid() {
        return loanDrawUuid;
    }

    public void setLoanDrawUuid(String loanDrawUuid) {
        this.loanDrawUuid = loanDrawUuid;
    }

    public String getExtMerchants() {
        return extMerchants;
    }

    public void setExtMerchants(String extMerchants) {
        this.extMerchants = extMerchants;
    }

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
