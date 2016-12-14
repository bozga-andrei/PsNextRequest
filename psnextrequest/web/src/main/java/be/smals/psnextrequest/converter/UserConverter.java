package be.smals.psnextrequest.converter;

import be.smals.psnextrequest.entity.User;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("userConverter")
public class UserConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {
        User user = new User();
        user.setUserId(Long.parseLong(value));
        return user;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {
        return String.valueOf(((User) value).getUserId());
    }

}
