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
     * @param userId
     * @param imageServerUrl
     * @return
     */
    boolean signatureFileForLmj(int userId, String imageServerUrl);
    /**
     * 请求是否合规
     * @return
     */
    SupplyChainLmjResultDTO getUserInfo(String loanAppUuid, String loanDrawUuid);
    /**
     * 还款登记信息
     * @return
     */
    void getReimbursementRegistration(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO);
}
