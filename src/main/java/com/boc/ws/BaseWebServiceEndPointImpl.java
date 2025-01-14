package com.boc.ws;

import java.io.File;
import java.net.URL;

/*
Create By SaiMadan on Jun 14, 2016
*/

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.boc.model.BankBranch;
import com.boc.service.BankBranchService;
import com.boc.service.ProductService;

import javassist.Loader;

public class BaseWebServiceEndPointImpl {

	@Autowired 
	ProductService productService;
	
	@Autowired
	BankBranchService  bankBranchService;
	
   // public static Logger log = Logger.getLogger("com.boc.ws.BaseWebServiceEndPointImpl");
    private static Logger log =LoggerFactory.getLogger(BaseWebServiceEndPointImpl.class);
	static ResourceBundle configMsgBundle = ResourceBundle.getBundle("restconfig"); //Locale.getDefault()
	static ResourceBundle appExceptionMsgBundle = ResourceBundle.getBundle("ExceptionMessages",Locale.getDefault());
	
    private static Calendar lastLog4jPropertiesReloadedOn = null;
    public static String log4jpath;
    
	
	/*public static void init()
	{
		PropertyReader property;
		try {
			property = new PropertyReader();
			Properties prop = property.loadPropertyFile();
	        log4jpath = PropertyReader.getProperty(prop, "LOGPATH");
	        if(log4jpath != null)
	        {
	            File fin = new File(log4jpath);
	            Calendar lastModCal = Calendar.getInstance();
	            lastModCal.setTimeInMillis(fin.lastModified());
	            if(lastLog4jPropertiesReloadedOn != null)
	                log.debug((new StringBuilder("Log4j property file last loaded on:[")).append(lastLog4jPropertiesReloadedOn.getTime()).append("] ").append("Log4j property file last modified on:[").append(lastModCal.getTime()).append("]").toString());
	            if(lastLog4jPropertiesReloadedOn == null || lastLog4jPropertiesReloadedOn.before(lastModCal))
	            {
	                DOMConfigurator.configure(log4jpath);
	                lastLog4jPropertiesReloadedOn = lastModCal;
	                log.debug("Reloaded the Log4j property file as it has been modified since its last loaded time");
	            }
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	
	//String propertyFilePath = BOCCommon.getAppPath() + pathSep+"Autoconfig.properties" ;
	
	/*PropertyReader propertyReader = new PropertyReader();
	propertyReader.
	Properties props = BOCCommon.loadPropertyFile(propertyFilePath);
	
	String activityRoot = BOCCommon.getProperty(props, "LOG_PATH");
	String logpath=BOCCommon.getProperty(props, "LOG4J_FILE_PATH");
	
	//String logPropertyFile = BOCCommon.getAppPath() + pathSep+"config"+pathSep+"log4j.properties"; 
	String logPropertyFile =logpath+pathSep+"log4j.properties"; 
	PropertyConfigurator.configure(logPropertyFile);
	log = Logger.getLogger(BOCOperations.class);
	BOCCommon.loadLogConfiguration(logPropertyFile, activityRoot + pathSep+requestMessageName,referenceNumber+".log");
	log.info("Log File Initialized");*/
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	private String error;
	
	public static ResourceBundle getAppExceptionMsgBundle() {
		return appExceptionMsgBundle;
	}
	public String getExcptnMesProperty(String key)
    {
		if(appExceptionMsgBundle != null && appExceptionMsgBundle.containsKey(key)){
			return appExceptionMsgBundle.getString(key).intern();
		} else{
			return "Error in processing your request";
		}
    }
	
	public String getExcptnMesProperty(String key,Object[] params)
    {
		try
		{
		if(appExceptionMsgBundle != null && appExceptionMsgBundle.containsKey(key)){
			
			return MessageFormat.format(appExceptionMsgBundle.getString(key).intern(), params);
		} else{
			return "Error in processing your request";
		}
		}catch(Exception e){
			return "Error in processing your request";
		}
    }
	public static void setAppExceptionMsgBundle(ResourceBundle appExceptionMsgBundle) {
		BaseWebServiceEndPointImpl.appExceptionMsgBundle = appExceptionMsgBundle;
	}
	
	
	
}
