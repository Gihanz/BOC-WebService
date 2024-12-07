package com.boc.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.boc.connector.CMConnector;
import com.boc.dao.ProductDao;
import com.boc.dao.impl.ProductDaoImpl;
import com.boc.fiserv.impl.AcctCustInqServiceImpl;
import com.boc.fiserv.impl.AcctLstInqServiceImpl;
import com.boc.fiserv.impl.CIFEmployeeAddServiceImpl;
import com.boc.fiserv.impl.CIFFullNameAlertAddServiceImpl;
import com.boc.fiserv.impl.ChequeAddServiceImpl;
import com.boc.fiserv.impl.ChequeAddServiceImpl;
import com.boc.fiserv.impl.ChequeNameAlertAddServiceImpl;
import com.boc.fiserv.impl.CreateAccountServiceImpl;
import com.boc.fiserv.impl.CreateAlternateAddressServiceImpl;
import com.boc.fiserv.impl.CreateCollateralServiceImpl;
import com.boc.fiserv.impl.CreateCustToCustAddServiceImpl;
import com.boc.fiserv.impl.CustProfBasicAddServiceImpl;
import com.boc.fiserv.impl.DateCalcInqServiceImpl;
import com.boc.fiserv.impl.GuardianNameAlertAddServiceImpl;
import com.boc.fiserv.impl.StandingOrderAddServiceImpl;
import com.boc.fiserv.response.AccountAddResponse;
import com.boc.fiserv.response.AccountLstInqResponse;
import com.boc.fiserv.response.AltAdressAddResponse;
import com.boc.fiserv.response.CIFEmployeeAddResponse;
import com.boc.fiserv.response.CIFFullNameAddResponse;
import com.boc.fiserv.response.CalcDateResponse;
import com.boc.fiserv.response.ChequeAddServiceResponse;
import com.boc.fiserv.response.ChequeFullNameAddServiceResponse;
import com.boc.fiserv.response.CreateCollateralResponse;
import com.boc.fiserv.response.CustomerProfileAddRs;
import com.boc.fiserv.response.CustomerProfileRs;
import com.boc.service.CIFService;

public class CIFServiceImpl implements CIFService
{
	//@Autowired private ProductDao productDao;
	// public static Logger log = Logger.getLogger("com.boc.service.impl.CIFServiceImpl");
	 private static Logger log =LoggerFactory.getLogger(CIFServiceImpl.class);
	/*InvokeCIFScvImpl invokeCIFSvc = new InvokeCIFScvImpl();
	public CustomerProfileRs getCustProfBasicInq(String accountNo)
	{
		CustomerProfileRs customerProfileRs = invokeCIFSvc.getCustProfBasicInq(accountNo, 36, "SGINTNET", "SGINTNET","http://172.21.12.146/CRG_TRNG01/crg.aspx", "/opt/jaxb");
		return customerProfileRs;
	}*/
	AcctCustInqServiceImpl actCustInqService = new AcctCustInqServiceImpl();
	public CustomerProfileRs getCustProfBasicInq(String accountNo) throws Exception
	{
		CustomerProfileRs customerProfileRs = null;
		customerProfileRs = actCustInqService.invokeCustProfBasicInq(accountNo);
		return customerProfileRs;
	}
	
	public CustomerProfileRs getAcctCustInqRq(String accountNo,String actType) throws Exception
	{
		CustomerProfileRs customerProfileRs = actCustInqService.getAcctCustInqRq(accountNo,actType);
		return customerProfileRs;
	}
	
	public AccountLstInqResponse getAccountFundingList(String cifNumber) throws Exception
	{
		AccountLstInqResponse accountLstInqResponse = null;
		AcctLstInqServiceImpl acctLstInqServiceImpl = null;
		acctLstInqServiceImpl = new AcctLstInqServiceImpl();
		accountLstInqResponse = acctLstInqServiceImpl.execute(cifNumber,null,Long.valueOf(0));
		return accountLstInqResponse;
		
	}
	public AccountAddResponse openAccount(JSONObject accountDetails,String applicationId,String accountNoKey,String altAddressCheckKey,String checkGuardianKey) throws Exception
	{
		AccountAddResponse accountAddResponese = null;
		accountAddResponese = new AccountAddResponse();
		CreateAccountServiceImpl createAccountServiceImpl = null;
		createAccountServiceImpl = new CreateAccountServiceImpl();
		String accountNo = null,altAddressCheck=null,checkFullName = null,checkGuardianName=null;
		JSONObject jAccountObject = accountDetails;//new JSONObject(accountDetails);
		log.info("CIFServiceImpl:accountDetails obtained is "+jAccountObject.toString());
		if(jAccountObject.has(accountNoKey))
			accountNo = (String) jAccountObject.getString(accountNoKey);
		log.info("CIFServiceImpl:accountNo is "+accountNo);
		if(null==accountNo || "".equalsIgnoreCase(accountNo) || "null".equalsIgnoreCase(accountNo))
		{
		
		accountAddResponese =  createAccountServiceImpl.execute(jAccountObject, applicationId);
		log.info("CIFServiceImpl:accountAddResponese Error code is "+accountAddResponese.getErrorCode());
		
		if(null==accountAddResponese.getErrorCode()||"".equalsIgnoreCase(accountAddResponese.getErrorCode()))
		{
			log.info("CIFServiceImpl:accountNo is "+accountAddResponese.getAccountNo());
			accountNo = accountAddResponese.getAccountNo();
			//jAccountObject.append(accountNoKey,accountNo);
			jAccountObject.put(accountNoKey, accountNo);
			if(jAccountObject.has(altAddressCheckKey))
				altAddressCheck = (String)jAccountObject.getString(altAddressCheckKey);
			log.info("CIFServiceImpl:altAddressCheck is "+altAddressCheck);
			/*if(null!=altAddressCheck && altAddressCheck.equalsIgnoreCase("true"))
			{
				AltAdressAddResponse altAdressAddResponse = null;
				//Invoke Alternate Addres MEMO
				CreateAlternateAddressServiceImpl createAlternateAddrServiceImpl = null;
				createAlternateAddrServiceImpl = new CreateAlternateAddressServiceImpl();
				altAdressAddResponse = createAlternateAddrServiceImpl.execute(jAccountObject);
				if(null==altAdressAddResponse.getErrorCode() || ("".equalsIgnoreCase(altAdressAddResponse.getErrorCode())))
				{
					accountAddResponese.setAltAddressMemo(altAdressAddResponse.getAltAdressAdded());
				}
				else
				{
					accountAddResponese.setErrorCode(altAdressAddResponse.getErrorCode());
					accountAddResponese.setErrorDescription(altAdressAddResponse.getErrorDescription());
				}
			}*/		
		}	
		
		if(jAccountObject.has(checkGuardianKey))
			checkGuardianName = (String)jAccountObject.getString(checkGuardianKey);
		log.info("CIFServiceImpl:checkGuardianName is "+checkGuardianName);
		if(null!=checkGuardianName)
		{
			GuardianNameAlertAddServiceImpl guardianService = new GuardianNameAlertAddServiceImpl();
			CustomerProfileAddRs customerProfileAddRs = guardianService.execute(jAccountObject);
			if(null==customerProfileAddRs.getErrorCode() || ("".equalsIgnoreCase(customerProfileAddRs.getErrorCode())))
			{
				accountAddResponese.setGuardianMMemo(customerProfileAddRs.getCifFullNameRs());
			}
		}
		
		}
		else
		{
			if(jAccountObject.has(altAddressCheckKey))
				altAddressCheck = (String)jAccountObject.getString(altAddressCheckKey);
			log.info("CIFServiceImpl:altAddressCheck is "+altAddressCheck);
			/*if(null!=altAddressCheck && altAddressCheck.equalsIgnoreCase("true"))
			{
				AltAdressAddResponse altAdressAddResponse = null;
				//Invoke Alternate Addres MEMO
				CreateAlternateAddressServiceImpl createAlternateAddrServiceImpl = null;
				createAlternateAddrServiceImpl = new CreateAlternateAddressServiceImpl();
				altAdressAddResponse = createAlternateAddrServiceImpl.execute(jAccountObject);
				if(null==altAdressAddResponse.getErrorCode() || ("".equalsIgnoreCase(altAdressAddResponse.getErrorCode())))
				{
					accountAddResponese.setAltAddressMemo(altAdressAddResponse.getAltAdressAdded());
				}
				else
				{
					accountAddResponese.setErrorCode(altAdressAddResponse.getErrorCode());
					accountAddResponese.setErrorDescription(altAdressAddResponse.getErrorDescription());
				}
			}*/
			if(jAccountObject.has(checkGuardianKey))
				checkGuardianName = (String)jAccountObject.getString(checkGuardianKey);
			log.info("CIFServiceImpl:checkGuardianName is "+checkGuardianName);
			if(null!=checkGuardianName)
			{
				GuardianNameAlertAddServiceImpl guardianService = new GuardianNameAlertAddServiceImpl();
				CustomerProfileAddRs customerProfileAddRs = guardianService.execute(jAccountObject);
				if(null==customerProfileAddRs.getErrorCode() || ("".equalsIgnoreCase(customerProfileAddRs.getErrorCode())))
				{
					accountAddResponese.setGuardianMMemo(customerProfileAddRs.getCifFullNameRs());
				}
			}
		}
		return accountAddResponese;
	}
	
	public ChequeAddServiceResponse createChequeBook(Object chequeDetails) throws Exception
	{
		ChequeAddServiceResponse chequeAddServiceResponse = null;
		ChequeAddServiceImpl chequeAddServiceImpl = null;
		chequeAddServiceImpl = new ChequeAddServiceImpl();
		ChequeFullNameAddServiceResponse chequeFullNameAddServiceResponse = null;
		try
		{
			JSONObject jChequeObject = new JSONObject(chequeDetails);
			log.info(jChequeObject.toString());
			chequeAddServiceResponse = chequeAddServiceImpl.execute(jChequeObject);
			if(null==chequeAddServiceResponse.getErrorCode() || "".equalsIgnoreCase(chequeAddServiceResponse.getErrorCode()))
			{
				ChequeNameAlertAddServiceImpl chequeNameAlertAddServiceImpl = null;
				chequeNameAlertAddServiceImpl = new ChequeNameAlertAddServiceImpl();
				chequeFullNameAddServiceResponse = chequeNameAlertAddServiceImpl.execute(jChequeObject);
				if(null!=chequeFullNameAddServiceResponse.getErrorCode() || "".equalsIgnoreCase(chequeFullNameAddServiceResponse.getErrorCode()))
				{
					chequeAddServiceResponse.setChqFullNameStatus(chequeFullNameAddServiceResponse.getChqFullNameStatus());
				}
				else
				{
					chequeAddServiceResponse.setErrorCode(chequeFullNameAddServiceResponse.getErrorCode());
					chequeAddServiceResponse.setErrorDescription(chequeFullNameAddServiceResponse.getErrorDescription());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Exception Occured is "+e.fillInStackTrace());
			log.error("Exception Occured is "+e.getStackTrace());
			log.error("Exception Message is "+e.getMessage());
			throw e;
		}
		
		return chequeAddServiceResponse;
	}
	
	public CustomerProfileAddRs createCIF(Object cifDetails,String CASACifReferenceNumberKey,String CASACIFIDKey,String CASAMemoCreatedKey,String CASAEmployeeCreatedKey,String CASACustomertypeKey,String CASACustomerSybtypeKey,String CASAEmployerNameKey,String caseType,String solutionName) throws Exception
	{
		CustProfBasicAddServiceImpl custProfBasicAddServiceImpl = null;
		CustomerProfileAddRs CustomerProfileAddRs = null;
		CustomerProfileAddRs CustomerProfileAddResultRs = null;
		CustomerProfileAddResultRs = new CustomerProfileAddRs();
		String cifNo = null,cifReferenceNo=null,customerType=null,customerSubType=null,employerName=null;
		CIFFullNameAlertAddServiceImpl CIFFullNameServiceImpl = null;
		CIFFullNameServiceImpl = new CIFFullNameAlertAddServiceImpl();
		CIFEmployeeAddServiceImpl CIFEmployeeAddServiceImpl = null;
		CIFEmployeeAddServiceImpl = new CIFEmployeeAddServiceImpl();
		CreateCustToCustAddServiceImpl CreateCustToCustAddServiceImpl = null;
		CreateCustToCustAddServiceImpl = new CreateCustToCustAddServiceImpl();
		try
		{
		JSONObject jCIFObject = new JSONObject(cifDetails);
		log.info(cifDetails.toString());
		if(jCIFObject.has(CASACIFIDKey))
			cifNo = (String) jCIFObject.getString(CASACIFIDKey);
		log.info("createCIF:cifNo is "+cifNo);
		if(null==cifNo || "".equalsIgnoreCase(cifNo) || "null".equalsIgnoreCase(cifNo))
		{
			custProfBasicAddServiceImpl = new CustProfBasicAddServiceImpl();
			CustomerProfileAddRs = custProfBasicAddServiceImpl.execute(cifDetails);
			if(null!=CustomerProfileAddRs)
			{
				CustomerProfileAddResultRs.setErrorCode(CustomerProfileAddRs.getErrorCode());
				CustomerProfileAddResultRs.setErrorDescription(CustomerProfileAddRs.getErrorDescription());
				if(null==CustomerProfileAddRs.getErrorCode() || ("".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode())))
				{
					cifNo = CustomerProfileAddRs.getCifNo();
					CustomerProfileAddResultRs.setCifNo(cifNo);
					log.info("createCIF generated is :cifNo is "+cifNo);
					CMConnector cmConnector = new CMConnector();
					if(jCIFObject.has(CASACifReferenceNumberKey))
					{
						cifReferenceNo = (String) jCIFObject.getString(CASACifReferenceNumberKey);
						log.info("createCIF :cifReferenceNo is "+cifReferenceNo);
					}
					if(null!=cifReferenceNo)
					{
						/*HashMap propertymap = null;
						propertymap = new HashMap();
						propertymap.put(CASACIFIDKey, cifNo);
						cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
						jCIFObject.put(CASACIFIDKey, cifNo);
						
						CustomerProfileAddRs = CIFFullNameServiceImpl.execute(jCIFObject);
						if(null!=CustomerProfileAddRs)
						{
							CustomerProfileAddResultRs.setErrorCode(CustomerProfileAddRs.getErrorCode());
							CustomerProfileAddResultRs.setErrorDescription(CustomerProfileAddRs.getErrorDescription());
							if(null==CustomerProfileAddRs.getErrorCode() || "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
							{
								CustomerProfileAddResultRs.setFullNameMemoCreated("True");
								/*propertymap = new HashMap();
								propertymap.put(CASAMemoCreatedKey, "True");
								cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
							}
						}
						if(jCIFObject.has(CASACustomertypeKey))
							customerType = (String) jCIFObject.getString(CASACustomertypeKey);
						log.info("createCIF :customerType is "+customerType);
						if(jCIFObject.has(CASACustomerSybtypeKey))
							customerSubType = (String) jCIFObject.getString(CASACustomerSybtypeKey);
						log.info("createCIF :customerType is "+customerSubType);
						
						if(null!=customerType && customerType.equalsIgnoreCase("P") && null!=customerSubType && customerSubType.equalsIgnoreCase("1"))
						{
							if(jCIFObject.has(CASAEmployerNameKey))
								employerName = (String) jCIFObject.getString(CASAEmployerNameKey);
							log.info("createCIF :employerName is "+employerName);
							if(null!=employerName && !("".equalsIgnoreCase(employerName)))
							{
								CustomerProfileAddRs = CIFEmployeeAddServiceImpl.execute(jCIFObject);
								if(null!=CustomerProfileAddRs)
								{
									CustomerProfileAddResultRs.setErrorCode(CustomerProfileAddRs.getErrorCode());
									CustomerProfileAddResultRs.setErrorDescription(CustomerProfileAddRs.getErrorDescription());
									if(null==CustomerProfileAddRs.getErrorCode()|| "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
									{
										CustomerProfileAddResultRs.setEmployeeMemoCreated("True");
										/*propertymap = new HashMap();
										propertymap.put(CASAEmployeeCreatedKey, "True");
										cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
									}
								}
							}
						}
						CustomerProfileAddRs = CreateCustToCustAddServiceImpl.execute(cifDetails);
						if(null!=CustomerProfileAddRs)
						{
							if(null==CustomerProfileAddRs.getErrorCode()|| "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
							{
								log.info(""+CustomerProfileAddResultRs.getCustToCustResponse());
								/*propertymap = new HashMap();
								propertymap.put(CASAEmployeeCreatedKey, "True");
								cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
							}
						}
					}
				}
			}
		}
		else if(null!= cifNo)
		{
			log.info("createCIF generated is :cifNo is "+cifNo);
			CMConnector cmConnector = new CMConnector();
			if(jCIFObject.has(CASACifReferenceNumberKey))
			{
				cifReferenceNo = (String) jCIFObject.getString(CASACifReferenceNumberKey);
				log.info("createCIF :cifReferenceNo is "+cifReferenceNo);
			}
			if(null!=cifReferenceNo)
			{
				/*HashMap propertymap = null;
				propertymap = new HashMap();
				propertymap.put(CASACIFIDKey, cifNo);
				cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
				jCIFObject.put(CASACIFIDKey, cifNo);
				log.info("createCIF :jCIFObject is "+jCIFObject);
				CustomerProfileAddRs = CIFFullNameServiceImpl.execute(jCIFObject);
				if(null!=CustomerProfileAddRs)
				{
					CustomerProfileAddResultRs.setErrorCode(CustomerProfileAddRs.getErrorCode());
					CustomerProfileAddResultRs.setErrorDescription(CustomerProfileAddRs.getErrorDescription());
					if(null==CustomerProfileAddRs.getErrorCode() || "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
					{
						CustomerProfileAddResultRs.setFullNameMemoCreated("True");
						/*propertymap = new HashMap();
						propertymap.put(CASAMemoCreatedKey, "True");
						cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
					}
				}
				if(jCIFObject.has(CASACustomertypeKey))
					customerType = (String) jCIFObject.getString(CASACustomertypeKey);
				log.info("createCIF :customerType is "+customerType);
				if(null!=customerType && customerType.equalsIgnoreCase("P") && null!=customerSubType && customerSubType.equalsIgnoreCase("1"))
				{
					if(jCIFObject.has(CASAEmployerNameKey))
						employerName = (String) jCIFObject.getString(CASAEmployerNameKey);
					log.info("createCIF :employerName is "+employerName);
					if(null!=employerName && !("".equalsIgnoreCase(employerName)))
					{
						CustomerProfileAddRs = CIFEmployeeAddServiceImpl.execute(jCIFObject);
						if(null!=CustomerProfileAddRs)
						{
							CustomerProfileAddResultRs.setErrorCode(CustomerProfileAddRs.getErrorCode());
							CustomerProfileAddResultRs.setErrorDescription(CustomerProfileAddRs.getErrorDescription());
							if(null==CustomerProfileAddRs.getErrorCode()|| "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
							{
								CustomerProfileAddResultRs.setEmployeeMemoCreated("True");
								/*propertymap = new HashMap();
								propertymap.put(CASAEmployeeCreatedKey, "True");
								cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
							}
						}
					}
				}
				CustomerProfileAddRs = CreateCustToCustAddServiceImpl.execute(cifDetails);
				if(null!=CustomerProfileAddRs)
				{
					if(null==CustomerProfileAddRs.getErrorCode()|| "".equalsIgnoreCase(CustomerProfileAddRs.getErrorCode()))
					{
						log.info(""+CustomerProfileAddResultRs.getCustToCustResponse());
						/*propertymap = new HashMap();
						propertymap.put(CASAEmployeeCreatedKey, "True");
						cmConnector.updateProperty(cifReferenceNo,CASACifReferenceNumberKey,caseType,solutionName,propertymap);*/
					}
				}
			}
		}
		}
		catch(Exception e)
		{
			log.error("Exception Occured is "+e.fillInStackTrace());
			log.error("Exception Occured is "+e.getStackTrace());
			log.error("Exception Message is "+e.getMessage());
			throw e;
		}
		return CustomerProfileAddResultRs;
	}
	
	public CreateCollateralResponse createCollateral(Object paramaetersObj,String collateralDescKey,String collateralDesc) throws JAXBException, Exception
	{
		CreateCollateralServiceImpl serviceImpl = null; 
		serviceImpl =	new CreateCollateralServiceImpl();
		HashMap paramaetersMap  = null;
		paramaetersMap = new HashMap();
		JSONObject jObject = new JSONObject(paramaetersObj);
    	for(Object key:jObject.keySet())
    	{
    		 String keyStr = (String)key;
    		 Object keyvalue = null;
    		 keyvalue = jObject.get(keyStr);
    		 log.info(keyStr+" "+keyvalue);
    		 paramaetersMap.put(keyStr, keyvalue);
    	}
    	paramaetersMap.put(collateralDescKey, collateralDesc);
		CreateCollateralResponse collaterResponse = serviceImpl.execute(paramaetersMap);
		return collaterResponse;
	}
	
	public CalcDateResponse getCalcDate() throws Exception
	{
		DateCalcInqServiceImpl serviceImpl = null;
		serviceImpl = new DateCalcInqServiceImpl();
		CalcDateResponse calcDateResponse = serviceImpl.getDateCalcInq();
		return calcDateResponse;
	}

	public Long createStandingOrder(Object paramaetersObj) throws JAXBException, Exception {
		JSONObject jsonObject = new JSONObject(paramaetersObj);
		return new StandingOrderAddServiceImpl().execute(jsonObject);
	}


}
