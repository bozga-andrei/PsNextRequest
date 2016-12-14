
package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;


/**
 * Managed bean controller for the language selection screen.
 *
 * @author AndreiBozga
 */

@ManagedBean(name = "userUpdateController")
@RequestScoped
public class UserUpdateController implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -233461385453334452L;

    @EJB(name = "ejb/PSNextRequestBeanUser")
    private PSNextRequestServiceRemoteUser serviceUser;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private String oldPassword;

    private String newPassword;

    FacesContext context = FacesContext.getCurrentInstance();


    public void userUpdate(ActionEvent actionEvent) {
        try {
            User updateUser = sessionBean.getUser();
            serviceUser.updateUser(updateUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modifications enregistrées.", ""));
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again", ""));
        }
    }

    public void userPwdUpdate(ActionEvent actionEvent) {
        StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
        try {
            User updatePwdUser = sessionBean.getUser();
            //check existing password
            if (pwEncryptor.checkPassword(oldPassword, updatePwdUser.getUserPassword())) {
                updatePwdUser.setUserPassword(pwEncryptor.encryptPassword(newPassword));
                serviceUser.updateUser(updatePwdUser);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mot de passe changé.", ""));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Le mot de passe choisi ne correspond pas au mot de passe actuel!", ""));
            }
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again", ""));
        }
    }


    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
