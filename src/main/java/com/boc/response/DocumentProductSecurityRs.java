

package com.boc.response;

/*
Create By SaiMadan on Jun 23, 2016
*/
public class DocumentProductSecurityRs 
{
	String[] documentName;
	Character[] scanRequired;
	Character[] mandatory;
	String[] uploadRequired;
	public String[] getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String[] documentName) {
		this.documentName = documentName;
	}
	public Character[] getScanRequired() {
		return scanRequired;
	}
	public void setScanRequired(Character[] scanRequired) {
		this.scanRequired = scanRequired;
	}
	public Character[] getMandatory() {
		return mandatory;
	}
	public void setMandatory(Character[] mandatory) {
		this.mandatory = mandatory;
	}
	public String[] getUploadRequired() {
		return uploadRequired;
	}
	public void setUploadRequired(String[] uploadRequired) {
		this.uploadRequired = uploadRequired;
	}
	
	
	
}
