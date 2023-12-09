package com._3stock.module.aggregates;


import com._3stock.module.vo.Stock;

public class StockResult {

    private Integer resultcode;
    private String reason;

    private Stock[] result;

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Stock[] getResult() {
        return result;
    }

    public void setResult(Stock[] result) {
        this.result = result;
    }
}
