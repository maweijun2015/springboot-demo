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
}
