package com.boc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "BSR_PAYEE_BASE", schema = "WFCONFIG", uniqueConstraints = @UniqueConstraint(columnNames = { "PAYEE_ID"}) )
public class Payee implements Serializable {
	
	private int pId;
	private String payee;
	private String payeeType;
	private String targetType;
	private String accountNo;
	private String targetDes;
	private String feePlan;
	private String currency;
	
	@Id

	@Column(name = "PAYEE_ID", unique = true, nullable = false)
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
	@Column(name = "PAYEE", nullable = false)
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	@Column(name = "PAYEE_TYPE", nullable = false)
	public String getPayeeType() {
		return payeeType;
	}
	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}
	
	@Column(name = "TARGET_TYPE", nullable = false)
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	@Column(name = "ACCOUNT_NO", nullable = false)
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Column(name = "TARGET_DESCRIPTION", nullable = false)
	public String getTargetDes() {
		return targetDes;
	}
	public void setTargetDes(String targetDes) {
		this.targetDes = targetDes;
	}
	
	@Column(name = "FEE_PLAN", nullable = false)
	public String getFeePlan() {
		return feePlan;
	}
	public void setFeePlan(String feePlan) {
		this.feePlan = feePlan;
	}
	
	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

}
