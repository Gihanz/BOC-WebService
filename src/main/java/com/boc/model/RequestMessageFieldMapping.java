package com.boc.model;
// Generated Jun 10, 2016 11:13:38 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RequestMessageFieldMapping generated by hbm2java
 */
@Entity
@Table(name = "REQUEST_MESSAGE_FIELD_MAPPING", schema = "WFCONFIG")
public class RequestMessageFieldMapping implements java.io.Serializable {

	private int mtfmid;
	private int requestMessageTypeId;
	private String xmlFieldName;
	private String xmlFieldType;
	private String cmPropertyName;
	private String cmPropertyType;
	private String defaultValue;

	public RequestMessageFieldMapping() {
	}

	public RequestMessageFieldMapping(int mtfmid, int requestMessageTypeId) {
		this.mtfmid = mtfmid;
		this.requestMessageTypeId = requestMessageTypeId;
	}

	public RequestMessageFieldMapping(int mtfmid, int requestMessageTypeId, String xmlFieldName, String xmlFieldType,
			String cmPropertyName, String cmPropertyType, String defaultValue) {
		this.mtfmid = mtfmid;
		this.requestMessageTypeId = requestMessageTypeId;
		this.xmlFieldName = xmlFieldName;
		this.xmlFieldType = xmlFieldType;
		this.cmPropertyName = cmPropertyName;
		this.cmPropertyType = cmPropertyType;
		this.defaultValue = defaultValue;
	}

	@Id

	@Column(name = "MTFMID", unique = true, nullable = false)
	public int getMtfmid() {
		return this.mtfmid;
	}

	public void setMtfmid(int mtfmid) {
		this.mtfmid = mtfmid;
	}

	@Column(name = "REQUEST_MESSAGE_TYPE_ID", nullable = false)
	public int getRequestMessageTypeId() {
		return this.requestMessageTypeId;
	}

	public void setRequestMessageTypeId(int requestMessageTypeId) {
		this.requestMessageTypeId = requestMessageTypeId;
	}

	@Column(name = "XML_FIELD_NAME", length = 50)
	public String getXmlFieldName() {
		return this.xmlFieldName;
	}

	public void setXmlFieldName(String xmlFieldName) {
		this.xmlFieldName = xmlFieldName;
	}

	@Column(name = "XML_FIELD_TYPE", length = 20)
	public String getXmlFieldType() {
		return this.xmlFieldType;
	}

	public void setXmlFieldType(String xmlFieldType) {
		this.xmlFieldType = xmlFieldType;
	}

	@Column(name = "CM_PROPERTY_NAME", length = 50)
	public String getCmPropertyName() {
		return this.cmPropertyName;
	}

	public void setCmPropertyName(String cmPropertyName) {
		this.cmPropertyName = cmPropertyName;
	}

	@Column(name = "CM_PROPERTY_TYPE", length = 20)
	public String getCmPropertyType() {
		return this.cmPropertyType;
	}

	public void setCmPropertyType(String cmPropertyType) {
		this.cmPropertyType = cmPropertyType;
	}

	@Column(name = "DEFAULT_VALUE", length = 100)
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
