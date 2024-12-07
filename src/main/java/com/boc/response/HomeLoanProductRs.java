

package com.boc.response;

import java.util.List;

/*
Created By SaiMadan on Aug 1, 2016
*/
public class HomeLoanProductRs 
{

	String errorCode;
	String errorDescription;
	List<String> productLst;
	List<String> loanPurposesLst;
	public List<String> getProductLst() {
		return productLst;
	}
	public void setProductLst(List<String> productLst) {
		this.productLst = productLst;
	}
	public List<String> getLoanPurposesLst() {
		return loanPurposesLst;
	}
	public void setLoanPurposesLst(List<String> loanPurposesLst) {
		this.loanPurposesLst = loanPurposesLst;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
	
}
