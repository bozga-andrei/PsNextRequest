
package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;


/**
 * Managed bean controller for the language selection screen.
 *
 * @author AndreiBozga
 */

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

    @EJB(name = "ejb/PSNextRequestBeanUser")
    private PSNextRequestServiceRemoteUser serviceUser;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;


    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = context.getExternalContext();

    private String login;
    private String password;

    //@ManagedProperty(value = "#{user}")
    private User user;
    List<FacesMessage> errors = new ArrayList<FacesMessage>();

    public String handleConnexion() throws PSNextRequestServiceException {
        try {
            if (login.equals(null) || login.equals("")) {
                System.out.println("login is:" + login);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Entrez votre nom d'utilisateur svp!", ""));
                errors.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "Entrez votre nom d'utilisateur svp!", ""));
            }
            if (password.equals(null) || password.equals("")) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Entrez votre mot de passe svp!", ""));
                errors.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "Entrez votre mot de passe svp!", ""));
            }

            if (errors.isEmpty()) {
                user = serviceUser.loginUser(login, password);
                if (user == null || user.equals("")) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom d'utilisateur ou le mot de passe n'est pas valide", ""));
                    errors.add(new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom d'utilisateur ou le mot de passe n'est pas valide", ""));
                }
                if (errors.isEmpty()) {
                    sessionBean.setUser(user);
                    externalContext.getSessionMap().put("sessionBean", sessionBean);
                    return "toRequestsConsult";
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            context.addMessage("loginControllerBtn", new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom d'utilisateur ou le mot de passe n'est pas valide", ""));
            context.renderResponse();
            return null;
        }
    }

    public String handleDeconnexion() {
        externalContext.invalidateSession();
        sessionBean.setUser(null);
        return "toLogin";
    }


    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
