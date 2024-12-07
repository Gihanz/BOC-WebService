package com.boc.response;

public class UserRLCBranchRs 
{

	String rlcCode;
	String rlcName;
	String branchCode;
	String branchName;
	String branchContactNo;
	String areaCode;
	String areaName;
	String provinceCode;
	String provinceName;
	
	public UserRLCBranchRs(String rlcCode, String rlcName, String branchCode, String branchName,String branchContactNo,String areaCode,String areaName,String provinceCode,String provinceName) {
		super();
		this.rlcCode = rlcCode;
		this.rlcName = rlcName;
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.branchContactNo = branchContactNo;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
	}
	
	public UserRLCBranchRs() {
		super();
	}

	public String getRlcCode() {
		return rlcCode;
	}
	public void setRlcCode(String rlcCode) {
		this.rlcCode = rlcCode;
	}
	public String getRlcName() {
		return rlcName;
	}
	public void setRlcName(String rlcName) {
		this.rlcName = rlcName;
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

	public String getBranchContactNo() {
		return branchContactNo;
	}

	public void setBranchContactNo(String branchContactNo) {
		this.branchContactNo = branchContactNo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
