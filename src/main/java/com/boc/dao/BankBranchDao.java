package com.boc.dao;

import java.util.Map;

public interface BankBranchDao {
	
	Map<String, String> getBankList();
	Map<String, String> getBranchesFromBank(String bank);
	

}
