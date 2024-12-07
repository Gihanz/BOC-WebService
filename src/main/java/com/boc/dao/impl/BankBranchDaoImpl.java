package com.boc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.boc.dao.BankBranchDao;
import com.boc.dao.exceptions.DAOException;

@Repository
public class BankBranchDaoImpl extends abstractWFConfigdao implements BankBranchDao {
	private static Logger log =LoggerFactory.getLogger(BankBranchDaoImpl.class);
	
	public Map<String, String> getBankList() {		
		
		log.info("bank branch db access..............");
		
		String bankQry = null;
		Map<String, String> bankData = new HashMap<String, String>();
		
		try{
			bankQry = "select distinct bankName,bankNameDisplay from BankBranch";
			log.info("Query is "+bankQry);
			Query qry = session().createQuery(bankQry);
			
			List<Object[]> lstBanks  = qry.list();
			if(lstBanks !=null)
			{
				log.debug("bank List is "+lstBanks.size());
				
				for(Object[] bank:lstBanks)
				{					
					if(null !=bank[0] && null !=bank[1])
					{			
						bankData.put((String)bank[0], (String)bank[1]);
					}
				}
				
			}
			
		}catch(HibernateException hex)
		{
			log.info("Error in getBanks");
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getBanks",hex);
		}		
		
		return bankData;	
      
	}

	
	public Map<String, String> getBranchesFromBank(String bank) {       		
		return null;
	}

}
