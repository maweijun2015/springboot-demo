package com.znjf33.external.api.provider.dao;

import com.znjf33.common.utils.DateUtil;
import com.znjf33.external.api.provider.common.TableConstants;
import com.znjf33.external.api.provider.config.JunitBase;
import com.znjf33.external.api.provider.domain.ZnjfExceptionRecordParamDO;
import com.znjf33.external.api.provider.domain.ZnjfExceptionRecordResultDO;
import com.znjf33.external.api.provider.mapper.ZnjfExceptionRecordMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maweijun
 * @description
 * @create 17/12/25
 */
public class CallbackExceptionDaoTest extends JunitBase {

    @Autowired
    private ZnjfExceptionRecordMapper znjfExceptionRecordMapper;

    /**
     * 查询处理中的异常表数据
     */
    @Test
    public void getZnjfExternalUserByUserIdAndUuid(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryByPrimaryKey();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 查询在线充值当天超过30分钟异常数据
     */
    @Test
    public void queryAccountRechargeException(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountRechargeException();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 查询提现超过30分钟异常数据
     */
    @Test
    public void queryAccountCashException(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountCashException();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 还款回调异常统计
     */
    @Test
    public void queryAccountRePayException(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountRePayException();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 查询还款异常数据
     */
    @Test
    public void queryVerifyFullException(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryVerifyFullException();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 查询转让异常数据
     */
    @Test
    public void queryBondException(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryBondException();
        Assert.assertNotNull(znjfExceptionRecordResultDOList);
    }

    /**
     * 保存异常数据
     */
    @Test
    public void saveZnjfExceptionRecord(){
        try {
            List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList = new ArrayList<>();
            ZnjfExceptionRecordParamDO znjfExceptionRecordParamDO = new ZnjfExceptionRecordParamDO();
            znjfExceptionRecordParamDO.setTradeNo("12345");
            znjfExceptionRecordParamDO.setChannelType("100001");
            znjfExceptionRecordParamDO.setStatus(TableConstants.ZNJF_CHANNEL_TYPE_STATUS_WAIT);
            znjfExceptionRecordParamDO.setUserId(902);
            znjfExceptionRecordParamDO.setMsg("test");
            znjfExceptionRecordParamDO.setAddTime(DateUtil.getNow());
            znjfExceptionRecordParamDOList.add(znjfExceptionRecordParamDO);
            int a = znjfExceptionRecordMapper.saveZnjfExceptionRecord(znjfExceptionRecordParamDOList);
            Assert.assertTrue(1==a);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
