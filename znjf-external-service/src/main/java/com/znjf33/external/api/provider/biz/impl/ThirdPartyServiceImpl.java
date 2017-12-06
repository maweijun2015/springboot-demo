package com.znjf33.external.api.provider.biz.impl;

import com.znjf33.common.utils.HclientFileUtil;
import com.znjf33.common.utils.StringUtil;
import com.znjf33.common.utils.constant.enums.EnumUploadImgType;
import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.provider.biz.ThirdPartyService;
import com.znjf33.external.api.provider.common.Constant;
import com.znjf33.external.api.provider.protocol.LoanProtocol;
import com.znjf33.general.api.service.NoticeService;
import com.znjf33.general.api.service.SystemConfigService;
import com.znjf33.useraccount.api.dto.UserSignatureDto;
import com.znjf33.useraccount.api.service.UserSignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

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

    @Autowired
    private UserSignatureService userSignatureService;

    @Autowired
    private LoanProtocol loanProtocol;

    /**
     * 发送邮件
     * @param type
     * @param message
     * @param noticeTypeNid
     */
    @Override
    public void sendEmail(String type,String message,String noticeTypeNid){
        try{
            String emailAddress = systemConfigService.getConfigValue(Constant.LMJ_EMAIL_ADDRESS);
            noticeService.sendEmail(type, message, noticeTypeNid, emailAddress);
            LOGGER.error("发送邮件成功,type={}",type);
        }catch (Exception e){
            LOGGER.error("发送邮件失败,type={}",type);
        }
    }

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    @Override
    public boolean signatureFileForLmjSignature(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
        String srcFile="";
        String userResult="";
        String platformResult = "";
        String signResult = "";
        try{
            LOGGER.info("************电子签名开始******************");

            //根据模板生产协议文件
            loanProtocol.executor(supplyChainLmjParamDTO,supplyChainLmjParamDTO.getContent());

            //生成的文件路径
            srcFile = loanProtocol.getInPdfName();
            LOGGER.info("user_id="+supplyChainLmjParamDTO.getUserId());
            userResult = userSignatureService.userDoSignature(supplyChainLmjParamDTO.getUserId(), "乙方（电子签章）:", srcFile);
            if(StringUtil.isNotBlank(userResult)){
                platformResult = userSignatureService.platformDoSignature("丙方（电子签章）:", userResult);

                if(StringUtil.isNotBlank(platformResult)){
                    signResult = HclientFileUtil.uploadFileMethod(supplyChainLmjParamDTO.getImageServerUrl() + HclientFileUtil.UPLOAD_PATH,
                            EnumUploadImgType.USERINFO.getValue(), new File(platformResult));

                    UserSignatureDto borrowerUserSign = userSignatureService.getUserSignatureInfo(supplyChainLmjParamDTO.getUserId());
                    userSignatureService.saveSignedFile(platformResult, "借款协议-" + supplyChainLmjParamDTO.getUserId(),
                            new String[]{borrowerUserSign.getAccountId()});
                    LOGGER.info("************电子签名结束******************");
                }
            }
        }catch (Exception e){
            LOGGER.error("****************电子签名失败****************",e);
            return false;
        }finally {
            File file = new File(srcFile);
            if(file.isFile() && file.exists()){
                file.delete();
            }
            File userResultFile = new File(userResult);
            if(userResultFile.isFile() && userResultFile.exists()){
                userResultFile.delete();
            }
        }
        return true;
    }

}
