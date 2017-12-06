package com.znjf33.external.api.provider.biz;

import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;

/**
 * @author maweijun
 * @description
 * @create 17/12/6
 */
public interface ThirdPartyService {
    /**
     * 发送邮件
     * @param type
     * @param message
     * @param noticeTypeNid
     */
    void sendEmail(String type,String message,String noticeTypeNid);

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    boolean signatureFileForLmjSignature(SupplyChainLmjParamDTO supplyChainLmjParamDTO);
}
