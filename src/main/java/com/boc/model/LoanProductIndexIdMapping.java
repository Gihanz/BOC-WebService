package com.boc.model;
// Generated Nov 7, 2016 6:21:29 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * LoanProductIndexIdMapping generated by hbm2java
 */
@Entity
@Table(name = "LOAN_PRODUCT_INDEX_ID_MAPPING", schema = "WFCONFIG", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PID", "IDX_ID" }) )
public class LoanProductIndexIdMapping implements java.io.Serializable {

	private int prodIndexId;
	private ProductBase productBase;
	private IndexIdBase indexIdBase;

	public LoanProductIndexIdMapping() {
	}

	public LoanProductIndexIdMapping(int prodIndexId, ProductBase productBase, IndexIdBase indexIdBase) {
		this.prodIndexId = prodIndexId;
		this.productBase = productBase;
		this.indexIdBase = indexIdBase;
	}

	@Id

	@Column(name = "PROD_INDEX_ID", unique = true, nullable = false)
	public int getProdIndexId() {
		return this.prodIndexId;
	}

	public void setProdIndexId(int prodIndexId) {
		this.prodIndexId = prodIndexId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID", nullable = false)
	public ProductBase getProductBase() {
		return this.productBase;
	}

	public void setProductBase(ProductBase productBase) {
		this.productBase = productBase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDX_ID", nullable = false)
	public IndexIdBase getIndexIdBase() {
		return this.indexIdBase;
	}

	public void setIndexIdBase(IndexIdBase indexIdBase) {
		this.indexIdBase = indexIdBase;
	}

}
