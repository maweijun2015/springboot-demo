package com.znjf33.external.api.provider.service.impl;

import com.znjf33.external.api.dto.OrderinfoDTO;
import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjReimbursementParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjResultDTO;
import com.znjf33.external.api.provider.common.TableConstants;
import com.znjf33.external.api.provider.domain.SupplyChainLmjParamDO;
import com.znjf33.external.api.provider.domain.SupplyChainLmjResultDO;
import com.znjf33.external.api.provider.domain.ZnjfFundAttachmentDO;
import com.znjf33.external.api.provider.mapper.SupplyChainLmjMapper;
import com.znjf33.external.api.provider.protocol.LoanProtocol;
import com.znjf33.external.api.service.SupplyChainLmjService;
import com.znjf33.common.service.common.OpenMessage;
import com.znjf33.common.utils.DateUtil;
import com.znjf33.common.utils.HclientFileUtil;
import com.znjf33.common.utils.OrderNoUtils;
import com.znjf33.common.utils.StringUtil;
import com.znjf33.common.utils.constant.enums.EnumUploadImgType;
import com.znjf33.useraccount.api.dto.UserSignatureDto;
import com.znjf33.useraccount.api.service.UserSignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
@Service
public class SupplyChainLmjServiceImpl implements SupplyChainLmjService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyChainLmjServiceImpl.class);

    @Autowired
    private SupplyChainLmjMapper supplyChainLmjMapper;

    @Autowired
    private UserSignatureService userSignatureService;

    @Autowired
    private LoanProtocol loanProtocol;

    /**
     * 支用申请
     * @return
     */
    @Transactional
    @Override
    public SupplyChainLmjResultDTO getApplyLoan(SupplyChainLmjParamDTO supplyChainLmjParamDTO) {
        LOGGER.info("支用申请-getCheckApplyLoan(): userid=" + supplyChainLmjParamDTO.getMemberId() + " loanAppUuid="
                + supplyChainLmjParamDTO.getLoanAppUuid() + " loanDrawUuid=" + supplyChainLmjParamDTO.getLoanDrawUuid());
        SupplyChainLmjResultDTO supplyChainLmjResultDTO = new SupplyChainLmjResultDTO();
        SupplyChainLmjParamDO supplyChainLmjParamDO = new SupplyChainLmjParamDO();
        getApplyLoanDtoToDo(supplyChainLmjParamDTO,supplyChainLmjParamDO);
        //判断该用户是否授权
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfExternalUserByUserIdAndUuid(supplyChainLmjParamDO.getMemberId(),
                supplyChainLmjParamDO.getLoanAppUuid());
        if (supplyChainLmjResultDO == null) {
            supplyChainLmjResultDTO.setCode(OpenMessage.ERROR_LINES_REFUSED_APPLY.code());
            return supplyChainLmjResultDTO;
        }
        Integer znjfFundForSame = supplyChainLmjMapper.getZnjfFundForSame(supplyChainLmjParamDO.getLoanDrawUuid());
        if (znjfFundForSame > 0){
            supplyChainLmjResultDTO.setCode(OpenMessage.ERROR_LINES_REFUSED_APPLY.code());
            return supplyChainLmjResultDTO;
        }
        supplyChainLmjParamDO.setUserId(supplyChainLmjResultDO.getUserId());

        Integer userCount = supplyChainLmjMapper.countUserByUserInfo(supplyChainLmjResultDO.getUserId());
        if (userCount <= 0){
            supplyChainLmjResultDTO.setCode(OpenMessage.ERROR_LINES_REFUSED_APPLY.code());
            return supplyChainLmjResultDTO;
        }

        //查询信用总额度
        Float creditAmount = supplyChainLmjMapper.getCreditAmountByUserId(supplyChainLmjParamDO.getUserId());
        //查询信用使用额度
        Float usedCreditAmount = supplyChainLmjMapper.getUsedCreditAmount(supplyChainLmjParamDO.getUserId());

        BigDecimal creditAmountBig = new BigDecimal(Float.toString(creditAmount));
        BigDecimal usedCreditAmountBig = new BigDecimal(Float.toString(usedCreditAmount));
        BigDecimal remainingCreditAmount = creditAmountBig.subtract(usedCreditAmountBig);

        if (remainingCreditAmount.floatValue() <  supplyChainLmjParamDO.getDrawAmount()){
            supplyChainLmjResultDTO.setCode(OpenMessage.ERROR_LINES_NO_MATCHING.code());
            return supplyChainLmjResultDTO;
        }
        //支用申请信息录入
        supplyChainLmjParamDO.setDealNo(OrderNoUtils.getSerialNumber());
        supplyChainLmjParamDO.setFundStatus(TableConstants.znjf_fund_status_no_pay);
        supplyChainLmjParamDO.setMoneyStatus(TableConstants.znjf_fund_money_status_no);
        supplyChainLmjParamDO.setProcessStatus(TableConstants.znjf_fund_process_status_no);
        supplyChainLmjParamDO.setChannelFrom(TableConstants.znjf_fund_data_from_lmj);
        supplyChainLmjParamDO.setRepaymentDateApplied(DateUtil.rollDay(
                DateUtil.getDateByFullDateStrOther(supplyChainLmjParamDO.getLoanDrawDate()),supplyChainLmjParamDO.getPeriod()));
        supplyChainLmjMapper.saveApplyLoan(supplyChainLmjParamDO);
        //支用申请附件信息录入
        List<ZnjfFundAttachmentDO> znjfFundAttachmentDOList = listZnjfFundAttachment(supplyChainLmjParamDTO.getOrderinfoDTO(),
                supplyChainLmjParamDO);
        supplyChainLmjMapper.saveApplyLoanAttachment(znjfFundAttachmentDOList);
        return supplyChainLmjResultDTO;
    }

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    @Override
    public boolean signatureFileForLmj(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
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

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public SupplyChainLmjResultDTO getUserInfo(String loanAppUuid, String loanDrawUuid) {
        SupplyChainLmjResultDTO supplyChainLmjResultDTO = new SupplyChainLmjResultDTO();
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getUserInfo(loanAppUuid);
        if (supplyChainLmjResultDO == null){
            return null;
        }
        Integer fundExist = supplyChainLmjMapper.getFundExist(supplyChainLmjResultDO.getUserId(),loanDrawUuid);
        if (fundExist <= 0){
            return null;
        }
        supplyChainLmjResultDTO.setUserId(supplyChainLmjResultDO.getUserId());
        supplyChainLmjResultDTO.setMobilePhone(supplyChainLmjResultDO.getMobilePhone());
        return supplyChainLmjResultDTO;
    }

    /**
     * 还款登记信息
     * @return
     */
    @Transactional
    @Override
    public void getReimbursementRegistration(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO) {
        LOGGER.info("还款登记信息");
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getUserInfo(supplyChainLmjReimbursementParamDTO.getLoanAppUuid());
        supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfFundByUserId(supplyChainLmjResultDO.getUserId());
        supplyChainLmjMapper.updateBorrowRepayment(supplyChainLmjResultDO.getUserId(),supplyChainLmjResultDO.getBorrowId());
        supplyChainLmjMapper.updateZnjfFundStatus(supplyChainLmjResultDO.getUserId(),supplyChainLmjReimbursementParamDTO.getLoanDrawUuid(),
                TableConstants.MONEY_STATUS_REPAYING);
    }


    private List<ZnjfFundAttachmentDO> listZnjfFundAttachment(List<OrderinfoDTO> orderinfoDTOs,
                                                              SupplyChainLmjParamDO supplyChainLmjParamDO ){
        if (orderinfoDTOs == null || orderinfoDTOs.size() == 0){
            return null;
        }
        List<ZnjfFundAttachmentDO> znjfFundAttachmentDOList = new ArrayList<>();
        for (int i=0;i<orderinfoDTOs.size();i++){
            ZnjfFundAttachmentDO znjfFundAttachmentDO = new ZnjfFundAttachmentDO();
            znjfFundAttachmentDO.setOrderNo(supplyChainLmjParamDO.getLoanDrawUuid());
            znjfFundAttachmentDO.setDealNo(supplyChainLmjParamDO.getDealNo());
            znjfFundAttachmentDO.setGoodsNumber(orderinfoDTOs.get(i).getGoodsNumber());
            znjfFundAttachmentDO.setGoodsName(orderinfoDTOs.get(i).getGoodsName());
            znjfFundAttachmentDOList.add(znjfFundAttachmentDO);
        }
        return znjfFundAttachmentDOList;
    }

    private void getApplyLoanDtoToDo(SupplyChainLmjParamDTO supplyChainLmjParamDTO,
                                     SupplyChainLmjParamDO supplyChainLmjParamDO){
        supplyChainLmjParamDO.setLoanAppUuid(supplyChainLmjParamDTO.getLoanAppUuid());
        supplyChainLmjParamDO.setLoanDrawUuid(supplyChainLmjParamDTO.getLoanDrawUuid());
        supplyChainLmjParamDO.setMemberId(supplyChainLmjParamDTO.getMemberId());
        supplyChainLmjParamDO.setConsignee(supplyChainLmjParamDTO.getConsignee());
        supplyChainLmjParamDO.setConsigneeAddress(supplyChainLmjParamDTO.getConsigneeAddress());
        supplyChainLmjParamDO.setConsigneePhone(supplyChainLmjParamDTO.getConsigneePhone());
        supplyChainLmjParamDO.setDrawAmount(supplyChainLmjParamDTO.getDrawAmount());
        supplyChainLmjParamDO.setLoanDrawDate(supplyChainLmjParamDTO.getLoanDrawDate());
        supplyChainLmjParamDO.setMemberName(supplyChainLmjParamDTO.getMemberName());
        supplyChainLmjParamDO.setMemberNo(supplyChainLmjParamDTO.getMemberNo());
        supplyChainLmjParamDO.setPeriod(supplyChainLmjParamDTO.getPeriod());
        supplyChainLmjParamDO.setDuration(supplyChainLmjParamDTO.getDuration());
    }







}



