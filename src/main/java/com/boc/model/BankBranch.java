package com.boc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "BSR_BANK_BRANCH_BASE", schema = "WFCONFIG")
public class BankBranch implements java.io.Serializable {
	
	private int bbId;
	private String bankCode;
	private String bankName;
	private String bankNameDisplay;
	private String branchCode;
	private String branchName;
	private String branchNameDisplay;
	
	
	public BankBranch() {
		
	}
	
	public BankBranch(int bbId, String bankName, String bankNameDisplay,
			String branchCode, String branchName, String branchNameDisplay) {
		this.bbId = bbId;
		this.bankName = bankName;
		this.bankNameDisplay = bankNameDisplay;
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.branchNameDisplay = branchNameDisplay;
	}
	
	@Id

	@Column(name = "BB_ID", unique = true, nullable = false)
	public int getBbId() {
		return bbId;
	}

	public void setBbId(int bbId) {
		this.bbId = bbId;
	}
	
	@Column(name = "BANK_CODE", nullable = false)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	@Column(name = "BANK_NAME", nullable = false)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_NAME_DISPLAY", nullable = false)
	public String getBankNameDisplay() {
		return bankNameDisplay;
	}

	public void setBankNameDisplay(String bankNameDisplay) {
		this.bankNameDisplay = bankNameDisplay;
	}

	@Column(name = "BRANCH_CODE", nullable = false)
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "BRANCH_NAME", nullable = false)
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "BRANCH_NAME_DISPLAY", nullable = false)
	public String getBranchNameDisplay() {
		return branchNameDisplay;
	}

	public void setBranchNameDisplay(String branchNameDisplay) {
		this.branchNameDisplay = branchNameDisplay;
	}

	
}
