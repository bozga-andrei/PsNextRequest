package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.ProjectSessionBean;
import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.bean.TaskSessionBean;
import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.Responsible;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceRemoteProject;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import be.smals.psnextrequest.service.tasks.PSNextRequestServiceRemoteTask;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "taskControllerBean")
@RequestScoped
public class TaskController {

    List<User> source;
    List<User> sourceFiltered = new ArrayList<User>();
    List<User> target;
    FacesContext context = FacesContext.getCurrentInstance();
    private Project project;
    private Task task;
    private Long taskId;
    private String taskDescription;
    private int taskStatus;
    private String taskName;
    private List<Task> tasks;
    private Task selectedTask;
    private List<User> selectedResponsibles;
    private List<User> valueSelectedResponsibles;
    private List<SelectItem> responsiblesItems;
    @SuppressWarnings("unused")
    private ArrayList<User> users;
    //pickList
    private DualListModel<User> pickListUsers;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{taskSessionBean}")
    private TaskSessionBean taskSessionBean;
    @ManagedProperty(value = "#{projectSessionBean}")
    private ProjectSessionBean projectSessionBean;
    @EJB(name = "ejb/PSNextRequestBeanTask")
    private PSNextRequestServiceRemoteTask serviceTask;
    @EJB(name = "ejb/PSNextRequestBeanUser")
    private PSNextRequestServiceRemoteUser serviceUser;
    @EJB(name = "ejb/PSNextRequestBeanProject")
    private PSNextRequestServiceRemoteProject serviceProject;
    @EJB(name = "ejb/PSNextRequestBeanRequest")
    private PSNextRequestServiceRemoteRequest serviceRequest;

    @PostConstruct
    public void init() {
        System.out.println("Init Methode");

        if (selectedResponsibles == null) {
            selectedResponsibles = new ArrayList<User>();
        }
        target = new ArrayList<User>();
        source = new ArrayList<User>();

        if (sourceFiltered.isEmpty()) {
            System.out.println("SourceFiltered is Empty");
            source = getUsers();
        } else {
            source = sourceFiltered;
        }

        //remplir la liste avec l'utilisateurs
        pickListUsers = new DualListModel<User>(source, target);

    }


    /**
     * recuperer la seletion du tableau tasks
     *
     * @param event
     */
    public void onSelectionTask(SelectEvent event) {
        Task task = taskSessionBean.getSelectedTask();
        List<Responsible> existingResponsibles = new ArrayList<Responsible>();
        existingResponsibles = task.getResponsibles();
        for (Responsible resp : existingResponsibles) {
            target.add(resp.getUser());
        }

        List<Integer> indexs = new ArrayList<Integer>();
        for (User sourceUser : source) {
            for (User targetUser : target) {
                if (sourceUser.getUserDistinguishedName().equals(targetUser.getUserDistinguishedName())) {
                    indexs.add(source.indexOf(sourceUser));
                }
            }
        }
        setSourceFiltered(source);
        int nbRemoved = 0;
        for (int index : indexs) {
            getSourceFiltered().remove(index - nbRemoved);
            nbRemoved++;
        }

        pickListUsers = new DualListModel<User>(sourceFiltered, target);
        taskSessionBean.setPickListResponsibles(pickListUsers);

    }


    public void createTask(ActionEvent actionEvent) {
        try {
            if (taskName != "" && taskName != null) {
                task = new Task();
                task.setTaskName(taskName);
                task.setTaskDescription(taskDescription);
                task.setTaskStatus(0);
                task.setProject(projectSessionBean.getSelectedProject());
                if (selectedResponsibles == null || selectedResponsibles.isEmpty()) {
                    for (User user : pickListUsers.getTarget()) {
                        selectedResponsibles.add(serviceUser.getUserById(user.getUserId()));
                    }
                }
                if (selectedResponsibles != null && !selectedResponsibles.isEmpty()) {
                    task = serviceTask.createTask(selectedResponsibles, task);
                    RequestContext.getCurrentInstance().execute("PF('createTask').hide()");
                    context.addMessage("msg", new FacesMessage("Nouvelle tâche créée."));
                } else {
                    RequestContext.getCurrentInstance().execute("createTask.jq.effect('shake', { times:3 }, 100)");
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sélectionnez au moins un responsable svp!", ""));
                }
            } else {
                RequestContext.getCurrentInstance().execute("createTask.jq.effect('shake', { times:3 }, 100)");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom de la tâche est obligatoire!", ""));
            }

        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Erreur:" + e.getMessage()));
        }
    }

    public String updateTask() {
        try {
            Task task = taskSessionBean.getSelectedTask();
//			task.setTaskName(taskName);
//			task.setTaskDescription(taskDescription);
//			task.setTaskStatus(taskStatus);
            if (selectedResponsibles == null || selectedResponsibles.isEmpty()) {
                for (User user : taskSessionBean.getPickListResponsibles().getTarget()) {
                    selectedResponsibles.add(serviceUser.getUserById(user.getUserId()));
                }
            }
            if (selectedResponsibles != null && !selectedResponsibles.isEmpty()) {
                List<Responsible> exitingResponsibles = new ArrayList<Responsible>();
                List<User> exitingUserResponsibles = new ArrayList<User>();
                List<User> userResponsiblesToRemove = new ArrayList<User>();
                List<Integer> indexs = new ArrayList<Integer>();

                exitingResponsibles = task.getResponsibles();
                for (Responsible resp : exitingResponsibles) {
                    exitingUserResponsibles.add(resp.getUser());
                }

                for (User existingResp : exitingUserResponsibles) {
                    for (User newResp : selectedResponsibles) {
                        if (existingResp.getUserId().equals(newResp.getUserId())) {
                            indexs.add(exitingUserResponsibles.indexOf(existingResp));
                        }
                    }
                }

//		    	int nbRemoved=0;
//				for(int index : indexs){
//					System.out.println("remove index: " + index);
//					exitingUserResponsibles.remove(index-nbRemoved);
//					nbRemoved++;
//					System.out.println("isRemoved index: " + index);
//				}

                for (User existingResp : exitingUserResponsibles) {
                    userResponsiblesToRemove.add(existingResp);
                }
                serviceTask.updateTask(selectedResponsibles, userResponsiblesToRemove, task);
                return "toTaskConsult";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sélectionnez au moins un responsable svp!", ""));
                return null;
            }

        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Erreur:" + e.getMessage()));
            return null;
        }
    }

    public String deleteTask() {
        try {
            serviceTask.disableTask(taskSessionBean.getSelectedTask());
            return "toTaskConsult";
        } catch (PSNextRequestServiceException e) {
            context.addMessage(null, new FacesMessage("Une erreur est survenu, la tâche ne peut pas étre supprimée"));
            return null;
        }
    }


    public List<User> getUsers() {
        try {
            return serviceUser.getAllUsers();
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Une erreur est survenu pour la recuperation de utilisateurs"));
            return null;
        }
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        try {
            tasks = new ArrayList<Task>();
            tasks = serviceTask.getTasksByProject(projectSessionBean.getSelectedProject());
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("msgDialog", new FacesMessage("Une erreur est survenu pour la recuperation de projets"));
            return null;
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public List<SelectItem> getResponsiblesItems() {
        return responsiblesItems;
    }

    public void setResponsiblesItems(List<SelectItem> responsiblesItems) {
        this.responsiblesItems = responsiblesItems;
    }

    public List<User> getValueSelectedResponsibles() {
        return valueSelectedResponsibles;
    }

    public void setValueSelectedResponsibles(List<User> valueSelectedResponsibles) {
        this.valueSelectedResponsibles = valueSelectedResponsibles;
    }

    public DualListModel<User> getPickListUsers() {
        return pickListUsers;
    }

    public void setPickListUsers(DualListModel<User> pickListUsers) {
        this.pickListUsers = pickListUsers;
    }

    public List<User> getSource() {
        return source;
    }

    public void setSource(List<User> source) {
        this.source = source;
    }

    public List<User> getTarget() {
        return target;
    }

    public void setTarget(List<User> target) {
        this.target = target;
    }

    public List<User> getSourceFiltered() {
        return sourceFiltered;
    }

    public void setSourceFiltered(List<User> sourceFiltered) {
        this.sourceFiltered = sourceFiltered;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskSessionBean getTaskSessionBean() {
        return taskSessionBean;
    }

    public void setTaskSessionBean(TaskSessionBean taskSessionBean) {
        this.taskSessionBean = taskSessionBean;
    }

    public ProjectSessionBean getProjectSessionBean() {
        return projectSessionBean;
    }

    public void setProjectSessionBean(ProjectSessionBean projectSessionBean) {
        this.projectSessionBean = projectSessionBean;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public List<User> getSelectedResponsibles() {
        return selectedResponsibles;
    }

    public void setSelectedResponsibles(List<User> selectedResponsibles) {
        this.selectedResponsibles = selectedResponsibles;
    }


}
