package be.smals.psnextrequest.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceRemoteProject;

@ManagedBean(name = "projectConverter")
public class ProjectConverter implements Converter{
	
	@EJB(name = "ejb/PSNextRequestBeanProject")
	private PSNextRequestServiceRemoteProject serviceProject;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (value == null || value.isEmpty()) {
            return null;
        }
		Long id = Long.parseLong(value);
		Project project = new Project();
		project.setProjectId(id);
		try {
			project = serviceProject.getProjectById(id);
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
		}
		return project;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value == "0" || value == null ){
			return null;
		}
		if (!(value instanceof Project)) {
            return null;
        }
		return String.valueOf( ((Project)value).getProjectId());
	}

}
