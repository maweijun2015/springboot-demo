package com.znjf33.external.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
public class SupplyChainLmjParamDTO implements Serializable {
    private static final long serialVersionUID = 9087332819512401946L;
    /**
     * 贷款申请唯一编码
     */
    private String loanAppUuid;
    /**
     * 支用申请唯一编号
     */
    private String loanDrawUuid;
    /**
     * 上传下载地址
     */
    private String realPath;
    /**
     * 根据模板生产协议文件
     */
    private String content;
    /**
     * 支用申请时间
     */
    private String loanDrawDate;
    /**
     * 图片服务路径
     */
    private String imageServerUrl;
    /**
     * 借款天数
     */
    private Integer duration;
    /**
     * 客户编号
     */
    private Integer memberId;
    /**
     * 用户id
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
    private Float drawAmount;
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
    /**
     * 订单信息
     */
    private List<OrderinfoDTO> orderinfoDTO;
    /**
     * 借款人姓名/企业名称
     */
    private String loanUserRealName;
    /**
     * 合同编号
     */
    private String agreementNo;
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
     * 借款用途
     */
    private String loanUse;

    /**
     * 签署日期
     */
    private String signDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
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

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Float getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(Float drawAmount) {
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

    public List<OrderinfoDTO> getOrderinfoDTO() {
        return orderinfoDTO;
    }

    public void setOrderinfoDTO(List<OrderinfoDTO> orderinfoDTO) {
        this.orderinfoDTO = orderinfoDTO;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
