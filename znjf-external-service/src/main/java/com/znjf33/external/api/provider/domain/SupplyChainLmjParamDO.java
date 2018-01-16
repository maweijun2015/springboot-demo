package com.znjf33.external.api.provider.domain;

import java.util.Date;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
public class SupplyChainLmjParamDO {
    /**
     * 贷款申请唯一编码
     */
    private String loanAppUuid;
    /**
     * 交易号
     */
    private String dealNo;
    /**
     * 支用申请唯一编号
     */
    private String loanDrawUuid;
    /**
     * 支用申请时间
     */
    private String loanDrawDate;
    /**
     * 借款天数
     */
    private Integer duration;
    /**
     * 资金状态
     */
    private Integer fundStatus;
    /**
     * 数据来源
     */
    private Integer channelFrom;
    /**
     * 收款状态
     */
    private Integer moneyStatus;
    /**
     * 发标队列处理
     */
    private Integer processStatus;
    /**
     * 申请还款时间
     */
    private Date repaymentDateApplied;
    /**
     * 客户编号
     */
    private Integer memberId;
    /**
     * 金服用户id
     */
    private Integer userId;
    /**
     * 客户姓名
     */
    private String memberName;
    /**
     * 证件号码
     */
    private String memberNo;
    /**
     * 支用申请金额
     */
    private Double drawAmount;
    /**
     * 分期数，随借随还业务为0
     */
    private Integer period;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 收货人手机号
     */
    private String consigneePhone;
    /**
     * 收货地址
     */
    private String consigneeAddress;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(Integer fundStatus) {
        this.fundStatus = fundStatus;
    }

    public Integer getChannelFrom() {
        return channelFrom;
    }

    public void setChannelFrom(Integer channelFrom) {
        this.channelFrom = channelFrom;
    }

    public Integer getMoneyStatus() {
        return moneyStatus;
    }

    public void setMoneyStatus(Integer moneyStatus) {
        this.moneyStatus = moneyStatus;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Date getRepaymentDateApplied() {
        return repaymentDateApplied;
    }

    public void setRepaymentDateApplied(Date repaymentDateApplied) {
        this.repaymentDateApplied = repaymentDateApplied;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoanAppUuid() {
        return loanAppUuid;
    }

    public void setLoanAppUuid(String loanAppUuid) {
        this.loanAppUuid = loanAppUuid;
    }

    public String getLoanDrawUuid() {
        return loanDrawUuid;
    }

    public void setLoanDrawUuid(String loanDrawUuid) {
        this.loanDrawUuid = loanDrawUuid;
    }

    public String getLoanDrawDate() {
        return loanDrawDate;
    }

    public void setLoanDrawDate(String loanDrawDate) {
        this.loanDrawDate = loanDrawDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public Double getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(Double drawAmount) {
        this.drawAmount = drawAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }
}
