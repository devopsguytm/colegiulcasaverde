package com.cloudant;

import java.net.*;
import java.io.*;
import org.json.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import com.cloudant.client.api.*;

@ManagedBean(name="getLatestNews")
@SessionScoped

public class GetLatestNews implements Serializable{
	
   private static final long serialVersionUID = 1L;

   public Database db =null;	
	
	   public String response 	="";
	   public String title    	="";
	   public String body    	 ="";
	   public String author   	="";
	   public String date     	="";
	   
	   
	   public String   getTitle() 		{ return title;	 }
	   public String   getBody() 		{ return body;	 }
	   public String   getAuthor() 		{ return author; }
	   public String   getDate() 		{ return date;	 }
	   public String   getResponse() 	{ return response; }
   
        
	   public void setTitle(String text)	{ 	 title = text;    }        
	   public void setBody(String text) 	{ 	  body = text;    }
	   public void setAuthor(String text)	{ 	author = text;    }
	   public void setDate(String text)	{	  date = text;    }     
	
    
	  {
			try {	
				CloudantClient client = ClientBuilder.url(new URL("https://40f49618-e45a-45d8-9110-1d264e6e2766-bluemix.cloudant.com"))
		                .username("40f49618-e45a-45d8-9110-1d264e6e2766-bluemix")
		                .password("fc5d08660956c8ada867c0aaf7d237eb3ad76d89d3bd87b34fa110a011080c39")
		                .build();
				
				System.out.println("Server Version: " + client.serverVersion());
				
				List<String> databases = client.getAllDbs();
				System.out.println("All my databases : ");
				for ( String db : databases ) {
				    System.out.println(db);
				    System.out.println();
				}
				
				db = client.database("colegiul_casa_verde", false);
				
				getNewsFromDB();
				
				}
			catch (Exception e){
				System.out.println("Error initalizing Cloudant connection ! ");
				e.printStackTrace();
			}
	  }
	

	  public String   convertStreamToString(java.io.InputStream is) {
	        @SuppressWarnings("resource")
			java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	  }


	  public void getNewsFromDB(){
		
		try{
			
			 
			    InputStream result_input_stream=db.find("news");
			    JSONObject obj = new JSONObject(convertStreamToString(result_input_stream));
			    
			    title=	obj.getString("title");
			    body=	obj.getString("body");
			    author=	obj.getString("author");
			    date=	obj.getString("date");
			    
			    response=date+"\n\n"+title+"\n\n"+body+"\n\n"+author;
			    
			    System.out.println(date+ " - got news from Cloudant . ");
			   
				
	    }
		catch(Exception e) {
			
			
			System.out.println("Error getting news from Cloudant ! ");
			e.printStackTrace();
	 		
	  	}
	   	  	                    
		
	}
	
                

}
		
		