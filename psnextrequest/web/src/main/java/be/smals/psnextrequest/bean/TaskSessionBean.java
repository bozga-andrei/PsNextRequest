package be.smals.psnextrequest.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DualListModel;

import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;

@ManagedBean(name = "taskSessionBean")
@SessionScoped
public class TaskSessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3702250746683304286L;

	private Task selectedTask;
	
	private DualListModel<User> pickListResponsibles;
	
	public TaskSessionBean(){
		if(pickListResponsibles == null){
			pickListResponsibles = new DualListModel<User>();
		}
	}
	
	
	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public DualListModel<User> getPickListResponsibles() {
		return pickListResponsibles;
	}

	public void setPickListResponsibles(DualListModel<User> pickListResponsibles) {
		this.pickListResponsibles = pickListResponsibles;
	}	
	

}
