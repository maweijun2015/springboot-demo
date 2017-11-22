package com.znjf33.external.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author maweijun
 * @description
 * @create 17/10/22
 */
public class SupplyChainLmjReimbursementParamDTO implements Serializable {
    private static final long serialVersionUID = -7594315163414910793L;
    /**
     * 贷款申请唯一编码
     */
    private String loanAppUuid;
    /**
     * 支用申请唯一编号
     */
    private String loanDrawUuid;
    /**
     * 扣款结果
     * PASS   - 成功   REJECT – 失败
     */
    private String status;
    /**
     * 当期是否结清：
     0  - 未结清
     2  - 已结清
     */
    private Integer isSettle;
    /**
     * 结果反馈
     */
    private List<RepaymentDataDTO> repaymentDataDTOList;

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

    public Integer getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }

    public List<RepaymentDataDTO> getRepaymentDataDTOList() {
        return repaymentDataDTOList;
    }

    public void setRepaymentDataDTOList(List<RepaymentDataDTO> repaymentDataDTOList) {
        this.repaymentDataDTOList = repaymentDataDTOList;
    }
}
