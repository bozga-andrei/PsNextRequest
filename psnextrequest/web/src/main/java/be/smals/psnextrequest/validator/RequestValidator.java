package be.smals.psnextrequest.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("requestValidator")
public class RequestValidator implements Validator {
	/**
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String duration = (String) value;
		
		if(!duration.contains(":") || duration.length() > 5) {
			throw new ValidatorException(new FacesMessage("Respectez le format hh:mm svp!"));		
	    }
		
	}
}
