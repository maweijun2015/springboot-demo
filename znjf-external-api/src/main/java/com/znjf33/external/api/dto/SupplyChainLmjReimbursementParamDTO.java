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
     * 翼支付接入识别编码
     */
    private String quanwangtongYizhifuPartnerid;
    /**
     * 翼支付放宽到银行卡接口
     */
    private String quanwangtongYizhifuPaytb;
    /**
     * 乐木几开放秘钥
     */
    private String lmjApiSecretKey;
    /**
     * 全网通帐号
     */
    private String quanwangtongName;
    /**
     * 全网通账户
     */
    private String quanwangtongACCOUNT;
    /**
     * 结果反馈
     */
    private List<RepaymentDataDTO> repaymentDataDTOList;

    public String getLmjApiSecretKey() {
        return lmjApiSecretKey;
    }

    public void setLmjApiSecretKey(String lmjApiSecretKey) {
        this.lmjApiSecretKey = lmjApiSecretKey;
    }

    public String getQuanwangtongName() {
        return quanwangtongName;
    }

    public void setQuanwangtongName(String quanwangtongName) {
        this.quanwangtongName = quanwangtongName;
    }

    public String getQuanwangtongACCOUNT() {
        return quanwangtongACCOUNT;
    }

    public void setQuanwangtongACCOUNT(String quanwangtongACCOUNT) {
        this.quanwangtongACCOUNT = quanwangtongACCOUNT;
    }

    public String getQuanwangtongYizhifuPaytb() {
        return quanwangtongYizhifuPaytb;
    }

    public void setQuanwangtongYizhifuPaytb(String quanwangtongYizhifuPaytb) {
        this.quanwangtongYizhifuPaytb = quanwangtongYizhifuPaytb;
    }

    public String getQuanwangtongYizhifuPartnerid() {
        return quanwangtongYizhifuPartnerid;
    }

    public void setQuanwangtongYizhifuPartnerid(String quanwangtongYizhifuPartnerid) {
        this.quanwangtongYizhifuPartnerid = quanwangtongYizhifuPartnerid;
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
