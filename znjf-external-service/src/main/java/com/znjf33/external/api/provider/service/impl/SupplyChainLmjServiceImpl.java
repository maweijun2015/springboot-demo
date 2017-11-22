package com.znjf33.external.api.provider.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.znjf33.common.service.common.Constants;
import com.znjf33.common.service.common.Message;
import com.znjf33.common.utils.DateUtil;
import com.znjf33.common.utils.HclientFileUtil;
import com.znjf33.common.utils.OrderNoUtils;
import com.znjf33.common.utils.StringUtil;
import com.znjf33.common.utils.constant.enums.EnumUploadImgType;
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
import com.znjf33.investment.api.dto.LMJTransferDto;
import com.znjf33.investment.api.service.BorrowUpdateService;
import com.znjf33.useraccount.api.dto.UserSignatureDto;
import com.znjf33.useraccount.api.service.UserSignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



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

    @Autowired
    private BorrowUpdateService borrowUpdateService;

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
            supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_LINES_REFUSED_APPLY.code());
            return supplyChainLmjResultDTO;
        }
        Integer znjfFundForSame = supplyChainLmjMapper.getZnjfFundForSame(supplyChainLmjParamDO.getLoanDrawUuid());
        if (znjfFundForSame > 0){
            supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_LINES_REFUSED_APPLY.code());
            return supplyChainLmjResultDTO;
        }
        supplyChainLmjParamDO.setUserId(supplyChainLmjResultDO.getUserId());

        Integer userCount = supplyChainLmjMapper.countUserByUserInfo(supplyChainLmjResultDO.getUserId());
        if (userCount <= 0){
            supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_LINES_REFUSED_APPLY.code());
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
            supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_LINES_NO_MATCHING.code());
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
        if(znjfFundAttachmentDOList !=null){
            supplyChainLmjMapper.saveApplyLoanAttachment(znjfFundAttachmentDOList);
        }
        return supplyChainLmjResultDTO;
    }

    /**
     * 签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    @Override
    public void signatureFileForLmj(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
        try {
            LOGGER.info(supplyChainLmjParamDTO.getUserId() + " 签约开始" + supplyChainLmjParamDTO.getLoanDrawUuid());
            getBorrowSigningInfo(supplyChainLmjParamDTO);
            //E签宝签约
            boolean signatureFileForLmj = signatureFileForLmjSignature(supplyChainLmjParamDTO);
            if (!signatureFileForLmj){
                LOGGER.error(supplyChainLmjParamDTO.getLoanDrawUuid() + " 签约失败 signatureFileForLmj == " +signatureFileForLmj);
                updateZnjfFundProcessStatus(supplyChainLmjParamDTO);
                return;
            }
            LOGGER.info("e签宝签约成功");
            //标的生成
            LMJTransferDto lmjTransferDto = new LMJTransferDto();
            lmjTransferDto.setDealNo(supplyChainLmjParamDTO.getAgreementNo());
            lmjTransferDto.setAccount(supplyChainLmjParamDTO.getLoanAmount());
            lmjTransferDto.setEntrustApr(12d);
            lmjTransferDto.setEntrustEndDate(supplyChainLmjParamDTO.getEntrustEndDate());
            lmjTransferDto.setLoanDuration(supplyChainLmjParamDTO.getDuration());
            lmjTransferDto.setBorrowUse(Constants.TAIAN1_BORROW_USE);

            LOGGER.info("标的生成开始");
            borrowUpdateService.addQuanwangtongBorrow(lmjTransferDto,supplyChainLmjParamDTO.getLoanDrawUuid(), Constants.SUPPLY_CHAIN_CHANNEL_FROM);
            LOGGER.info("标的生成成功");
            //状态更新
            LOGGER.info("************更新fund表状态******************");
            supplyChainLmjMapper.updateZnjfFundStatus(supplyChainLmjParamDTO.getUserId(),supplyChainLmjParamDTO.getLoanDrawUuid(),TableConstants.MONEY_STATUS_PAY,
                    TableConstants.znjf_fund_data_from_lmj);
            LOGGER.info("状态更新成功");
        }catch (Exception e){
            LOGGER.error(supplyChainLmjParamDTO.getUserId() + " 签约失败" + supplyChainLmjParamDTO.getLoanDrawUuid());
            updateZnjfFundProcessStatus(supplyChainLmjParamDTO);
        }
    }

    /**
     * 更新fund状态
     * @param supplyChainLmjParamDTO
     * @return
     */
    @Override
    public void updateZnjfFundProcessStatus(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
        LOGGER.info("更新fund状态 loanDrawUuid = " + supplyChainLmjParamDTO.getLoanDrawUuid());
        supplyChainLmjMapper.updateZnjfFundProcessStatus(supplyChainLmjParamDTO.getUserId(),supplyChainLmjParamDTO.getLoanDrawUuid(),TableConstants.znjf_fund_process_status_error,
                TableConstants.znjf_fund_data_from_lmj);
    }

    /**
     * E签宝签约
     * @param supplyChainLmjParamDTO
     * @return
     */
    private boolean signatureFileForLmjSignature(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
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

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public SupplyChainLmjResultDTO getUserInfo(String loanAppUuid, String loanDrawUuid) {
        LOGGER.info("************获取用户信息******************");
        SupplyChainLmjResultDTO supplyChainLmjResultDTO = new SupplyChainLmjResultDTO();
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getUserInfo(loanAppUuid);
        if (supplyChainLmjResultDO == null){
            return null;
        }
        Integer fundExist = supplyChainLmjMapper.getFundExist(supplyChainLmjResultDO.getUserId(),loanDrawUuid);
        if (fundExist <= 0){
            return null;
        }
        getUserInfoDoToDto(supplyChainLmjResultDTO,supplyChainLmjResultDO);
        return supplyChainLmjResultDTO;
    }

    /**
     * 获取标的信息
     * @return
     */
    private SupplyChainLmjResultDTO getBorrowInfo(Integer userId,String loanDrawUuid) {
        LOGGER.info("************获取标的信息******************");
        SupplyChainLmjResultDTO supplyChainLmjResultDTO = new SupplyChainLmjResultDTO();
        Integer countFundsToday = supplyChainLmjMapper.countFundsToday(userId,TableConstants.znjf_fund_data_from_lmj,
                TableConstants.MONEY_STATUS_PAY);
        supplyChainLmjResultDTO.setCountFundsToday(countFundsToday);
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnfFundByOrderNo(userId,TableConstants.znjf_fund_data_from_lmj,loanDrawUuid);
        if (supplyChainLmjResultDO == null){
            return null;
        }
        supplyChainLmjResultDTO.setAmountApplied(supplyChainLmjResultDO.getAmountApplied());
        supplyChainLmjResultDTO.setDuration(supplyChainLmjResultDO.getDuration());
        return supplyChainLmjResultDTO;
    }

    /**
     * 还款登记信息
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void getReimbursementRegistration(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO) {
        LOGGER.info("还款登记信息");
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getUserInfo(supplyChainLmjReimbursementParamDTO.getLoanAppUuid());
        Integer userId = supplyChainLmjResultDO.getUserId();
        supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfFundByUserId(userId,supplyChainLmjReimbursementParamDTO.getLoanDrawUuid());
        String number = supplyChainLmjReimbursementParamDTO.getRepaymentDataDTOList().get(0).getNumber();
        supplyChainLmjMapper.updateBorrowRepayment(userId,supplyChainLmjResultDO.getBorrowId(),number);
        supplyChainLmjMapper.updateZnjfFundRepayStatus(userId,supplyChainLmjReimbursementParamDTO.getLoanDrawUuid(),
                TableConstants.MONEY_STATUS_REPAYING);
    }

    /**
     * 获取乐木几翼支付成功与否回调
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void getCallbackPayStatus(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO) {
        LOGGER.info("获取乐木几翼支付成功与否回调");
        //更新znjf_lemuji_pay表状态

        //调用企业间转帐接口
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

    private void getUserInfoDoToDto(SupplyChainLmjResultDTO supplyChainLmjResultDTO,
                                    SupplyChainLmjResultDO supplyChainLmjResultDO){
        supplyChainLmjResultDTO.setUserId(supplyChainLmjResultDO.getUserId());
        supplyChainLmjResultDTO.setMobilePhone(supplyChainLmjResultDO.getMobilePhone());
        supplyChainLmjResultDTO.setFrdbName(supplyChainLmjResultDO.getFrdbName());
        supplyChainLmjResultDTO.setRealName(supplyChainLmjResultDO.getRealName());
    }

    private void getBorrowSigningInfo(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
        SupplyChainLmjResultDTO borrowInfo = getBorrowInfo(supplyChainLmjParamDTO.getUserId(),supplyChainLmjParamDTO.getLoanDrawUuid());

        Date nowTime = DateUtil.getNow();
        Date entrustEndDate = DateUtil.rollDay(nowTime, borrowInfo.getDuration());
        String dealNo = "ZJF" + DateUtil.getTimeYear(nowTime) + "JD-TA-6" + supplyChainLmjParamDTO.getUserId() + "-"
                + DateUtil.getTimeMonth(nowTime) + DateUtil.getTimeDay(nowTime)+ borrowInfo.getCountFundsToday();
        supplyChainLmjParamDTO.setAgreementNo(dealNo);
        supplyChainLmjParamDTO.setLoanAmount(borrowInfo.getAmountApplied());
        supplyChainLmjParamDTO.setLoanApr(12d);
        supplyChainLmjParamDTO.setLoanStartDate(DateUtil.dateStr2(nowTime));
        supplyChainLmjParamDTO.setLoanEndDate(DateUtil.dateStr2(entrustEndDate));
        supplyChainLmjParamDTO.setDuration(borrowInfo.getDuration());
        supplyChainLmjParamDTO.setLoanUse(Constants.TAIAN1_BORROW_USE);
        supplyChainLmjParamDTO.setEntrustEndDate(entrustEndDate);
    }



}



