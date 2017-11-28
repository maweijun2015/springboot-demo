package com.znjf33.external.api.provider.dao;

import com.alibaba.fastjson.JSON;
import com.znjf33.common.utils.DateUtil;
import com.znjf33.common.utils.OrderNoUtils;
import com.znjf33.external.api.provider.common.TableConstants;
import com.znjf33.external.api.provider.config.JunitBase;
import com.znjf33.external.api.provider.domain.LemujiPayDo;
import com.znjf33.external.api.provider.domain.SupplyChainLmjResultDO;
import com.znjf33.external.api.provider.mapper.SupplyChainLmjMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maweijun
 * @description
 * @create 17/11/21
 */
public class SupplyChainLmjDaoTest extends JunitBase{

    @Autowired
    private SupplyChainLmjMapper supplyChainLmjMapper;

    /**
     * 查询用户是否授权
     */
    @Test
    public void getZnjfExternalUserByUserIdAndUuid(){
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfExternalUserByUserIdAndUuid(81606,
                "1710271310001272");
                Assert.assertNotNull(supplyChainLmjResultDO);
    }

    /**
     * 查询交易详情
     */
    @Test
    public void getZnjfLemujiPayByUuid(){
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfLemujiPayByUuid("LMJZY201711021523557709");
        Assert.assertNotNull(supplyChainLmjResultDO);
    }

    /**
     * 查询标信息
     */
    @Test
    public void getZnjfFundByUserId(){
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfFundByUserId(902,"LMJZY201711021523557709");
        Assert.assertNotNull(supplyChainLmjResultDO);
    }

    /**
     * 更新还款表第三方每一笔还款唯一编号
     */
    @Test
    public void updateBorrowRepayment(){
        String number = "12345678";
        supplyChainLmjMapper.updateBorrowRepayment(902,23473l,number);
        Assert.assertTrue(true);
    }

    /**
     * 保存ZnjfLemujiPay表-翼支付充值接口
     */
    @Test
    public void saveZnjfLemujiPayFB(){
        LemujiPayDo pay = new LemujiPayDo();
        pay.setUserId(902);
        pay.setOrderNo(OrderNoUtils.getSerialNumber());
        pay.setLoanDrawUuid("LMJZY201711021523557709");
        pay.setTransactionNo(OrderNoUtils.getSerialNumber());
        pay.setPayeeAccountName("杭州浙农互联网金融服务有限公司");
        pay.setPayeeAccountNo("0000000000161859");
        pay.setTransactionAmount(300l);
        pay.setRemark("项目放款：" + OrderNoUtils.getSerialNumber());
        pay.setTransactionTime(DateUtil.getNow());
        pay.setTransactionType(LemujiPayDo.PAY_TYPE_TO_PLATFORM);

        Map<String,String> params = new HashMap<>();
        params.put("PARTNERID", "e40dfb3c25fa31ce3a226695414cbbc3");
        params.put("externalId",pay.getOrderNo());
        params.put("reqSeq",pay.getTransactionNo());
        params.put("transactionAmount", String.valueOf(pay.getTransactionAmount()));
        params.put("trsMemo","");

        pay.setTransactionDesc(JSON.toJSONString(params));
        pay.setPayStatus(0);
        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
        Assert.assertTrue(true);
    }

    /**
     * 保存ZnjfLemujiPay表-翼支付企业间转账
     */
    @Test
    public void saveZnjfLemujiPayQY(){
        LemujiPayDo pay = new LemujiPayDo();
        pay.setOrderNo(OrderNoUtils.getSerialNumber());
        pay.setTransactionNo(OrderNoUtils.getSerialNumber());
        pay.setLoanDrawUuid("LMJZY201711021523557709");
        pay.setUserId(902);
        pay.setPayeeMerchantNo("0000000000101652");
        pay.setTransactionAmount(300l);
        pay.setTransactionTime(DateUtil.getNow());
        pay.setTransactionType(LemujiPayDo.PAY_TYPE_TO_FINANCER);

        Map<String,String> params = new HashMap<>();
        params.put("PARTNERID","e40dfb3c25fa31ce3a226695414cbbc3");
        params.put("PAYEECODE",pay.getPayeeMerchantNo());
        params.put("AREACODE","330000");
        params.put("TXNAMOUNT", String.valueOf(pay.getTransactionAmount()));
        params.put("ORDERSEQ",pay.getOrderNo());
        params.put("TRANSSEQ",pay.getTransactionNo());
        params.put("TRADETIME",DateUtil.dateStr3(pay.getTransactionTime()));
        params.put("MARK1","");
        pay.setTransactionDesc(JSON.toJSONString(params));
        pay.setPayStatus(0);
        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
        Assert.assertTrue(true);
    }

    /**
     * 保存ZnjfLemujiPay表-翼支付付款到银行卡
     */
    @Test
    public void saveZnjfLemujiPayYH(){
        LemujiPayDo pay = new LemujiPayDo();
        pay.setOrderNo(OrderNoUtils.getSerialNumber());
        pay.setTransactionNo(OrderNoUtils.getSerialNumber());
        pay.setLoanDrawUuid("LMJZY201711021523557709");
        pay.setUserId(902);
        pay.setPayeeAccountName("杭州乐木几网络科技有限公司");
        pay.setPayeeAccountNo("571909377910306");
        pay.setTransactionAmount(300l);
        pay.setRemark("项目还款:"+OrderNoUtils.getSerialNumber());
        pay.setTransactionTime(DateUtil.getNow());
        pay.setTransactionType(LemujiPayDo.PAY_TYPE_TO_BANKACCOUNT);

        Map<String,String> params = new HashMap<>();
        params.put("PARTNERID","e40dfb3c25fa31ce3a226695414cbbc3");
        params.put("externalId",pay.getOrderNo());
        params.put("transactionAmount", String.valueOf(pay.getTransactionAmount()));
        params.put("reqSeq",pay.getTransactionNo());
        params.put("trsMemo","");
        pay.setTransactionDesc(JSON.toJSONString(params));
        pay.setPayStatus(0);
        supplyChainLmjMapper.saveZnjfLemujiPay(pay);
        Assert.assertTrue(true);
    }

    /**
     * 查询交易详情
     */
    @Test
    public void getZnjfLemujiPayByOrderId(){
        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfLemujiPayByOrderId("1711271313838686");
        Assert.assertNotNull(supplyChainLmjResultDO);
    }

    /**
     * 更新znjf_lemuji_pay表状态
     */
    @Test
    public void updateZnjfLemujiPayStatus(){
        supplyChainLmjMapper.updateZnjfLemujiPayStatus(LemujiPayDo.PAY_STATUS_SUCCESS,"","1711271313838686");
        Assert.assertTrue(true);
    }

    /**
     *  查询商户号
     */
    @Test
    public void getZnjfExternalUserByUserId(){
        SupplyChainLmjResultDO znjfExternalUser = supplyChainLmjMapper.getZnjfExternalUserByUserId(902);
        Assert.assertTrue("正确", "0000000000101652".equals(znjfExternalUser.getExtMerchants()));
    }

    /**
     *  查询预还款信息
     */
    @Test
    public void getRepaymentInfo(){
        SupplyChainLmjResultDO znjfExternalUser = supplyChainLmjMapper.getRepaymentInfo("LMJZY201711021523557709");
        Assert.assertTrue("正确", znjfExternalUser.getCapital() == 1050);
    }

    /**
     *  更新还款表状态
     */
    @Test
    public void updateBorrowRepaymentStatus(){
        supplyChainLmjMapper.updateBorrowRepaymentStatus(902,23473l);
        Assert.assertTrue(true);
    }

    /**
     *  更新融资还款状态
     */
    @Test
    public void updateZnjfFundRepayStatus(){
        supplyChainLmjMapper.updateZnjfFundRepayStatus(902,"LMJZY201711021523557709",
                TableConstants.MONEY_STATUS_REPAYING);
        Assert.assertTrue(true);
    }
}
