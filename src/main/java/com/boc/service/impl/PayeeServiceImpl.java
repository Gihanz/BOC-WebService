package com.boc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boc.dao.PayeeListDao;
import com.boc.dao.impl.PayeeListDaoImpl;
import com.boc.response.StandingOrderPayee;
import com.boc.service.PayeeService;

@Service
@Configurable
public class PayeeServiceImpl implements PayeeService{
	private static Logger log =LoggerFactory.getLogger(PayeeServiceImpl.class);
    private PayeeListDao payeeDao = null;
    
   

	public PayeeServiceImpl() {
		log.info("PayeeListDao created.......");
		this.payeeDao = new PayeeListDaoImpl();
	}



	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getPayeeTypes() {
		log.info("service payee types accesssssssss.........");
		List<String> payeeTypes =  payeeDao.getPayeeTypes();
		
		log.info("x[0] : ... " + payeeTypes.get(0));
		
		return payeeTypes;
	}



	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public List<String> getPayeeListByType(String payeeType) {
		log.info("service payeeList accesssssssss.........");
		List<String> payees =  payeeDao.getPayeeListByType(payeeType);
		
		log.info("x[0] : ... " + payees.get(0));
		
		return payees;
	}



	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public StandingOrderPayee getPayeeDetails(String payee) {
		log.info("service payee data accesssssssss.........");
		StandingOrderPayee payeeData =  payeeDao.getPayeeDetails(payee);
		
		log.info("x[0] : ... " + payeeData.getAccountNo());
		
		return payeeData;
	}

	

}
