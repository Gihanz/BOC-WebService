package com.boc.service;

import java.util.List;

import com.boc.response.StandingOrderPayee;



public interface PayeeService {
	List<String> getPayeeTypes();
	List<String> getPayeeListByType(String payeeType);
	StandingOrderPayee getPayeeDetails(String payee);
}
