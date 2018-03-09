package com.example.modules.custom.entity;

import java.util.Date;

import com.example.base.bean.dao.BranchTable;

public class CustomTxt extends BranchTable {
    private Integer txtId;

    private String txtUrl;

    private String txtClass;

    private String authorName;

    private String title;

    private String content;

    private String dateStr;

    private String isFetch;

    private Date lastFetchTime;

    private Date firstFetchTime;

    private Date createTime;

    private Integer orderby;

    private String markFlag;

    private String remark;

    public Integer getTxtId() {
        return txtId;
    }

    public void setTxtId(Integer txtId) {
        this.txtId = txtId;
    }

    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl == null ? null : txtUrl.trim();
    }

    public String getTxtClass() {
        return txtClass;
    }

    public void setTxtClass(String txtClass) {
        this.txtClass = txtClass == null ? null : txtClass.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr == null ? null : dateStr.trim();
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

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public String getMarkFlag() {
        return markFlag;
    }

    public void setMarkFlag(String markFlag) {
        this.markFlag = markFlag == null ? null : markFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public void branchRule() {
		setTableName("custom_txt_" + getRuleFlag());
		
	}
}