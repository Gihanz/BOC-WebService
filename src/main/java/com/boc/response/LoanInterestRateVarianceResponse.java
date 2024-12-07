

package com.boc.response;

import java.math.BigDecimal;

/*
Created By SaiMadan on Nov 7, 2016
*/
public class LoanInterestRateVarianceResponse {

	public BigDecimal varianceRateFixed;
	public BigDecimal varianceRateVariable1;
	public BigDecimal varianceRateVariable2;
	public BigDecimal getVarianceRateFixed() {
		return varianceRateFixed;
	}
	public void setVarianceRateFixed(BigDecimal varianceRateFixed) {
		this.varianceRateFixed = varianceRateFixed;
	}
	public BigDecimal getVarianceRateVariable1() {
		return varianceRateVariable1;
	}
	public void setVarianceRateVariable1(BigDecimal varianceRateVariable1) {
		this.varianceRateVariable1 = varianceRateVariable1;
	}
	public BigDecimal getVarianceRateVariable2() {
		return varianceRateVariable2;
	}
	public void setVarianceRateVariable2(BigDecimal varianceRateVariable2) {
		this.varianceRateVariable2 = varianceRateVariable2;
	}
	
	
	
}
