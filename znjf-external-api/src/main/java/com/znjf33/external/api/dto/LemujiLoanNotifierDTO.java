package com.znjf33.external.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 放款通知model
 *
 * @author wangyc
 * @since 2017/10/20
 */
public class LemujiLoanNotifierDTO extends LemujiBaseDTO {
    /**
     * 贷款申请唯一编码
     */
    private String loanAppUuid;
    /**
     * 支用唯一编号
     */
    private String loanDrawUuid;
    /**
     *放款是否成功：YES：是; NO : 否
     */
    private String status;
    /**
     * 审批意见
     */
    private String comment;
    /**
     * 数组对象，还款计划表（成功时，需反馈）
     */
    private List<LemujiRepaymentDTO> repaymentList;
    /**
     * 借款借据图片地址
     */
    private String IOUsUrl;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<LemujiRepaymentDTO> getRepaymentList() {
        return repaymentList;
    }

    public void setRepaymentList(List<LemujiRepaymentDTO> repaymentList) {
        this.repaymentList = repaymentList;
    }

    public String getIOUsUrl() {
        return IOUsUrl;
    }

    public void setIOUsUrl(String IOUsUrl) {
        this.IOUsUrl = IOUsUrl;
    }
}
