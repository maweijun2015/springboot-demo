package com.znjf33.external.api.provider.service.impl;

import com.znjf33.common.utils.DateUtil;
import com.znjf33.external.api.provider.biz.ThirdPartyService;
import com.znjf33.external.api.provider.common.Constant;
import com.znjf33.external.api.provider.common.TableConstants;
import com.znjf33.external.api.provider.domain.ZnjfExceptionRecordParamDO;
import com.znjf33.external.api.provider.domain.ZnjfExceptionRecordResultDO;
import com.znjf33.external.api.provider.mapper.ZnjfExceptionRecordMapper;
import com.znjf33.external.api.service.CallbackExceptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author maweijun
 * @description
 * @create 17/12/21
 */
public class CallbackExceptionServiceImpl implements CallbackExceptionService {

    @Autowired
    ZnjfExceptionRecordMapper znjfExceptionRecordMapper;

    @Autowired
    private ThirdPartyService thirdPartyService;


    /**
     * 统计所有银行回调异常
     */
    @Override
    public void analyzeExceptionProcess(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryByPrimaryKey();
        ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord = new ConcurrentHashMap<>();
        if (znjfExceptionRecordResultDOList != null && znjfExceptionRecordResultDOList.size() != 0){
            for (ZnjfExceptionRecordResultDO znjfExceptionRecordResultDO:znjfExceptionRecordResultDOList){
                mapRecord.put(znjfExceptionRecordResultDO.getTradeNo(),znjfExceptionRecordResultDO);
            }
        }
        List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList = new ArrayList<>();
        analyzeRechargeExceptionRecord(znjfExceptionRecordParamDOList,mapRecord);
        analyzeCashExceptionRecord(znjfExceptionRecordParamDOList,mapRecord);
        analyzeRePayExceptionRecord(znjfExceptionRecordParamDOList,mapRecord);
        analyzeVerifyFullExceptionRecord(znjfExceptionRecordParamDOList,mapRecord);
        analyzeBondExceptionRecord(znjfExceptionRecordParamDOList,mapRecord);
        znjfExceptionRecordMapper.saveZnjfExceptionRecord(znjfExceptionRecordParamDOList);
    }

    /**
     * 发送所有异常表中的异常
     */
    @Override
    public void sendExceptionRecord(){
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryByPrimaryKey();
        if (znjfExceptionRecordResultDOList == null || znjfExceptionRecordResultDOList.size() == 0){
            return;
        }
        for (ZnjfExceptionRecordResultDO znjfExceptionRecordResultDO:znjfExceptionRecordResultDOList){
            thirdPartyService.sendEmail("银行没有回调","流水号:"+znjfExceptionRecordResultDO.getTradeNo(),
                    Constant.EXCEPTION_REMINDER,Constant.EXCEPTION_REMINDER_EMAIL_ADDRESS);
            znjfExceptionRecordMapper.updateByPrimaryKey(znjfExceptionRecordResultDO.getId());
        }
    }

    /**
     * 回调异常记录
     * @param znjfExceptionRecordResultDOList
     * @param ChannelType
     */
    private void analyzeRecord(List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList,
                               List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                               ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord,String ChannelType){
        if (znjfExceptionRecordResultDOList == null || znjfExceptionRecordResultDOList.size() == 0){
            return;
        }
        for (ZnjfExceptionRecordResultDO znjfExceptionRecordResultDO:znjfExceptionRecordResultDOList){
            if (mapRecord.containsKey(znjfExceptionRecordResultDO.getTradeNo())){
                continue;
            }
            ZnjfExceptionRecordParamDO znjfExceptionRecordParamDO = new ZnjfExceptionRecordParamDO();
            znjfExceptionRecordParamDO.setTradeNo(znjfExceptionRecordResultDO.getTradeNo());
            znjfExceptionRecordParamDO.setChannelType(ChannelType);
            znjfExceptionRecordParamDO.setStatus(TableConstants.ZNJF_CHANNEL_TYPE_STATUS_WAIT);
            znjfExceptionRecordParamDO.setUserId(znjfExceptionRecordResultDO.getUserId());
            znjfExceptionRecordParamDO.setMsg(znjfExceptionRecordResultDO.getMsg());
            znjfExceptionRecordParamDO.setAddTime(DateUtil.getNow());
            znjfExceptionRecordParamDOList.add(znjfExceptionRecordParamDO);
        }
    }

    /**
     * 在线充值异常统计
     */
    private void analyzeRechargeExceptionRecord(List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                                                ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord ){
        //查询在线充值超过30分钟异常数据
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountRechargeException();
        analyzeRecord(znjfExceptionRecordResultDOList,znjfExceptionRecordParamDOList,mapRecord, TableConstants.ZNJF_CHANNEL_TYPE_RECHARGE);
    }

    /**
     * 提现回调异常统计
     */
    private void analyzeCashExceptionRecord(List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                                            ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord ){
        //查询提现超过30分钟异常数据
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountCashException();
        analyzeRecord(znjfExceptionRecordResultDOList,znjfExceptionRecordParamDOList,mapRecord, TableConstants.ZNJF_CHANNEL_TYPE_CASH);
    }

    /**
     * 还款回调异常统计
     */
    private void analyzeRePayExceptionRecord(List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                                             ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord ){
        //查询还款异常数据
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryAccountRePayException();
        analyzeRecord(znjfExceptionRecordResultDOList,znjfExceptionRecordParamDOList,mapRecord, TableConstants.ZNJF_CHANNEL_TYPE_REPAY);
    }

    /**
     * 查询放款回调异常数据
     */
    private void analyzeVerifyFullExceptionRecord(List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                                                  ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord ){
        //查询还款异常数据
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryVerifyFullException();
        analyzeRecord(znjfExceptionRecordResultDOList,znjfExceptionRecordParamDOList,mapRecord,TableConstants.ZNJF_CHANNEL_TYPE_VERIFY_FULL);
    }

    /**
     * 查询转让回调异常数据
     */
    private void analyzeBondExceptionRecord(List<ZnjfExceptionRecordParamDO> znjfExceptionRecordParamDOList,
                                            ConcurrentMap<String,ZnjfExceptionRecordResultDO> mapRecord ){
        //查询转让异常数据
        List<ZnjfExceptionRecordResultDO> znjfExceptionRecordResultDOList = znjfExceptionRecordMapper.queryBondException();
        analyzeRecord(znjfExceptionRecordResultDOList,znjfExceptionRecordParamDOList,mapRecord,TableConstants.ZNJF_CHANNEL_TYPE_BATCH_BOND);
    }

}
