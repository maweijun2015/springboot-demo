package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * @author maweijun
 * @description
 * @create 17/10/16
 */
public class SupplyChainLmjResultDTO implements Serializable {
    private static final long serialVersionUID = 3073451196909322903L;
    /**
     * code
     */
    private String code;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 手机号
     */
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
