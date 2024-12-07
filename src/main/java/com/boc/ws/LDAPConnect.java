

package com.boc.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/*
Created By SaiMadan on Oct 19, 2016
*/
public class LDAPConnect {

	public static void main(String a[])
	/*{
	String url = "ldap://172.20.8.44:389";
	Hashtable env = new Hashtable();
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, url);
	env.put(Context.SECURITY_AUTHENTICATION, "simple");
	env.put(Context.SECURITY_PRINCIPAL, "CN=wasadmin,OU=DMS,DC=bocadt,DC=LOCAL");
	//env.put(Context.SECURITY_PRINCIPAL, "CN=p8admin_test,OU=DMS,DC=bankofceylon,DC=LOCAL");
	env.put(Context.SECURITY_CREDENTIALS, "boc@123");
	 
	try {
	    DirContext ctx = new InitialDirContext(env);
	    System.out.println("connected");
	    System.out.println(ctx.getEnvironment());
	     
	    // do something useful with the context...
	 
	    ctx.close();
	 
	} catch (AuthenticationNotSupportedException ex) {
	    System.out.println("The authentication is not supported by the server");
	} catch (AuthenticationException ex) {
	    System.out.println("incorrect password or username");
	} catch (NamingException ex) {
	    System.out.println("error when trying to create the context");
	}
	}*/
	{
		Integer gracePeriod = 11;
		String calDateStr="03-01-2016";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dt = null;
		try {
			dt = sdf.parse(calDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MONTH,+gracePeriod);
		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		
		Long day = Long.valueOf(cal.get(cal.DAY_OF_MONTH));
		Long month = Long.valueOf(cal.get(cal.MONTH)+1);					
		Long year = Long.valueOf(cal.get(cal.YEAR));
				
		calDateStr = String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
		System.out.println("After adding 30 days communicator date is "+calDateStr);
		
		
		String calDateStr1="16-03-2017";
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dt1 = null;
		try {
			dt1 = sdf.parse(calDateStr1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dt1);
		
		System.out.println(cal1.after(cal));
		
		
	}
}
