package com.boc.service;

import javax.xml.bind.JAXBException;

import com.boc.fiserv.response.CreateCollateralResponse;
import com.boc.fiserv.response.CustomerProfileRs;

public interface CIFService 
{
	public CustomerProfileRs getCustProfBasicInq(String accountNo) throws Exception;
	public CustomerProfileRs getAcctCustInqRq(String accountNo,String actType) throws Exception;
	public CreateCollateralResponse createCollateral(Object paramaetersObj,String collateralDescKey,String collateralDesc) throws JAXBException, Exception;
}
