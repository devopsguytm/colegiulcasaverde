package com.login;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONObject;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.constants.Constants;

public class UserDAO {

	private static Database db = null;

	public static boolean initConnection() {

		try {
			CloudantClient client = ClientBuilder.url(new URL(Constants.cloudantURL))
					.iamApiKey(Constants.cloudantIAMApiKey)
					.build();

			// System.out.println("Server Version: " + client.serverVersion());

			db = client.database(Constants.cloudantDB_users, false);
			return true;

		} catch (Exception e) {
			System.out.println("Error initalizing Cloudant connection ! ");
			e.printStackTrace();
			return false;
		}

	}

	public static boolean login(String username, String password) {
		
		initConnection();

		try {

			InputStream result_input_stream = db.find("users");
			JSONObject obj = new JSONObject(convertStreamToString(result_input_stream));

			if (password.contentEquals(obj.getString("password"))) {
				System.out.println("Autentificare reusita.");
				return true;

			} else {
				System.out.println("Autentificare esuata.");
				return false;
			}

		} catch (Exception e) {

			System.out.println("Error getting users from Cloudant ! ");
			e.printStackTrace();
			return false;

		}
	}

	public static String convertStreamToString(java.io.InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
