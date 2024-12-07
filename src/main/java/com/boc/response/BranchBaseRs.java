

package com.boc.response;

/*
Created By SaiMadan on Nov 17, 2016
*/
public class BranchBaseRs 
{
	private int bid;
	private String branchCode;
	private String branchName;
	private String branchGrade;
	private String branchContactNumber;
	private Short editflag;
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchGrade() {
		return branchGrade;
	}
	public void setBranchGrade(String branchGrade) {
		this.branchGrade = branchGrade;
	}
	public String getBranchContactNumber() {
		return branchContactNumber;
	}
	public void setBranchContactNumber(String branchContactNumber) {
		this.branchContactNumber = branchContactNumber;
	}
	public Short getEditflag() {
		return editflag;
	}
	public void setEditflag(Short editflag) {
		this.editflag = editflag;
	}

	
}
