package com.boc.service;

import java.util.Map;

public interface BankBranchService {
	
	Map<String, String> getBankList();
	Map<String, String> getBranchesFromBank(String bank);

}
