package be.smals.psnextrequest.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    /**
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (value != null) {
            String login = (String) value;
            if (login.equals("admin") || login.equals("ADMIN") || login.equals("psnextadmin")) {
                //throw new ValidatorException(new FacesMessage("Ce nom d'utilisateur est reservé, veuillez en choisir un autre"));
            }
        }

    }

}


