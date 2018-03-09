package com.example.base.modules.seq.entity;

import java.util.Date;

public class SeqCfg {
    private String seqName;

    private Integer initVal;

    private Integer stockVal;

    private String currentVal;

    private Integer incrementVal;

    private Integer status;

    private Date initTime;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }

    public Integer getInitVal() {
        return initVal;
    }

    public void setInitVal(Integer initVal) {
        this.initVal = initVal;
    }

    public Integer getStockVal() {
        return stockVal;
    }

    public void setStockVal(Integer stockVal) {
        this.stockVal = stockVal;
    }

    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal == null ? null : currentVal.trim();
    }

    public Integer getIncrementVal() {
        return incrementVal;
    }

    public void setIncrementVal(Integer incrementVal) {
        this.incrementVal = incrementVal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }
}