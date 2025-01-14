package com.boc.dao.impl;

import java.math.BigDecimal;
/*
Create By SaiMadan on Jun 8, 2016
*/
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.naming.NamingException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.boc.dao.ProductDao;
import com.boc.dao.exceptions.DAOException;
import com.boc.model.AreaBranchMapping;
import com.boc.model.BranchBase;
import com.boc.model.ProductBase;
import com.boc.model.ProductDocumentChecklistMapping;
import com.boc.model.ProductLoanDetails;
import com.boc.model.ProfessionList;
import com.boc.model.ProvinceBranchMapping;
import com.boc.model.RlcBranchMapping;
import com.boc.model.RoleBase;
import com.boc.model.UserRoleProductCategoryBranchMapping;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.model.UsrBase;
import com.boc.response.BranchBaseRs;
import com.boc.response.DocumentProductSecurityRs;
import com.boc.response.LoanInterestRateVarianceResponse;
import com.boc.response.ProductLoanDetailsResponse;
import com.boc.response.UserBranchRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserRLCBranchRs;

import antlr.StringUtils;

@Repository
public class ProductDaoImpl  extends abstractWFConfigdao implements ProductDao {
	private static Logger log =LoggerFactory.getLogger(ProductDaoImpl.class);
	
	
	public void getPayeeTypes() {
       
		log.info("payee db access..............");
		
		String payeeTypesQry = null;
		List<String> lstPayeeType =null;
		
		try{
			payeeTypesQry = "select distinct PAYEE_TYPE from BSR_PAYEE_BASE";
			log.info("Query is "+payeeTypesQry);
			Query qry = session().createQuery(payeeTypesQry);
			
			
			if(qry != null){			
				lstPayeeType  = qry.list();
				log.info("getPayeeList : " + lstPayeeType.get(0));
			}else{
				log.info("Null object query");
			}
			
		}catch(HibernateException hex)
		{
			log.info("Error in getPayee");
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProfession",hex);
		}
		
	}
	
	public void getProduct()
	{
		String query = null; 
		query = "from ProductBase";
		Query qry = session().createQuery(query);
		List<ProductBase> lstProduct = qry.list();
		
		if(lstProduct !=null)
		{
			log.info("lstProduct is "+lstProduct.size());
			for(ProductBase productBase:lstProduct)
			{
				log.info("Product Description is "+productBase.getProductDescription());
			}
		}
	}
	
	public UserBranchRs getBranchByUserId(String userId)
	{
		//userId.toU
		String branchName = null;
		String branchCode = null;
		Set<RlcBranchMapping> rlcBranchMappingSet = null;
		Set<AreaBranchMapping> areaBranchMappingsSet=null;
		Set<ProvinceBranchMapping> provinceBranchMappingSet=null;
		Iterator areaBranchMappingsIter = null,rlcBranchMappingIterator = null,provinceBranchMappingIter=null;
		
		//HashMap branchMap = null;
		//branchMap = new HashMap();
		String brachQry = null;
		UserBranchRs usrbranchResponse = new UserBranchRs();
		try
		{
			//brachQry = " Select usrBase.branchBase.branchCode,usrBase.branchBase.branchName,usrBase.branchBase.branchContactNumber from UsrBase usrBase where ntId=:userId" ;
			//productQry = "select productBase.productName from ProductBase productBase join productBase.professionProductMappings professionMappings where professionMappings.professionList.professionType=:professionName";
			brachQry = " select branchBase from BranchBase branchBase join branchBase.usrBases usrBase where usrBase.ntId=:userId";
			Query qry = session().createQuery(brachQry).setString("userId", userId.toUpperCase());
			List<BranchBase> lstbranch  = qry.list();
			for(int i=0;i<lstbranch.size();i++)
			{
				BranchBase branchbase = (BranchBase)lstbranch.get(i);
				if(null!=branchbase)
				{
					usrbranchResponse.setBranchCode(branchbase.getBranchCode());
					usrbranchResponse.setBranchName(branchbase.getBranchName());
					usrbranchResponse.setBranchContactNumber(branchbase.getBranchContactNumber());
				}
				
				rlcBranchMappingSet = branchbase.getRlcBranchMappings();
				rlcBranchMappingIterator = rlcBranchMappingSet.iterator();
				while(rlcBranchMappingIterator.hasNext())
				{
					RlcBranchMapping rlcBranchMapping = (RlcBranchMapping) rlcBranchMappingIterator.next();
					if(null!=rlcBranchMapping)
					{
						usrbranchResponse.setRlcCode(rlcBranchMapping.getRlcBase().getRlcCode());
						usrbranchResponse.setRlcName(rlcBranchMapping.getRlcBase().getRlcName());
					}
				}
				areaBranchMappingsSet = branchbase.getAreaBranchMappings();
				areaBranchMappingsIter = areaBranchMappingsSet.iterator();
				while(areaBranchMappingsIter.hasNext())
				{
					AreaBranchMapping areaBranchMappings = (AreaBranchMapping) areaBranchMappingsIter.next();
					if(null!=areaBranchMappings)
					{
						//if(areaBranchMappings.getAreaBase().getAreaCode())?null:usrbranchResponse.setAreaCode(areaBranchMappings.getAreaBase().getAreaCode());
						usrbranchResponse.setAreaCode(areaBranchMappings.getAreaBase().getAreaCode());
						usrbranchResponse.setAreaName(areaBranchMappings.getAreaBase().getAreaName());
					}
				}
				provinceBranchMappingSet =  branchbase.getProvinceBranchMappings();
				provinceBranchMappingIter =  provinceBranchMappingSet.iterator();
				while(provinceBranchMappingIter.hasNext())
				{
					ProvinceBranchMapping provinceBranchMapping = (ProvinceBranchMapping) provinceBranchMappingIter.next();
					if(null!=provinceBranchMapping)
					{
						usrbranchResponse.setProvinceCode(provinceBranchMapping.getProvinceBase().getProvinceCode());
						usrbranchResponse.setProvinceName(provinceBranchMapping.getProvinceBase().getProvinceName());
					}
				}
			}
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBranchCode",hex);
		}
		return usrbranchResponse;
		
		
	}
	
	public List<BranchBaseRs> getAllBranches()
	{
		List<BranchBaseRs> branchBaseRsLst = null;
		branchBaseRsLst = new ArrayList<BranchBaseRs>();
		String branchQry = "Select distinct bid,branchCode,branchName from BranchBase order by branchName";
		log.debug("Query is "+branchQry);
		Query qry = session().createQuery(branchQry);
		List<Object[]> lstBranch  = qry.list();
		if(lstBranch !=null)
		{
			//log.debug("branch List is "+lstBranch.size());
			
			for(Object[] branch:lstBranch)
			{
				BranchBaseRs branchBase = new BranchBaseRs();
				if(null !=branch[0])
				{
					branchBase.setBid((Integer)branch[0]);
				}
				if(null !=branch[1])
				{
					branchBase.setBranchCode((String)branch[1]);
				}
				if(null !=branch[2])
				{
					branchBase.setBranchName((String)branch[2]);
				}
				branchBaseRsLst.add(branchBase);
			}
			
		}
		return branchBaseRsLst;
	}
	
	public BranchBaseRs getBranchCodeByBranchname(String branchName)
	{
		BranchBaseRs branchBase = null;
		String branchQry = "Select branchBase from BranchBase branchBase where branchBase.branchName=:branchName";
		log.debug("Query is "+branchQry);
		Query qry = session().createQuery(branchQry).setParameter("branchName", branchName);
		List<BranchBase> lstBranch  = qry.list();
		if(lstBranch !=null)
		{
				branchBase  = new BranchBaseRs();
				if(null !=lstBranch && lstBranch.size()>0)
				{
					branchBase.setBid((Integer)lstBranch.get(0).getBid());
				
					branchBase.setBranchCode((String)lstBranch.get(0).getBranchCode());
				
					branchBase.setBranchName((String)lstBranch.get(0).getBranchName());
				}
		}
		return branchBase;
		
	}
	
	public UserBranchRs getBranchByBranchCode(String branchCode)
	{
		//userId.toU
		String branchName = null;
		Set<RlcBranchMapping> rlcBranchMappingSet = null;
		rlcBranchMappingSet = new HashSet<RlcBranchMapping>();
		Set<AreaBranchMapping> areaBranchMappingsSet=null;
		Set<ProvinceBranchMapping> provinceBranchMappingSet=null;
		Iterator areaBranchMappingsIter = null,rlcBranchMappingIterator = null,provinceBranchMappingIter=null;
		String brachQry = null;
		UserBranchRs usrbranchResponse = new UserBranchRs();
		try
		{
			brachQry = " select branchBase from BranchBase branchBase where branchBase.branchCode=:branchCode";
			Query qry = session().createQuery(brachQry).setString("branchCode", branchCode);
			List<BranchBase> lstbranch  = qry.list();
			for(int i=0;i<lstbranch.size();i++)
			{
				BranchBase branchbase = (BranchBase)lstbranch.get(i);
				if(null!=branchbase)
				{
					usrbranchResponse.setBranchCode(branchbase.getBranchCode());
					usrbranchResponse.setBranchName(branchbase.getBranchName());
					usrbranchResponse.setBranchContactNumber(branchbase.getBranchContactNumber());
				}
				
				rlcBranchMappingSet = branchbase.getRlcBranchMappings();
				rlcBranchMappingIterator = rlcBranchMappingSet.iterator();
				while(rlcBranchMappingIterator.hasNext())
				{
					RlcBranchMapping rlcBranchMapping = (RlcBranchMapping) rlcBranchMappingIterator.next();
					if(null!=rlcBranchMapping)
					{
						usrbranchResponse.setRlcCode(rlcBranchMapping.getRlcBase().getRlcCode());
						usrbranchResponse.setRlcName(rlcBranchMapping.getRlcBase().getRlcName());
					}
				}
				areaBranchMappingsSet = branchbase.getAreaBranchMappings();
				areaBranchMappingsIter = areaBranchMappingsSet.iterator();
				while(areaBranchMappingsIter.hasNext())
				{
					AreaBranchMapping areaBranchMappings = (AreaBranchMapping) areaBranchMappingsIter.next();
					if(null!=areaBranchMappings)
					{
						//if(areaBranchMappings.getAreaBase().getAreaCode())?null:usrbranchResponse.setAreaCode(areaBranchMappings.getAreaBase().getAreaCode());
						usrbranchResponse.setAreaCode(areaBranchMappings.getAreaBase().getAreaCode());
						usrbranchResponse.setAreaName(areaBranchMappings.getAreaBase().getAreaName());
					}
				}
				provinceBranchMappingSet =  branchbase.getProvinceBranchMappings();
				provinceBranchMappingIter =  provinceBranchMappingSet.iterator();
				while(provinceBranchMappingIter.hasNext())
				{
					ProvinceBranchMapping provinceBranchMapping = (ProvinceBranchMapping) provinceBranchMappingIter.next();
					if(null!=provinceBranchMapping)
					{
						usrbranchResponse.setProvinceCode(provinceBranchMapping.getProvinceBase().getProvinceCode());
						usrbranchResponse.setProvinceName(provinceBranchMapping.getProvinceBase().getProvinceName());
					}
				}
			}
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBranchCode",hex);
		}
		return usrbranchResponse;
	
	}
	
	public List<String> getProfession()
	{
		String professionQry = null;
		List<String> lstProfession = null;
		try
		{
		professionQry = "Select professionLst.professionType From ProfessionList professionLst";
		Query qry = session().createQuery(professionQry);
		lstProfession = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProfession",hex);
		}
		return lstProfession;
	}
	
	public List<String> getEmployer()
	{
		String employerQry = null;
		List<String> lstEmployer = null;
		try
		{
			employerQry = "Select employerBase.empolyerName From PrivateEmployerBase employerBase";
			Query qry = session().createQuery(employerQry);
			lstEmployer= qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getEmployer",hex);
		}
		return lstEmployer;
	}
	
	public List<String> getProductsByProfession(String professionName)
	{
		List<String> lstProduct = null;
		String productQry = null;
		try
		{
			productQry = "select productBase.productName from ProductBase productBase join productBase.professionProductMappings professionMappings where professionMappings.professionList.professionType=:professionName";
			Query qry = session().createQuery(productQry).setString("professionName", professionName);
			lstProduct = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductByProfession",hex);
		}
		return lstProduct;
	}
	
	public ProductLoanDetailsResponse getProductLoanDetails(String productName)
	{
		ProductLoanDetailsResponse productLoanDetailRs = null;
		String productLoanDetailsQry = null; 
		try
		{
			productLoanDetailsQry =	"Select productLoanDetails.maxRepaymentPeriod,productLoanDetails.interestRateFixed,productLoanDetails.interestRateVariable1,productLoanDetails.interestRateVariable2,productLoanDetails.productBase.productCode,productLoanDetails.minAge,productLoanDetails.maxAge,productLoanDetails.maxLoanAmount,productLoanDetails.percentageOfGrossSalary,productLoanDetails.percentageOfOtherIncome,productLoanDetails.productBase.pid from ProductLoanDetails productLoanDetails where productLoanDetails.productBase.productName=:productName";
			Query qry = session().createQuery(productLoanDetailsQry).setString("productName", productName);
			//List<ProductLoanDetails> lstProductLoanDetail = qry.list();
			log.info("Executed query"+productLoanDetailsQry);
			List<Object[]> lstProductLoanDetail = qry.list();
			
			if(null !=lstProductLoanDetail && lstProductLoanDetail.size()>0)
			{
				for(Object[] productLoanDet:lstProductLoanDetail)
				{
					productLoanDetailRs = new ProductLoanDetailsResponse();
					if(null!=productLoanDet[0])
						productLoanDetailRs.setMaxRepaymentPeriod((Integer)productLoanDet[0]);
					if(null!=productLoanDet[1])
						productLoanDetailRs.setInterestRateFixed((BigDecimal)productLoanDet[1]);
					if(null!=productLoanDet[2])
						productLoanDetailRs.setInterestRateVariable1((BigDecimal)productLoanDet[2]);
					if(null!=productLoanDet[3])
						productLoanDetailRs.setInterestRateVariable2((BigDecimal)productLoanDet[3]);
					if(null!=productLoanDet[4])
						productLoanDetailRs.setProductCode((String)productLoanDet[4]);
					if(null!=productLoanDet[5])
						productLoanDetailRs.setMinAge((Integer)productLoanDet[5]);
					if(null!=productLoanDet[6])
						productLoanDetailRs.setMaxAge((Integer)productLoanDet[6]);
					if(null!=productLoanDet[7])
						productLoanDetailRs.setMaxLoanAmount((Double)productLoanDet[7]);
					if(null!=productLoanDet[8])
						productLoanDetailRs.setPercentageGrossSalary((BigDecimal)productLoanDet[8]);
					if(null!=productLoanDet[9])
						productLoanDetailRs.setPercentageOtherIncome((BigDecimal)productLoanDet[9]);
					if(null!=productLoanDet[10])
						productLoanDetailRs.setPid((Integer)productLoanDet[10]);
				}
				log.info("==========lstProductLoanDetail size is "+lstProductLoanDetail.size());
				//productLoanDetail = lstProductLoanDetail.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductLoanDetails",hex);
		}
		
		return productLoanDetailRs;
	}
	
	public LoanInterestRateVarianceResponse getProductLoanVarianceDetails(int pid,int repaymentPeriod)
	{
		LoanInterestRateVarianceResponse loanInterestVariance = null;
		String productLoanDetailsQry = null; 
		try
		{
			//productLoanDetailsQry =	"Select productLoanDetails.maxRepaymentPeriod,productLoanDetails.interestRateFixed,productLoanDetails.interestRateVariable1,productLoanDetails.interestRateVariable2,productLoanDetails.productBase.productCode,productLoanDetails.minAge,productLoanDetails.maxAge,productLoanDetails.maxLoanAmount,productLoanDetails.percentageOfGrossSalary,productLoanDetails.percentageOfOtherIncome from ProductLoanDetails productLoanDetails where productLoanDetails.productBase.productName=:productName";
			productLoanDetailsQry =	"Select loanVarianceDetails.varianceFixed,loanVarianceDetails.varianceRateVariable1,loanVarianceDetails.varianceRateVariable2 from LoanInterestRateVariance loanVarianceDetails where loanVarianceDetails.productBase.pid="+pid+" and loanVarianceDetails.minRepaymentPeriod <"+repaymentPeriod+" and loanVarianceDetails.maxRepaymentPeriod >="+repaymentPeriod+"";
			Query qry = session().createQuery(productLoanDetailsQry);
			//List<ProductLoanDetails> lstProductLoanDetail = qry.list();
			log.info("Executed query"+productLoanDetailsQry);
			List<Object[]> lstProductLoanDetail = qry.list();
			
			if(null !=lstProductLoanDetail && lstProductLoanDetail.size()>0)
			{
				for(Object[] productLoanDet:lstProductLoanDetail)
				{
					loanInterestVariance = new LoanInterestRateVarianceResponse();
					if(null!=productLoanDet[0])
						loanInterestVariance.setVarianceRateFixed((BigDecimal)productLoanDet[0]);
					if(null!=productLoanDet[1])
						loanInterestVariance.setVarianceRateVariable1((BigDecimal)productLoanDet[1]);
					if(null!=productLoanDet[2])
						loanInterestVariance.setVarianceRateVariable2((BigDecimal)productLoanDet[2]);
				}
				log.info("==========lstProductLoanDetail size is "+lstProductLoanDetail.size());
				//productLoanDetail = lstProductLoanDetail.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductLoanDetails",hex);
		}
		
		return loanInterestVariance;
	}
	
	public List<String> getDocumentCheckByProduct(String productName)
	{
		String productDocumentQry = null;
		List<String> lstProductDocumentChecklst = null;
		try
		{
			productDocumentQry = "Select documentTypeBase.documentType from DocumentTypeBase documentTypeBase join "
					+ " documentTypeBase.productDocumentChecklistMappings productCheckListMappings where productCheckListMappings.productBase.productName=:productName";
			Query qry = session().createQuery(productDocumentQry).setString("productName", productName);
			lstProductDocumentChecklst = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getDocumentList",hex);
		}
		return lstProductDocumentChecklst;
	}
	
	public List<String> getSecurityDocumentCheckByProduct(String productName)
	{
		String productDocumentQry = null;
		List<String> lstProductDocumentChecklst = null;
		try
		{
			productDocumentQry = "Select documentTypeBase.documentType from DocumentTypeBase documentTypeBase join "
					+ " documentTypeBase.productDocumentChecklistMappings productCheckListMappings where productCheckListMappings.productBase.productName=:productName";
			Query qry = session().createQuery(productDocumentQry).setString("productName", productName);
			lstProductDocumentChecklst = qry.list();		
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getSecurityDocumentList",hex);
		}
		return lstProductDocumentChecklst;
	}
	
	public int getRoleIdByName(String roleName)
	{
		int roleId = 0;
		String roleQry = null; 
		try
		{
			roleQry = "Select roleBase.rid from RoleBase roleBase where roleBase.roleName=:roleName";
			Query qry = session().createQuery(roleQry).setString("roleName", roleName);
			List<Integer> lstRole  = qry.list();
			if(lstRole !=null && lstRole.size()>0)
			{
				roleId = lstRole.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getRoleIdByName",hex);
		}
		return roleId;
	}
	
	public int getUserIdByName(String ntId)
	{
		int userId = 0;
		String roleQry = null; 
		try
		{
			roleQry = " select usr.uid from UsrBase usr where usr.ntId=:ntId";
			Query qry = session().createQuery(roleQry).setString("ntId", ntId.toUpperCase());
			List<Integer> lstUsr  = qry.list();
			if(lstUsr !=null && lstUsr.size()>0 )
			{
				userId = lstUsr.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getUserIdByName",hex);
		}
		return userId;
	}
	
	public String getProductCodebyName(String productName)
	{
		String productCode = null;
		String productIdQry = null;
		try
		{
			productIdQry = "Select productBase.productCode from ProductBase productBase where productBase.productName=:productName";
			Query qry = session().createQuery(productIdQry).setString("productName", productName);
			List<String> lstProduct  = qry.list();
			if(lstProduct !=null && lstProduct.size() > 0)
			{
				productCode = lstProduct.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCodeByName",hex);
		}
		return productCode;
	}
	
	public String getBranchCodebyName(String branchName)
	{
		String branchCode = null;
		String branchIdQry = null;
		try
		{
			branchIdQry = "Select branchBase.branchCode from BranchBase branchBase where branchBase.branchName=:branchName";
			Query qry = session().createQuery(branchIdQry).setString("branchName", branchName);
			List<String> lstBranch  = qry.list();
			if(lstBranch !=null && lstBranch.size() > 0)
			{
				branchCode = lstBranch.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBranchCodebyName",hex);
		}
		return branchCode;
	}
	
	public String getProductCategory(String productName)
	{
		String productCategory = null;
		String productCategoryQry = null;
		try
		{
			productCategoryQry = "Select productBase.productCategoryBase.productCategory from ProductBase productBase where productBase.productName=:productName";
			Query qry = session().createQuery(productCategoryQry).setString("productName", productName);
			List<String> lstProduct  = qry.list();
			if(lstProduct !=null && lstProduct.size()>0)
			{
				productCategory = lstProduct.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategory",hex);
		}
		return productCategory;
	}
	
	public DocumentProductSecurityRs getSecuredDocuments(List<String> securityList, String productName)
	{
		/*List<DocumentProductSecurityRs> documentProductSecurityRsLst = null;
		documentProductSecurityRsLst = new ArrayList<DocumentProductSecurityRs>();*/
		DocumentProductSecurityRs documentProductSecurityRs = null;
		String  securityStr = null;
		String[] strArray = null;
		String unionQry = null;
		List<String> documentNameLst = null;
		List<Character> scanrequiredlst = null;
		List<Character> mandatoryLst = null;
		
		try
		{
			if(null!=securityList)
			{
				strArray = securityList.toArray(new String[0]);
				securityStr = singleQuoteAndComma(strArray);
			}
			String documentLstQry = "select documentBase.document_type as docType,prodDocumentMapping.scan_required as required,prodDocumentMapping.mandatory as mandatory from Product_Document_CheckList_Mapping prodDocumentMapping join Document_Type_Base documentBase on prodDocumentMapping.DOCUMENT_ID = documentBase.DID join Product_Base productBase on prodDocumentMapping.product_id=productBase.PID  where productBase.product_name='"+productName+"'";
			String productLstQry = "Select documentBase.document_type as docType,securityDocumentMapping.scan_required as required,securityDocumentMapping.mandatory as mandatory from Security_Type_Document_CheckList_Mapping securityDocumentMapping join Document_Type_Base documentBase on securityDocumentMapping.DOCUMENT_ID = documentBase.DID join Security_Type_Base securityTypeBase on securityDocumentMapping.security_type_id=securityTypeBase.security_type_id where securityTypeBase.nature_of_security in ("+securityStr+")";
			if(securityStr!=null)
				//#updated by vikshith : This code will get distinct records
				unionQry = "Select distinct AUnion.docType, AUnion.required, AUnion.mandatory from ("+documentLstQry+" union "+productLstQry+") AUnion";
			else
				//#updated by vikshith : This code will get distinct records
				unionQry = documentLstQry;
			
			log.info("Query is "+unionQry);
			//Query qry = session().createSQLQuery(documentLstQry).setParameter("productName", productName);
			Query qry = session().createSQLQuery(unionQry);//.setParameter("productName", productName).setParameterList("securityList", securityList);
			List<Object[]> lstproductDocumentLst = qry.list();
			if(null != lstproductDocumentLst)
			{
				documentProductSecurityRs = new DocumentProductSecurityRs();
				documentNameLst = new ArrayList<String>();
				scanrequiredlst = new ArrayList<Character>();
				mandatoryLst = new ArrayList<Character>();
				log.info("lstproductDocumentLst size is"+lstproductDocumentLst.size());
				for(Object[] documentArray:lstproductDocumentLst)
				{
					if(null!=documentArray[0])
					{
						documentNameLst.add((String)documentArray[0]);
						//documentProductSecurityRs.setDocumentName((String)documentArray[0]);
						//System.out.println(documentArray[0]);
					}
					if(null!=documentArray[1])
					{
						scanrequiredlst.add((Character)documentArray[1]);
						//documentProductSecurityRs.setScanRequired((Character)documentArray[1]);
						//System.out.println(documentArray[1]);
					}
					if(null!=documentArray[2])
					{
						mandatoryLst.add((Character)documentArray[2]);
						//documentProductSecurityRs.setMandatory((Character)documentArray[2]);
						//System.out.println(documentArray[2]);
					}
				}
				documentProductSecurityRs.setDocumentName(documentNameLst.toArray(new String[0]));
				documentProductSecurityRs.setScanRequired(scanrequiredlst.toArray(new Character[0]));
				documentProductSecurityRs.setMandatory(mandatoryLst.toArray(new Character[0]));
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getsecurityDocumentByProduct",hex);
		}
		return documentProductSecurityRs;
	}
	
	//#added by vikshith : get document checklist By securityList, productName and loanPurpose 
	public DocumentProductSecurityRs getDocChklstByProdNameSecurityNPurpose(List<String> securityList, String productName, String loanPurposes)
	{
		DocumentProductSecurityRs documentProductSecurityRs = null;
		String  securityStr = null;
		String[] strArray = null;
		String unionQry = null;
		
		try
		{
			if(null!=securityList)
			{
				strArray = securityList.toArray(new String[0]);
				securityStr = singleQuoteAndComma(strArray);
			}
			String documentLstQry = "select documentBase.document_type as docType,prodDocumentMapping.scan_required as required,prodDocumentMapping.mandatory as mandatory from Product_Document_CheckList_Mapping prodDocumentMapping join Document_Type_Base documentBase on prodDocumentMapping.DOCUMENT_ID = documentBase.DID join Product_Base productBase on prodDocumentMapping.product_id=productBase.PID  where productBase.product_name='"+productName+"'";
			String productLstQry =  "Select documentBase.document_type as docType,securityDocumentMapping.scan_required as required,securityDocumentMapping.mandatory as mandatory from Security_Type_Document_CheckList_Mapping securityDocumentMapping join Document_Type_Base documentBase on securityDocumentMapping.DOCUMENT_ID = documentBase.DID join Security_Type_Base securityTypeBase on securityDocumentMapping.security_type_id=securityTypeBase.security_type_id where securityTypeBase.nature_of_security in ("+securityStr+")";
			String PurposeLstQry =  "Select documentBase.document_type as docType,loanPurposeDocumentMapping.scan_required as required,loanPurposeDocumentMapping.mandatory as mandatory from LOAN_PURPOSES_DOCUMENT_CHECKLIST_MAPPING loanPurposeDocumentMapping join Document_Type_Base documentBase on loanPurposeDocumentMapping.DOCUMENT_ID = documentBase.DID join LOAN_PURPOSES loanPurposes on loanPurposeDocumentMapping.PURPOSE_ID=loanPurposes.PID where loanPurposes.LOAN_PURPOSES='"+loanPurposes+"'";
			if(securityStr!=null)
				unionQry = "Select distinct AUnion.docType, AUnion.required, AUnion.mandatory from ("+documentLstQry+" union "+productLstQry+" union "+PurposeLstQry+") AUnion";
			else
				unionQry = "Select distinct AUnion.docType, AUnion.required, AUnion.mandatory from ("+documentLstQry+" union "+PurposeLstQry+") AUnion";
			
			log.info("Query is "+unionQry);
			
			Query qry = session().createSQLQuery(unionQry);
			List<Object[]> lstproductDocumentLst = qry.list();
			if(null != lstproductDocumentLst)
			{
				documentProductSecurityRs = new DocumentProductSecurityRs();
				List<String> documentNameLst = new ArrayList<String>();
				List<Character> scanrequiredlst = new ArrayList<Character>();
				List<Character> mandatoryLst = new ArrayList<Character>();
				List<String> uploadRequiredLst = new ArrayList<String>();
				log.info("lstproductDocumentLst size is"+lstproductDocumentLst.size());
				
				for(Object[] documentArray:lstproductDocumentLst)
				{
					if(null!=documentArray[0])
					{
						documentNameLst.add((String)documentArray[0]);
						//documentProductSecurityRs.setDocumentName((String)documentArray[0]);
						//System.out.println(documentArray[0]);
					}
					if(null!=documentArray[1])
					{
						scanrequiredlst.add((Character)documentArray[1]);
						//documentProductSecurityRs.setScanRequired((Character)documentArray[1]);
						//System.out.println(documentArray[1]);
					}
					if(null!=documentArray[2])
					{
						mandatoryLst.add((Character)documentArray[2]);
						//documentProductSecurityRs.setMandatory((Character)documentArray[2]);
						//System.out.println(documentArray[2]);
					}
					uploadRequiredLst.add("false");
					
				}
				documentProductSecurityRs.setDocumentName(documentNameLst.toArray(new String[0]));
				documentProductSecurityRs.setScanRequired(scanrequiredlst.toArray(new Character[0]));
				documentProductSecurityRs.setMandatory(mandatoryLst.toArray(new Character[0]));
				documentProductSecurityRs.setUploadRequired(uploadRequiredLst.toArray(new String[0]));
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getsecurityDocumentByProduct",hex);
		}
		return documentProductSecurityRs;
	}
	
	
	private String singleQuoteAndComma(String[] strArray) {
	    String in = "";
	    for (int i = 0; i < strArray.length ; i++) {
	        in += "'" + strArray[i] + "'";
	        if (i != strArray.length - 1) {
	            in += ",";
	        }
	    }
	    return in;
	}
	
	
	public List<String> getRLCCOUsersByRoleBranch(String branchCode,String roleName)
	{
		List<String> usrList = null;
		
		try
		{
		String productCategoryBranchQry = "Select distinct usrBase.NT_ID from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left join "
				+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.USR_BASE usrBase on leftProductCategory.uid=usrBase.uid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.ROLE_BASE rolBase on productCategory.RID=rolBase.RID where rolBase.ROLE_NAME=:roleName) and branchBase.BRANCH_CODE=:branchCode";
		log.info("Query is "+productCategoryBranchQry);
		Query qry = session().createSQLQuery(productCategoryBranchQry).setParameter("roleName", roleName).setParameter("branchCode", branchCode);
		usrList = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return usrList;
	}
	
	public UserProductBranchRs getProductCategoryBranch(String usrName)
	{
		Set<String> productCategoryLst = null;
		Set<String> branchCodeLst = null;
		Set<String> branchNameLst = null;
		Set<String> roleNameLst = null;
		productCategoryLst = new HashSet<String>();
		branchCodeLst = new HashSet<String>();
		branchNameLst = new HashSet<String>();
		roleNameLst = new HashSet<String>();		
		UserProductBranchRs userproductBranch = null; 
		try
		{
			String productCategoryBranchQry = "Select distinct productCategoryBase.Product_Category,branchBase.branch_code,branchBase.branch_name,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left join "
					+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.USR_BASE usrBase "
					+ " on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0)";
					
			log.info("Query is "+productCategoryBranchQry);
			Query qry = session().createSQLQuery(productCategoryBranchQry).setParameter("usrName", usrName.toUpperCase());
			List<Object[]> lstproductCategoryBranch  = qry.list();
			if(lstproductCategoryBranch !=null)
			{
				userproductBranch =	new UserProductBranchRs();
				for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
				{
					if(null !=productCategoryBranchArray[0])
					{
						productCategoryLst.add((String)productCategoryBranchArray[0]);							
					}
					if(null !=productCategoryBranchArray[1])
					{
						branchCodeLst.add((String)productCategoryBranchArray[1]);
					}
					if(null !=productCategoryBranchArray[2])
					{
						branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[3])
					{
						roleNameLst.add((String)productCategoryBranchArray[3]);
					}				
				}
				
				/*String usrBranchQry = "Select distinct branchBase.branch_code,branchBase.branch_name,roleBase.role_name,productCategoryBase.Product_Category from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id join  WFCONFIG.USR_BASE usrBase on leftProductCategory.uid = usrBase.uid join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid  where usrBase.nt_id=:usrName";
				log.info("Query is "+usrBranchQry);
				Query usrQry = session().createSQLQuery(usrBranchQry).setParameter("usrName", usrName.toUpperCase());
				List<Object[]> lstUsrBranch  = usrQry.list();
				if(lstUsrBranch !=null)
				{
					for(Object[] usrBranch:lstUsrBranch)
					{
						if(null!=usrBranch[0])
						{
							branchCodeLst.add((String) usrBranch[0]);
						}
						if(null!=usrBranch[1])
						{
							branchNameLst.add((String) usrBranch[1]);
						}
						if(null!=usrBranch[2])
						{
							roleNameLst.add((String) usrBranch[2]);
						}
						if(null!=usrBranch[3])
						{
							productCategoryLst.add((String) usrBranch[3]);
						}
					}
				}*/
				
				userproductBranch.setBranchCode(branchCodeLst);
				userproductBranch.setBranchName(branchNameLst);
				userproductBranch.setRoleName(roleNameLst);
				userproductBranch.setProductCategoryId(productCategoryLst);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return userproductBranch;
	}
	
	public UserBranchRs getRLCBranchByUserId(String userId)
	{
		String rlcBrachQry = null;
		HashMap branchMap = null;
		branchMap = new HashMap();
		UserBranchRs usrbranchResponse = null;
		usrbranchResponse = new UserBranchRs();
		try
		{
			//rlcBrachQry = " Select rlcBase.rlcCode,rlcBase.rlcName from UsrBase usrBase join usrBase.branchBase  join usrBase.branchBase. rlcBranchMapping join RlcBase rlcBase where ntId=:userId" ;
			//String rlcBranchId = "Select from UsrBase usrBase where usrBase.ntId=";
			//rlcBrachQry = "Select from RlcBranchMapping rlcbranchMapping join rlcbranchMapping.branchBase.  where rlcbranchMapping.branchBase;
			rlcBrachQry = "Select RLCBASE.RLC_CODE,RLCBASE.RLC_NAME from WFCONFIG.RLC_BRANCH_MAPPING RLCBRANCHMAPPING JOIN RLC_BASE RLCBASE ON RLCBRANCHMAPPING.RLC_ID=RLCBASE.RLC_ID JOIN WFCONFIG.BRANCH_BASE BRANCHBASE ON RLCBRANCHMAPPING.BID = BRANCHBASE.BID JOIN WFCONFIG.USR_BASE USRBASE ON BRANCHBASE.BID=USRBASE.BID WHERE USRBASE.NT_ID=:userId";
			Query qry = session().createSQLQuery(rlcBrachQry).setString("userId", userId.toUpperCase());
			List<Object[]> lstUser  = qry.list();
			if(lstUser !=null)
			{
				log.info("lstProduct is "+lstUser.size());
				for(Object[] usrBase:lstUser)
				{
					usrbranchResponse.setBranchCode((String) usrBase[0]);
					usrbranchResponse.setBranchName((String) usrBase[1]);
					//branchMap.put("RLCCode", usrBase[0]);
					//branchMap.put("RLCName", usrBase[1]);
				}
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBranchCode",hex);
		}
		return usrbranchResponse;
	}
	
	
	//# Added by Vikshith : This API will get RLC code, Rlc name, Branch code and branch name  by ntUserId
	public UserRLCBranchRs getRLCBranchDetailsByUserId(String ntId){
		UserRLCBranchRs usrRLCBranchResponse = null;
		try
		{
			if(null != ntId && !ntId.isEmpty())
			{
				Query qry = session().createSQLQuery("Select RLCBASE.RLC_CODE as rlcCode, RLCBASE.RLC_NAME as rlcName, BRANCHBASE.BRANCH_CODE as branchCode, BRANCHBASE.BRANCH_NAME as branchName, BRANCHBASE.BRANCH_CONTACT_NUMBER as branchContactNo from WFCONFIG.RLC_BRANCH_MAPPING RLCBRANCHMAPPING JOIN RLC_BASE RLCBASE ON RLCBRANCHMAPPING.RLC_ID=RLCBASE.RLC_ID JOIN WFCONFIG.BRANCH_BASE BRANCHBASE ON RLCBRANCHMAPPING.BID = BRANCHBASE.BID JOIN WFCONFIG.USR_BASE USRBASE ON BRANCHBASE.BID=USRBASE.BID WHERE USRBASE.NT_ID=:userId")
						.addScalar("rlcCode")
						.addScalar("rlcName")
						.addScalar("branchCode")
						.addScalar("branchName")
						.addScalar("branchContactNo")
						.setResultTransformer(Transformers.aliasToBean(UserRLCBranchRs.class));
				qry.setString("userId", ntId.toUpperCase());
				List<UserRLCBranchRs> lsUserRLCBranchRs =  qry.list();
				
				if(null != lsUserRLCBranchRs && !lsUserRLCBranchRs.isEmpty()){
					usrRLCBranchResponse = lsUserRLCBranchRs.get(0);
				}else
					usrRLCBranchResponse = new UserRLCBranchRs();
			}else{
				throw new DAOException("er.db.getRLCBranch.details.userID.Empty");
			}
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBranchCode",hex);
		}
		return usrRLCBranchResponse;
	}
	
	/*public UserProductBranchRs getProductCategoryRLCBranch1(String usrName)
	{
		Set<String> productCategoryLst = null;
		Set<String> branchCodeLst = null;
		Set<String> branchNameLst = null;
		Set<String> roleNameLst = null;
		productCategoryLst = new HashSet<String>();
		branchCodeLst = new HashSet<String>();
		branchNameLst = new HashSet<String>();
		roleNameLst = new HashSet<String>();		
		UserProductBranchRs userproductBranch = null; 
		try
		{
			String productCategoryBranchQry = "Select productCategoryBase.Product_Category, branchBase.branch_code,branchBase.branch_name,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_RLC_MAPPING productCategoryRLC "
					+ " join WFCONFIG.RLC_BRANCH_MAPPING rlcBranchMapping ON productCategoryRLC.RLC_ID=rlcBranchMapping.RLC_ID  join WFCONFIG.BRANCH_BASE branchBase on rlcBranchMapping.BID = branchBase.BID left join "
					+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategoryRLC.urpcid=leftProductCategory.urpcid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " where productCategoryRLC.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.USR_BASE usrBase "
					+ " on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and productCategory.product_category_id=2)";
					
			
			log.info("Query is "+productCategoryBranchQry);
			Query qry = session().createSQLQuery(productCategoryBranchQry).setParameter("usrName", usrName.toUpperCase());
			List<Object[]> lstproductCategoryBranch  = qry.list();
			if(lstproductCategoryBranch !=null)
			{
				userproductBranch =	new UserProductBranchRs();
				for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
				{
					if(null !=productCategoryBranchArray[0])
					{
						productCategoryLst.add((String)productCategoryBranchArray[0]);							
					}
					if(null !=productCategoryBranchArray[1])
					{
						branchCodeLst.add((String)productCategoryBranchArray[1]);
					}
					if(null !=productCategoryBranchArray[2])
					{
						branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[3])
					{
						roleNameLst.add((String)productCategoryBranchArray[3]);
					}				
				}
				userproductBranch.setBranchCode(branchCodeLst);
				userproductBranch.setBranchName(branchNameLst);
				userproductBranch.setRoleName(roleNameLst);
				userproductBranch.setProductCategoryId(productCategoryLst);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranchRLC",hex);
		}
		
		return userproductBranch;
	}*/
	
	public UserProductBranchRs getProductCategoryRLCBranch(String usrName)
	{
		Set<String> productCategoryLst = null;
		Set<String> branchCodeLst = null;
		Set<String> branchNameLst = null;
		Set<String> roleNameLst = null;
		productCategoryLst = new HashSet<String>();
		branchCodeLst = new HashSet<String>();
		branchNameLst = new HashSet<String>();
		roleNameLst = new HashSet<String>();		
		UserProductBranchRs userproductBranch = null; 
		try
		{
			String productCategoryBranchQry = "Select productCategoryBase.Product_Category, rlcBase.rlc_code,rlcBase.rlc_name,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_RLC_MAPPING productCategoryRLC "
					+ " join WFCONFIG.RLC_BRANCH_MAPPING rlcBranchMapping ON productCategoryRLC.RLC_ID=rlcBranchMapping.RLC_ID  JOIN WFCONFIG.RLC_BASE rlcBase ON rlcBranchMapping.RLC_ID=rlcBase.RLC_ID join WFCONFIG.BRANCH_BASE branchBase on rlcBranchMapping.BID = branchBase.BID left join "
					+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategoryRLC.urpcid=leftProductCategory.urpcid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " where productCategoryRLC.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.USR_BASE usrBase "
					+ " on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and productCategory.product_category_id=2) ";
					
			
			log.info("Query is "+productCategoryBranchQry);
			Query qry = session().createSQLQuery(productCategoryBranchQry).setParameter("usrName", usrName.toUpperCase());
			List<Object[]> lstproductCategoryBranch  = qry.list();
			if(lstproductCategoryBranch !=null)
			{
				userproductBranch =	new UserProductBranchRs();
				for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
				{
					if(null !=productCategoryBranchArray[0])
					{
						productCategoryLst.add((String)productCategoryBranchArray[0]);							
					}
					if(null !=productCategoryBranchArray[1])
					{
						branchCodeLst.add((String)productCategoryBranchArray[1]);
					}
					if(null !=productCategoryBranchArray[2])
					{
						branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[3])
					{
						roleNameLst.add((String)productCategoryBranchArray[3]);
					}				
				}
				userproductBranch.setBranchCode(branchCodeLst);
				userproductBranch.setBranchName(branchNameLst);
				userproductBranch.setRoleName(roleNameLst);
				userproductBranch.setProductCategoryId(productCategoryLst);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranchRLC",hex);
		}
		
		return userproductBranch;
	}
	
	public List<String> getHomeLoanProducts()
	{
		String productQry = null;
		List<String> lstproducts;
		try
		{
			productQry = "select productBase.productName FROM ProductBase productBase WHERE productBase.productCategoryBase.productCategoryId=2";
			Query qry = session().createQuery(productQry);
			lstproducts  = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getHomeLoanProducts",hex);
		}
		return lstproducts;
	}
	
	public List<String> getHomeLoanPurpose()
	{
		String loanPurposeQry = null;
		List<String> lstLoanPurposes;
		try
		{
			loanPurposeQry = "select loanPurpose.loanPurposes FROM LoanPurposes loanPurpose WHERE loanPurpose.productCategoryBase.productCategoryId=2";
			Query qry = session().createQuery(loanPurposeQry);
			lstLoanPurposes  = qry.list();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getHomeLoanPurposes",hex);
		}
		return lstLoanPurposes;
	}
	
	
	/*public List<String> getBranchCode(List<Integer> branchIdLst)
	{
		List<String> branchCodeLst = null;
		try
		{
			String branchCodeQry = "select branchBase.branchCode from BranchBase branchBase where branchBase.bid in (:branchIdLst)";
			Query qry = session().createQuery(branchCodeQry).setParameterList("branchIdLst", branchIdLst);
			branchCodeLst = qry.list();
		}
		catch(HibernateException hex)
		{
			System.out.println(hex.getMessage());
			throw new DAOException("er.db.getBranchCode",hex);
		}
		return branchCodeLst;
	}*/
	
	public int getReferenceNo(final String productName,final String years)
	{
		int referenceNo = 0;
		try
		{
			referenceNo = session().doReturningWork(
			new ReturningWork<Integer>() {
	
				@Override
				public Integer execute(Connection conn) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callableStatement = conn.prepareCall("{Call WFCONFIG.GET_SEQUENCE_NUMBER(?,?,?)}");
					callableStatement.setString(1, productName);
					callableStatement.setString(2, years);
					callableStatement.registerOutParameter(3, new Integer(1));
					callableStatement.execute();
					log.info("OUTPUT IS "+callableStatement.getObject(3));
			        int seqNo = Integer.parseInt((String) callableStatement.getObject(3));
					return seqNo;
				}
				
			});
			log.info("referenceNo is "+referenceNo);
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			log.error("Error ", hex.fillInStackTrace());
			throw new DAOException("er.db.getReferenceNo",hex);
		}
		return referenceNo;
	}
	
	public int getCasReferenceNo(final String branchCode,final String years)
	{
		int referenceNo = 0;
		try
		{
			referenceNo = session().doReturningWork(
			new ReturningWork<Integer>() {
	
				@Override
				public Integer execute(Connection conn) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callableStatement = conn.prepareCall("{Call WFCONFIG.GET_CAS_SEQUENCE_NUMBER(?,?,?)}");
					callableStatement.setString(1, branchCode);
					callableStatement.setString(2, years);
					callableStatement.registerOutParameter(3, new Integer(1));
					callableStatement.execute();
					log.info("OUTPUT IS "+callableStatement.getObject(3));
			        int seqNo = Integer.parseInt((String) callableStatement.getObject(3));
					return seqNo;
				}
				
			});
			log.info("referenceNo is "+referenceNo);
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			log.error("Error ", hex.fillInStackTrace());
			throw new DAOException("er.db.getReferenceNo",hex);
		}
		return referenceNo;
	}

	public String getCollateralDescription(String collateralCode)
	{
		String collateralQry=null,collateralDesc=null;
		try
		{
			collateralQry = " select collateral.collateralDescription from CollateralBase collateral where collateral.collateralCode=:collateralCode";
			Query qry = session().createQuery(collateralQry).setString("collateralCode", collateralCode);
			List<String> lstCollateral  = qry.list();
			if(lstCollateral !=null && lstCollateral.size()>0 )
			{
				collateralDesc = lstCollateral.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getCollateralDescByCode",hex);
		}
		return collateralDesc;
	}
	
	public List<String> getIndexByProduct(String productName)
	{
		String indexQry = null;
		List<String> indexDescList = null;
		int indexId= 0;
		String indexDesc = null;
		String indexIdDesc = null;
		try
		{
			indexQry = "Select productIndexMapping.indexIdBase.description,productIndexMapping.indexIdBase.idxId from LoanProductIndexIdMapping productIndexMapping where productIndexMapping.productBase.productName=:productName";
			Query qry = session().createQuery(indexQry).setString("productName", productName);
			List<Object[]> lstIndex  = qry.list();
			if(lstIndex !=null && lstIndex.size()>0 )
			{
				indexDescList = new ArrayList<String>();
				for(Object[] index:lstIndex)
				{
					if(null !=index[0])
					{
						indexDesc = (String) index[0];
					}
					if(null != index[1])
					{
						indexId = (Integer) index[1];
					}
					indexIdDesc = indexDesc+"("+indexId+")";
					indexDescList.add(indexIdDesc);
				}
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getCollateralDescByCode",hex);
		}
		return indexDescList;
	}
	
	public BigDecimal getInterestRateByIndex(String indexId)
	{
		BigDecimal interestRate = null;
		String indexIdQry = null;
		indexIdQry = "Select indexIdBase.indexRate from IndexIdBase indexIdBase where indexIdBase.idxId=:indexId";
		Query qry = session().createQuery(indexIdQry).setInteger("indexId", Integer.parseInt(indexId));
		List<BigDecimal> indexIdList = qry.list();
		if(null!= indexIdList && indexIdList.size() >0 )
		{
			for(BigDecimal indexInterestRate:indexIdList)
			{
				interestRate =  indexInterestRate;
			}
			
		}
		return interestRate;
	}
	
	public String getNationalityByCode(String citizenCode)
	{
		String nationality = null;
		log.info("citizenCode is "+citizenCode);
		System.out.println("citizenCode is "+citizenCode);
		String citizenQry = "Select nationality.description from NationalityMaster nationality where nationality.citizencode=:citizenCode";
		Query qry = session().createQuery(citizenQry).setString("citizenCode", citizenCode);
		List<String> lstNationality  = qry.list();
		if(lstNationality !=null && lstNationality.size() > 0)
		{
			nationality = lstNationality.get(0);
		}
		return nationality;
	}
	
	
	public int getActApplicationId(final String yearStr,final String branchCode)
	{
		int seqNo=0;
        try 
        {
        	seqNo = session().doReturningWork(
        		new ReturningWork<Integer>() {
    				@Override
    				public Integer execute(Connection conn) throws SQLException {
    					// TODO Auto-generated method stub
    					CallableStatement callableStatement = conn.prepareCall("{Call WFCONFIG.GET_CAS_ACT_APPLICATION_ID(?,?,?)}");
    					callableStatement.setString(1, branchCode);
    					callableStatement.setString(2, yearStr);
    					callableStatement.registerOutParameter(3, new Integer(1));
    					callableStatement.execute();
    					log.info("OUTPUT IS "+callableStatement.getObject(3));
    			        int seqNo = Integer.parseInt((String) callableStatement.getObject(3));
    					return seqNo;
    				}
    				
    			});
    			log.info("seqNo is "+seqNo);
        }
        catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			log.error("Error ", hex.fillInStackTrace());
		}
        return seqNo;
	}
	
	//Added by Tushar
	public String calculate(String prefCustomer,String srcofin,String profcode,String citizenship){
		String result=null,indexQry=null;
		int prefcust,prcode,srcofinc,cit,prefcustw,srcofincw,prcodew,citw,res;
	
		try{
			
				indexQry = "Select picklistvalues.picklistFilter FROM PicklistvaluesMaster picklistvalues where (picklistvalues.picklistCode=:prefCustomer and picklistvalues.picklistName=:prefCustomerKey) or (picklistvalues.picklistCode=:SourceofIncome and picklistvalues.picklistName=:SourceofIncomeKey) or (picklistvalues.picklistCode=:ProfessionCode and picklistvalues.picklistName=:ProfessionCodeKey) or (picklistvalues.picklistCode=:Citizenship and picklistvalues.picklistName=:CitizenshipKey)";
				String[] prefCustomerArr = prefCustomer.split("=");
				String[] sourceofIncomeArr = srcofin.split("=");
				String[] professionCodeArr = profcode.split("=");
				String[] citizenshipArr = citizenship.split("=");
				
				Query qry = session().createQuery(indexQry).setString("prefCustomer", prefCustomerArr[1]).setString("prefCustomerKey", prefCustomerArr[0]).setString("SourceofIncome", sourceofIncomeArr[1]).setString("SourceofIncomeKey", sourceofIncomeArr[0]).setString("ProfessionCode", professionCodeArr[1]).setString("ProfessionCodeKey", professionCodeArr[0]).setString("Citizenship", citizenshipArr[1]).setString("CitizenshipKey", citizenshipArr[0]);
				//Query qry = session().createQuery(indexQry).setString("prefCustomer", prefCustomerArr).setString("prefCustomerKey", prefCustomer).setString("SourceofIncome", srcofin).setString("SourceofIncomeKey", srcofin).setString("ProfessionCode", profcode).setString("ProfessionCodeKey", profcode).setString("Citizenship", citizenship).setString("CitizenshipKey", citizenship);
				List<String> citizen = qry.list();
				System.out.println(citizen);
				prefcust=Integer.parseInt((String)citizen.get(0));
				srcofinc=Integer.parseInt((String)citizen.get(1));
				prcode=Integer.parseInt((String)citizen.get(2));
				cit=Integer.parseInt((String)citizen.get(3));
					
				ResourceBundle rsbundle = ResourceBundle.getBundle("restconfig");
		
				prefcustw=Integer.parseInt((String)rsbundle.getString("prefCustomerweight"));
				srcofincw =Integer.parseInt((String)rsbundle.getString("soiweight"));
				prcodew =Integer.parseInt((String)rsbundle.getString("profcoweight"));
				citw =Integer.parseInt((String)rsbundle.getString("citizenshweight"));
				res=(prefcust*prefcustw)+(srcofinc*srcofincw)+(prcode*prcodew)+(cit*citw);
	    
	  	 	if(res>31)
	  	 		{
	  	 			result="3"; //High
	  	 		}
	  	 	
	  	 	else if(res>21 && res<32)
	  	 		{
	  	 			result="2"; //Medium
	  	 		}
	  	 	
	  	 	else 
	    		{
	    	result="1"; //Low
	    		}
	    
			
	}
		
		
		catch(Exception hex)
		{
			hex.printStackTrace();
			log.error(hex.getMessage());
			log.error("Error ", hex.fillInStackTrace());
			/*throw new DAOException(hex);*/
		}
		return result;
	}
		

	public String calculateNp(String customertype,String srcoffund,String bustype,String jrisk){
		String result=null,indexQry=null;
		int custypew,srcoffundsw,btypew,jurisw,custype,btype,srcoffunds,juris,res;
		try{
				String[] customertypeArr = customertype.split("=");
				String[] srcoffundArr = srcoffund.split("=");
				String[] bustypeArr = bustype.split("=");
				String[] jriskArr = jrisk.split("=");
				indexQry = "Select picklistvalues.picklistFilter FROM PicklistvaluesMaster picklistvalues where (picklistvalues.picklistCode=:cty and picklistvalues.picklistName=:ctyKey) or (picklistvalues.picklistCode=:SourceofIncome and picklistvalues.picklistName=:SourceofIncomeKey) or (picklistvalues.picklistCode=:ProfessionCode and picklistvalues.picklistName=:ProfessionCodeKey) or (picklistvalues.picklistCode=:jri and picklistvalues.picklistName=:jriKey)";
				Query qry = session().createQuery(indexQry).setString("cty", customertypeArr[1]).setString("ctyKey", customertypeArr[0]).setString("SourceofIncome", srcoffundArr[1]).setString("SourceofIncomeKey", srcoffundArr[0]).setString("ProfessionCode", bustypeArr[1]).setString("ProfessionCodeKey", bustypeArr[0]).setString("jri", jriskArr[1]).setString("jriKey", jriskArr[0]);
				List<String> citizen = qry.list();
				custype=Integer.parseInt((String)citizen.get(0));
				srcoffunds=Integer.parseInt((String)citizen.get(1));
				btype=Integer.parseInt((String)citizen.get(2));
				juris=Integer.parseInt((String)citizen.get(3));
				ResourceBundle rsbundle = ResourceBundle.getBundle("restconfig");
				custypew =Integer.parseInt((String)rsbundle.getString("custypeweight"));
		 		srcoffundsw =Integer.parseInt((String)rsbundle.getString("sofweight"));
				btypew =Integer.parseInt((String)rsbundle.getString("bustypeweight"));
				jurisw =Integer.parseInt((String)rsbundle.getString("jurisriskweight"));
	    		res=(custype*custypew)+(srcoffunds*srcoffundsw)+(btype*btypew)+(juris*jurisw);
	    
	    			if(res>41)
	    			{
	    				result="3";//High
	    			}
	  	 	
	    			else if(res>27 && res<42)
	    			{
	    				result="2";//Medium
	    			}
	  	 	
	    			else 
	    			{
	    				result="1";//Low
	    			}
	    
	    			
			}
		
		
		catch(Exception hex)
			{
				hex.printStackTrace();
				log.error(hex.getMessage());
				log.error("Error ", hex.fillInStackTrace());
				/*throw new DAOException(hex);*/
			}
		return result;	
	}

	

	

		
}
