package com.znjf33.external.api.provider.common;

/**
 * @author maweijun
 * @description
 * @create 17/9/11
 */
public interface TableConstants {
    /** 资金状态 - 未支付 **/
    Integer ZNJF_FUND_STATUS_NO_PAY = -1;
    /** 资金状态：已支付 */
    Integer MONEY_STATUS_PAY = 1;
    /** 资金状态：还款中 */
    Integer MONEY_STATUS_REPAYING = 7;
    /** 收款状态 - 未确认 */
    Integer ZNJF_FUND_MONEY_STATUS_NO = 0;
    /** 发标队列处理状态 - 不处理 */
    Integer ZNJF_FUND_PROCESS_STATUS_NO = -1;
    /** 发标队列处理状态 - 处理失败 */
    Integer ZNJF_FUND_PROCESS_STATUS_ERROR = 12;
    /** 数据来源 - 全网通 */
    Integer ZNJF_FUND_DATA_FROM_LMJ = 1;
    /** 渠道类型表 - 渠道类型 - 在线充值 */
    String ZNJF_CHANNEL_TYPE_RECHARGE = "100002";
    /** 渠道类型表 - 渠道类型 - 提现回调 */
    String ZNJF_CHANNEL_TYPE_CASH = "100003";
    /** 渠道类型表 - 渠道类型 - 放款回调 */
    String ZNJF_CHANNEL_TYPE_VERIFY_FULL = "100006";
    /** 渠道类型表 - 渠道类型 - 批量债券转让回调 */
    String ZNJF_CHANNEL_TYPE_BATCH_BOND = "100009";
    /** 渠道类型表 - 渠道类型 - 还款回调 */
    String ZNJF_CHANNEL_TYPE_REPAY = "100010";
    /** 渠道类型表 - 状态 - 待处理 */
    Integer ZNJF_CHANNEL_TYPE_STATUS_WAIT = 0;
    /** 异常记录表 - 状态 - 处理中 */
    Integer ZNJF_EXCEPTION_RECORD_STATUS_DEAL = 1;

    /** 授信额度表 - 授信来源 - 佐力 */
    Integer ZNJF_CREDIT_LINES_FROM_ZL = 1;
}
