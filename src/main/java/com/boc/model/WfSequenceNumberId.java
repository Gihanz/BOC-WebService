package com.boc.model;
// Generated Jun 10, 2016 11:13:38 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WfSequenceNumberId generated by hbm2java
 */
@Embeddable
public class WfSequenceNumberId implements java.io.Serializable {

	private int sequenceNo;
	private String productCode;

	public WfSequenceNumberId() {
	}

	public WfSequenceNumberId(int sequenceNo, String productCode) {
		this.sequenceNo = sequenceNo;
		this.productCode = productCode;
	}

	@Column(name = "SEQUENCE_NO", nullable = false)
	public int getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	@Column(name = "PRODUCT_CODE", nullable = false, length = 5)
	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WfSequenceNumberId))
			return false;
		WfSequenceNumberId castOther = (WfSequenceNumberId) other;

		return (this.getSequenceNo() == castOther.getSequenceNo())
				&& ((this.getProductCode() == castOther.getProductCode())
						|| (this.getProductCode() != null && castOther.getProductCode() != null
								&& this.getProductCode().equals(castOther.getProductCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getSequenceNo();
		result = 37 * result + (getProductCode() == null ? 0 : this.getProductCode().hashCode());
		return result;
	}

}
