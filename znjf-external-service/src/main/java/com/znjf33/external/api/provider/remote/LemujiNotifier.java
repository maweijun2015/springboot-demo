package com.znjf33.external.api.provider.remote;

import com.alibaba.fastjson.JSON;
import com.znjf33.common.utils.ApiResponser;
import com.znjf33.common.utils.HttpUtil;
import com.znjf33.external.api.dto.LemujiBaseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wangyc on 2017/10/19.
 */
public class LemujiNotifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(LemujiNotifier.class);

    private static final String SUCCESS_INDEX = "000000";
    private static final String RESULT_CODE = "code";
    private static final String PROCESSING_CODE = "011007";

    private LemujiNotifier() {}

    public static boolean push(LemujiAPI api, LemujiBaseDTO model, String actionId,String lmjApiSecretKey,
                               String rootUrl,String partnerAppId) {
        boolean isSuccess = false;
        String params = JSON.toJSONString(model);

        LOGGER.info("推送地址：" + api.getName());
        LOGGER.info("推送内容：" + params);
        // 推送消息
        String s = HttpUtil.postJsonWithEncrypt(rootUrl + api.getId(), params, lmjApiSecretKey,actionId,partnerAppId);
        LOGGER.info("*********Lemuji Return:"+s);
        ApiResponser apiResponser = ApiResponser.build(s);
        if(SUCCESS_INDEX.equals(apiResponser.getString(RESULT_CODE))){
            isSuccess = true;
        }
        return isSuccess;
    }

    public static boolean payPushTransfer(String quanwangtongYizhifuTransfer, Map<String,String> params, String lmjApiSecretKey,
                                  String externalId,String partnerAppId) {
        boolean isSuccess = false;
        String s = HttpUtil.postPayToLemuji(quanwangtongYizhifuTransfer, params,lmjApiSecretKey,partnerAppId);
        LOGGER.info("{} 支付到平台翼支付账号:{}",externalId,s);
        ApiResponser responser = ApiResponser.build(s);
        String responsecode = responser.getString("code");
        LOGGER.info("{} 支付到平台翼支付账号  msg : {} ",externalId,new String(responser.getString("msg")));
        if(PROCESSING_CODE.equalsIgnoreCase(responsecode)){
            LOGGER.info("{} 调用平台翼支付账号接口成功",externalId);
        }else {
            LOGGER.error("{} 调用平台翼支付接口失败",externalId);
        }
        return isSuccess;
    }

    public static boolean payPushPaytb(String quanwangtongYizhifuTransfer, Map<String,String> params, String lmjApiSecretKey,
                                          String externalId,String partnerAppId) {
        boolean isSuccess = false;
        String s = HttpUtil.postPayToLemuji(quanwangtongYizhifuTransfer, params,lmjApiSecretKey,partnerAppId);
        LOGGER.info("{} 支付到银行卡:{}",externalId,s);
        ApiResponser responser = ApiResponser.build(s);
        String responsecode = responser.getString("code");
        LOGGER.info("{} 支付到银行卡  msg : {} ",externalId,new String(responser.getString("msg")));
        if(PROCESSING_CODE.equalsIgnoreCase(responsecode)){
            LOGGER.info("{} 调用支付到银行卡接口成功",externalId);
        }else {
            LOGGER.error("{} 调用支付到银行卡接口失败",externalId);
        }
        return isSuccess;
    }

}