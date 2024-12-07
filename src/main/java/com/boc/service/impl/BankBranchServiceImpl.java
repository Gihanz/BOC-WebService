

package com.boc.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boc.dao.BankBranchDao;
import com.boc.service.BankBranchService;

/*
Created By SaiMadan on May 24, 2017
*/
@Service
@Configurable
public class BankBranchServiceImpl implements BankBranchService{
	private static Logger log =LoggerFactory.getLogger(BankBranchServiceImpl.class);
	@Autowired private BankBranchDao bankBranchDao = null;
    
	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, String> getBankList() {
		log.info("getBankList");
		return bankBranchDao.getBankList();
	}



	@Override
	@Transactional(value="transactionManager",readOnly=true)
	public Map<String, String> getBranchesFromBank(String bank) {		
		return bankBranchDao.getBranchesFromBank(bank);
	}

	

}
