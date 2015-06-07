package be.smals.psnextrequest.bean;

import java.io.Serializable;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Role;
import be.smals.psnextrequest.entity.User;

@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -521844760121501573L;
	
	@ManagedProperty(value = "#{user}")
	private User user;
	
	private boolean isAdmin;
	
	private boolean isResponsible;
	
	private Request request;

	private Request responsibleRequest;
	
	private Request adminRequest;
	
	private TimeZone timeZone;
	
	public boolean isLoggedIn() {
        return user != null;
    }
	
	public boolean getIsAdmin(){
		for(Role roles : user.getRoles()){
			if(roles.getRoleName().contains("admin")){
				this.isAdmin = true;
				return isAdmin;
			}	
		}
		return false;
	}
	
	public boolean getIsResponsible(){
		this.isResponsible = !user.getResponsibles().isEmpty();
		return isResponsible;
	}
	
	
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Request getResponsibleRequest() {
		return responsibleRequest;
	}

	public void setResponsibleRequest(Request responsibleRequest) {
		this.responsibleRequest = responsibleRequest;
	}

	public Request getAdminRequest() {
		return adminRequest;
	}
	
	public void setAdminRequest(Request adminRequest) {
		this.adminRequest = adminRequest;
	}

	public TimeZone getTimeZone() {
		timeZone = TimeZone.getDefault();
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	
	
	
}
