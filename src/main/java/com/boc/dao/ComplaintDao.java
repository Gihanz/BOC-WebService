package com.boc.dao;

import com.boc.response.Customer;
import java.util.List;
import java.util.Map;
import org.apache.commons.json.JSONObject;

public abstract interface ComplaintDao
{
  public abstract Customer[] getCustomerByNIC(String paramString);
  
  public abstract Customer[] getCustomerByMobile(String paramString);
  
  public abstract Boolean addComplaint(JSONObject paramJSONObject);
  
  public abstract Boolean setComplaintStatus(String paramString1, String paramString2);
  
  public abstract List<Map<String, String>> getMajorCategories();
  
  public abstract List<Map<String, String>> getSubCategories(String paramString);
  
  public abstract List<Map<String, String>> getDepartments(String paramString);
  
  public abstract List<Map<String, String>> getEscalationEmailByBranch(String paramString);
  
  public abstract int getSLA(String paramString);
}