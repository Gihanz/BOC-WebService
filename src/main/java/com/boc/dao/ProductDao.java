package com.boc.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.boc.model.ProductLoanDetails;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.response.BranchBaseRs;
import com.boc.response.DocumentProductSecurityRs;
import com.boc.response.LoanInterestRateVarianceResponse;
import com.boc.response.ProductLoanDetailsResponse;
import com.boc.response.UserBranchRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserRLCBranchRs;

public interface ProductDao {

	public void getProduct();
	public UserBranchRs getBranchByUserId(String userId);
	public UserBranchRs getBranchByBranchCode(String branchCode);
	public List<String> getProfession();
	public List<String> getEmployer();
	public List<String> getProductsByProfession(String professionName);
	public ProductLoanDetailsResponse getProductLoanDetails(String productName);
	public LoanInterestRateVarianceResponse getProductLoanVarianceDetails(int pid,int repaymentPeriod);
	public List<String> getDocumentCheckByProduct(String productName);
	public int getRoleIdByName(String roleName);
	public int getUserIdByName(String ntId);
	public String getProductCodebyName(String productName);
	public String getBranchCodebyName(String branchName);
	public String getProductCategory(String productName);
	public List<String> getIndexByProduct(String productName);
	public BigDecimal getInterestRateByIndex(String indexId);
	public UserProductBranchRs getProductCategoryBranch(String usrName);
	public UserProductBranchRs getProductCategoryRLCBranch(String usrName);
	public DocumentProductSecurityRs getSecuredDocuments(List<String> securityList, String productName);
	public String getNationalityByCode(String citizenCode);
	public List<BranchBaseRs> getAllBranches();
	public BranchBaseRs getBranchCodeByBranchname(String branchName);
	//#added by vikshith : get document checklist By securityList, productName and loanPurpose 
	public DocumentProductSecurityRs getDocChklstByProdNameSecurityNPurpose(List<String> securityList, String productName, String loanPurposes);
	
	public int getReferenceNo(final String productName,final String years);
	public int getCasReferenceNo(final String branchCode,final String years);
	public String getCollateralDescription(String collateralCode);
	public UserBranchRs getRLCBranchByUserId(String userId);
	public int getActApplicationId(final String yearStr,final String branchCode);
	//# Added by Vikshith : This API will get RLC code, Rlc name, Branch code and branch name  by ntUserId
	public UserRLCBranchRs getRLCBranchDetailsByUserId(String ntId);
	public List<String> getRLCCOUsersByRoleBranch(String branchCode,String roleName);
	public List<String> getHomeLoanProducts();
	public List<String> getHomeLoanPurpose();
	//Added by Tushar 
	public String calculate(String prefCustomer,String srcofin,String profcode,String citizenship);
	public String calculateNp(String customertype,String srcoffund,String businesstype,String jrisk);
	public void getPayeeTypes();
	
	
}
