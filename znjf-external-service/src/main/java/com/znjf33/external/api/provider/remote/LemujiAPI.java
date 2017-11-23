package com.znjf33.external.api.provider.remote;

/**
 * Created by wyc on 2017/10/19.
 */
public enum LemujiAPI {
    CREDIT_SET("", "授信接口"),
    LOAN_SUCCESS_NOTIFIER("", "放款通知接口"),
    REPAYMENT_SUCCESS_NOTIFIER("", "还款成功通知接口");



    private String id;
    private String name;

    LemujiAPI(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
