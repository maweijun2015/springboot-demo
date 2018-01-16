package com.znjf33.external.api.provider.mapper;

import java.util.Date;
import java.util.List;

import com.znjf33.external.api.provider.domain.LemujiPayDo;
import com.znjf33.external.api.provider.domain.SupplyChainLmjParamDO;
import com.znjf33.external.api.provider.domain.SupplyChainLmjResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
@Mapper
public interface SupplyChainLmjMapper {

    SupplyChainLmjResultDO getZnjfExternalUserByUserIdAndUuid(@Param("userId") Integer userId, @Param("loanAppUuid") String loanAppUuid);
    Double getCreditAmountByUserId(@Param("userId") Integer userId);
    Double getUsedCreditAmount(@Param("userId") Integer userId);
    void saveApplyLoan(SupplyChainLmjParamDO supplyChainLmjParamDO);
    void saveApplyLoanAttachment(List item);
    Integer countUserByUserInfo(@Param("userId") Integer userId);
    SupplyChainLmjResultDO getZnjfFundByUserId(@Param("userId") Integer userId,@Param("loanDrawUuid") String loanDrawUuid);
    SupplyChainLmjResultDO getZnfFundByOrderNo(@Param("userId") Integer userId,@Param("channelFrom") Integer channelFrom,@Param("loanDrawUuid") String loanDrawUuid);
    SupplyChainLmjResultDO getUserInfo(@Param("loanAppUuid") String loanAppUuid);
    Integer countFundsToday(@Param("userId") Integer userId, @Param("channelFrom") Integer channelFrom,@Param("fundStatus") Integer fundStatus);
    Integer getFundExist(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid);
    Integer getZnjfFundForSame(@Param("loanDrawUuid") String loanDrawUuid);

    Integer queryDateLimitByUserId(@Param("userId") Integer userId);

    /**
     * 更新还款表第三方编码
     *
     * @param userId
     * @param borrowId
     * @param repaymentNo
     */
    void updateBorrowRepayment(@Param("userId") Integer userId, @Param("borrowId") Long borrowId, @Param("repaymentNo") String repaymentNo);
    /**
     * 更新还款表状态
     *
     * @param userId
     * @param borrowId
     */
    void updateBorrowRepaymentStatus(@Param("userId") Integer userId, @Param("borrowId") Long borrowId);

    /**
     * 更新融资还款状态
     *
     * @param userId
     * @param loanDrawUuid
     * @param fundStatus
     */
    void updateZnjfFundRepayStatus(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid, @Param("fundStatus") Integer fundStatus);

    /**
     * 更新融资状态
     *
     * @param userId
     * @param loanDrawUuid
     * @param fundStatus
     * @param channelFrom
     */
    void updateZnjfFundStatus(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid, @Param("fundStatus") Integer fundStatus,@Param("channelFrom") Integer channelFrom, @Param("loanAgreement") String loanAgreement);
    void updateZnjfFundProcessStatus(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid, @Param("processStatus") Integer processStatus,@Param("channelFrom") Integer channelFrom);

    /**
     * 更新支付状态
     * @param payStatus
     * @param orderNo
     */
    void updateZnjfLemujiPayStatus(@Param("payStatus") Integer payStatus, @Param("payMsg")String payMsg, @Param("orderNo")String orderNo);

    /**
     * 查询商户号
     * @param userId
     */
    SupplyChainLmjResultDO getZnjfExternalUserByUserId(@Param("userId") Integer userId);
    /**
     * 查询交易详情
     * @param orderNo
     */
    SupplyChainLmjResultDO getZnjfLemujiPayByOrderId(@Param("orderNo") String orderNo);
    /**
     * 查询交易详情
     * @param loanDrawUuid
     */
    SupplyChainLmjResultDO getZnjfLemujiPayByUuid(@Param("loanDrawUuid") String loanDrawUuid);
    /**
     * 保存支付信息表
     * @param lemujiPayDo
     */
    void saveZnjfLemujiPay(LemujiPayDo lemujiPayDo);

    /**
     * 查询预还款信息
     * @param orderNo
     * @return
     */
    SupplyChainLmjResultDO getRepaymentInfo(@Param("orderNo") String orderNo);

    /**
     * 根据外部用户id查询金服用户id
     * @param extUserId
     * @return
     */
    SupplyChainLmjResultDO getZnjfExternalUserByExtUserId(@Param("extUserId") Integer extUserId);

    /**
     * 更新授信系统
     */
    int updateZnjfCreditLines(@Param("creditLine") Float creditLine, @Param("useLine")Float useLine, @Param("userId")Integer userId, @Param("creditFrom")Integer creditFrom);

    /**
     * 查询佐力已用额度
     * @param userId
     * @return
     */
    Double queryUseLinesByUserId(@Param("userId") Integer userId);
}
