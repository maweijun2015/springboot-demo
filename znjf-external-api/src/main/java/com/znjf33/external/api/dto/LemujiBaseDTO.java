package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * @author wangyc
 * @since 2017/10/20
 */
public class LemujiBaseDTO implements Serializable {
    private static final long serialVersionUID = -4926624922996904699L;
    private String reqFlowNo;

    public String getReqFlowNo() {
        return reqFlowNo;
    }

    public void setReqFlowNo(String reqFlowNo) {
        this.reqFlowNo = reqFlowNo;
    }
}
