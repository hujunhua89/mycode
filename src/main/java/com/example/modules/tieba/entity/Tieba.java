package com.example.modules.tieba.entity;

import java.util.Date;

public class Tieba {
    private Integer tId;

    private String tiebaId;

    private String tiebaName;

    private String firstClass;

    private String secondClass;

    private String tiebaUrl;

    private String tiebaInfo;

    private String tiebaZtNum;

    private String tiebaTzNum;

    private String tiebaPeopleNum;

    private String isFetch;

    private Date lastFetchTime;

    private Date firstFetchTime;

    private Date createTime;

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String getTiebaId() {
        return tiebaId;
    }

    public void setTiebaId(String tiebaId) {
        this.tiebaId = tiebaId == null ? null : tiebaId.trim();
    }

    public String getTiebaName() {
        return tiebaName;
    }

    public void setTiebaName(String tiebaName) {
        this.tiebaName = tiebaName == null ? null : tiebaName.trim();
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass == null ? null : firstClass.trim();
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass == null ? null : secondClass.trim();
    }

    public String getTiebaUrl() {
        return tiebaUrl;
    }

    public void setTiebaUrl(String tiebaUrl) {
        this.tiebaUrl = tiebaUrl == null ? null : tiebaUrl.trim();
    }

    public String getTiebaInfo() {
        return tiebaInfo;
    }

    public void setTiebaInfo(String tiebaInfo) {
        this.tiebaInfo = tiebaInfo == null ? null : tiebaInfo.trim();
    }

    public String getTiebaZtNum() {
        return tiebaZtNum;
    }

    public void setTiebaZtNum(String tiebaZtNum) {
        this.tiebaZtNum = tiebaZtNum == null ? null : tiebaZtNum.trim();
    }

    public String getTiebaTzNum() {
        return tiebaTzNum;
    }

    public void setTiebaTzNum(String tiebaTzNum) {
        this.tiebaTzNum = tiebaTzNum == null ? null : tiebaTzNum.trim();
    }

    public String getTiebaPeopleNum() {
        return tiebaPeopleNum;
    }

    public void setTiebaPeopleNum(String tiebaPeopleNum) {
        this.tiebaPeopleNum = tiebaPeopleNum == null ? null : tiebaPeopleNum.trim();
    }

    public String getIsFetch() {
        return isFetch;
    }

    public void setIsFetch(String isFetch) {
        this.isFetch = isFetch == null ? null : isFetch.trim();
    }

    public Date getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(Date lastFetchTime) {
        this.lastFetchTime = lastFetchTime;
    }

    public Date getFirstFetchTime() {
        return firstFetchTime;
    }

    public void setFirstFetchTime(Date firstFetchTime) {
        this.firstFetchTime = firstFetchTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}