package com.znjf33.external.api.provider.service;

import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjResultDTO;
import com.znjf33.external.api.provider.config.JunitBase;
import com.znjf33.external.api.service.SupplyChainLmjService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author maweijun
 * @description
 * @create 17/11/27
 */
public class SupplyChainLmjServiceTest extends JunitBase {

    @Autowired
    private SupplyChainLmjService supplyChainLmjService;

    /**
     * 获取乐木几翼支付成功与否回调-充值接口回调
     */
    @Test
    public void getCallbackPayStatus(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("000000");
        supplyChainLmjParamDTO.setPayType("chargeFB");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuTransfer("http://www.lemuji.com/qwtys/api/bestpay/transfer_ZNJF.php");
        supplyChainLmjParamDTO.setLmjApiSecretKey("77ed904243c2fde0fb309252e98a960c");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuPartnerid("e40dfb3c25fa31ce3a226695414cbbc3");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

    /**
     * 获取乐木几翼支付成功与否回调-充值接口回调-失败
     */
    @Test
    public void getCallbackPayStatusFail(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("-1");
        supplyChainLmjParamDTO.setPayType("chargeFB");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuChargefb("http://www.lemuji.com/qwtys/api/bestpay/chargeFB_ZNJF.php");
        supplyChainLmjParamDTO.setLmjApiSecretKey("77ed904243c2fde0fb309252e98a960c");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuPartnerid("e40dfb3c25fa31ce3a226695414cbbc3");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

    /**
     * 获取乐木几翼支付成功与否回调-企业间转帐接口回调
     */
    @Test
    public void getCallbackPayStatusTransfer(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("000000");
        supplyChainLmjParamDTO.setPayType("transfer");
        supplyChainLmjParamDTO.setLmjIdentifyPartnerid("e40dfb3c25fa31ce3a226695414cbbc3");
        supplyChainLmjParamDTO.setLmjApiSecretKey("77ed904243c2fde0fb309252e98a960c");
        supplyChainLmjParamDTO.setLmjUrl("http://www.lemuji.com/test_qwtys/api/znjf/znjf.php");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

    /**
     * 获取乐木几翼支付成功与否回调-企业间转帐接口回调-失败
     */
    @Test
    public void getCallbackPayStatusTransferFail(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("-1");
        supplyChainLmjParamDTO.setPayType("transfer");
        supplyChainLmjParamDTO.setLmjApiSecretKey("77ed904243c2fde0fb309252e98a960c");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuTransfer("http://www.lemuji.com/qwtys/api/bestpay/transfer_ZNJF.php");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuPartnerid("e40dfb3c25fa31ce3a226695414cbbc3");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

    /**
     * 获取乐木几翼支付成功与否回调-付款到银行账户接口回调
     */
    @Test
    public void getCallbackPayStatusPayTB(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("000000");
        supplyChainLmjParamDTO.setPayType("payTB");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

    /**
     * 获取乐木几翼支付成功与否回调-付款到银行账户接口回调-失败
     */
    @Test
    public void getCallbackPayStatusPayTBFail(){
        SupplyChainLmjParamDTO supplyChainLmjParamDTO = new SupplyChainLmjParamDTO();
        supplyChainLmjParamDTO.setExternalId("1711271313838686");
        supplyChainLmjParamDTO.setPayCode("-1");
        supplyChainLmjParamDTO.setPayType("payTB");
        supplyChainLmjParamDTO.setLmjApiSecretKey("77ed904243c2fde0fb309252e98a960c");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuPaytb("http://www.lemuji.com/qwtys/api/bestpay/payTB_ZNJF.php");
        supplyChainLmjParamDTO.setQuanwangtongYizhifuPartnerid("e40dfb3c25fa31ce3a226695414cbbc3");
        SupplyChainLmjResultDTO b = supplyChainLmjService.getCallbackPayStatus(supplyChainLmjParamDTO);
        Assert.assertNotNull(b);
    }

}
