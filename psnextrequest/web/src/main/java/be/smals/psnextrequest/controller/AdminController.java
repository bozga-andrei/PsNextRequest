package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.ProjectSessionBean;
import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.ProjectTeam;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceRemoteProject;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;
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

@ManagedBean(name = "adminControllerBean")
@RequestScoped
public class AdminController {

    List<User> source;
    List<User> sourceFiltered = new ArrayList<User>();
    List<User> target;
    FacesContext context = FacesContext.getCurrentInstance();
    private Project project;
    private String description;
    private Long projectId;
    private Request request;
    private List<Request> requests;
    private List<Project> projects;
    private List<User> valueSelectedResponsibles;
    private List<SelectItem> responsiblesItems;
    private String projectName;
    private User user;
    @SuppressWarnings("unused")
    private ArrayList<User> users;
    private List<AdminController> listTableProjects;
    private int tableRespSelectedRespId;
    private List<Request> filteredRequests;
    private boolean reqChecked;
    private boolean showTheRequests = false;
    private boolean showTheProjects = true;
    private Request selectedRequest;
    private AdminController selectedProject;
    private List<SelectItem> selectedTableResp;
    private List<SelectItem> selectedResponsiblesItems;
    private List<User> selectedResponsibles;
    //pickList
    private DualListModel<User> pickListUsers;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{projectSessionBean}")
    private ProjectSessionBean projectSessionBean;
    @EJB(name = "ejb/PSNextRequestBeanUser")
    private PSNextRequestServiceRemoteUser serviceUser;
    @EJB(name = "ejb/PSNextRequestBeanProject")
    private PSNextRequestServiceRemoteProject serviceProject;
    @EJB(name = "ejb/PSNextRequestBeanRequest")
    private PSNextRequestServiceRemoteRequest serviceRequest;

    @PostConstruct
    public void init() {
        System.out.println("Init Methode");
        if (listTableProjects == null) {
            listTableProjects = new ArrayList<AdminController>();
        }

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
     * recuperer la seletion du tableau requests
     *
     * @param event
     */
    public void onSelection(SelectEvent event) {
        System.out.println("OnSelection request methode");
        setSelectedRequest((Request) event.getObject());
        sessionBean.setAdminRequest((Request) event.getObject());
    }

    /**
     * recuperer la seletion du tableau projects
     *
     * @param event
     */
    public void onSelectionProject(SelectEvent event) {
        System.out.println("OnSelectionProject Methode");

        //Project project = projectSessionBean.getSelectedProject();
        Project project = new Project();
        ProjectTeam prjTeam = new ProjectTeam();
        try {
            project = serviceProject.getProjectById(projectSessionBean.getSelectedProject().getProjectId());

            prjTeam = serviceProject.getProjectTeamByName(project.getProjectTeam().getProjectTeamName());
        } catch (PSNextRequestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        prjTeam.getUsers().size();

        List<User> teamMembers = project.getProjectTeam().getUsers();
        for (User user : teamMembers) {
            target.add(user);
        }

        List<Integer> indexs = new ArrayList<Integer>();
        for (User sourceUser : source) {
            for (User targetUser : target) {
                if (sourceUser.getUserDistinguishedName().equals(targetUser.getUserDistinguishedName())) {
                    //source.remove(source.indexOf(sourceUser));
                    indexs.add(source.indexOf(sourceUser));
                    System.out.println("Is removed: " + sourceUser.getUserDistinguishedName());
                }
            }
        }
        setSourceFiltered(source);
        int nbRemoved = 0;
        for (int index : indexs) {
            System.out.println("remove index: " + index);
            getSourceFiltered().remove(index - nbRemoved);
            nbRemoved++;
            System.out.println("isRemoved index: " + index);
        }

        System.out.println("SourceFilter size = " + sourceFiltered.size());
        pickListUsers = new DualListModel<User>(sourceFiltered, target);
        setProjectName(projectSessionBean.getSelectedProject().getProjectName());
    }

    public void showRequests(ActionEvent event) {
        showTheProjects = false;
        showTheRequests = true;
    }

    public void showProjects(ActionEvent event) {
        showTheRequests = false;
        showTheProjects = true;
    }

    public String createProject() {
        try {
            project = new Project();
            project.setProjectName(projectName);
            if (selectedResponsibles == null || selectedResponsibles.isEmpty()) {
                for (User user : pickListUsers.getTarget()) {
                    selectedResponsibles.add(serviceUser.getUserById(user.getUserId()));
                }
            }
            if (selectedResponsibles != null && !selectedResponsibles.isEmpty()) {
                project = serviceProject.createProject(selectedResponsibles, project);
            } else {
                context.addMessage("createProjectBtn", new FacesMessage("Sélectionnez au moins un responsable"));
                return null;
            }
            return "toProjectManage";
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("createProjectBtn", new FacesMessage("Erreur:" + e.getMessage()));
            return null;
        }
    }

    public String updateProject() {
        Project project;
        project = projectSessionBean.getSelectedProject();
        System.out.println("getSelected Project is : " + project);
        setProjectName(project.getProjectName());
        System.out.println("Project name is: " + projectName);

        try {
            project.getProjectTeam().setUsers(target);
            serviceProject.updateProject(target, project);
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("createProjectBtn", new FacesMessage("Erreur:" + e.getMessage()));
        }
        return null;
    }

    /**
     * Remplir la liste avec les demandes
     *
     * @throws PSNextRequestServiceException
     */
    public void setRequests() throws PSNextRequestServiceException {
        requests = new ArrayList<Request>();
        requests = serviceRequest.getAllRequests();
    }

    public List<Request> getRequests() {
        try {
            setRequests();
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Une erreur est survenu pour la recuperation de requests"));
            return null;
        }
        return requests;
    }

    /**
     * Remplir la liste avec les projets
     *
     * @throws PSNextRequestServiceException
     */
    public List<Project> getProjects() {
        try {
            projects = new ArrayList<Project>();
            projects = serviceProject.getAllProjects();
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Une erreur est survenu pour la recuperation de projets"));
            return null;
        }
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Recuperer les utilisateurs de la BD
     *
     * @throws PSNextRequestServiceException
     */
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Request> getFilteredRequests() {
        return filteredRequests;
    }

    public void setFilteredRequests(List<Request> filteredRequests) {
        this.filteredRequests = filteredRequests;
    }

    public boolean isReqChecked() {
        return reqChecked;
    }

    public void setReqChecked(boolean reqChecked) {
        this.reqChecked = reqChecked;
    }

    public Request getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(Request selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public AdminController getSelectedProject() {
        System.out.println("Selected Project is: " + selectedProject);
        return selectedProject;
    }

    public void setSelectedProject(AdminController selectedProject) throws PSNextRequestServiceException {
        projectSessionBean.setSelectedProject(serviceProject.getProjectById(selectedProject.getProjectId()));
        this.selectedProject = selectedProject;
    }

    public List<SelectItem> getSelectedTableResp() {
        return selectedTableResp;
    }

    public void setSelectedTableResp(List<SelectItem> selectedTableResp) {
        this.selectedTableResp = selectedTableResp;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<SelectItem> getResponsiblesItems() {
        return responsiblesItems;
    }

    public void setResponsiblesItems(List<SelectItem> responsiblesItems) {
        this.responsiblesItems = responsiblesItems;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getTableRespSelectedRespId() {
        return tableRespSelectedRespId;
    }

    public void setTableRespSelectedRespId(int tableRespSelectedRespId) {
        this.tableRespSelectedRespId = tableRespSelectedRespId;
    }

    public boolean isShowTheRequests() {
        return showTheRequests;
    }

    public void setShowTheRequests(boolean showTheRequests) {
        this.showTheRequests = showTheRequests;
    }

    public boolean isShowTheProjects() {
        return showTheProjects;
    }

    public void setShowTheProjects(boolean showTheProjects) {
        this.showTheProjects = showTheProjects;
    }

    public List<SelectItem> getSelectedResponsiblesItems() {
        return selectedResponsiblesItems;
    }

    public void setSelectedResponsiblesItems(List<SelectItem> selectedResponsiblesItems) {
        this.selectedResponsiblesItems = selectedResponsiblesItems;
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

    public ProjectSessionBean getProjectSessionBean() {
        return projectSessionBean;
    }

    public void setProjectSessionBean(ProjectSessionBean projectSessionBean) {
        this.projectSessionBean = projectSessionBean;
    }


}
