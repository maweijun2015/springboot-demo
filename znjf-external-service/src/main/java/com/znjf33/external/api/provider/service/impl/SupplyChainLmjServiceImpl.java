package com.znjf33.external.api.provider.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.znjf33.common.service.common.Constants;
import com.znjf33.common.service.common.Message;
import com.znjf33.common.utils.*;
import com.znjf33.common.utils.constant.enums.EnumUploadImgType;
import com.znjf33.external.api.dto.*;
import com.znjf33.external.api.provider.common.RemoteConstants;
import com.znjf33.external.api.provider.common.TableConstants;
import com.znjf33.external.api.provider.domain.*;
import com.znjf33.external.api.provider.mapper.SupplyChainLmjMapper;
import com.znjf33.external.api.provider.protocol.LoanProtocol;
import com.znjf33.external.api.provider.remote.LemujiAPI;
import com.znjf33.external.api.provider.remote.LemujiNotifier;
import com.znjf33.external.api.service.SupplyChainLmjService;
import com.znjf33.investment.api.dto.LMJTransferDto;
import com.znjf33.investment.api.service.BorrowUpdateService;
import com.znjf33.useraccount.api.dto.UserSignatureDto;
import com.znjf33.useraccount.api.service.UserSignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        supplyChainLmjParamDO.setFundStatus(TableConstants.ZNJF_FUND_STATUS_NO_PAY);
        supplyChainLmjParamDO.setMoneyStatus(TableConstants.ZNJF_FUND_MONEY_STATUS_NO);
        supplyChainLmjParamDO.setProcessStatus(TableConstants.ZNJF_FUND_PROCESS_STATUS_NO);
        supplyChainLmjParamDO.setChannelFrom(TableConstants.ZNJF_FUND_DATA_FROM_LMJ);
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
                    TableConstants.ZNJF_FUND_DATA_FROM_LMJ);
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
        supplyChainLmjMapper.updateZnjfFundProcessStatus(supplyChainLmjParamDTO.getUserId(),supplyChainLmjParamDTO.getLoanDrawUuid(),TableConstants.ZNJF_FUND_PROCESS_STATUS_ERROR,
                TableConstants.ZNJF_FUND_DATA_FROM_LMJ);
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
        Integer countFundsToday = supplyChainLmjMapper.countFundsToday(userId,TableConstants.ZNJF_FUND_DATA_FROM_LMJ,
                TableConstants.MONEY_STATUS_PAY);
        supplyChainLmjResultDTO.setCountFundsToday(countFundsToday);
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnfFundByOrderNo(userId,TableConstants.ZNJF_FUND_DATA_FROM_LMJ,loanDrawUuid);
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
    public boolean getReimbursementRegistration(SupplyChainLmjReimbursementParamDTO supplyChainLmjReimbursementParamDTO) {
        LOGGER.info("还款登记信息");
        SupplyChainLmjResultDO znjfLemujiPay =  supplyChainLmjMapper.getZnjfLemujiPayByUuid(supplyChainLmjReimbursementParamDTO.getLoanDrawUuid());
        if (znjfLemujiPay == null){
            return false;
        }
        SupplyChainLmjResultDO znjfFundBorrow = supplyChainLmjMapper.getZnjfFundByUserId(znjfLemujiPay.getUserId(),supplyChainLmjReimbursementParamDTO.getLoanDrawUuid());
        if (znjfFundBorrow == null){
            return false;
        }
        String number = supplyChainLmjReimbursementParamDTO.getRepaymentDataDTOList().get(0).getNumber();
        //更新还款表第三方每一笔还款唯一编号
        supplyChainLmjMapper.updateBorrowRepayment(znjfLemujiPay.getUserId(),znjfFundBorrow.getBorrowId(),number);
        //还款,翼支付付款到银行卡
        LemujiPayDo pay = getDoToPay(supplyChainLmjReimbursementParamDTO.getLoanDrawUuid(),znjfLemujiPay.getUserId(),"",
                znjfLemujiPay.getTransactionAmount(), supplyChainLmjReimbursementParamDTO.getQuanwangtongName(),
                supplyChainLmjReimbursementParamDTO.getQuanwangtongACCOUNT(),LemujiPayDo.PAY_TYPE_TO_BANKACCOUNT,
                "项目还款:"+OrderNoUtils.getSerialNumber());
        Map<String,String> params = getToPayParamsTwo(supplyChainLmjReimbursementParamDTO.getQuanwangtongYizhifuPartnerid(),pay.getTransactionAmount(),
                pay.getOrderNo(),pay.getTransactionNo());
        pay.setTransactionDesc(JSON.toJSONString(params));
        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
        LemujiNotifier.payPushPaytb(supplyChainLmjReimbursementParamDTO.getQuanwangtongYizhifuPaytb(),params,supplyChainLmjReimbursementParamDTO.getLmjApiSecretKey(),
                pay.getOrderNo(),supplyChainLmjReimbursementParamDTO.getQuanwangtongYizhifuPartnerid());
        return true;
    }

    /**
     * 获取乐木几翼支付成功与否回调
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public SupplyChainLmjResultDTO getCallbackPayStatus(SupplyChainLmjParamDTO supplyChainLmjParamDTO) {
        LOGGER.info("获取乐木几翼支付成功与否回调 externalId = {}",supplyChainLmjParamDTO.getExternalId() );
        SupplyChainLmjResultDTO supplyChainLmjResultDTO = new SupplyChainLmjResultDTO();
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfLemujiPayByOrderId(supplyChainLmjParamDTO.getExternalId());
        if(supplyChainLmjResultDO == null){
            LOGGER.info("账户不存在 externalId = {}",supplyChainLmjParamDTO.getExternalId() );
            supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_ACCOUNT_NOT_EXIST.code());
            return supplyChainLmjResultDTO;
        }
        SupplyChainLmjResultDO znjfExternalUser = supplyChainLmjMapper.getZnjfExternalUserByUserId(supplyChainLmjResultDO.getUserId());
        if (!"000000".equals(supplyChainLmjParamDTO.getPayCode())){
            LOGGER.error("getCallbackPayStatus(),乐木几回调code不是000000,code={},type={},externalId={},msg={}",supplyChainLmjParamDTO.getPayCode(),
                    supplyChainLmjParamDTO.getPayType(),supplyChainLmjParamDTO.getExternalId(),supplyChainLmjParamDTO.getPayMsg());
            if (supplyChainLmjResultDO.getRetryNum() >=3){
                //重发次数大于3次
                supplyChainLmjResultDTO.setCode(Message.ERROR_OPEN_RETRY_NUM_THREE.code());
                return supplyChainLmjResultDTO;
            }
            //更新失败状态
            supplyChainLmjMapper.updateZnjfLemujiPayStatus(LemujiPayDo.PAY_STATUS_FAIL,supplyChainLmjParamDTO.getPayMsg(),supplyChainLmjParamDTO.getExternalId());
            if ("chargeFB".equals(supplyChainLmjParamDTO.getPayType())){
                //重发充值到企业翼支付
                callWingToPayChargeFb(supplyChainLmjParamDTO,supplyChainLmjResultDO.getTransactionAmount(), supplyChainLmjParamDTO.getExternalId());
            }else if ("transfer".equals(supplyChainLmjParamDTO.getPayType())){
                //重发企业间转账
                callWingToPayTransferFail(supplyChainLmjParamDTO,supplyChainLmjResultDO, znjfExternalUser, supplyChainLmjParamDTO.getExternalId());
            }else if ("payTB".equals(supplyChainLmjParamDTO.getPayType())){
                //重发放款到银行卡
                callWingToPayPayTbFail(supplyChainLmjParamDTO, supplyChainLmjParamDTO.getExternalId(), supplyChainLmjResultDO.getTransactionAmount());
            }
            return supplyChainLmjResultDTO;
        }
        //更新znjf_lemuji_pay表状态
        supplyChainLmjMapper.updateZnjfLemujiPayStatus(LemujiPayDo.PAY_STATUS_SUCCESS,supplyChainLmjParamDTO.getPayMsg(),supplyChainLmjParamDTO.getExternalId());
        //充值接口回调
        if ("chargeFB".equals(supplyChainLmjParamDTO.getPayType())){
            //调用企业间转帐接口
            Map<String,String> params = callWingToPayTransferSuccess(supplyChainLmjParamDTO, supplyChainLmjResultDO, znjfExternalUser);
            supplyChainLmjResultDTO.setParams(params);
            supplyChainLmjResultDTO.setCode(Message.INFO_SUCCESS.code());
            return supplyChainLmjResultDTO;
        }
        //企业间转帐接口回调
        if ("transfer".equals(supplyChainLmjParamDTO.getPayType())){
            SupplyChainLmjResultDO  repaymentInfo = supplyChainLmjMapper.getRepaymentInfo(supplyChainLmjResultDO.getLoanDrawUuid());
            //调用乐木几放款接口
            callLmjLending(supplyChainLmjParamDTO, supplyChainLmjResultDO, znjfExternalUser, repaymentInfo);
        }
        //付款到银行账户接口回调
        if ("payTB".equals(supplyChainLmjParamDTO.getPayType())){
            SupplyChainLmjResultDO znjfFundByUserId = supplyChainLmjMapper.getZnjfFundByUserId(supplyChainLmjResultDO.getUserId(),
                    supplyChainLmjResultDO.getLoanDrawUuid());
            supplyChainLmjMapper.updateBorrowRepaymentStatus(supplyChainLmjResultDO.getUserId(),znjfFundByUserId.getBorrowId());
            supplyChainLmjMapper.updateZnjfFundRepayStatus(supplyChainLmjResultDO.getUserId(),supplyChainLmjResultDO.getLoanDrawUuid(),
                    TableConstants.MONEY_STATUS_REPAYING);
            LOGGER.info("付款到银行账户接口回调成功,externalId={},userId={}",supplyChainLmjParamDTO.getExternalId(),
                    supplyChainLmjResultDO.getUserId());
        }
        supplyChainLmjResultDTO.setCode(Message.INFO_SUCCESS.code());
        return supplyChainLmjResultDTO;
    }

    /**
     * 翼支付充值接口-重发
     */
    private void callWingToPayChargeFb(SupplyChainLmjParamDTO supplyChainLmjParamDTO,long transactionAmount,String orderNo){
        Map<String,String> params = getToPayParamsTwo(supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid(),transactionAmount,
                orderNo,OrderNoUtils.getSerialNumber());
        LemujiNotifier.payPushChargeFb(supplyChainLmjParamDTO.getQuanwangtongYizhifuChargefb(),params,supplyChainLmjParamDTO.getLmjApiSecretKey(),
                supplyChainLmjParamDTO.getExternalId(),supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid());
        LOGGER.info("翼支付充值接口-重发回调成功,externalId={}",supplyChainLmjParamDTO.getExternalId());
    }

    /**
     * 翼支付放款到银行卡接口-重发
     */
    private void callWingToPayPayTbFail(SupplyChainLmjParamDTO supplyChainLmjParamDTO,String orderNo,long transactionAmount){
        //翼支付付款到银行卡
        Map<String,String> params = getToPayParamsTwo(supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid(),transactionAmount,
                orderNo,OrderNoUtils.getSerialNumber());
        LemujiNotifier.payPushPaytb(supplyChainLmjParamDTO.getQuanwangtongYizhifuPaytb(),params,supplyChainLmjParamDTO.getLmjApiSecretKey(),
                orderNo,supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid());
        LOGGER.info("翼支付放款到银行卡接口-重发回调成功,externalId={}",supplyChainLmjParamDTO.getExternalId());
    }

    /**
     * 翼支付企业间转账接口-第一次
     */
    private Map<String,String> callWingToPayTransferSuccess(SupplyChainLmjParamDTO supplyChainLmjParamDTO,SupplyChainLmjResultDO supplyChainLmjResultDO,
                                       SupplyChainLmjResultDO znjfExternalUser){
        LemujiPayDo pay =  getDoToPay(supplyChainLmjResultDO.getLoanDrawUuid(),supplyChainLmjResultDO.getUserId(),znjfExternalUser.getExtMerchants(),
                supplyChainLmjResultDO.getTransactionAmount(), "","",LemujiPayDo.PAY_TYPE_TO_FINANCER,"");
        Map<String,String>  params = getToPayParams(supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid(),pay.getPayeeMerchantNo(),
                pay.getTransactionAmount(), pay.getOrderNo(),pay.getTransactionNo(),pay.getTransactionTime());
        pay.setTransactionDesc(JSON.toJSONString(params));
        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
        LOGGER.info("翼支付充值数据保存成功,externalId={}",supplyChainLmjParamDTO.getExternalId());
        return params;
    }


    /**
     * 翼支付企业间转账接口-第一次
     * @param supplyChainLmjParamDTO
     */
    @Override
    public void sendPayTransfer(SupplyChainLmjParamDTO supplyChainLmjParamDTO){
        LemujiNotifier.payPushTransfer(supplyChainLmjParamDTO.getQuanwangtongYizhifuTransfer(),supplyChainLmjParamDTO.getParams(),supplyChainLmjParamDTO.getLmjApiSecretKey(),
                supplyChainLmjParamDTO.getExternalId(),supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid());
        LOGGER.info("充值接口回调成功,externalId={}",supplyChainLmjParamDTO.getExternalId());
    }

//    /**
//     * 翼支付企业间转账接口-第一次
//     */
//    private void callWingToPayTransferSuccess(SupplyChainLmjParamDTO supplyChainLmjParamDTO,SupplyChainLmjResultDO supplyChainLmjResultDO,
//                                              SupplyChainLmjResultDO znjfExternalUser){
//        Map<String,String>  params = callWingToPayTransferSuccessToParams(supplyChainLmjParamDTO,supplyChainLmjResultDO,znjfExternalUser);
//        LemujiNotifier.payPushTransfer(supplyChainLmjParamDTO.getQuanwangtongYizhifuTransfer(),params,supplyChainLmjParamDTO.getLmjApiSecretKey(),
//                supplyChainLmjParamDTO.getExternalId(),supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid());
//        LOGGER.info("充值接口回调成功,externalId={},userId={}",supplyChainLmjParamDTO.getExternalId(),
//                supplyChainLmjResultDO.getUserId());
//    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public Map<String,String> callWingToPayTransferSuccessToParams(SupplyChainLmjParamDTO supplyChainLmjParamDTO,SupplyChainLmjResultDO supplyChainLmjResultDO,
//                                                      SupplyChainLmjResultDO znjfExternalUser){
//        LemujiPayDo pay =  getDoToPay(supplyChainLmjResultDO.getLoanDrawUuid(),supplyChainLmjResultDO.getUserId(),znjfExternalUser.getExtMerchants(),
//                supplyChainLmjResultDO.getTransactionAmount(), "","",LemujiPayDo.PAY_TYPE_TO_FINANCER,"");
//        Map<String,String>  params = getToPayParams(supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid(),pay.getPayeeMerchantNo(),
//                pay.getTransactionAmount(),
//                pay.getOrderNo(),pay.getTransactionNo(),pay.getTransactionTime());
//        pay.setTransactionDesc(JSON.toJSONString(params));
//        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
//        return params;
//    }

    /**
     * 翼支付企业间转账接口-重发
     */
    private void callWingToPayTransferFail(SupplyChainLmjParamDTO supplyChainLmjParamDTO,SupplyChainLmjResultDO supplyChainLmjResultDO,
                                       SupplyChainLmjResultDO znjfExternalUser,String orderNo){
        Map<String,String>  params = getToPayParams(supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid(),znjfExternalUser.getExtMerchants(),
                supplyChainLmjResultDO.getTransactionAmount(),orderNo,OrderNoUtils.getSerialNumber(),DateUtil.getNow());
        LemujiNotifier.payPushTransfer(supplyChainLmjParamDTO.getQuanwangtongYizhifuTransfer(),params,supplyChainLmjParamDTO.getLmjApiSecretKey(),
                supplyChainLmjParamDTO.getExternalId(),supplyChainLmjParamDTO.getQuanwangtongYizhifuPartnerid());
        LOGGER.info("翼支付企业间转账接口-重发回调成功,externalId={},userId={}",supplyChainLmjParamDTO.getExternalId(),
                supplyChainLmjResultDO.getUserId());
    }

    /**
     * 翼支付转换
     */
    private LemujiPayDo getDoToPay(String LoanDrawUuid,int userId,String extMerchants,long transactionAmount,
                                   String payeeAccountName,String payeeAccountNo,int transactionType,String remark) {
        LemujiPayDo pay = new LemujiPayDo();
        pay.setOrderNo(OrderNoUtils.getSerialNumber());
        pay.setTransactionNo(OrderNoUtils.getSerialNumber());
        pay.setLoanDrawUuid(LoanDrawUuid);
        pay.setUserId(userId);
        pay.setPayeeMerchantNo(extMerchants);
        pay.setTransactionAmount(transactionAmount);
        pay.setTransactionTime(DateUtil.getNow());
        pay.setTransactionType(transactionType);

        pay.setPayeeAccountName(payeeAccountName);
        pay.setPayeeAccountNo(payeeAccountNo);
        pay.setRemark(remark);
        pay.setPayStatus(0);
        return pay;
    }

    /**
     * 转换翼支付参数2
     */
    private Map<String,String>  getToPayParamsTwo(String partnerId,long transactionAmount,
                                                  String orderNo,String transactionNo) {
        Map<String,String> params = new HashMap<>();
        params.put("PARTNERID",partnerId);
        params.put("externalId",orderNo);
        params.put("transactionAmount", String.valueOf(transactionAmount));
        params.put("reqSeq",transactionNo);
        params.put("trsMemo","");
        return params;
    }

    /**
     * 转换翼支付参数
     */
    private Map<String,String>  getToPayParams(String partnerId,String payeeMerchantNo,long transactionAmount,
                                               String orderNo,String transactionNo,Date transactionTime) {
        Map<String,String> params = new HashMap<>();
        params.put("PARTNERID",partnerId);
        params.put("PAYEECODE",payeeMerchantNo);
        params.put("AREACODE","330000");
        params.put("TXNAMOUNT", String.valueOf(transactionAmount));
        params.put("ORDERSEQ",orderNo);
        params.put("TRANSSEQ",transactionNo);
        params.put("TRADETIME",DateUtil.dateStr3(transactionTime));
        params.put("MARK1","");
        return params;
    }

    /**
     * 调用乐木几放款接口
     */
    private void callLmjLending(SupplyChainLmjParamDTO supplyChainLmjParamDTO,SupplyChainLmjResultDO supplyChainLmjResultDO,
                                SupplyChainLmjResultDO znjfExternalUser,SupplyChainLmjResultDO  repaymentInfo){
        try {
            LemujiLoanNotifierDTO notifierModel = new LemujiLoanNotifierDTO();
            notifierModel.setLoanAppUuid(znjfExternalUser.getLoanAppUuid());
            notifierModel.setLoanDrawUuid(supplyChainLmjResultDO.getLoanDrawUuid());
            notifierModel.setReqFlowNo(OrderNoUtils.getSerialNumber());
            notifierModel.setStatus("YES");
            notifierModel.setComment("ok");

            LemujiRepaymentDTO repayment = new LemujiRepaymentDTO();
            repayment.setRepaymentAmount(String.format("%.2f",repaymentInfo.getRepaymentAccount()) );
            repayment.setRepaymentDate(DateUtil.dateStr7(repaymentInfo.getRepaymentTime()));
            repayment.setRepaymentPrincipal(String.format("%.2f",repaymentInfo.getCapital()) );
            BigDecimal manageFee = new BigDecimal(repaymentInfo.getManageFee());
            BigDecimal interest = new BigDecimal(repaymentInfo.getInterest());
            repayment.setRepaymentInterest(String.format("%.2f",manageFee.add(interest).doubleValue()) );
            repayment.setRepaymentIssue("1");

            List<LemujiRepaymentDTO> repayments = new ArrayList<>();
            repayments.add(repayment);
            notifierModel.setRepaymentList(repayments);
            LemujiNotifier.push(LemujiAPI.LOAN_SUCCESS_NOTIFIER,notifierModel,RemoteConstants.QUANWANGTONG_LENDING_TO_INFORM,
                    supplyChainLmjParamDTO.getLmjApiSecretKey(),supplyChainLmjParamDTO.getLmjUrl(),supplyChainLmjParamDTO.getLmjIdentifyPartnerid());
            LOGGER.info("企业间转帐接口回调成功,externalId={},userId={}",supplyChainLmjParamDTO.getExternalId(),
                    supplyChainLmjResultDO.getUserId());
        } catch (Exception ex) {
            LOGGER.error("放款 - 推送资金状态失败：" + ex.getMessage());
        }
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
        String dealNo = "ZJF" + DateUtil.getTimeYear(nowTime) + "JD-TA-6-" + supplyChainLmjParamDTO.getUserId() + "-"
                + DateUtil.getTimeMonth(nowTime) + DateUtil.getTimeDay(nowTime)+ borrowInfo.getCountFundsToday();
        String dealHeadNo = "ZJF" + DateUtil.getTimeYear(nowTime) + "JD-TA-6-" + supplyChainLmjParamDTO.getUserId() + "-"
                + DateUtil.getTimeMonth(nowTime) + DateUtil.getTimeDay(nowTime)+ borrowInfo.getCountFundsToday();
        supplyChainLmjParamDTO.setAgreementNo(dealNo);
        supplyChainLmjParamDTO.setDealHeadNo(dealHeadNo);
        supplyChainLmjParamDTO.setLoanAmount(borrowInfo.getAmountApplied());
        supplyChainLmjParamDTO.setLoanApr(12d);
        supplyChainLmjParamDTO.setLoanStartDate(DateUtil.dateStr2(nowTime));
        supplyChainLmjParamDTO.setLoanEndDate(DateUtil.dateStr2(entrustEndDate));
        supplyChainLmjParamDTO.setDuration(borrowInfo.getDuration());
        supplyChainLmjParamDTO.setLoanUse(Constants.TAIAN1_BORROW_USE);
        supplyChainLmjParamDTO.setEntrustEndDate(entrustEndDate);
    }



}



