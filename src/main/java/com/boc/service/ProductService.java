package com.boc.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.boc.model.ProductLoanDetails;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.response.BranchBaseRs;
import com.boc.response.DocumentProductSecurityRs;
import com.boc.response.ProductLoanDetailsResponse;
import com.boc.response.UserBranchRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserRLCBranchRs;

public interface ProductService {
	public void getTest();
	public void getProductService();
	public UserBranchRs getBranchByUserId(String ntId) throws Exception;
	public UserBranchRs getBranchByBranchCode(String branchCode) throws Exception;
	public List<String> getProfession() throws Exception;
	public List<String> getEmployer() throws Exception;
	public List<String> getProductsByProfession(String professionName) throws Exception;
	public String getProductCategoryByProduct(String productName) throws Exception;
	public List<String> getIndexByProduct(String productName) throws Exception;
	public BigDecimal getInterestRateByIndex(String indexId) throws Exception;
	public ProductLoanDetailsResponse getProductLoanDetails(String productName) throws Exception;
	public ProductLoanDetailsResponse getProductLoanVarianceDetails(String productName, int maxRepaymentPeriod) throws Exception;
	public List<String> getDocumentCheckByProduct(String productName) throws Exception;
	public String getReferenceNo(String branchCode,String productName) throws Exception;
	public int getRoleIdByName(String roleName) throws Exception;
	public DocumentProductSecurityRs getSecuredDocumentsByProductSecurity(String security, String productName) throws Exception;
	public String getCasaReferenceNo(String branchCode) throws Exception;
	public List<BranchBaseRs> getAllBranches() throws Exception;
	public BranchBaseRs getBranchCodeByBranchname(String branchName) throws Exception;
	//#added by vikshith : get document checklist By securityList, productName and loanPurpose 
	public DocumentProductSecurityRs getDocChklstByProdNameSecurityNPurpose(String security, String productName, String loanPurposes) throws Exception;
	
	public UserProductBranchRs getProductCategoryBranchByUser(String ntId) throws Exception;
	public UserProductBranchRs getProductCategoryRLCBranchByUser(String ntId) throws Exception;
	public String getNationalityByCode(String citizenCode) throws Exception;
	public String getCollateralDescription(String collateralCode) throws Exception;
	public UserBranchRs getRLCBranchByUserId(String ntId) throws Exception;
	public String getActApplicationId(String branchCode) throws Exception;
	//# Added by Vikshith : This API will get RLC code, Rlc name, Branch code and branch name  by ntUserId
	public UserRLCBranchRs getRLCBranchDetailsByUserId(String ntId);
	
	public List<String> getHomeLoanProducts();
	public List<String> getHomeLoanPurposes();
	public List<String> getRLCCOUsersByRoleBranch(String branchCode,String roleId);
	//Added by Tushar
	public String calculatePersonalResponsiveness(String prefCustomer,String srcofin,String profcode,String citizenship) throws Exception;
	public String calculateNonPersonalResponsiveness(String customertype,String srcoffund,String businesstype,String jrisk) throws Exception;
	public void getPayeeTypes();
}
