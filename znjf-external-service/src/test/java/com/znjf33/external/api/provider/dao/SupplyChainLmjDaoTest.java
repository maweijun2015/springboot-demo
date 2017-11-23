package com.znjf33.external.api.provider.dao;

import com.znjf33.external.api.provider.config.JunitBase;
import com.znjf33.external.api.provider.domain.SupplyChainLmjResultDO;
import com.znjf33.external.api.provider.mapper.SupplyChainLmjMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
//        SupplyChainLmjResultDO supplyChainLmjResultDO = supplyChainLmjMapper.getZnjfLemujiPayByUuid(81606,
//                "1710271310001272");
//                Assert.assertNotNull(supplyChainLmjResultDO);
    }
}
