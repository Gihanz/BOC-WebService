package com.boc.ws;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
/*
Create By SaiMadan on Jun 14, 2016
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.apache.commons.json.JSONArray;
import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.oasis_open.docs.ns.cmis.core._200908.CmisPropertiesType;
import org.oasis_open.docs.ns.cmis.core._200908.CmisPropertyString;
import org.oasis_open.docs.ns.cmis.core._200908.ObjectFactory;
import org.oasis_open.docs.ns.cmis.messaging._200908.CmisContentStreamType;
import org.oasis_open.docs.ns.cmis.messaging._200908.CmisExtensionType;
import org.oasis_open.docs.ns.cmis.ws._200908.ObjectService;
import org.oasis_open.docs.ns.cmis.ws._200908.ObjectServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boc.fiserv.response.AccountAddResponse;
import com.boc.fiserv.response.AccountLstInqResponse;
import com.boc.fiserv.response.CalcDateResponse;
import com.boc.fiserv.response.ChequeAddServiceResponse;
import com.boc.fiserv.response.CreateCollateralResponse;
import com.boc.fiserv.response.CustomerProfileAddRs;
import com.boc.fiserv.response.CustomerProfileRs;
import com.boc.response.BankBranch;
import com.boc.response.BranchBaseRs;
import com.boc.response.Customer;
import com.boc.response.DocumentProductSecurityRs;
import com.boc.response.HomeLoanProductRs;
import com.boc.response.ProductLoanDetailsResponse;
//import com.boc.response.StandingOrderPayee;
import com.boc.response.UserBranchRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserRLCBranchRs;
import com.boc.service.ComplaintService;
import com.boc.service.exceptions.BSLException;
import com.boc.service.impl.BankBranchServiceImpl;
import com.boc.service.impl.CIFServiceImpl;
import com.boc.service.impl.ComplaintServiceImpl;
import com.boc.service.impl.PayeeServiceImpl;
import com.boc.service.impl.ProductServiceImpl;
@RestController
public class WsServiceImpl extends BaseWebServiceEndPointImpl{
	 //public static Logger log = Logger.getLogger("com.boc.ws.WsServiceImpl");
	 private static Logger log =LoggerFactory.getLogger(WsServiceImpl.class);	
	@RequestMapping(value="/test")
	public String testService()
	{
		productService.getProductService();
		return "it is working fine";
	}
	
	@RequestMapping(value="/getBranchByUserId",produces="application/json")
	public UserBranchRs getBranchByUserId(@RequestParam("ntId") String ntId) 
	{
		//log.info("Hello Sai");
		UserBranchRs userBranchRs = null;
		try
		{
			userBranchRs =  productService.getBranchByUserId(ntId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
			//setError(getExcptnMesProperty(e.getMessage()));
		}
		return userBranchRs;
	}
	
	@RequestMapping(value="/getBranchByBranchCode",produces="application/json")
	public UserBranchRs getBranchByBranchCode(@RequestParam("branchCode") String branchCode)
	{
		UserBranchRs userBranchRs = null;
		try
		{
			userBranchRs =  productService.getBranchByBranchCode(branchCode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
			//setError(getExcptnMesProperty(e.getMessage()));
		}
		return userBranchRs;
	}
	
	@RequestMapping(value="/getBranchCodeByBranchname",produces="application/json")
	public BranchBaseRs getBranchCodeByBranchname(@RequestParam("branchName") String branchName)
	{
		BranchBaseRs branchBaseRs = null;
		try
		{
			branchBaseRs =  productService.getBranchCodeByBranchname(branchName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
			//setError(getExcptnMesProperty(e.getMessage()));
		}
		return branchBaseRs;
	}
	
	@RequestMapping(value="/getProfessionLst",produces="text/plain")
	public String getProfession()
	{
		String profesisionStr = "";
		List<String> professionLst = null;
		professionLst = new ArrayList<String>();
		try
		{
			professionLst = productService.getProfession();
			int i=0;
			for(String profesision:professionLst)
			{
				if(i==0)
					profesisionStr = profesision;
				else
					profesisionStr = profesisionStr+"_"+profesision;
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return configMsgBundle.getString("cmprofessionLst")+"="+profesisionStr;
	}
	
	@RequestMapping(value="/getEmployer",produces="text/plain")
	public String getEmployer()
	{
		String employerStr = "";
		List<String> employerLst = null;
		employerLst = new ArrayList<String>();
		try
		{
			employerLst = productService.getEmployer();
			int i=0;
			for(String employer:employerLst)
			{
				if(i==0)
					employerStr = employer;
				else
					employerStr = employerStr+"_"+employer;
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return configMsgBundle.getString("cmemployerLst")+"="+employerStr;
	}
	
	@RequestMapping(value="/getAllBranches",produces="application/json")
	public List<BranchBaseRs> getAllBranches()
	{
		List<BranchBaseRs> allBranchLst = null;
		allBranchLst = new ArrayList<BranchBaseRs>();
		try
		{
			allBranchLst = productService.getAllBranches();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return allBranchLst;
	}
	
	
	@RequestMapping(value="/getProductsByProfession",produces="text/plain")
	public String getProductsByProfession(@RequestParam("professionName") String professionName)
	{
		String productStr = "";
		List<String> productLst = null;		
		try
		{
			productLst = productService.getProductsByProfession(professionName);
			int i=0;
			for(String productName:productLst)
			{
				if(i==0)
					productStr = productName;
				else
					productStr = productStr+"_"+productName;
				i++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return configMsgBundle.getString("cmproductLst")+"="+productStr;
	}
	@RequestMapping(value="/getProductCategoryByProduct",produces="text/plain")
	public String getProductCategoryByProduct(@RequestParam("productName") String productName)
	{
		String productCategoryName = null;
		try
		{
		productCategoryName = productService.getProductCategoryByProduct(productName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return configMsgBundle.getString("cmProductCategoryId")+"="+productCategoryName;
	}
	
	@RequestMapping(value="/getProductLoanDetails",produces="text/xml")
	public String getProductLoanDetails(@RequestParam("productName") String productName)
	{
		String productCategoryName = null;
		String response = null;
		try
		{
		ProductLoanDetailsResponse productLoanDetails = productService.getProductLoanDetails(productName);
		productCategoryName = productService.getProductCategoryByProduct(productName);
			if(null != productLoanDetails)
			{
			response=configMsgBundle.getString("begXml")+"<"+configMsgBundle.getString("cmrepaymentPeriod")+">"+productLoanDetails.getMaxRepaymentPeriod()+"</"+configMsgBundle.getString("cmrepaymentPeriod")+">"
					+ "<"+configMsgBundle.getString("cminterestRateFixed")+">"+productLoanDetails.getInterestRateFixed()+"</"+configMsgBundle.getString("cminterestRateFixed")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable1")+">"+productLoanDetails.getInterestRateVariable1()+"</"+configMsgBundle.getString("cminterestRateVariable1")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable2")+">"+productLoanDetails.getInterestRateVariable2()+"</"+configMsgBundle.getString("cminterestRateVariable2")+">"
					+"<"+configMsgBundle.getString("cmProductCategoryId")+">"+productCategoryName+"</"+configMsgBundle.getString("cmProductCategoryId")+">"
					+"<"+configMsgBundle.getString("cmproductCode")+">"+productLoanDetails.getProductCode()+"</"+configMsgBundle.getString("cmproductCode")+">"
					+"<"+configMsgBundle.getString("cmMinAge")+">"+productLoanDetails.getMinAge()+"</"+configMsgBundle.getString("cmMinAge")+">"
					+"<"+configMsgBundle.getString("cmMaxAge")+">"+productLoanDetails.getMaxAge()+"</"+configMsgBundle.getString("cmMaxAge")+">"
					+"<"+configMsgBundle.getString("cmMaxLoanAmount")+">"+productLoanDetails.getMaxLoanAmount()+"</"+configMsgBundle.getString("cmMaxLoanAmount")+">"
					+"<"+configMsgBundle.getString("cmGrossPercentage")+">"+productLoanDetails.getPercentageGrossSalary()+"</"+configMsgBundle.getString("cmGrossPercentage")+">"
					+"<"+configMsgBundle.getString("cmOtherIncomePercentage")+">"+productLoanDetails.getPercentageOtherIncome()+"</"+configMsgBundle.getString("cmOtherIncomePercentage")+">"
					+configMsgBundle.getString("endXml");
			}
			else
			{
				throw new BSLException("Product Loan Detail doesn't exists for this product");
			}
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return response;
	}
	
	@RequestMapping(value="/getProductLoanVarianceDetails",produces="text/xml")
	public String getProductLoanVarianceDetails(@RequestParam("productName") String productName)
	{
		String productCategoryName = null;
		String response = null;
		try
		{
		ProductLoanDetailsResponse productLoanDetails = productService.getProductLoanVarianceDetails(productName,0);
		productCategoryName = productService.getProductCategoryByProduct(productName);
			if(null != productLoanDetails)
			{
			response=configMsgBundle.getString("begXml")+"<"+configMsgBundle.getString("cmrepaymentPeriod")+">"+productLoanDetails.getMaxRepaymentPeriod()+"</"+configMsgBundle.getString("cmrepaymentPeriod")+">"
					+"<"+configMsgBundle.getString("cmMaxRepaymentPeriod")+">"+productLoanDetails.getMaxRepaymentPeriod()+"</"+configMsgBundle.getString("cmMaxRepaymentPeriod")+">"
					+ "<"+configMsgBundle.getString("cminterestRateFixed")+">"+productLoanDetails.getInterestRateFixed()+"</"+configMsgBundle.getString("cminterestRateFixed")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable1")+">"+productLoanDetails.getInterestRateVariable1()+"</"+configMsgBundle.getString("cminterestRateVariable1")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable2")+">"+productLoanDetails.getInterestRateVariable2()+"</"+configMsgBundle.getString("cminterestRateVariable2")+">"
					+"<"+configMsgBundle.getString("cmProductCategoryId")+">"+productCategoryName+"</"+configMsgBundle.getString("cmProductCategoryId")+">"
					+"<"+configMsgBundle.getString("cmproductCode")+">"+productLoanDetails.getProductCode()+"</"+configMsgBundle.getString("cmproductCode")+">"
					+"<"+configMsgBundle.getString("cmMinAge")+">"+productLoanDetails.getMinAge()+"</"+configMsgBundle.getString("cmMinAge")+">"
					+"<"+configMsgBundle.getString("cmMaxAge")+">"+productLoanDetails.getMaxAge()+"</"+configMsgBundle.getString("cmMaxAge")+">"
					+"<"+configMsgBundle.getString("cmMaxLoanAmount")+">"+productLoanDetails.getMaxLoanAmount()+"</"+configMsgBundle.getString("cmMaxLoanAmount")+">"
					+"<"+configMsgBundle.getString("cmGrossPercentage")+">"+productLoanDetails.getPercentageGrossSalary()+"</"+configMsgBundle.getString("cmGrossPercentage")+">"
					+"<"+configMsgBundle.getString("cmOtherIncomePercentage")+">"+productLoanDetails.getPercentageOtherIncome()+"</"+configMsgBundle.getString("cmOtherIncomePercentage")+">"
					+configMsgBundle.getString("endXml");
			}
			else
			{
				
				throw new BSLException("Product Loan Detail doesn't exists for this product");
			}
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return response;
	}
	
	@RequestMapping(value="/getProductLoanVarianceDetailsWithRepayment",produces="text/xml")
	public String getProductLoanVarianceDetailsWithRepayment(@RequestParam("productName") String productName, int maxRepaymentPeriod)
	{
		String productCategoryName = null;
		String response = null;
		try
		{
		ProductLoanDetailsResponse productLoanDetails = productService.getProductLoanVarianceDetails(productName,maxRepaymentPeriod);
		productCategoryName = productService.getProductCategoryByProduct(productName);
			if(null != productLoanDetails)
			{
			response=configMsgBundle.getString("begXml")+"<"+configMsgBundle.getString("cmrepaymentPeriod")+">"+productLoanDetails.getMaxRepaymentPeriod()+"</"+configMsgBundle.getString("cmrepaymentPeriod")+">"
					+ "<"+configMsgBundle.getString("cminterestRateFixed")+">"+productLoanDetails.getInterestRateFixed()+"</"+configMsgBundle.getString("cminterestRateFixed")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable1")+">"+productLoanDetails.getInterestRateVariable1()+"</"+configMsgBundle.getString("cminterestRateVariable1")+">"
					+ "<"+configMsgBundle.getString("cminterestRateVariable2")+">"+productLoanDetails.getInterestRateVariable2()+"</"+configMsgBundle.getString("cminterestRateVariable2")+">"
					+"<"+configMsgBundle.getString("cmProductCategoryId")+">"+productCategoryName+"</"+configMsgBundle.getString("cmProductCategoryId")+">"
					+"<"+configMsgBundle.getString("cmproductCode")+">"+productLoanDetails.getProductCode()+"</"+configMsgBundle.getString("cmproductCode")+">"
					+"<"+configMsgBundle.getString("cmMinAge")+">"+productLoanDetails.getMinAge()+"</"+configMsgBundle.getString("cmMinAge")+">"
					+"<"+configMsgBundle.getString("cmMaxAge")+">"+productLoanDetails.getMaxAge()+"</"+configMsgBundle.getString("cmMaxAge")+">"
					+"<"+configMsgBundle.getString("cmMaxLoanAmount")+">"+productLoanDetails.getMaxLoanAmount()+"</"+configMsgBundle.getString("cmMaxLoanAmount")+">"
					+"<"+configMsgBundle.getString("cmGrossPercentage")+">"+productLoanDetails.getPercentageGrossSalary()+"</"+configMsgBundle.getString("cmGrossPercentage")+">"
					+"<"+configMsgBundle.getString("cmOtherIncomePercentage")+">"+productLoanDetails.getPercentageOtherIncome()+"</"+configMsgBundle.getString("cmOtherIncomePercentage")+">"
					+configMsgBundle.getString("endXml");
			}
			else
			{
				throw new BSLException("Product Loan Detail doesn't exists for this product");
			}
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return response;
	}
	
	@RequestMapping(value="/getDocumentCheckByProduct",produces="text/plain")
	public String getDocumentCheckByProduct(@RequestParam("productName") String productName)
	{
		String documentChkStr = "";
		List<String> documentChkLst = null;
		try
		{
			documentChkLst = productService.getDocumentCheckByProduct(productName);
			if(null != documentChkLst)
			{
				int i=0;
				for(String documentChk:documentChkLst)
				{
					if(i==0)
						documentChkStr = productName;
					else
						documentChkStr = documentChkStr+"_"+documentChk;
					i++;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return configMsgBundle.getString("cmdocumentCheck")+"="+documentChkStr;
	}
	
	@RequestMapping(value="/getCIFByAccoutNo",produces="application/json")
	public CustomerProfileRs getCIFByAccoutNo(@RequestParam("accountNo") String accountNo,@RequestParam("actType") String actType)
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		CustomerProfileRs customerResponse = null;
		String cifNo = null;
		String citizenCode = null;
		try
		{
			customerResponse = cifService.getAcctCustInqRq(accountNo,actType);
			citizenCode = customerResponse.getCitizencode();
			if(null!=citizenCode)
			{
			String nationality = productService.getNationalityByCode(citizenCode);
			customerResponse.setNationality(nationality);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return customerResponse;
	}
	
	@RequestMapping(value="/getAddrDetailByCIFNo",produces="application/json")
	public CustomerProfileRs getAddrDetailByCIFNo(@RequestParam("cifNo") String cifNo)
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		CustomerProfileRs customerResponse = null;
		String citizenCode = null;
		try
		{
			customerResponse = cifService.getCustProfBasicInq(cifNo);
			citizenCode = customerResponse.getCitizencode();
			if(null!=citizenCode)
			{
			String nationality = productService.getNationalityByCode(citizenCode);
			customerResponse.setNationality(nationality);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return customerResponse;
	}
	@RequestMapping(value="/getBranchWorklistByUserId",produces="application/json")
	public UserProductBranchRs getBranchWorklistByUserId(@RequestParam("userId") String userId)
	{
		UserProductBranchRs userProductBranchRs = null;
		try
		{
			userProductBranchRs = productService.getProductCategoryBranchByUser(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return userProductBranchRs;
	}
	
	@RequestMapping(value="/getRLCBranchWorklistByUserId",produces="application/json")
	public UserProductBranchRs getRLCBranchWorklistByUserId(@RequestParam("userId") String userId)
	{
		UserProductBranchRs userProductBranchRs = null;
		try
		{
			userProductBranchRs = productService.getProductCategoryRLCBranchByUser(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return userProductBranchRs;
	}
	@RequestMapping(value="/getRLCBranchByUserId",produces="application/json")
	public UserBranchRs getRLCBranchByUserI(@RequestParam("userId") String userId)
	{
		UserBranchRs userBranchResponse = null; 
		try
		{
			userBranchResponse = productService.getRLCBranchByUserId(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return userBranchResponse;
	}
	
	
	//# Added by Vikshith : This API will get RLC code, Rlc name, Branch code and branch name  by ntUserId
	@RequestMapping(value="/getRLCBranchDetailsByUserId",produces="application/json")
	public UserRLCBranchRs getRLCBranchDetailsByUserId(@RequestParam("userId") String userId)
	{
		UserRLCBranchRs userRLCBranchResponse = null; 
		try
		{
			userRLCBranchResponse = productService.getRLCBranchDetailsByUserId(userId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return userRLCBranchResponse;
	}
	
	/*@RequestMapping(value="/getDocsByProdSecurity",produces="text/plain")
	public String getDocsByProdSecurity(@RequestParam("security") String security,@RequestParam("productName") String productName)
	{
		String documentProductSecurityStr = null;
		
		try
		{
			List<DocumentProductSecurityRs> documentProductSecurityRsLst = productService.getSecuredDocumentsByProductSecurity(security, productName);
			
			if(null!=documentProductSecurityRsLst && documentProductSecurityRsLst.size()>0)
			{
				int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=configMsgBundle.getString("begXml")+"<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					else
					{
						documentProductSecurityStr+="<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					i++;
				}
				documentProductSecurityStr=documentProductSecurityStr+configMsgBundle.getString("endXml");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return documentProductSecurityStr;
	}*/
	
	@RequestMapping(value="/getDocsByProdSecurity",produces="application/json")
	public DocumentProductSecurityRs getDocsByProdSecurity(@RequestParam("security") String security,@RequestParam("productName") String productName)
	{
		String documentProductSecurityStr = null;
		DocumentProductSecurityRs documentProductSecurityRs = null;
		try
		{
			documentProductSecurityRs = productService.getSecuredDocumentsByProductSecurity(security, productName);
			
			/*if(null!=documentProductSecurityRsLst && documentProductSecurityRsLst.size()>0)
			{
				int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=configMsgBundle.getString("begXml")+"<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					else
					{
						documentProductSecurityStr+="<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					i++;
				}
				documentProductSecurityStr=documentProductSecurityStr+configMsgBundle.getString("endXml");
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return documentProductSecurityRs;
	}
	
	//#added by vikshith : get document checklist By securityList, productName and loanPurpose 
	@RequestMapping(value="/getDocsByProdSecurityNPurpose",produces="application/json")
	public DocumentProductSecurityRs getDocsByProdSecurityNPurpose(@RequestParam("security") String security, @RequestParam("productName") String productName, @RequestParam("loanPurpose") String loanPurpose)
	{
		String documentProductSecurityStr = null;
		DocumentProductSecurityRs documentProductSecurityRs = null;
		try
		{
			documentProductSecurityRs = productService.getDocChklstByProdNameSecurityNPurpose(security, productName,loanPurpose);
			
			/*if(null!=documentProductSecurityRsLst && documentProductSecurityRsLst.size()>0)
			{
				int i=0;
				for(DocumentProductSecurityRs documentProductSecurityRs:documentProductSecurityRsLst)
				{
					if(i==0)
					{
						documentProductSecurityStr=configMsgBundle.getString("begXml")+"<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					else
					{
						documentProductSecurityStr+="<table index=\""+i+"\"><"+configMsgBundle.getString("cmsecurityDocument")+">"+documentProductSecurityRs.getDocumentName()
						+"</"+configMsgBundle.getString("cmsecurityDocument")+"><"+configMsgBundle.getString("cmscanRequired")+">"+documentProductSecurityRs.getScanRequired()+"</"+configMsgBundle.getString("cmscanRequired")
						+"><"+configMsgBundle.getString("cmmandatory")+">"+documentProductSecurityRs.getMandatory()+"</"+configMsgBundle.getString("cmmandatory")+">";
					}
					i++;
				}
				documentProductSecurityStr=documentProductSecurityStr+configMsgBundle.getString("endXml");
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return documentProductSecurityRs;
	}
	
	
	
	@RequestMapping(value="/getReferenceNoByProductBranch",produces="application/json")
	public String getReferenceNoByProductBranch(@RequestParam("branchCode") String branchCode,@RequestParam("productName") String productName)
	{
		String referenceNo = null;
		try
		{
			referenceNo = productService.getReferenceNo(branchCode,productName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return referenceNo;
	}
		
	@RequestMapping(value="/getIndexByProduct",method=RequestMethod.GET,produces="application/json")
	public List<String> getIndexByProduct(@RequestParam("productName") String productName)
	{
		List<String> indexDescList = null;
		try
		{
			indexDescList = productService.getIndexByProduct(productName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return indexDescList;
		
	}
	
	@RequestMapping(value="/getInterestRateByIndex",method=RequestMethod.GET,produces="application/json")
	public BigDecimal getInterestRateByIndex(@RequestParam("indexId") String indexId)
	{
		BigDecimal interestRate;
		try {
			interestRate = productService.getInterestRateByIndex(indexId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return interestRate;
	}
	
	
	@RequestMapping(value="/CreateCollateral", method=RequestMethod.POST,  produces="application/json", consumes="application/json")
	public CreateCollateralResponse createCollateral(@RequestBody Object formParameters) throws JAXBException, Exception
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		String collateralCodeKey = configMsgBundle.getString("cmCollateralCode");
		String collateralDescKey = configMsgBundle.getString("cmCollateralDesc");
		JSONObject jObject = new JSONObject(formParameters);
		if(jObject.getString(collateralCodeKey).isEmpty())
		{
			log.error("Collateral Code value is empty");
			throw new BSLException("er.db.collateralCodeNotFound");
		}
		String collateralDesc = productService.getCollateralDescription(jObject.getString(collateralCodeKey));
		log.info("collateralDesc obtained from DB is "+collateralDesc);
		CreateCollateralResponse collateralResponse = cifService.createCollateral(formParameters,collateralDescKey,collateralDesc);
		return collateralResponse;
	}
	
	@RequestMapping(value="/getHomeLoanProductPurpose", method=RequestMethod.POST,  produces="application/json")
	public HomeLoanProductRs getHomeLoanProductPurpose()
	{
		HomeLoanProductRs homeLoanProductRs = null;
		homeLoanProductRs = new HomeLoanProductRs();
		
		List<String> lstproducts=null;
		lstproducts = productService.getHomeLoanProducts();
		List<String> lstLoanPurposes=null;
		lstLoanPurposes = productService.getHomeLoanPurposes();
		
		homeLoanProductRs.setProductLst(lstproducts);
		homeLoanProductRs.setLoanPurposesLst(lstLoanPurposes);
		
		return homeLoanProductRs;
	}
	
	@RequestMapping(value="/getRLCCOUsersByRoleBranch", method=RequestMethod.GET, produces="application/json")
	public List<String> getRLCCOUsersByRoleBranch(@RequestParam("branchCode") String branchCode,@RequestParam("roleName") String roleName)
	{
		List<String> coUserLst = null;
		coUserLst = productService.getRLCCOUsersByRoleBranch(branchCode,roleName);
		return coUserLst;
	}
	
	@RequestMapping(value="/getCalcDate",method=RequestMethod.POST,produces="application/json")
	public CalcDateResponse getCalcDate()
	{
		CIFServiceImpl cifService = null; 
		CalcDateResponse calcDateResponse = null;
		cifService =	new CIFServiceImpl();
		try
		{
			calcDateResponse = cifService.getCalcDate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return calcDateResponse;
	}
	
	@RequestMapping(value="/getCasaReferenceNoByBranch",produces="application/json")
	public String getCasaReferenceNoByBranch(@RequestParam("branchCode") String branchCode)
	{
		String referenceNo = null;
		try
		{
			referenceNo = productService.getCasaReferenceNo(branchCode);
			log.info("getCasaReferenceNoByBranch:referenceNo is "+referenceNo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return referenceNo;
	}
	
	@RequestMapping(value="/createCIF",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public CustomerProfileAddRs createCIF(@RequestBody Object cifDetails)
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		CustomerProfileAddRs customerResponse = null;
		String cifNo = null;
		String citizenCode = null;
		try
		{
			String caseType = configMsgBundle.getString("CASCIFCASETYPE");			
			String CASASolutionName = configMsgBundle.getString("CASSOLUTIONNAME");
			String CASACifReferenceNumberKey = configMsgBundle.getString("CASCIFREFERENCENUMBER");
			String CASACIFIDKey = configMsgBundle.getString("CASCIFID");
			String CASAMemoCreatedKey = configMsgBundle.getString("CASCIFMEMOCREATED");
			String CASAEmployeeCreatedKey = configMsgBundle.getString("CASEMPLOYEECREATED");	
			String CASACustomertypeKey = configMsgBundle.getString("CASCUSTOMERTYPE");
			String CASACustomerSybtypeKey = configMsgBundle.getString("CASCUSTOMERSUBTYPE");
			String CASAEmployerNameKey = configMsgBundle.getString("CASEMPLOYERNAME");
			
			customerResponse = cifService.createCIF(cifDetails,CASACifReferenceNumberKey,CASACIFIDKey,CASAMemoCreatedKey,CASAEmployeeCreatedKey,CASACustomertypeKey,CASACustomerSybtypeKey,CASAEmployerNameKey,caseType,CASASolutionName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return customerResponse;
	}
	
	@RequestMapping(value="/getAccountFundingList",method=RequestMethod.GET,produces="application/json")
	public AccountLstInqResponse getAccountFundingList(@RequestParam("cifNumber") String cifNumber)
	{
		AccountLstInqResponse accountLstInqResponse = null;
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		try {
			accountLstInqResponse = cifService.getAccountFundingList(cifNumber);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return accountLstInqResponse;
		
	}
	
	@RequestMapping(value="/openAccount",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public AccountAddResponse openAccount(@RequestBody Object accountDetails)
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		AccountAddResponse acccountAddResponse = null;
		String branchCode=null,applicationId=null;
		try
		{
			JSONObject jAccountObject = new JSONObject(accountDetails);
			if(jAccountObject.has(configMsgBundle.getString("CASBRANCHCODE")))
				branchCode = (String)jAccountObject.getString(configMsgBundle.getString("CASBRANCHCODE"));
			log.info("openAccount:branchCode is "+branchCode);
			if(null!=branchCode)
			{
				applicationId = productService.getActApplicationId(branchCode);
				log.info("openAccount:branchCode is "+branchCode);
				String accountNoKey = configMsgBundle.getString("CASACCOUNTNO");
				String altAddressCheckKey = configMsgBundle.getString("CASALTADDRESSCHECK");
				String checkGuardianKey = configMsgBundle.getString("CASGUARDIANNAME");
				
				acccountAddResponse = cifService.openAccount(jAccountObject, applicationId, accountNoKey,altAddressCheckKey,checkGuardianKey);
			}
			else
			{
				throw new BSLException("BranchCode not Found in Account details");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		return acccountAddResponse;
	}
	
	@RequestMapping(value="/createCheque",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public ChequeAddServiceResponse createCheque(@RequestBody Object chequeDetails)
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		ChequeAddServiceResponse chequeAddServiceResponse = null;
		try
		{
			chequeAddServiceResponse = cifService.createChequeBook(chequeDetails);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
		return chequeAddServiceResponse;
	}
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET,produces="application/json")
	public String accounts(@RequestParam("nicNo") String nicNo,@RequestParam("operation") String operation)
	{
		String output = null;
		if(null!= nicNo)
		{

			
			StringBuffer outputStr = new StringBuffer(); 
			
			String CBOURL = configMsgBundle.getString("CBOURL");
			String restUrl = CBOURL+"/api/DMS/test/nicBr/"+operation+"/"+nicNo;
			URL url;
			try {
				url = new URL(restUrl);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "GET" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setUseCaches( false );

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			
			log.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				outputStr.append(output);
				log.info(output);
			}
			if(null!=outputStr)
				output=outputStr.toString();
			conn.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return output;
	}
	
	//Added by  Mahesh For getting data from Communiucator
	//  Form URL and put into Request header
	@RequestMapping(value="/getDataFromCommunicator",produces="application/json")
	public String getDataFromCommunicator(@RequestHeader HttpHeaders  headers) 
	{
		String finalUrl="";
		String responseValue = null;
		URL url;
		log.info("Headers retrived from Request"+headers);
		if(null!=headers)
		{
			finalUrl =configMsgBundle.getString("CBOURL")+ headers.get("serviceURL").get(0);
			log.info("Final URL is"+finalUrl);

			StringBuffer outputStr = new StringBuffer(); 

			try {
				url = new URL(finalUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setInstanceFollowRedirects( false );
				conn.setRequestMethod( "GET" );
				conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
				conn.setRequestProperty(configMsgBundle.getString("CBOAPI"), configMsgBundle.getString("CBOAPIVALUE"));
				conn.setRequestProperty( "charset", "utf-8");
				conn.setUseCaches( false );

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));


				log.info("Output from Server .... \n");
				while ((responseValue = br.readLine()) != null) {
					outputStr.append(responseValue);
					log.info("Response Recieved from Server"+responseValue);
				}

				if(null!=outputStr)
				{
					responseValue=outputStr.toString();
				}
				br.close();
				conn.disconnect();
				System.out.println(responseValue);

			}  catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
				log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
			}  catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
				log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
			} 
		}else
		{
			log.error("Header not present in requset");
		}


		return responseValue;

	}
	
	@RequestMapping(value="/sendHexImageToCore",produces="application/json",consumes="application/json")
	public String  sendHexImageToCore(@RequestBody JSONObject imageData)
	//public String  sendHexImageToCore(String serviceUrl,MultipartFile file)
	{
		String finalUrl =configMsgBundle.getString("CBOURL")+"image";
		log.info("Final URL is "+finalUrl);
		String responseValue=null;
		StringBuffer outputStr = new StringBuffer(); 
		String input="{";
		
		try {
			//if(null!=file)
			log.info("Input obtained");
			if(null!=imageData)
			{
				JSONObject jsonObject = imageData;
				log.info("Mapped JSON object");
				/*log.info("content is "+file.getInputStream());
				DataInputStream fin = new DataInputStream(file.getInputStream());
				StringBuffer fileContent = new StringBuffer();
				String currentLine;
				while((currentLine=fin.readLine())!=null)
				{
					fileContent.append(currentLine);
				}
				jsonObject.put("imageHex", fileContent.toString());*/
				/*String fileContent = new String(imageData);
				jsonObject.put("imageHex", fileContent.toString());
				jsonObject.put("ApplicationNo", "20");
				jsonObject.put("customerNo", cifId);
				jsonObject.put("customerName", customerName);
				jsonObject.put("imageType", "1");
				jsonObject.put("imageNote", "Note");
				jsonObject.put("scanPerson", "PF202020");
				jsonObject.put("commitPerson", "PF202020");
				jsonObject.put("commitPerson", "PF303030");
				jsonObject.put("imageDescription", "Description");
				jsonObject.put("imageBranch", "1");
				jsonObject.put("numberOfIdentifiers", "10");
				jsonObject.put("identifiersRequired", "2");
				jsonObject.put("specialInstruction1", "SELF");
				jsonObject.put("specialInstruction2", "asd");*/
				log.info("jsonObject is "+jsonObject);
				URL url = new URL(finalUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setInstanceFollowRedirects( false );
				conn.setRequestMethod( "POST" );
				conn.setRequestProperty( "Content-Type", "application/json"); 
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty( "Content-Length", Integer.toString( jsonObject.size() ));
				conn.setRequestProperty(configMsgBundle.getString("CBOAPI"), configMsgBundle.getString("CBOAPIVALUE"));
				conn.setRequestProperty( "charset", "utf-8");
				conn.setUseCaches( false );
				DataOutputStream  wr = new DataOutputStream (conn.getOutputStream());
					   wr.write(jsonObject.toString().getBytes());
					   wr.flush();
					   
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				

				log.info("Output from Server .... \n");
				while ((responseValue = br.readLine()) != null) {
					outputStr.append(responseValue);
					log.info("Response Recieved from Server"+responseValue);
				}

				if(null!=outputStr)
				{
					responseValue=outputStr.toString();
				}
				br.close();
				wr.close();
				conn.disconnect();
				System.out.println(responseValue);
			}
			else
			{
				log.error("Post Data not available in requset");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
			log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
			log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
		} 
		return responseValue; 
			
	}
	
	@RequestMapping(value="/getPostDataFromCommunicator",produces="application/json")
	public String getPostDataFromCommunicator(@RequestHeader HttpHeaders  headers,@RequestBody Object postData) 
	{
		log.info("getPostDataFromCommunicator ");
		String finalUrl="";
		String responseValue = null;
		URL url;
		log.info("Headers retrived from Request"+headers);
		if(null!=headers)
		{
			finalUrl =configMsgBundle.getString("CBOURL")+ headers.get("serviceURL").get(0);
			log.info("Final URL is "+finalUrl);
			log.info("postData is "+postData);
			StringBuffer outputStr = new StringBuffer(); 
			byte[] xmlByteArr;
			try {
				if(null!=postData)
				{
					log.info("Final URL is "+finalUrl.contains("atm/inq"));
					if(finalUrl.contains("atm/inq"))
					{
						JSONArray jsonArray = new JSONArray();
						ArrayList cifList = (ArrayList) postData;
						for(int j=0;j<cifList.size();j++)
						{
							jsonArray.put(cifList.get(j));							
						}
						log.info("jsonArray  is "+jsonArray);
						xmlByteArr = jsonArray.toString().getBytes("UTF-8");
					}
					else
					{
						JSONObject jsonObject = new JSONObject(postData);
						xmlByteArr = jsonObject.toString().getBytes("UTF-8");
					}
					int    postDataLength = xmlByteArr.length;
					url = new URL(finalUrl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.setInstanceFollowRedirects( false );
					conn.setRequestMethod( "POST" );
					conn.setRequestProperty( "Content-Type", "application/json"); 
					conn.setRequestProperty("Accept", "application/json");
					conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
					conn.setRequestProperty(configMsgBundle.getString("CBOAPI"), configMsgBundle.getString("CBOAPIVALUE"));
					conn.setRequestProperty( "charset", "utf-8");
					conn.setUseCaches( false );
					DataOutputStream  wr = new DataOutputStream (conn.getOutputStream());
						   wr.write(xmlByteArr);
						   wr.flush();
						   
					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
					
	
					log.info("Output from Server .... \n");
					while ((responseValue = br.readLine()) != null) {
						outputStr.append(responseValue);
						log.info("Response Recieved from Server"+responseValue);
					}
	
					if(null!=outputStr)
					{
						responseValue=outputStr.toString();
					}
					br.close();
					wr.close();
					conn.disconnect();
					System.out.println(responseValue);
				}
				else
				{
					log.error("Post Data not available in requset");
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
				log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
			}  catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
				log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("getPostDataFromCommunicator exception "+e.fillInStackTrace());
				log.error("getPostDataFromCommunicator exception Message "+e.getMessage());
			} 
		}/*else
		{
			log.error("Header not present in requset");
		}*/


		return responseValue;

	}
	
	
	@RequestMapping(value="/uploadDoc",method=RequestMethod.POST, consumes="multipart/form-data", produces="text/plain")
	public String uploadDoc(@RequestParam("file") MultipartFile file,@RequestParam("referenceNo") String referenceNo,@RequestParam("fileName") String fileName)
	{
		ObjectService obj= new ObjectService();
		ObjectServicePort objport=obj.getObjectServicePort();
		
		try{
			BindingProvider prov=(BindingProvider) objport;
			String username = configMsgBundle.getString("filenetAdmin");
			String password = configMsgBundle.getString("filenetPassword");
			prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
			prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
			CmisPropertiesType prop=new CmisPropertiesType();
			
			ObjectFactory objectFactory=new ObjectFactory();
			CmisPropertyString docclas=objectFactory.createCmisPropertyString();
			docclas.setPropertyDefinitionId("cmis:objectTypeId");
			String casaDocClass = configMsgBundle.getString("casaDocumentClass");
			docclas.getValue().add(casaDocClass);
			prop.getProperty().add(docclas);
			
			objectFactory=new ObjectFactory();
			CmisPropertyString refNo=objectFactory.createCmisPropertyString();
			refNo.setPropertyDefinitionId("CAS_ReferenceNo");
			refNo.getValue().add(referenceNo);
			log.info("uploadDoc:referenceNo is "+referenceNo);
			prop.getProperty().add(refNo);

			objectFactory=new ObjectFactory();
			CmisPropertyString docType5=objectFactory.createCmisPropertyString();
			docType5.setPropertyDefinitionId("cmis:name");
			docType5.getValue().add(fileName);
			prop.getProperty().add(docType5);
			
			log.info("uploadDoc:title is PhotoTitle");
			
			/*objectFactory=new ObjectFactory();
			CmisPropertyString docType6=objectFactory.createCmisPropertyString();
			//docType6.setPropertyDefinitionId("DisplayName");
			docType6.setDisplayName("Photo");
			prop.getProperty().add(docType6);*/
			
			
			//byte[] content = "CMIS Testing Client".getBytes();
			byte[] content = null;
			System.out.println("file is "+file.toString());
			System.out.println("file size is "+file.getBytes());
			if (!file.isEmpty()) 
				content = file.getBytes();
			//byte[] content = imageBytes.getBytes(1, (int) imageBytes.length());;
			InputStream stream = new ByteArrayInputStream(content);
			CmisContentStreamType  contentStream = new CmisContentStreamType();
			DataHandler dataHandler = new DataHandler(new InputStreamDataSource(stream));
			contentStream.setStream(dataHandler);
			contentStream.setMimeType("image/jpg");
			contentStream.setFilename(fileName);
			contentStream.setLength(BigInteger.valueOf(content.length));
			
			
			org.oasis_open.docs.ns.cmis.core._200908.CmisAccessControlListType addACEs =new org.oasis_open.docs.ns.cmis.core._200908.CmisAccessControlListType();		
			org.oasis_open.docs.ns.cmis.core._200908.CmisAccessControlListType removeACEs = new org.oasis_open.docs.ns.cmis.core._200908.CmisAccessControlListType();		
			
			Holder<CmisExtensionType> extension=new Holder<CmisExtensionType>();
			Holder<String>  id= new Holder<String> ();
			
			List<String> a= Arrays.asList( new String []{});
			//objport.createDocument("DOS", prop, "{C006AA58-0000-C018-8CC3-D067853BC357}", contentStream, org.oasis_open.docs.ns.cmis.core._200908.EnumVersioningState.MAJOR, a, addACEs, removeACEs, extension, id);
			//objport.createDocument("fnObjStr", prop, "{70238A5A-0000-C11D-856A-6E9A0A1DA27B}", contentStream, org.oasis_open.docs.ns.cmis.core._200908.EnumVersioningState.MAJOR, a, addACEs, removeACEs, extension, id);
			objport.createDocument("CMTOS", prop, "{70238A5A-0000-C11D-856A-6E9A0A1DA27B}", contentStream, org.oasis_open.docs.ns.cmis.core._200908.EnumVersioningState.MAJOR, a, addACEs, removeACEs, extension, id);  
			
			//objport.createDocument("fnObjStr", prop, "{70238A5A-0000-C11D-856A-6E9A0A1DA27B}", contentStream, org.oasis_open.docs.ns.cmis.core._200908.EnumVersioningState.MAJOR, new String[]{}, addACEs, removeACEs, extension,id);
			
			//sendHexImageToCore("image",file);
			
			
			/*String finalUrl = "http://drdmtest01:9080/RestWS/getPostDataFromCommunicator";
			String responseValue = null;
			StringBuffer outputStr = new StringBuffer(); 
			//JSONObject jsonObject = new JSONObject(postData);
			//xmlByteArr = jsonObject.toString().getBytes("UTF-8");
			
			URL url = new URL(finalUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/json"); 
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty( "Content-Length", Integer.toString( content.length ));
			conn.setRequestProperty("serviceURL", "api/secure/image");
			conn.setRequestProperty( "charset", "utf-8");
			conn.setUseCaches( false );
			log.info("Invoking ");
			DataOutputStream  wr = new DataOutputStream (conn.getOutputStream());
				   wr.write(content);
				   wr.flush();
				   
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			

			log.info("Output from Server .... \n");
			while ((responseValue = br.readLine()) != null) {
				outputStr.append(responseValue);
				log.info("Response Recieved from Server"+responseValue);
			}

			if(null!=outputStr)
			{
				responseValue=outputStr.toString();
			}
			br.close();
			wr.close();
			conn.disconnect();
			System.out.println(responseValue);*/
		
		}
		catch(Exception e){
			System.out.println(e.fillInStackTrace());
			
		}
			
			
		return "true";
	}
	
	
	
	//Added by Tushar
	@RequestMapping(value="/getResponsiveness",produces="application/json")
	public String getResponsiveness(@RequestParam("MainType") String mainType,@RequestParam("param1") String param1,@RequestParam("param2") String param2,@RequestParam("param3") String param3,@RequestParam("param4") String param4)
	{
		String responsiveness=null;
		
		try
		{
			if(mainType.equalsIgnoreCase("P")){
				String prefCustomer=param1;
				String srcofinc=param2;
				String profcode=param3;
				String citizenship=param4;
				
			responsiveness = productService.calculatePersonalResponsiveness(prefCustomer,srcofinc,profcode,citizenship);
			}
			
			else if(mainType.equalsIgnoreCase("NP")){
				String customerType=param1;
				String srcoffund=param2;
				String businesstype=param3;
				String jurisk=param4;
				
				
				responsiveness = productService.calculateNonPersonalResponsiveness(customerType,srcoffund,businesstype,jurisk);
			}
			else
			{
				
				responsiveness="Type is not set as Either Personel or NonPersonel";
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
			//setError(getExcptnMesProperty(e.getMessage()));
		}
		return responsiveness;
	}
	
	@RequestMapping(value="/createStandingOrder", method=RequestMethod.POST,  produces="application/json", consumes="application/json")
	public Long createStandingOrder(@RequestBody Object formParameters) throws JAXBException, Exception
	{
		CIFServiceImpl cifService = null; 
		cifService =	new CIFServiceImpl();
		JSONObject jObject = new JSONObject(formParameters);
		
		Long statusCode = cifService.createStandingOrder(formParameters);
		return statusCode;
	}
	
	
	@RequestMapping(value="/test",produces="application/json")
	public void getTest()
	{
		log.info("Service payee access");
		ProductServiceImpl payeeService= null;
		try
		{
			payeeService = new ProductServiceImpl();
			payeeService.getPayeeTypes();
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	} 
	
	@RequestMapping(value="/getPayeeTypes",produces="application/json")
	public List<String> getPayeeTypes()
	{
		log.info("Service payee access");
		PayeeServiceImpl payeeService= null;
		try
		{
			payeeService = new PayeeServiceImpl();
			return payeeService.getPayeeTypes();
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	} 
		
	@RequestMapping(value="/getPayeeList",produces="application/json")
	public List<String> getPayeeListbyType(@RequestParam("payeeType") String payeeType)
	{
		PayeeServiceImpl payeeService = null;
		try
		{
			payeeService = new PayeeServiceImpl();
			return payeeService.getPayeeListByType(payeeType);
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	}
	
	/*@RequestMapping(value="/getPayee",produces="application/json")
	public StandingOrderPayee getPayeeDetails(@RequestParam("payee") String payee)
	{
		PayeeServiceImpl payeeService = null;
		try
		{
			payeeService = new PayeeServiceImpl();
			return payeeService.getPayeeDetails(payee);
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	}*/
	
	@RequestMapping(value="/getBanks",produces="application/json")
	public Map<String, String> getBankList()
	{
		//BankBranchServiceImpl bankBranchService = null;
		try
		{
			//bankBranchService = new BankBranchServiceImpl();
			return bankBranchService.getBankList();
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	}
	
	@RequestMapping(value="/getBankBranches",produces="application/json")
	public Map<String, String> getBankBranchesList(@RequestParam("bank") String bank)
	{
		BankBranchServiceImpl bankBranchService = null;
		try
		{
			bankBranchService = new BankBranchServiceImpl();
			return bankBranchService.getBranchesFromBank(bank);
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e.fillInStackTrace()+""+e.getStackTrace()+e.getLocalizedMessage());
			throw new BSLException(getExcptnMesProperty(e.getMessage()));
		}
		
	}
	
	@RequestMapping(value={"/getCustomerByNic"}, produces={"application/json"})
	  public Customer[] getCustomerByNIC(@RequestParam("nic") String nic)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getCustomerByNIC(nic);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getCustomerByMobile"}, produces={"application/json"})
	  public Customer[] getCustomerByMobile(@RequestParam("mobileno") String mobileno)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getCustomerByMobile(mobileno);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/addComplaint"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"}, consumes={"application/json"})
	  public Boolean addComplaint(@RequestBody Object formParameters)
	    throws JAXBException, Exception
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      JSONObject jObject = new JSONObject(formParameters);
	      return complaintService.addComplaint(jObject);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/setComplaintStatus"}, produces={"application/json"})
	  public Boolean setComplaintStatus(@RequestParam("status") String status, @RequestParam("ref") String ref)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.setComplaintStatus(status, ref);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getMajorCategories"}, produces={"application/json"})
	  public List<Map<String, String>> getMajorCategories()
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getMajorCategories();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getSubCategories"}, produces={"application/json"})
	  public List<Map<String, String>> getSubCategoriesByMajor(@RequestParam("majorCategoryId") String majorCategory)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getSubCategories(majorCategory);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getDepartments"}, produces={"application/json"})
	  public List<Map<String, String>> getDepartmentsBySubCategory(@RequestParam("subCategoryId") String subCategory)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getDepartments(subCategory);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getSLA"}, produces={"application/json"})
	  public int getSLABySubCategory(@RequestParam("subCategoryId") String subCategory)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getSLA(subCategory);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	  
	  @RequestMapping(value={"/getEscalationEmailByBranch"}, produces={"application/json"})
	  public List<Map<String, String>> getEscalationEmailByBranch(@RequestParam("branchCode") String branch)
	  {
	    ComplaintService complaintService = null;
	    try
	    {
	      complaintService = new ComplaintServiceImpl();
	      return complaintService.getEscalationEmailByBranch(branch);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      log.error(e.fillInStackTrace() + "" + e.getStackTrace() + e.getLocalizedMessage());
	      throw new BSLException(getExcptnMesProperty(e.getMessage()));
	    }
	  }
	
	
}
