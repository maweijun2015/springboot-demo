package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */
public class LemujiRepaymentDTO extends LemujiBaseDTO {
    /**
     * 还款期数
     */
    private String repaymentIssue;
    /**
     * 应还日期yyyymmdd
     */
    private String repaymentDate;
    /**
     * 应还本金(单位：元，小数点保留两位)
     */
    private String repaymentPrincipal;
    /**
     * 应还利息（单位：元，小数点保留两位）
     */
    private String repaymentInterest;
    /**
     * 应还总金额(单位：元，小数点保留两位)
     */
    private String repaymentAmount;

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

    public String getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(String repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public String getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(String repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }
}
