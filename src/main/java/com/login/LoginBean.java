package com.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "loginBean")
@SessionScoped

public class LoginBean implements Serializable {

	@PostConstruct
	public void init() {
		
	}
	
	public String logOut(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null)
			session.invalidate();
		
		return "logout";
		
	}

	private static final long serialVersionUID = 1L;
	
	private String username="liceul_silvic";
	private String password="";
	private String message="";
	private boolean authenticated=false;

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String loginProject() {

		

		boolean result = UserDAO.login(username, password);
		if (result) {
			setMessage("");
			authenticated=true;
			return "change";
		} else {
			setMessage("Credentiale gresite.");
			authenticated=false;
			return "login";
		}
	}
}
