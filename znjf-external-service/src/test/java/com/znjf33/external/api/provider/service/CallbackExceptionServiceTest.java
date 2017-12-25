package com.znjf33.external.api.provider.service;

import com.znjf33.external.api.dto.SupplyChainLmjParamDTO;
import com.znjf33.external.api.dto.SupplyChainLmjResultDTO;
import com.znjf33.external.api.provider.config.JunitBase;
import com.znjf33.external.api.service.CallbackExceptionService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author maweijun
 * @description
 * @create 17/12/25
 */
public class CallbackExceptionServiceTest extends JunitBase {

    @Autowired
    CallbackExceptionService callbackExceptionService;

    @Test
    public void analyzeExceptionProcess(){
        callbackExceptionService.analyzeExceptionProcess();
        Assert.assertTrue(true);
    }

    @Test
    public void sendExceptionRecord(){
        callbackExceptionService.sendExceptionRecord();
        Assert.assertTrue(true);
    }
}
