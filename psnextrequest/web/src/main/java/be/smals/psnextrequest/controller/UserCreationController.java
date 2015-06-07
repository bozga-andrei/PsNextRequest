
package be.smals.psnextrequest.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;


/**
 * Managed bean controller for the language selection screen.
 * 
 * @author AndreiBozga
 * 
 * @since
 * 
 */

@ManagedBean(name = "userCreationController")
@RequestScoped
public class UserCreationController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -233461385453334452L;

	@EJB(name="ejb/PSNextRequestBeanUser")
    private PSNextRequestServiceRemoteUser serviceUser;
	
	@ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private String login;
    
    private String password;
    
    private String firstname;
    
    private String name;
    
    private String mail;

    

	public String handleUserCreation() throws PSNextRequestServiceException {	
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			User newUser = new User();
			newUser.setUserDistinguishedName(login);
			newUser.setUserFirstName(firstname);
			newUser.setUserLastName(name);
			newUser.setUserMail(mail);
			newUser.setUserPassword(password);
			User user = serviceUser.createUser(newUser);
			sessionBean.setUser(user);
			return "toRequestsConsult";
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), ""));
			return null; 
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again", ""));
			return null; 
		}		  
    }

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}     
	
	

}
