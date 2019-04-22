package com.reprisk.riskAnalysis.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class XmlReader{
	static String news;
public static String getxmlContent(String fileName) {
	
	try {
		
	      SAXParserFactory factory = SAXParserFactory.newInstance();
	      SAXParser saxParser = factory.newSAXParser();
	 
	      DefaultHandler handler = new DefaultHandler() {
	 
	      /* boolean newsitem = false;
	        boolean date = false;
	        boolean title = false;
	        boolean source = false;
	        boolean author = false; */
	        boolean text = false;
	 
	        public void startElement(String uri, String localName,
	            String qName, Attributes attributes)
	            throws SAXException {
	 
	          //System.out.println("Start Element :" + qName);
	 
	         /* if (qName.equalsIgnoreCase("news-item")) {
	        	  newsitem = true;
	          }
	 
	          if (qName.equalsIgnoreCase("date")) {
	        	  date = true;
	          }
	 
	          if (qName.equalsIgnoreCase("title")) {
	        	  title = true;
	          }
	 
	          if (qName.equalsIgnoreCase("source")) {
	        	  source = true;
	          }
	          if (qName.equalsIgnoreCase("author")) {
	        	  author = true;
	          }*/
	          if (qName.equalsIgnoreCase("text")) {
	        	  text = true;
	          }
	 
	        }
	 
	        public void endElement(String uri, String localName,
	                String qName)
	                throws SAXException {
	 
	              //System.out.println("End Element :" + qName);
	 
	        }
	 
	        public void characters(char ch[], int start, int length)
	            throws SAXException {
	        	
	         // System.out.println(new String(ch, start, length));
	        	 
	        	 
	         /* if (newsitem) {
	            System.out.println("News Item : "
	                + new String(ch, start, length));
	            newsitem = false;
	          }
	 
	          if (date) {
	              System.out.println("Date : "
	                  + new String(ch, start, length));
	              date = false;
	           }
	 
	          if (title) {
	              System.out.println("Title : "
	                  + new String(ch, start, length));
	              title = false;
	           }
	 
	          if (source) {
	              System.out.println("Source : "
	                  + new String(ch, start, length));
	              source = false;
	           }
	          if (author) {
	              System.out.println("Author : "
	                  + new String(ch, start, length));
	              author = false;
	           }*/
	          if (text) {
	        	  /*System.out.println("Text : "
	                  + new String(ch, start, length));*/
	              text = false;
	               XmlReader.news =  new String(ch, start, length);
	               
	           }
	          
	 
	        }
	 
	      };
	 
	      File file = new File(fileName);
	      InputStream inputStream= new FileInputStream(file);
	      Reader reader = new InputStreamReader(inputStream,"UTF-8");
	      
	      InputSource is = new InputSource(reader);
	      is.setEncoding("UTF-8");
	      
	      saxParser.parse(is, handler);
	      
	      reader.close();
	      inputStream.close();
	 
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	return news;

}	
	
	
}
