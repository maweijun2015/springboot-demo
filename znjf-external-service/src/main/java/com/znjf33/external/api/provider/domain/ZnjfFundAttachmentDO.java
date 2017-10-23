package com.znjf33.external.api.provider.domain;

/**
 * @author maweijun
 * @description
 * @create 17/10/17
 */
public class ZnjfFundAttachmentDO {
    /**
     * 交易号
     */
    private String dealNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 数量
     */
    private Integer goodsNumber;

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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
