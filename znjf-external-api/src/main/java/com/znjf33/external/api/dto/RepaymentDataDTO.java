package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * @author maweijun
 * @description
 * @create 17/10/22
 */
public class RepaymentDataDTO implements Serializable {
    private static final long serialVersionUID = -997954320099457950L;
    /**
     * 还款期数
     */
    private String repaymentIssue;
    /**
     * 还款日期
     */
    private String repaymentDate;
    /**
     * 应还本金（单位：元，小数点保留两位）
     */
    private Float repaymentPrincipalY;
    /**
     * 实还本金(单位：元，小数点保留两位)
     */
    private Float repaymentPrincipal;
    /**
     * 未还本金（单位：元，小数点保留两位）
     */
    private Float repaymentPrincipalW;
    /**
     * 应还利息（单位：元，小数点保留两位）
     */
    private Float repaymentInterestY;
    /**
     * 实还利息（单位：元，小数点保留两位）
     */
    private Float repaymentInterest;
    /**
     *未还利息（单位：元，小数点保留两位）
     */
    private Float repaymentInterestW;
    /**
     * 应还罚息（单位：元，小数点保留两位）
     */
    private Float repaymentlateInterestY;
    /**
     * 实还罚息（单位：元，小数点保留两位）
     */
    private Float repaymentlateInterest;
    /**
     * 未还罚息（单位：元，小数点保留两位）
     */
    private Float repaymentlateInterestW;
    /**
     * 实还总金额(单位：元，小数点保留两位)
     */
    private Float repaymentAmount;
    /**
     * 应还总金额（单位：元，小数点保留两位）
     */
    private Float repaymentAmountY;
    /**
     * 每一笔还款唯一编号
     */
    private String number;
    /**
     * 还款类型：
     1：提前结清
     0：正常还款
     */
    private Integer type;

    public String getRepaymentIssue() {
        return repaymentIssue;
    }

    public void setRepaymentIssue(String repaymentIssue) {
        this.repaymentIssue = repaymentIssue;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Float getRepaymentPrincipalY() {
        return repaymentPrincipalY;
    }

    public void setRepaymentPrincipalY(Float repaymentPrincipalY) {
        this.repaymentPrincipalY = repaymentPrincipalY;
    }

    public Float getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(Float repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public Float getRepaymentPrincipalW() {
        return repaymentPrincipalW;
    }

    public void setRepaymentPrincipalW(Float repaymentPrincipalW) {
        this.repaymentPrincipalW = repaymentPrincipalW;
    }

    public Float getRepaymentInterestY() {
        return repaymentInterestY;
    }

    public void setRepaymentInterestY(Float repaymentInterestY) {
        this.repaymentInterestY = repaymentInterestY;
    }

    public Float getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(Float repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public Float getRepaymentInterestW() {
        return repaymentInterestW;
    }

    public void setRepaymentInterestW(Float repaymentInterestW) {
        this.repaymentInterestW = repaymentInterestW;
    }

    public Float getRepaymentlateInterestY() {
        return repaymentlateInterestY;
    }

    public void setRepaymentlateInterestY(Float repaymentlateInterestY) {
        this.repaymentlateInterestY = repaymentlateInterestY;
    }

    public Float getRepaymentlateInterest() {
        return repaymentlateInterest;
    }

    public void setRepaymentlateInterest(Float repaymentlateInterest) {
        this.repaymentlateInterest = repaymentlateInterest;
    }

    public Float getRepaymentlateInterestW() {
        return repaymentlateInterestW;
    }

    public void setRepaymentlateInterestW(Float repaymentlateInterestW) {
        this.repaymentlateInterestW = repaymentlateInterestW;
    }

    public Float getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Float repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Float getRepaymentAmountY() {
        return repaymentAmountY;
    }

    public void setRepaymentAmountY(Float repaymentAmountY) {
        this.repaymentAmountY = repaymentAmountY;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
