package com.boc.response;

import java.util.List;
import java.util.Set;

public class UserProductBranchRs 
{
Set<String> roleName = null;
Set<String> branchName = null;
Set<String> branchCode = null;
Set<String>productCategoryId = null;
public Set<String> getRoleName() {
	return roleName;
}
public void setRoleName(Set<String> roleName) {
	this.roleName = roleName;
}
public Set<String> getBranchName() {
	return branchName;
}
public void setBranchName(Set<String> branchName) {
	this.branchName = branchName;
}
public Set<String> getBranchCode() {
	return branchCode;
}
public void setBranchCode(Set<String> branchCode) {
	this.branchCode = branchCode;
}
public Set<String> getProductCategoryId() {
	return productCategoryId;
}
public void setProductCategoryId(Set<String> productCategoryId) {
	this.productCategoryId = productCategoryId;
}


	
}
