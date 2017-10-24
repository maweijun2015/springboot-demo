package com.znjf33.external.api.provider.domain;

import java.io.Serializable;

/**
 * @author maweijun
 * @description
 * @create 17/10/23
 */
public class ProtocolInfo implements Serializable{
    private static final long serialVersionUID = 5009415201550898559L;
    /**
     * 借款人姓名/企业名称
     */
    private String loanUserRealName;
    /**
     * 借款人法人姓名
     */
    private String loanLegalUserName;
    /**
     * 借款金额
     */
    private Double loanAmount;
    /**
     * 借款利率
     */
    private Double loanApr;
    /**
     * 借款开始日期
     */
    private String loanStartDate;
    /**
     * 还款日期
     */
    private String loanEndDate;
    /**
     * 借款期限
     */
    private Integer loanDuration;
    /**
     * 借款用途
     */
    private String loanUse;

    /**
     * 签署日期
     */
    private String signDate;

    /**
     * 投资人类型1-个人；2-企业
     */
    private int tenderUserType;
    /**
     * 投资人用户名
     */
    private String tenderUserName;
    /**
     * 投资人姓名/企业名称
     */
    private String tenderUserRealName;
    /**
     * 投资人身份证号/企业营业执照
     */
    private String tenderUserCardId;
    /**
     * 投资金额
     */
    private String tenderAmount;
    /**
     * 投资预期收益
     */
    private String tenderExpectedAmount;
    /**
     * 投资人信息服务费
     */
    private String tenderServiceFee;
    /**
     * 投资人预期年化
     */
    private String tenderApr;

    public int getTenderUserType() {
        return tenderUserType;
    }

    public void setTenderUserType(int tenderUserType) {
        this.tenderUserType = tenderUserType;
    }

    public String getTenderUserName() {
        return tenderUserName;
    }

    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName;
    }

    public String getTenderUserRealName() {
        return tenderUserRealName;
    }

    public void setTenderUserRealName(String tenderUserRealName) {
        this.tenderUserRealName = tenderUserRealName;
    }

    public String getTenderUserCardId() {
        return tenderUserCardId;
    }

    public void setTenderUserCardId(String tenderUserCardId) {
        this.tenderUserCardId = tenderUserCardId;
    }

    public String getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(String tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public String getTenderExpectedAmount() {
        return tenderExpectedAmount;
    }

    public void setTenderExpectedAmount(String tenderExpectedAmount) {
        this.tenderExpectedAmount = tenderExpectedAmount;
    }

    public String getTenderServiceFee() {
        return tenderServiceFee;
    }

    public void setTenderServiceFee(String tenderServiceFee) {
        this.tenderServiceFee = tenderServiceFee;
    }

    public String getTenderApr() {
        return tenderApr;
    }

    public void setTenderApr(String tenderApr) {
        this.tenderApr = tenderApr;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getLoanApr() {
        return loanApr;
    }

    public void setLoanApr(Double loanApr) {
        this.loanApr = loanApr;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getLoanUserRealName() {
        return loanUserRealName;
    }

    public void setLoanUserRealName(String loanUserRealName) {
        this.loanUserRealName = loanUserRealName;
    }

    public String getLoanLegalUserName() {
        return loanLegalUserName;
    }

    public void setLoanLegalUserName(String loanLegalUserName) {
        this.loanLegalUserName = loanLegalUserName;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
