package com.cloudant;

import java.net.*;
import java.io.*;
import org.json.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

import com.cloudant.client.api.*;
import com.constants.Constants;
import com.login.*;

@ManagedBean(name = "setLatestNews")
@SessionScoped

public class SetLatestNews implements Serializable {

	private static final long serialVersionUID = 1L;
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	@SuppressWarnings("deprecation")
	private LoginBean loginBean = (LoginBean) facesContext.getApplication().createValueBinding("#{loginBean}")
			.getValue(facesContext);

	public Database db = null;

	public String response = "";
	public String title = "";
	public String body = "";
	public String author = "";
	public String date = "";

	public boolean authenticated = loginBean.isAuthenticated();

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getAuthor() {
		return author;
	}

	public String getDate() {
		return date;
	}

	public String getResponse() {
		return response;
	}

	public void setTitle(String text) {

		System.out.println("Title entered = " + text);
		title = text;
	}

	public void setBody(String text) {

		System.out.println("Body entered = " + text);
		body = text;
	}

	public void setAuthor(String text) {

		System.out.println("Author entered = " + text);
		author = text;
	}

	public void setDate(String text) {

		System.out.println("Date entered = " + text);
		date = text;
	}

	{
		try {
			CloudantClient client = ClientBuilder.url(new URL(Constants.cloudantURL))
					.username(Constants.cloudantUsername).password(Constants.cloudantPassword).build();

			db = client.database(Constants.cloudantDB, false);

			getNewsFromDB();

		} catch (Exception e) {
			System.out.println("Error initalizing Cloudant connection ! ");
			e.printStackTrace();
		}
	}

	public String convertStreamToString(java.io.InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public void getNewsFromDB() {

		try {

			InputStream result_input_stream = db.find("news");
			JSONObject obj = new JSONObject(convertStreamToString(result_input_stream));

			title = obj.getString("title");
			body = obj.getString("body");
			author = obj.getString("author");
			date = obj.getString("date");

			response = "";

		} catch (Exception e) {

			System.out.println("Error getting news from Cloudant ! ");
			e.printStackTrace();

		}

	}

	public void setNewsInDB() {

		String news_rev = "";

		try {

			if (db.contains("news")) {

				InputStream result_input_stream = db.find("news");
				JSONObject obj = new JSONObject(convertStreamToString(result_input_stream));

				news_rev = obj.getString("_rev");

				Map<String, Object> h = new HashMap<String, Object>();
				h.put("_id", "news");
				h.put("date", date);
				h.put("title", title);
				h.put("body", body);
				h.put("author", author);
				h.put("_rev", news_rev);

				db.update(h);

			}

			response = "Stire adaugata cu success.";

		} catch (Exception e) {

			System.out.println("Error setting news in Cloudant ! ");
			e.printStackTrace();

		}

	}

}
