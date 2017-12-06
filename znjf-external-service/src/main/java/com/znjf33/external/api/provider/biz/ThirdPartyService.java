package com.znjf33.external.api.provider.biz;

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
}
