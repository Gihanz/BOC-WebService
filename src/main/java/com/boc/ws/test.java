

package com.boc.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Created By SaiMadan on Apr 3, 2017
*/
public class test {
	private static Logger log =LoggerFactory.getLogger(test.class);
	
	public static void main(String a[])
	{
		test t = new test();
		String caseParameters = "{\"CAS_CustomerMainType\":\"P\",\"CAS_BID\":\"1\",\"CAS_ReferenceNo\":\"4848484\",\"CAS_CusTitle\":\"MR\",\"CAS_Mobile\":\"1477769\",\"CAS_EmployerName\":\"XYZHBC HPYZA\",\"CAS_FullNameEntityName\":\"TUSHAR SAFAYA\",\"CAS_Income\":\"9.54836621E8\",\"CAS_PostCode\":\"18005\",\"CAS_Initials\":\"T\",\"CAS_MaritalStatus\":\"S\",\"CAS_PassportNo\":\"PXYZABIZBV\",\"CAS_Address1\":\"#717 24TH CROSS KSLAYOUT\",\"CAS_Gender\":\"M\",\"CAS_PhoneNumber\":\"0185432407\",\"CAS_CompanyAddress1\":\"SAHE AS ABOVE\",\"CAS_Email\":\"tusHARSAFAYAANPLCO\",\"CAS_SurnameShortEntityName\":\"SAFAYA\",\"CAS_TaxId\":\"1254321014\",\"CAS_NICNo\":\"123456789XYZW\"} ";
		//t.invokeCIFCase(caseParameters, "http://drdmtest01:9080/CreateCase", "CreateCASA");
		try {
			t.maxMonthDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void maxMonthDate() throws ParseException
	{
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date current = sf.parse("02/01/2018");
		Calendar todayCal =	Calendar.getInstance();
		todayCal.setTime(current);
		todayCal.add(Calendar.MONTH,-1);
		
		System.out.println("day is "+Long.valueOf(todayCal.getActualMaximum(Calendar.DAY_OF_MONTH)));
		System.out.println("month is "+Long.valueOf(todayCal.get(todayCal.MONTH)+1));
		System.out.println("year is "+Long.valueOf(todayCal.get(todayCal.YEAR)));
	}
	
	public String invokeCIFCase(String caseParameters,String restURL,String operation)
	{
		String output = null;
		StringBuffer outputStr = new StringBuffer(); 
		try 
		{
			if(null!= caseParameters)
			{
				String restUrl = restURL+"/"+operation;
				URL url = new URL(restUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setInstanceFollowRedirects( false );
				conn.setRequestMethod( "POST" );
				conn.setRequestProperty( "Content-Type", "application/json"); 
				conn.setRequestProperty( "charset", "utf-8");
				conn.setUseCaches( false );
				DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
				wr.write(caseParameters.getBytes());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
	
				
				log.debug("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					outputStr.append(output);
					log.debug(output);
				}
				if(null!=outputStr)
					output=outputStr.toString();
				conn.disconnect();
			}
			
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage()+""+e.fillInStackTrace());
			log.error("malformedURL, unable to getReferenceNumber");
			log.debug("Error Message is "+e.fillInStackTrace());
			log.debug("Error Message is "+e.getStackTrace());
			//throw new BSLException("er.db.malformedURL");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage()+""+e.fillInStackTrace());
			log.debug("Error Message is "+e.fillInStackTrace());
			log.debug("Error Message is "+e.getStackTrace());
			//throw new BSLException("er.db.protocalExceptionURL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage()+""+e.fillInStackTrace());
			log.error("IOException Occured, unable to getReferenceNumber");
			log.debug("Error Message is "+e.fillInStackTrace());
			log.debug("Error Message is "+e.getStackTrace());
			//throw new BSLException("er.db.ioexception");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage()+""+e.fillInStackTrace());
			log.error("Exception Occured, unable to getReferenceNumber");
			log.debug("Error Message is "+e.fillInStackTrace());
			log.debug("Error Message is "+e.getStackTrace());
			//throw new BSLException("er.db.ioexception");
		}
		
		return output;
	}

}
