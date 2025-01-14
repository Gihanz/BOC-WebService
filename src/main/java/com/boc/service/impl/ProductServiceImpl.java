package com.boc.service.impl;
import java.math.BigDecimal;
/*
Create By SaiMadan on Jun 15, 2016
*/
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.boc.dao.ProductDao;
import com.boc.dao.impl.ProductDaoImpl;
import com.boc.model.ProductLoanDetails;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.response.BranchBaseRs;
import com.boc.response.DocumentProductSecurityRs;
import com.boc.response.LoanInterestRateVarianceResponse;
import com.boc.response.ProductLoanDetailsResponse;
import com.boc.response.UserBranchRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserRLCBranchRs;
import com.boc.service.ProductService;
import com.boc.service.exceptions.BSLException;

@Service
@Configurable
public class ProductServiceImpl implements ProductService{
	private static Logger log =LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired private ProductDao productDao;
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public void getProductService()
	{
		productDao.getProduct();
	}
	

	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public void getTest() {
		
		productDao.getPayeeTypes();
			
		log.info("x[0]......... :" );
		
	}	
	
	

	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public void getPayeeTypes() {
		log.info("service product Impl accesssssssss.........");
		productDao.getPayeeTypes();
	}


	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserBranchRs getBranchByUserId(String ntId) throws Exception
	{
		
		HashMap branchMap = null;
		UserBranchRs userBranchResponse = null; 
		userBranchResponse = new UserBranchRs();
		userBranchResponse = productDao.getBranchByUserId(ntId);
					
		/*Set branchSet = null;
		Iterator iter = null;
		String branchName = null,branchCode=null;
		try
		{
		if(!branchMap.isEmpty())
		{
			branchSet = branchMap.keySet();
			iter = branchSet.iterator();
			while(iter.hasNext())
			{
				String key = (String) iter.next();
				if(key.equalsIgnoreCase("BranchName"))
				{
					branchName = (String) branchMap.get(key);
				}
				if(key.equalsIgnoreCase("BranchCode"))
				{
					branchCode = (String) branchMap.get(key);
				}
			}
		}
		else
		{
			throw new BSLException("er.db.branchCodeNotFound");
		}
		
		userBranchResponse.setBranchCode(branchCode);
		userBranchResponse.setBranchName(branchName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}*/
		if(null!=userBranchResponse && userBranchResponse.getBranchCode()==null)
		{
			throw new BSLException("er.db.branchCodeNotFound");
		}
		return userBranchResponse;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserBranchRs getBranchByBranchCode(String branchCode) throws Exception
	{
		UserBranchRs userBranchResponse = null; 
		userBranchResponse = new UserBranchRs();
		userBranchResponse = productDao.getBranchByBranchCode(branchCode);
		if(null!=userBranchResponse && userBranchResponse.getBranchCode()==null)
		{
			throw new BSLException("er.db.branchCodeNotFound");
		}
		return userBranchResponse;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserBranchRs getRLCBranchByUserId(String ntId) throws Exception
	{
		UserBranchRs userBranchResponse = null; 
		userBranchResponse = productDao.getRLCBranchByUserId(ntId);
		return userBranchResponse;
	}
	
	//# Added by Vikshith : This API will get RLC code, Rlc name, Branch code and branch name  by ntUserId
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserRLCBranchRs getRLCBranchDetailsByUserId(String ntId)
	{
		UserRLCBranchRs userBranchResponse = productDao.getRLCBranchDetailsByUserId(ntId);
		return userBranchResponse;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getProfession() throws Exception
	{
		List<String> professionLst = null;
		try
		{
			professionLst =	productDao.getProfession();
			if(null != professionLst && professionLst.size() == 0)
			{
					throw new BSLException("er.db.professionNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return professionLst;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getEmployer() throws Exception
	{
		List<String> employerLst = null;
		try
		{
			employerLst = productDao.getEmployer();
			if(null != employerLst && employerLst.size() == 0)
			{
					throw new BSLException("er.db.employerNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return employerLst;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<BranchBaseRs> getAllBranches() throws Exception
	{
		List<BranchBaseRs> branchBaseRsLst = null;
		try
		{
			branchBaseRsLst = productDao.getAllBranches();
			if(null != branchBaseRsLst && branchBaseRsLst.size() == 0)
			{
					throw new BSLException("er.db.allBranchesNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return branchBaseRsLst;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public BranchBaseRs getBranchCodeByBranchname(String branchName) throws Exception
	{
		BranchBaseRs branchBaseRs = null;
		try
		{
			branchBaseRs = productDao.getBranchCodeByBranchname(branchName);
			if(null == branchBaseRs)
			{
					throw new BSLException("er.db.branchNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return branchBaseRs;
	}
	
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getProductsByProfession(String professionName) throws Exception
	{
		List<String>  productsLst = null;
		try
		{
			productsLst = productDao.getProductsByProfession(professionName);
			if(null != productsLst && productsLst.size() == 0)
			{
				throw new BSLException("er.db.productByProfessionNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return productsLst;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public ProductLoanDetailsResponse getProductLoanDetails(String productName) throws Exception
	{
		ProductLoanDetails productLoanDetails = null;
		ProductLoanDetailsResponse productLaonDetailResponse = null;
		try
		{
			//productLoanDetails =  productDao.getProductLoanDetails(productName);
			productLaonDetailResponse =  productDao.getProductLoanDetails(productName);
			/*if(null==productLoanDetails)
			{
				throw new BSLException("er.db.productLoanDetailsNotFound");
			}
			else
			{
				System.out.println("%%%%%%%%%%%%%%%%%productLoanDetails%%%%%%%%%%"+productLoanDetails);
				productLaonDetailResponse = new ProductLoanDetailsResponse();
				if(null!=productLoanDetails.getInterestRateFixed())
					productLaonDetailResponse.setInterestRateFixed(productLoanDetails.getInterestRateFixed());
				else
					productLaonDetailResponse.setInterestRateFixed(new BigDecimal(0));
				if(null!=productLoanDetails.getInterestRateVariable1())
					productLaonDetailResponse.setInterestRateVariable1(productLoanDetails.getInterestRateVariable1());
				else
					productLaonDetailResponse.setInterestRateVariable1(new BigDecimal(0));
				if(null!=productLaonDetailResponse.getInterestRateVariable2())
					productLaonDetailResponse.setInterestRateVariable2(productLaonDetailResponse.getInterestRateVariable2());
				else
					productLaonDetailResponse.setInterestRateVariable2(new BigDecimal(0));
				if(null!=productLoanDetails.getMaxRepaymentPeriod())
					productLaonDetailResponse.setMaxRepaymentPeriod((int)productLoanDetails.getMaxRepaymentPeriod());
				else
					productLaonDetailResponse.setMaxRepaymentPeriod(0);
			}*/
			System.out.println("##############productLoanDetails is "+productLaonDetailResponse+"#############");
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			e.printStackTrace();
			throw e;
		}
		
		return productLaonDetailResponse;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public ProductLoanDetailsResponse getProductLoanVarianceDetails(String productName, int maxRepaymentPeriod) throws Exception
	{
		ProductLoanDetails productLoanDetails = null;
		ProductLoanDetailsResponse productLaonDetailResponse = null;
		try
		{
			productLaonDetailResponse =  productDao.getProductLoanDetails(productName);
			if(null!=productLaonDetailResponse && maxRepaymentPeriod==0)
			{
				LoanInterestRateVarianceResponse loanInterestVariance = productDao.getProductLoanVarianceDetails(productLaonDetailResponse.getPid(), productLaonDetailResponse.getMaxRepaymentPeriod());
				log.info("loanInterestVariance "+loanInterestVariance);
				if(null!=loanInterestVariance)
				{
					BigDecimal interestRateFixed = productLaonDetailResponse.getInterestRateFixed();
					BigDecimal varianceFixed = loanInterestVariance.getVarianceRateFixed();
					interestRateFixed = interestRateFixed.add(varianceFixed);
					log.info("varianceFixed is "+varianceFixed);
					productLaonDetailResponse.setInterestRateFixed(interestRateFixed);
					log.info("After Adding interestRateFixed "+interestRateFixed);
					
					BigDecimal interestRateVariable1 = productLaonDetailResponse.getInterestRateVariable1();
					BigDecimal varianceRateVariable1 = loanInterestVariance.getVarianceRateVariable1();
					interestRateVariable1 = interestRateVariable1.add(varianceRateVariable1);
					productLaonDetailResponse.setInterestRateVariable1(interestRateVariable1);
					log.info("After Adding interestRateVariable1 is "+interestRateVariable1);

					BigDecimal interestRateVariable2 = productLaonDetailResponse.getInterestRateVariable2();
					BigDecimal varianceRateVariable2 = loanInterestVariance.getVarianceRateVariable2();
					interestRateVariable2  = interestRateVariable2.add(varianceRateVariable2);
					productLaonDetailResponse.setInterestRateVariable2(interestRateVariable2);
					log.info("After Adding interestRateVariable2 is "+interestRateVariable2);
				}
				
			}
			if(null!=productLaonDetailResponse && maxRepaymentPeriod!=0)
			{
				LoanInterestRateVarianceResponse loanInterestVariance = productDao.getProductLoanVarianceDetails(productLaonDetailResponse.getPid(), maxRepaymentPeriod);
				log.info("loanInterestVariance maxRepaymentPeriod is not zero "+loanInterestVariance);
				if(null!=loanInterestVariance)
				{
					productLaonDetailResponse.setMaxRepaymentPeriod(maxRepaymentPeriod);
					log.info("Setting updated maxRepaymentPeriod "+maxRepaymentPeriod);
					
					BigDecimal interestRateFixed = productLaonDetailResponse.getInterestRateFixed();
					BigDecimal varianceFixed = loanInterestVariance.getVarianceRateFixed();
					interestRateFixed = interestRateFixed.add(varianceFixed);
					log.info("varianceFixed is "+varianceFixed);
					productLaonDetailResponse.setInterestRateFixed(interestRateFixed);
					log.info("After Adding interestRateFixed "+interestRateFixed);
					
					BigDecimal interestRateVariable1 = productLaonDetailResponse.getInterestRateVariable1();
					BigDecimal varianceRateVariable1 = loanInterestVariance.getVarianceRateVariable1();
					interestRateVariable1 = interestRateVariable1.add(varianceRateVariable1);
					productLaonDetailResponse.setInterestRateVariable1(interestRateVariable1);
					log.info("After Adding interestRateVariable1 is "+interestRateVariable1);

					BigDecimal interestRateVariable2 = productLaonDetailResponse.getInterestRateVariable2();
					BigDecimal varianceRateVariable2 = loanInterestVariance.getVarianceRateVariable2();
					interestRateVariable2  = interestRateVariable2.add(varianceRateVariable2);
					productLaonDetailResponse.setInterestRateVariable2(interestRateVariable2);
					log.info("After Adding interestRateVariable2 is "+interestRateVariable2);
				}
			}
				
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			e.printStackTrace();
			throw e;
		}
		
		return productLaonDetailResponse;
	}
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getDocumentCheckByProduct(String productName) throws Exception
	{
		List<String> documentChkLst = null;
		try
		{
			documentChkLst =	productDao.getDocumentCheckByProduct(productName);
			if(null != documentChkLst && documentChkLst.size()==0)
			{
				throw new BSLException("er.db.documentCheckListNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return documentChkLst;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public int getRoleIdByName(String roleName) throws Exception
	{
		int roleId = 0;
		try
		{
			roleId = productDao.getRoleIdByName(roleName);
			if(roleId==0)
			{
				throw new BSLException("er.db.roleNameNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return roleId;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getProductCategoryByProduct(String productName) throws Exception
	{
		String productCategoryName = null;
		try
		{
			productCategoryName=productDao.getProductCategory(productName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return productCategoryName;
	}
	
	/*@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<DocumentProductSecurityRs> getSecuredDocumentsByProductSecurity(String security, String productName) throws Exception
	{
		String documentProductSecurityStr=null;
		List<DocumentProductSecurityRs> documentProductSecurityRsLst = null;
		try
		{
			List<String> securityList = null;
			securityList  = new ArrayList<String>();
			if(null!=security)
			{
				if(security.indexOf(",")!=-1)
				{
					String[] securityArr =security.split(",");
					for(String securityStr:securityArr)
					{
						securityList.add(securityStr);
					}
				}
				else
				{
					securityList.add(security);
				}
			}
			documentProductSecurityRsLst = productDao.getSecuredDocuments(securityList, productName);
			if(null!=documentProductSecurityRsLst && documentProductSecurityRsLst.size()>0)
			{
				int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					else
					{
						documentProductSecurityStr+=","+documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					i++;
				}
			}
			else
			{
				throw new BSLException("er.db.securityDocumentNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return documentProductSecurityRsLst;
	}
	*/
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public DocumentProductSecurityRs getSecuredDocumentsByProductSecurity(String security, String productName) throws Exception
	{
		String documentProductSecurityStr=null;
		List<DocumentProductSecurityRs> documentProductSecurityRsLst = null;
		DocumentProductSecurityRs documentProductSecurityRs = null;
		try
		{
			List<String> securityList = null;			
			if(null !=security && !security.isEmpty())
			{
				securityList  = new ArrayList<String>();
				if(security.indexOf(",")!=-1)
				{
					String[] securityArr =security.split(",");
					for(String securityStr:securityArr)
					{
						securityList.add(securityStr);
					}
				}
				else
				{
					securityList.add(security);
				}
			}
			documentProductSecurityRs = productDao.getSecuredDocuments(securityList, productName);
			if(null!=documentProductSecurityRs)
			{
				/*int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					else
					{
						documentProductSecurityStr+=","+documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					i++;
				}*/
			}
			else
			{
				throw new BSLException("er.db.securityDocumentNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return documentProductSecurityRs;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public DocumentProductSecurityRs getDocChklstByProdNameSecurityNPurpose(String security, String productName, String loanPurposes) throws Exception
	{
		String documentProductSecurityStr=null;
		List<DocumentProductSecurityRs> documentProductSecurityRsLst = null;
		DocumentProductSecurityRs documentProductSecurityRs = null;
		try
		{
			List<String> securityList = null;			
			if(null!=security && !security.isEmpty())
			{
				securityList  = new ArrayList<String>();
				if(security.indexOf(",")!=-1)
				{
					String[] securityArr =security.split(",");
					for(String securityStr:securityArr)
					{
						securityList.add(securityStr);
					}
				}
				else
				{
					securityList.add(security);
				}
			}
			documentProductSecurityRs = productDao.getDocChklstByProdNameSecurityNPurpose(securityList, productName, loanPurposes);
			if(null!=documentProductSecurityRs)
			{
				/*int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					else
					{
						documentProductSecurityStr+=","+documentProductSecurityRs.getDocumentName()+"-"+documentProductSecurityRs.getScanRequired()+"-"+documentProductSecurityRs.getMandatory();
					}
					i++;
				}*/
			}
			else
			{
				throw new BSLException("er.db.securityDocumentNotFound");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return documentProductSecurityRs;
	}
	
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserProductBranchRs getProductCategoryBranchByUser(String ntId) throws Exception
	{
		UserProductBranchRs userProductBranchRs = null;
		try
		{
			userProductBranchRs =	productDao.getProductCategoryBranch(ntId);
			/*if(null == userProductBranchRs)
			{
				throw new BSLException("er.db.roleNameNotFound");
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return userProductBranchRs;
	}
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public UserProductBranchRs getProductCategoryRLCBranchByUser(String ntId) throws Exception
	{
		UserProductBranchRs userProductBranchRs = null;
		try
		{
			userProductBranchRs =	productDao.getProductCategoryRLCBranch(ntId);
			/*if(null == userProductBranchRs)
			{
				throw new BSLException("er.db.roleNameNotFound");
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return userProductBranchRs;
	}
	
	
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getReferenceNo(String branchCode,String productName) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		String referenceNo = null;
		int years = cal.get(cal.YEAR);
		try
		{
			String productCode = productDao.getProductCodebyName(productName);
			if(null==productCode)
					throw new BSLException("er.db.productCodeNotFoundForReferenceNo");
			int refNo = productDao.getReferenceNo(productCode,String.valueOf(years));
			String refNoStr = String.valueOf(refNo);
			String appendProductCode =  leftPad(productCode,5,'0');
			String appendbranchCode = leftPad(branchCode,4,'0');
			String appendedRefNo = leftPad(refNoStr,6,'0');
			referenceNo = appendbranchCode+appendProductCode+String.valueOf(years)+appendedRefNo;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return referenceNo;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getCasaReferenceNo(String branchCode) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		String referenceNo = null;
		int years = cal.get(cal.YEAR);
		try
		{
			int refNo = productDao.getCasReferenceNo(branchCode,String.valueOf(years));
			String refNoStr = String.valueOf(refNo);
			//String appendProductCode =  leftPad(productCode,5,'0');
			String appendbranchCode = leftPad(branchCode,4,'0');
			String appendedRefNo = leftPad(refNoStr,11,'0');
			referenceNo = appendbranchCode+String.valueOf(years)+appendedRefNo;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return referenceNo;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getNationalityByCode(String citizenCode) throws Exception
	{
		int citizenCodeint = Integer.parseInt(citizenCode);
		System.out.println("citizenCodeint is "+citizenCodeint);//To remove appended 0 from citizencode
		String nationality = productDao.getNationalityByCode(String.valueOf(citizenCodeint));
		return nationality;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getCollateralDescription(String collateralCode) throws Exception
	{
		String collateralDesc = null; 
		collateralDesc =	productDao.getCollateralDescription(collateralCode);
		return collateralDesc;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getIndexByProduct(String productName) throws Exception
	{
		List<String> indexDescList = null;
		indexDescList =	productDao.getIndexByProduct(productName);
		if(null==indexDescList)		
		{
			throw new BSLException("er.db.IndexIdNotFound");
		}
		return indexDescList;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public BigDecimal getInterestRateByIndex(String indexId) throws Exception
	{
		BigDecimal interestRate = productDao.getInterestRateByIndex(indexId);
		if(null == interestRate)
		{
			throw new BSLException("er.db.IndexIdNotFound");
		}
		return interestRate;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getHomeLoanProducts()
	{
		List<String> lstproducts=null;
		lstproducts = productDao.getHomeLoanProducts();
		if(null!=lstproducts && lstproducts.size()==0)
		{
			throw new BSLException("er.db.HomeLoanProductsNotFound");
		}
		return lstproducts;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getRLCCOUsersByRoleBranch(String branchCode,String roleName)
	{
		List<String> coUserList = null;
		coUserList = productDao.getRLCCOUsersByRoleBranch(branchCode,roleName);
		return coUserList;
	}
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getHomeLoanPurposes()
	{
		List<String> lstLoanPurposes=null;
		lstLoanPurposes = productDao.getHomeLoanPurpose();
		if(null!=lstLoanPurposes && lstLoanPurposes.size()==0)
		{
			throw new BSLException("er.db.HomeLoanPurposesNotFound");
		}
		return lstLoanPurposes;
	}
	
	public static String leftPad(String originalString, int length,
	         char padCharacter) {
	      String paddedString = originalString;
	      while (paddedString.length() < length) {
	         paddedString = padCharacter + paddedString;
	      }
	      return paddedString;
	   }
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String getActApplicationId(String branchCode) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		String referenceNo = null,applicationId=null;
		int years = cal.get(cal.YEAR);
		String  yearsStr = String.valueOf(years);
		int seq = productDao.getActApplicationId(yearsStr,branchCode);
		String seqStr = String.valueOf(seq);
		String appendedRefNo = leftPad(seqStr,9,'0');
		yearsStr = yearsStr.substring(2);
		applicationId = "1"+yearsStr+appendedRefNo;
		return applicationId;
	}
	
	
	
	//Added by Tushar
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String calculatePersonalResponsiveness(String prefCustomer,String srcofin,String profcode,String citizenship) throws Exception
	{ 		/*String pcu=prefCustomer;
	String so=soi;
	String pco=pc;
	String cit=citizenship;*/
		String responsiveness = null;
		try{
		responsiveness=productDao.calculate(prefCustomer,srcofin,profcode,citizenship);
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return responsiveness;
		}
	
	
	
	
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public String calculateNonPersonalResponsiveness(String customerType,String srcoffund,String businesstype,String jrisk) throws Exception
	{ 		
	/*String ctp=customerType;
	String so=sof;
	String btype=btyp;
	String jris=jrisk;*/
		String responsiveness = null;
		try
		{
		
		responsiveness=productDao.calculateNp(customerType,srcoffund,businesstype,jrisk);
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return responsiveness;
		
	}

	
}
 