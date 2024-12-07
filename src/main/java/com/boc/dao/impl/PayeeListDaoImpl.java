package com.boc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.boc.dao.PayeeListDao;
import com.boc.dao.exceptions.DAOException;
import com.boc.model.Payee;
import com.boc.response.StandingOrderPayee;


@Repository
public class PayeeListDaoImpl extends abstractWFConfigdao implements PayeeListDao {

	private static Logger log =LoggerFactory.getLogger(PayeeListDaoImpl.class);
	Connection con;
	Statement st;
	ResultSet rs;
	
	public PayeeListDaoImpl() {
		getDbInstance();
	}
	
	private void getDbInstance(){	
		
		String url = "jdbc:db2://172.21.20.185:50000/WFCONFIG";
		String user = "WFCONFIG";
		String password = "password123$";
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			
			log.info("db connection");
			
		} catch (ClassNotFoundException e) {
			log.info(e.getMessage().toString());
		}
		
		try {
			con = DriverManager.getConnection(url, user, password);					
		} catch (SQLException e) {
			log.info(e.getMessage().toString());
		}
	}
	
	
	public List<String> getPayeeTypes() {
		
		log.info("payee db access..............");
		
		List<String> lstPayeeType = new ArrayList<String>();
		int i=1;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT DISTINCT PAYEE_TYPE FROM BSR_PAYEE_BASE");
			
			while(rs.next()){
				log.info("elements : " + rs.getString(i));
				lstPayeeType.add(rs.getString(i));
			}
			
		} catch (SQLException e) {
			log.info(e.getMessage().toString());
		}
		
		finally {
			try {
				rs.close();
				st.close();
				con.commit();
				con.close();
			} catch (SQLException e) {
				log.info(e.getMessage().toString());
			}				
		}		
		return lstPayeeType;			
	}
	

	public List<String> getPayeeListByType(String payeeType) {
		
		List<String> lstPayee = new ArrayList<String>();
		int i=1;
		
		try{
			st = con.createStatement();
			rs = st.executeQuery("SELECT DISTINCT PAYEE FROM BSR_PAYEE_BASE WHERE PAYEE_TYPE='"+payeeType+"'");
						
			while(rs.next()){
				log.info("elements : " + rs.getString(i));
				lstPayee.add(rs.getString(i));
			}	
				
		}catch(SQLException hex)
		{
			log.info("Error in getPayeeList");
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProfession",hex);
		}
		finally {
			try {
				rs.close();
				st.close();
				con.commit();
				con.close();
			} catch (SQLException e) {
				log.info(e.getMessage().toString());
			}				
		}		
		
		return lstPayee;
	}


	
	public StandingOrderPayee getPayeeDetails(String payee) {
		
		StandingOrderPayee payeeRtn = new StandingOrderPayee();
				
		try{
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM BSR_PAYEE_BASE WHERE PAYEE='"+payee+"'");
						
			while(rs.next()){
				log.info("elements : " + rs.getString("PAYEE"));
				payeeRtn.setPayee(rs.getString("PAYEE"));
				payeeRtn.setPayeeType(rs.getString("PAYEE_TYPE"));
				payeeRtn.setTargetType(rs.getString("TARGET_TYPE"));
				payeeRtn.setTargetDes(rs.getString("TARGET_DESCRIPTION"));
				payeeRtn.setFeePlan(rs.getString("FEE_PLAN"));
				payeeRtn.setCurCode(rs.getString("CURRENCY"));
				payeeRtn.setAccountNo(rs.getString("ACCOUNT_NO"));
			}
			
		}catch(SQLException hex)
		{
			log.info("Error in getPayeeDetails");
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProfession",hex);
		}
		finally {
			try {
				rs.close();
				st.close();
				con.commit();
				con.close();
			} catch (SQLException e) {
				log.info(e.getMessage().toString());
			}				
		}		
		
		return payeeRtn;		
	}
	
}
