package com.boc.dao;

import java.util.List;

import com.boc.response.StandingOrderPayee;



public interface PayeeListDao {	
	List<String> getPayeeTypes();
	List<String> getPayeeListByType(String payeeType);
	StandingOrderPayee getPayeeDetails(String payee);
}
