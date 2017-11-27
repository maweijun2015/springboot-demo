package com.znjf33.external.api.provider.domain;

import java.util.Date;

/**
 * @author wangyc
 * @since 2017/10/22
 */
public class LemujiPayDo{
    /**
     * 支付处理中
     */
    public static final int PAY_STATUS_PAYING = 0;
    /**
     * 支付成功
     */
    public static final int PAY_STATUS_SUCCESS = 1;
    /**
     * 支付失败
     */
    public static final int PAY_STATUS_FAIL = 2;

    /**
     * 平台翼支付到融资人翼支付
     */
    public static final int PAY_TYPE_TO_FINANCER = 1;
    /**
     * 平台翼支付到银行账户
     */
    public static final int PAY_TYPE_TO_BANKACCOUNT = 2;
    /**
     * 银行账户到平台翼支付
     */
    public static final int PAY_TYPE_TO_PLATFORM = 3;

    private long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 支用唯一编码
     */
    private String loanDrawUuid;
    /**
     * 交易流水号
     */
    private String transactionNo;
    /**
     * 交易时间
     */
    private Date transactionTime;
    /**
     * 付款方账号
     */
    private String payerAccountNo;

    /**
     * 付款方账户名
     */
    private String payerAccountName;
    /**
     * 收款方账号
     */
    private String payeeAccountNo;
    /**
     * 收款方账户名
     */
    private String payeeAccountName;
    /**
     * 收款方账户名
     */
    private String payeeMerchantNo;
    /**
     * 交易金额
     */
    private Long transactionAmount;
    /**
     * 交易金额, 1-平台翼支付到融资人翼支付；2-平台翼支付到银行账户；3-银行账户到平台翼支付
     */
    private int transactionType;
    /**
     * 0-支付中；1-支付成功；2-支付失败
     */
    private int payStatus;
    /**
     * 备注
     */
    private String remark;

    /**
     * 交易详情, json字符串
     */
    private String transactionDesc;
    /**
     * 用户id
     */
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoanDrawUuid() {
        return loanDrawUuid;
    }

    public void setLoanDrawUuid(String loanDrawUuid) {
        this.loanDrawUuid = loanDrawUuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public String getPayeeAccountName() {
        return payeeAccountName;
    }

    public void setPayeeAccountName(String payeeAccountName) {
        this.payeeAccountName = payeeAccountName;
    }

    public String getPayeeMerchantNo() {
        return payeeMerchantNo;
    }

    public void setPayeeMerchantNo(String payeeMerchantNo) {
        this.payeeMerchantNo = payeeMerchantNo;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }
}
