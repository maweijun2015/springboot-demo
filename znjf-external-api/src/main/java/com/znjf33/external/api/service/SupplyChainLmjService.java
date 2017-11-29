package com.znjf33.external.api.service;


import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjReimbursementParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjResultDTO;

/**
 * @author maweijun
 * @description lemuji服务接口
 * @create 17/10/16
 */
public interface SupplyChainLmjService {

    /**
     * 验证支用申请用户
     * @return
     */
    SupplyChainLmjResultDTO getApplyLoan(SupplyChainLmjParamDTO supplyChainLmjParamDTO);

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    void signatureFileForLmj(SupplyChainLmjParamDTO supplyChainLmjParamDTO);
    /**
     * 请求是否合规
     * @return
     */
    SupplyChainLmjResultDTO getUserInfo(String loanAppUuid, String loanDrawUuid);
    /**
     * 还款登记信息
     * @return
     */
    boolean getReimbursementRegistration(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO);
    /**
     * 更新fund状态
     * @param supplyChainLmjParamDTO
     * @return
     */
    void updateZnjfFundProcessStatus(SupplyChainLmjParamDTO supplyChainLmjParamDTO);
    /**
     * 获取乐木几翼支付成功与否回调
     * @return
     */
    SupplyChainLmjResultDTO getCallbackPayStatus(SupplyChainLmjParamDTO supplyChainLmjParamDTO);
}
