
package be.smals.psnextrequest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;



import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.ProjectTeam;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceRemoteProject;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import be.smals.psnextrequest.service.tasks.PSNextRequestServiceRemoteTask;


/**
 * 
 * @author AndreiBozga
 * 
 * @since
 * 
 */
@ManagedBean(name = "requestControllerBean")
@RequestScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RequestController {
    
	private User user;
	
	private Request request = new Request();

    private String description;

    private String duration;

    private Date endDate;

    private Date startDate;

    private int userId;
    
    private String userFirstName;
    
    private String userLastName;
    
    private int status;

    private String visibleForResp;

    private String visibleForUser;
    
    private boolean reqChecked;
    
    private Long selectedProjectId;
    
    private Project selectedProject;
    
    private Long selectedTaskId;
    
    private Task selectedTask;
    
    private String projectName;
    
    private List<Project> projects;
    
    private List<Task> tasks;
    
    private boolean tasksVisible = true;
    
    private List<SelectItem> tasksItems;

    
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    
    @EJB(name = "ejb/PSNextRequestBeanRequest")
	private PSNextRequestServiceRemoteRequest serviceRequest;
    
    @EJB(name = "ejb/PSNextRequestBeanProject")
	private PSNextRequestServiceRemoteProject serviceProject;
    
    @EJB(name = "ejb/PSNextRequestBeanTask")
	private PSNextRequestServiceRemoteTask serviceTask;
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    /**
     * Initiation.
     */
    @PostConstruct
    public void init() {
    	projects = new ArrayList<Project>();
    	tasks = new ArrayList<Task>();
    	try {
    		projects = this.getProjects();
    		if(!projects.isEmpty()){
    			//erreur de validation. La valeur est incorrecte, si les tâches ne sont pas préchargées 
    			tasks = serviceTask.getAllTasks();
    		}
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
		}
    	
    }

	
	/**
     * Get all existing tasks for the selected project.
     * 
     */
	public void getTasksByProject(AjaxBehaviorEvent event){
		try{
			
			if(selectedProjectId != 0){
				tasks = serviceTask.getTasksByProject(serviceProject.getProjectById((long)selectedProjectId));
				if(!tasks.isEmpty()){
					tasksVisible = false;
				}
			}
		} catch (PSNextRequestServiceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), "")); 
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Un problème est survenu pour le chargement des tâches existants.", ""));
		}
	}
	
	/**
     * Create request and return the appropriate outcome
     * 
     * @return the outcome.
     */
	public String createRequest(){
		try {
			if(endDate.compareTo(startDate) < 0){
				context.addMessage("newRequestBtn", new FacesMessage("Sélectionnez une date de fin supérieure à la date du début svp!"));
				return null;
			}
			request.setTask(serviceTask.getTaskById((long)selectedTaskId));
			request.setUser(sessionBean.getUser());
			request.setRequestStartDate(startDate);
			request.setRequestEndDate(endDate);
			request.setRequestDuration(duration);
			request.setRequestDescription(description);
			request.setRequestVisibleForUser(true);
			serviceRequest.createRequest(request);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande créée avec success!", ""));
			return "toRequestsConsult";
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), ""));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again.", ""));
			return null;
		}
	}
	
	
	/**
     * Update request and and return the appropriate outcome
     * 
     * @return the outcome.
     */
    public String updateRequest(){
    	request = new Request();
		try {
			if(checkSelection()){
				request = sessionBean.getRequest();
				if(request.getRequestStatus() != 0 && request.getRequestStatus() != 3){
					context.addMessage("updateBtn", new FacesMessage("La demande doit être en cours de validation ou refusée pour pouvoir la modifier."));
					return null;
				} else if(request.getRequestEndDate().compareTo(request.getRequestStartDate()) < 0){
					context.addMessage("updateBtn", new FacesMessage("Sélectionnez une date de fin supérieure à la date du début svp!"));
					return null;
				}
				serviceRequest.updateRequest(request);
				return "toRequestsConsult";
			}
			return null;
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
    
    
    /**
     * Request: set visible for user attribut to "false" and and return the appropriate outcome
     * 
     * @return the outcome.
     */
    public String deleteRequest(ActionEvent actionEvent){
    	try {
    		if(checkSelection()){
    			request = sessionBean.getRequest();
        		if(request.getRequestStatus() == 0 ){
    				context.addMessage("deleteBtn", new FacesMessage("La demande ne peut pas être supprimée elle est en cours de validation."));
    				return null;
        		}
        		request.setRequestVisibleForUser(false);
        		serviceRequest.updateRequest(request);
    			sessionBean.setRequest(null);
    			return "toRequestsConsult";
    		}
    		return null;
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
    
    
    /**
     * Delete request and and return the appropriate outcome
     * 
     * @return the outcome.
     */
    public String physicallyDeleteRequest(){
    	try {
    		request = sessionBean.getRequest();
    		serviceRequest.deleteRequest(request);
			sessionBean.setRequest(null);
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
    	
    
    /**
     * Cancel request by the User
     * 
     */
	public String cancelRequest(){
    	try {
    		if(checkSelection()){
    			request = sessionBean.getRequest();
        		if(request.getRequestStatus() != 0 ){
    				context.addMessage("updateBtn", new FacesMessage("La demande ne peut pas être annulée, elle doit être en cours pour l'annuler."));
    				return null;
        		}
        		request.setRequestStatus(4);
        		serviceRequest.updateRequest(request);
    			sessionBean.setRequest(null);
    			return "toRequestsConsult";
    		} else {
    			return null;
    		}
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La demande n'a pas pu être annulée: " + e.getMessage(), ""));
			context.renderResponse();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again", ""));
			return null;
		}
    }

	/**
     * Reactivate request after canceled by User
     * 
     */
	public String reactivateRequest(){
    	try {
    		if(checkSelection()){
    			request = sessionBean.getRequest();
        		if(request.getRequestStatus() == 4 ){
        			request.setRequestStatus(0);
        			serviceRequest.updateRequest(request);
        			sessionBean.setRequest(null);
        			return "toRequestsConsult";
        		} else {
        			context.addMessage("updateBtn", new FacesMessage("La demande ne peut pas être réactivée, elle n'est pas annulée"));
    				return null;
        		}
    		} else {
    			context.addMessage("updateBtn", new FacesMessage("La demande ne peut pas être annulée, elle doit être en cours pour l'annuler."));
				return null;
    		}
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage("updateBtn", new FacesMessage("La demande n'a pas pu être annulée: " + e.getMessage()));
			context.renderResponse();
			return null;
		}
    }
	
    
    public boolean checkSelection(){
    	if(sessionBean.getRequest() == null){
    		context.addMessage("form:modificationBtn", new FacesMessage("Selectionnez une demande svp!"));
    		return false;
    	} else {
    		return true;
    	}
    }
    
 	
    
    
    public Request getRequest() {
		return request;
	}
    
	public void setRequest(Request request) {
		this.request = request;
	}
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVisibleForResp() {
		return visibleForResp;
	}

	public void setVisibleForResp(String visibleForResp) {
		this.visibleForResp = visibleForResp;
	}

	public String getVisibleForUser() {
		return visibleForUser;
	}

	public void setVisibleForUser(String visibleForUser) {
		this.visibleForUser = visibleForUser;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getSelectedProjectId() {
		return selectedProjectId;
	}

	public void setSelectedProjectId(Long selectedProjectId) {
		this.selectedProjectId = selectedProjectId;
	}
		
	public Long getSelectedTaskId() {
		return selectedTaskId;
	}
	
	public void setSelectedTaskId(Long selectedTaskId) {
		try {
			if(selectedTaskId != null)
			selectedTask = serviceTask.getTaskById((long)selectedTaskId);
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
		}
		this.selectedTaskId = selectedTaskId;
	}

	public List<Project> getProjects() throws PSNextRequestServiceException {
		if(projects.isEmpty()){
			sessionBean.getUser().getTeams().size();
			for(ProjectTeam ptjTeam : sessionBean.getUser().getTeams()){
				if(serviceProject.getProjectByTeam(ptjTeam).getProjectStatus() == 0)
				projects.add(serviceProject.getProjectByTeam(ptjTeam));
			}
		}
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public boolean isReqChecked() {
		return reqChecked;
	}

	public void setReqChecked(boolean reqChecked) {
		this.reqChecked = reqChecked;
	}

	public List<SelectItem> getTasksItems() {
		return tasksItems;
	}

	public void setTasksItems(List<SelectItem> tasksItems) {
		this.tasksItems = tasksItems;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public boolean isTasksVisible() {
		return tasksVisible;
	}

	public void setTasksVisible(boolean tasksVisible) {
		this.tasksVisible = tasksVisible;
	}


	
}
