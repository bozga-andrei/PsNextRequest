package be.smals.psnextrequest.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {
	/**
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		
		UIInput password = (UIInput) context.getViewRoot().findComponent("registerForm:pwd1");
		if (password == null){
			FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible de trouver le composant 'pwd1'.", "");
			throw new ValidatorException(message2);
		}
		String pwd1 = (String) password.getValue();
		String pwd2 = (String) value;
		
		if (!pwd2.equals(pwd1)) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas!", "Les mots de passe ne correspondent pas!");
	        throw new ValidatorException(message);
	    }
		
	}
}
