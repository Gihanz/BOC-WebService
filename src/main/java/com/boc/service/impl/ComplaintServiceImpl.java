package com.boc.service.impl;

import com.boc.dao.ComplaintDao;
import com.boc.dao.impl.ComplaintDaoImpl;
import com.boc.response.Customer;
import com.boc.service.ComplaintService;

import java.util.List;
import java.util.Map;

import org.apache.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Configurable
public class ComplaintServiceImpl implements ComplaintService
{
  private static Logger log = LoggerFactory.getLogger(PayeeServiceImpl.class);
  @Autowired private ComplaintDao complaintDao = null;
  
  public ComplaintServiceImpl()
  {
    this.complaintDao = new ComplaintDaoImpl();
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public Customer[] getCustomerByNIC(String nic)
  {
    return this.complaintDao.getCustomerByNIC(nic);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public Customer[] getCustomerByMobile(String mobileNo)
  {
    return this.complaintDao.getCustomerByMobile(mobileNo);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public Boolean addComplaint(JSONObject complaint)
  {
    return this.complaintDao.addComplaint(complaint);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public List<Map<String, String>> getMajorCategories()
  {
    return this.complaintDao.getMajorCategories();
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public List<Map<String, String>> getSubCategories(String majorCategory)
  {
    return this.complaintDao.getSubCategories(majorCategory);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public List<Map<String, String>> getDepartments(String subCategory)
  {
    return this.complaintDao.getDepartments(subCategory);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public Boolean setComplaintStatus(String status, String ref)
  {
    return this.complaintDao.setComplaintStatus(status, ref);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public int getSLA(String subCategory)
  {
    return this.complaintDao.getSLA(subCategory);
  }
  
  @Override
  @Transactional(value="transactionManager", readOnly=true)
  public List<Map<String, String>> getEscalationEmailByBranch(String branch)
  {
    return this.complaintDao.getEscalationEmailByBranch(branch);
  }
}
