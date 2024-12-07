package com.boc.dao.impl;

import com.boc.dao.ComplaintDao;
import com.boc.dao.exceptions.DAOException;
import com.boc.response.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.json.JSONException;
import org.apache.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplaintDaoImpl extends abstractWFConfigdao implements ComplaintDao
{
  private static Logger log = LoggerFactory.getLogger(PayeeListDaoImpl.class);
  
  ResultSet rs;
  ResultSet rs2;
  ResultSet rs3;
  ResultSet rs4;
  ResultSet rs5;
  ResultSet rs6;
  ResultSet rs7;
  ResultSet rs8;
  ResultSet rs9;
  ResultSet rs10;
  public Customer[] getCustomerByNIC(String nic)
  {
    int count = 0;
    try
    {
      
      rs2 = (ResultSet) session().createQuery("SELECT COUNT(*) AS COUNT FROM CCM_CUSTOMER_BASE WHERE NIC='" + nic + "'");
      while (this.rs2.next()) {
        count = Integer.parseInt(this.rs2.getString("COUNT"));
      }
      this.rs = (ResultSet) session().createQuery("SELECT * FROM CCM_CUSTOMER_BASE WHERE NIC='" + nic + "'");
      
      Customer[] customer = new Customer[count];
      
      int i = 0;
      Customer cusData;
      while (this.rs.next())
      {
        cusData = new Customer();
        cusData.setNic(this.rs.getString("NIC"));
        cusData.setMobile(this.rs.getString("MOBILE_NUMBER"));
        cusData.setRefNo(this.rs.getString("REFERENCE_NUMBER"));
        cusData.setComplaintDate(this.rs.getString("COMPLAINT_DATE"));
        cusData.setComplaintType(this.rs.getString("COMPLAINT_TYPE"));
        cusData.setComplaint(this.rs.getString("COMPLAINT"));
        cusData.setStatus(this.rs.getString("STATUS"));
        cusData.setCusName(this.rs.getString("CUSTOMER_NAME"));
        cusData.setComplaintSource(this.rs.getString("COMPLAINT_SOURCE"));
        cusData.setSolution(this.rs.getString("SOLUTION"));
        cusData.setCusType(this.rs.getString("CUSTOMER_TYPE"));
        customer[i] = cusData;
        i++;
      }
      return customer;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getCustomerByNIC", hex);
    }
    finally
    {
      try
      {
        this.rs.close();
        this.rs2.close();
        this.st.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public Customer[] getCustomerByMobile(String mobileNo)
  {
    int count = 0;
    try
    {
      this.st2 = this.con.createStatement();
      
      this.rs4 = this.st2.executeQuery("SELECT COUNT(*) AS COUNT FROM CCM_CUSTOMER_BASE WHERE MOBILE_NUMBER='" + mobileNo + "'");
      while (this.rs4.next()) {
        count = Integer.parseInt(this.rs4.getString("COUNT"));
      }
      this.rs3 = this.st2.executeQuery("SELECT * FROM CCM_CUSTOMER_BASE WHERE MOBILE_NUMBER='" + mobileNo + "'");
      
      Customer[] customer = new Customer[count];
      
      int i = 0;
      Customer cusData;
      while (this.rs3.next())
      {
        cusData = new Customer();
        cusData.setNic(this.rs3.getString("NIC"));
        cusData.setMobile(this.rs3.getString("MOBILE_NUMBER"));
        cusData.setRefNo(this.rs3.getString("REFERENCE_NUMBER"));
        cusData.setComplaintDate(this.rs3.getString("COMPLAINT_DATE"));
        cusData.setComplaintType(this.rs3.getString("COMPLAINT_TYPE"));
        cusData.setComplaint(this.rs3.getString("COMPLAINT"));
        cusData.setStatus(this.rs3.getString("STATUS"));
        cusData.setCusName(this.rs3.getString("CUSTOMER_NAME"));
        cusData.setComplaintSource(this.rs3.getString("COMPLAINT_SOURCE"));
        cusData.setSolution(this.rs3.getString("SOLUTION"));
        cusData.setCusType(this.rs3.getString("CUSTOMER_TYPE"));
        customer[i] = cusData;
        i++;
      }
      return customer;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getCustomerByMobile", hex);
    }
    finally
    {
      try
      {
        this.rs3.close();
        this.rs4.close();
        this.st2.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public Boolean addComplaint(JSONObject complaint)
  {
    String sql = null;
    PreparedStatement db2Stmt = null;
    committed = Boolean.valueOf(false);
    
    log.info("in");
    try
    {
      String nic = complaint.getString("NIC");
      String mobile = complaint.getString("MOBILE");
      String ref = complaint.getString("REFERENCE");
      String date = complaint.getString("DATE");
      String comType = complaint.getString("COMTYPE");
      String complnt = complaint.getString("COMPLAINT");
      String status = complaint.getString("STATUS");
      String customer = complaint.getString("CUSTOMER");
      String comSource = complaint.getString("COMSOURCE");
      String solutn = complaint.getString("SOLUTION");
      String cusType = complaint.getString("CUSTYPE");
      
      log.info(nic);
      
      sql = "INSERT INTO CCM_CUSTOMER_BASE(NIC,MOBILE_NUMBER,REFERENCE_NUMBER,COMPLAINT_DATE,COMPLAINT_TYPE,COMPLAINT,STATUS,CUSTOMER_NAME,COMPLAINT_SOURCE,SOLUTION,CUSTOMER_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
      db2Stmt = this.con.prepareStatement(sql);
      
      db2Stmt.setString(1, nic);
      db2Stmt.setString(2, mobile);
      db2Stmt.setString(3, ref);
      db2Stmt.setString(4, date);
      db2Stmt.setString(5, comType);
      db2Stmt.setString(6, complnt);
      db2Stmt.setString(7, status);
      db2Stmt.setString(8, customer);
      db2Stmt.setString(9, comSource);
      db2Stmt.setString(10, solutn);
      db2Stmt.setString(11, cusType);
      
      int i = db2Stmt.executeUpdate();
      
      log.info(String.valueOf(i));
      if (i == 1) {
        committed = Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getCustomerByMobile", hex);
    }
    finally
    {
      try
      {
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public List<Map<String, String>> getMajorCategories()
  {
    rtnData = new ArrayList();
    try
    {
      this.st3 = this.con.createStatement();
      this.rs5 = this.st3.executeQuery("SELECT DISTINCT MAJORCATEGORY_ID,MAJORCATEGORY FROM CCM_MAJOR_CATEGORY_BASE");
      while (this.rs5.next())
      {
        Map<String, String> bankData = new HashMap();
        bankData.put("MajorID", this.rs5.getString("MAJORCATEGORY_ID"));
        bankData.put("MajorType", this.rs5.getString("MAJORCATEGORY"));
        rtnData.add(bankData);
      }
      return rtnData;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getMajorCategories", hex);
    }
    finally
    {
      try
      {
        this.rs5.close();
        this.st3.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public List<Map<String, String>> getSubCategories(String majorCategoryId)
  {
    lstSubTypes = new ArrayList();
    int i = 1;
    try
    {
      this.st4 = this.con.createStatement();
      this.rs6 = this.st4.executeQuery("SELECT A.SUBCATEGORY AS SUBCATEGORY ,A.SUBCATEGORY_ID AS SUBCATEGORY_ID FROM CCM_SUBCATEGORY_BASE A RIGHT JOIN CCM_MAJOR_CATEGORY_SUBCATEGORY_MAPPING B ON A.SUBCATEGORY_ID = B.SUBCATEGORY_ID WHERE B.MAJORCATEGORY_ID='" + majorCategoryId + "'");
      while (this.rs6.next())
      {
        Map<String, String> bankData = new HashMap();
        bankData.put("SubID", this.rs6.getString("SUBCATEGORY_ID"));
        bankData.put("SubType", this.rs6.getString("SUBCATEGORY"));
        lstSubTypes.add(bankData);
      }
      return lstSubTypes;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getSubCategories", hex);
    }
    finally
    {
      try
      {
        this.rs6.close();
        this.st4.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public List<Map<String, String>> getDepartments(String subCategoryId)
  {
    lstDepartmnt = new ArrayList();
    int i = 1;
    try
    {
      this.st5 = this.con.createStatement();
      this.rs7 = this.st5.executeQuery("SELECT A.BRANCH_CODE AS BRANCH_CODE ,A.BRANCH_NAME AS BRANCH_NAME FROM BRANCH_BASE A LEFT JOIN CCM_SUBCATEGORY_DEPARTMENT_MAPPING B ON A.BRANCH_CODE = B.BRANCH_CODE WHERE B.SUBCATEGORY_ID='" + subCategoryId + "'");
      while (this.rs7.next())
      {
        Map<String, String> bankData = new HashMap();
        bankData.put("DeptCode", this.rs7.getString("BRANCH_CODE"));
        bankData.put("DeptName", this.rs7.getString("BRANCH_NAME"));
        lstDepartmnt.add(bankData);
      }
      return lstDepartmnt;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getSubCategories", hex);
    }
    finally
    {
      try
      {
        this.rs7.close();
        this.st5.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public int getSLA(String subCategoryId)
  {
    sla = -1;
    try
    {
      this.st6 = this.con.createStatement();
      this.rs8 = this.st6.executeQuery("SELECT SLA FROM CCM_SUBCATEGORY_BASE WHERE SUBCATEGORY_ID='" + subCategoryId + "'");
      while (this.rs8.next()) {
        sla = this.rs8.getInt("SLA");
      }
      return sla;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getSLA", hex);
    }
    finally
    {
      try
      {
        this.rs8.close();
        this.st6.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public Boolean setComplaintStatus(String status, String ref)
  {
    String sql = null;
    Statement stmt = null;
    committed = Boolean.valueOf(false);
    
    log.info("in");
    try
    {
      log.info(status);
      
      stmt = this.con.createStatement();
      sql = "UPDATE CCM_CUSTOMER_BASE SET STATUS = '" + status + "' WHERE REFERENCE_NUMBER ='" + ref + "'";
      
      int i = stmt.executeUpdate(sql);
      
      log.info(String.valueOf(i));
      if (i == 1) {
        committed = Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.setStatusCompliant", hex);
    }
    finally
    {
      try
      {
        stmt.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
  
  public List<Map<String, String>> getEscalationEmailByBranch(String branch)
  {
    email = new ArrayList();
    int i = 1;
    String escalationEmail = null;
    String cxmEmail = null;
    boolean empty = true;
    try
    {
      this.st7 = this.con.createStatement();
      this.rs9 = this.st7.executeQuery("SELECT EMAIL FROM CCM_ESCALATION_EMAIL_BASE WHERE PROVINCE_OR_DEPT_CODE='16201'");
      while (this.rs9.next()) {
        cxmEmail = this.rs9.getString("EMAIL");
      }
      this.rs10 = this.st7.executeQuery("SELECT EMAIL FROM WFCONFIG.CCM_ESCALATION_EMAIL_BASE WHERE PROVINCE_OR_DEPT_CODE = (SELECT DISTINCT A.PID FROM PROVINCE_BRANCH_MAPPING A INNER JOIN BRANCH_BASE B ON A.BID = B.BID WHERE B.BRANCH_CODE ='" + branch + "')");
      while (this.rs10.next())
      {
        escalationEmail = this.rs10.getString("EMAIL");
        empty = false;
      }
      if (empty)
      {
        this.rs10 = this.st7.executeQuery("SELECT EMAIL FROM CCM_ESCALATION_EMAIL_BASE WHERE PROVINCE_OR_DEPT_CODE ='" + branch + "'");
        while (this.rs10.next()) {
          escalationEmail = this.rs10.getString("EMAIL");
        }
      }
      Map<String, String> emailData = new HashMap();
      emailData.put("cxmEmail", cxmEmail);
      emailData.put("escalationEmail", escalationEmail);
      email.add(emailData);
      






















      return email;
    }
    catch (SQLException hex)
    {
      hex.printStackTrace();
      log.error("Error ", hex.fillInStackTrace());
      log.error(hex.getMessage());
      throw new DAOException("er.db.getEscalationEmailByBranch", hex);
    }
    finally
    {
      try
      {
        this.rs9.close();
        this.rs10.close();
        this.st7.close();
        this.con.commit();
        this.con.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        log.error("Error ", e.fillInStackTrace());
        log.error(e.getMessage());
      }
    }
  }
}
