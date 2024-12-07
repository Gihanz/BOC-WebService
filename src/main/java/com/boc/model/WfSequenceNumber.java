package com.boc.model;
// Generated Jun 10, 2016 11:13:38 AM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * WfSequenceNumber generated by hbm2java
 */

@Entity
@Table(name = "WF_SEQUENCE_NUMBER", schema = "WFCONFIG")
public class WfSequenceNumber implements java.io.Serializable {

	private WfSequenceNumberId id;
	private int currentYear;

	public WfSequenceNumber() {
	}

	public WfSequenceNumber(WfSequenceNumberId id, int currentYear) {
		this.id = id;
		this.currentYear = currentYear;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "sequenceNo", column = @Column(name = "SEQUENCE_NO", nullable = false)),
			@AttributeOverride(name = "productCode", column = @Column(name = "PRODUCT_CODE", nullable = false, length = 5)) })
	public WfSequenceNumberId getId() {
		return this.id;
	}

	public void setId(WfSequenceNumberId id) {
		this.id = id;
	}

	@Column(name = "CURRENT_YEAR", nullable = false)
	public int getCurrentYear() {
		return this.currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

}