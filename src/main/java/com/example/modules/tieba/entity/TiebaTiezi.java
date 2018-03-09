package com.example.modules.tieba.entity;

import java.util.Date;

import com.example.base.bean.dao.BranchTable;

public class TiebaTiezi extends BranchTable {
	private String tzId;

	private String authorId;

	private String authorName;

	private String title;

	private String content;

	private String postSDate;

	private Date postDate;

	private String replySDate;

	private Date replyDate;

	private String replyNum;

	private String replyerId;

	private String replyerName;

	private String auditerId;

	private String auditerName;

	private String isTop;

	private String isFetch;

	private Date lastFetchTime;

	private Date firstFetchTime;

	private Date createTime;

	public String getTzId() {
		return tzId;
	}

	public void setTzId(String tzId) {
		this.tzId = tzId == null ? null : tzId.trim();
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId == null ? null : authorId.trim();
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

	public String getPostSDate() {
		return postSDate;
	}

	public void setPostSDate(String postSDate) {
		this.postSDate = postSDate == null ? null : postSDate.trim();
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getReplySDate() {
		return replySDate;
	}

	public void setReplySDate(String replySDate) {
		this.replySDate = replySDate == null ? null : replySDate.trim();
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum == null ? null : replyNum.trim();
	}

	public String getReplyerId() {
		return replyerId;
	}

	public void setReplyerId(String replyerId) {
		this.replyerId = replyerId == null ? null : replyerId.trim();
	}

	public String getReplyerName() {
		return replyerName;
	}

	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName == null ? null : replyerName.trim();
	}

	public String getAuditerId() {
		return auditerId;
	}

	public void setAuditerId(String auditerId) {
		this.auditerId = auditerId == null ? null : auditerId.trim();
	}

	public String getAuditerName() {
		return auditerName;
	}

	public void setAuditerName(String auditerName) {
		this.auditerName = auditerName == null ? null : auditerName.trim();
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop == null ? null : isTop.trim();
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

	@Override
	public void branchRule() {
		setTableName("tieba_tiezi_" + getRuleFlag());
	}
}