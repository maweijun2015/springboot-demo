package com.znjf33.external.api.provider.common;

/**
 * @author maweijun
 * @description
 * @create 17/9/11
 */
public interface TableConstants {
    /** 资金状态 - 未支付 **/
    Integer znjf_fund_status_no_pay = -1;
    /** 资金状态：已支付 */
    Integer MONEY_STATUS_PAY = 1;
    /** 资金状态：还款中 */
    Integer MONEY_STATUS_REPAYING = 7;
    /** 收款状态 - 未确认 */
    Integer znjf_fund_money_status_no = 0;
    /** 发标队列处理状态 - 不处理 */
    Integer znjf_fund_process_status_no = -1;
    /** 数据来源 - 全网通 */
    Integer znjf_fund_data_from_lmj = 1;
}
