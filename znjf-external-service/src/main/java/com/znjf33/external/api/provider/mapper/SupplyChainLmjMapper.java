package com.znjf33.external.api.provider.mapper;

import java.util.Date;
import java.util.List;

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
    Float getCreditAmountByUserId(@Param("userId") Integer userId);
    Float getUsedCreditAmount(@Param("userId") Integer userId);
    void saveApplyLoan(SupplyChainLmjParamDO supplyChainLmjParamDO);
    void saveApplyLoanAttachment(List item);
    Integer countUserByUserInfo(@Param("userId") Integer userId);
    SupplyChainLmjResultDO getZnjfFundByUserId(@Param("userId") Integer userId,@Param("loanDrawUuid") String loanDrawUuid);
    SupplyChainLmjResultDO getZnfFundByOrderNo(@Param("userId") Integer userId,@Param("channelFrom") Integer channelFrom,@Param("loanDrawUuid") String loanDrawUuid);
    SupplyChainLmjResultDO getUserInfo(@Param("loanAppUuid") String loanAppUuid);
    Integer countFundsToday(@Param("userId") Integer userId, @Param("channelFrom") Integer channelFrom,@Param("fundStatus") Integer fundStatus);
    Integer getFundExist(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid);
    Integer getZnjfFundForSame(@Param("loanDrawUuid") String loanDrawUuid);

    /**
     * 更新还款表
     *
     * @param userId
     * @param borrowId
     * @param repaymentNo
     */
    void updateBorrowRepayment(@Param("userId") Integer userId, @Param("borrowId") Long borrowId, @Param("repaymentNo") String repaymentNo);

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
    void updateZnjfFundStatus(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid, @Param("fundStatus") Integer fundStatus,@Param("channelFrom") Integer channelFrom);
    void updateZnjfFundProcessStatus(@Param("userId") Integer userId, @Param("loanDrawUuid") String loanDrawUuid, @Param("processStatus") Integer processStatus,@Param("channelFrom") Integer channelFrom);

    /**
     * 更新支付状态
     * @param payStatus
     * @param orderNo
     */
    void updateZnjfLemujiPayStatus(@Param("payStatus") Integer payStatus, @Param("orderNo")String orderNo);

}
