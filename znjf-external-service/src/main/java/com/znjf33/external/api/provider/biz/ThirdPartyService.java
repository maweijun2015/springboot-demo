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
    void sendEmail(String type,String message,String noticeTypeNid,String lmjEmailAddress);

    /**
     * 发送异常邮件
     * @param type
     * @param message
     */
    public void sendEmailReminder(String type,String message);

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    String signatureFileForLmjSignature(SupplyChainLmjParamDTO supplyChainLmjParamDTO);
}
