package com.boc.model;
// Generated Jun 10, 2016 11:13:38 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserRoleProductCategoryBranchMapping generated by hbm2java
 */
@Entity
@Table(name = "USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING", schema = "WFCONFIG")
public class UserRoleProductCategoryBranchMapping implements java.io.Serializable {

	private int urpcbid;
	private int urpcid;
	private int bid;

	public UserRoleProductCategoryBranchMapping() {
	}

	public UserRoleProductCategoryBranchMapping(int urpcbid, int urpcid, int bid) {
		this.urpcbid = urpcbid;
		this.urpcid = urpcid;
		this.bid = bid;
	}

	@Id

	@Column(name = "URPCBID", unique = true, nullable = false)
	public int getUrpcbid() {
		return this.urpcbid;
	}

	public void setUrpcbid(int urpcbid) {
		this.urpcbid = urpcbid;
	}

	@Column(name = "URPCID", nullable = false)
	public int getUrpcid() {
		return this.urpcid;
	}

	public void setUrpcid(int urpcid) {
		this.urpcid = urpcid;
	}

	@Column(name = "BID", nullable = false)
	public int getBid() {
		return this.bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

}
