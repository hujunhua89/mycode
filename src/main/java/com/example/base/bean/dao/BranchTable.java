package com.example.base.bean.dao;

/***
 * 分表类
 * 
 * @ClassName:BranchTable
 * @Description:(描述)
 * @author:hujunhua
 * @date:2018年1月15日 下午5:12:23
 *
 */
public abstract  class BranchTable {
	/***
	 * 分表标识
	 */
	private String ruleFlag;

	/***
	 * 表名
	 */
	private String tableName;
	
	/***
	 * 定义分表规则
	* @Description: (描述) 
	* @param 
	* @return void 
	* @throws
	 */
	public abstract void branchRule();

	public String getRuleFlag() {
		return ruleFlag;
	}

	public void setRuleFlag(String ruleFlag) {
		this.ruleFlag = ruleFlag;
		this.branchRule();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
