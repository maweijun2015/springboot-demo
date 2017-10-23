package com.znjf33.external.api.dto;

import java.io.Serializable;

/**
 * @author maweijun
 * @description
 * @create 17/10/15
 */
public class OrderinfoDTO implements Serializable {
    private static final long serialVersionUID = 1531436379330151314L;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 数量
     */
    private Integer goodsNumber;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
