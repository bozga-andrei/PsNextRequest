package be.smals.psnextrequest.converter;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.tasks.PSNextRequestServiceRemoteTask;



@ManagedBean(name = "taskConverter")
public class TaskConverter implements Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5465422870183489913L;

	@EJB(name = "ejb/PSNextRequestBeanTask")
	private PSNextRequestServiceRemoteTask serviceTask;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == "0" || value == null || value.isEmpty()) {
            return null;
        }
		Long id = Long.parseLong(value);
		Task task = new Task();
		task.setTaskId(id);
		try {
			task = serviceTask.getTaskById(id);
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value == "0" || value == null ){
			return null;
		}
		if (!(value instanceof Task)) {
            return null;
        }
		return String.valueOf(((Task)value).getTaskId());
	}
	
	

}

