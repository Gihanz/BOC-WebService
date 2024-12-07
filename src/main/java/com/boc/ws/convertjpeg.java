

package com.boc.ws;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


/*
Created By SaiMadan on Mar 27, 2017
*/
public class convertjpeg {

	
	public static void main(String a[])
	{
		convertjpeg obj = new convertjpeg();
		obj.convertJpegOperation();
	}
	
	private static final char[] DIGITS = 
        {'0', '1', '2', '3', '4', '5', '6', '7',
         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	
	
	   public static final String toHex(byte[] data) {
	        final StringBuffer sb = new StringBuffer(data.length * 2);
	        for (int i = 0; i < data.length; i++) {
	            sb.append(DIGITS[(data[i] >>> 4) & 0x0F]);
	            sb.append(DIGITS[data[i] & 0x0F]);
	        }
	        return sb.toString();
	    }
	
	
	public void convertJpegOperation()
	{
		 File input= new File("D:/saimadan/UAT/Photo.jpg");
		 File output = new File("D:/saimadan/UAT/TestHex.tif");
		 StringBuffer fileContent = new StringBuffer();
		    try
		    {
		    	 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
		         BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		         DataInputStream fin = new DataInputStream(bis);
					
					String currentLine;
					while((currentLine=fin.readLine())!=null)
					{
						fileContent.append(currentLine);
					}
		       /* int read=0;
		        while ((read=bis.read()) != -1) {
		              String text = Integer.toString(read);
		              while (text.length() < 8) {
		                    text="0"+text;
		              }
		              bw.write(text);
		          byte[] imageBytes =     text.getBytes();
		          String hexImage = toHex(imageBytes);
		          bw.write(hexImage);
		        }*/
					//byte[] decoded = Base64.decodeBase64(fileContent.toString().getBytes());// getDecoder().decode(fileContent.toString());
		         System.out.println(toHex(fileContent.toString().getBytes()));
		        
		    } catch (IOException e) {
		            System.err.println(e);
		    }
		
	}
}
