package com.znjf33.external.api.provider.biz.impl;

import com.znjf33.external.api.provider.biz.ThirdPartyService;
import com.znjf33.external.api.provider.common.Constant;
import com.znjf33.general.api.service.NoticeService;
import com.znjf33.general.api.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maweijun
 * @description
 * @create 17/12/6
 */
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyServiceImpl.class);

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 发送邮件
     * @param type
     * @param message
     * @param noticeTypeNid
     */
    public void sendEmail(String type,String message,String noticeTypeNid){
        try{
            String emailAddress = systemConfigService.getConfigValue(Constant.LMJ_EMAIL_ADDRESS);
            noticeService.sendEmail(type, message, noticeTypeNid, emailAddress);
            LOGGER.error("发送邮件成功,type={}",type);
        }catch (Exception e){
            LOGGER.error("发送邮件失败,type={}",type);
        }
    }

}
