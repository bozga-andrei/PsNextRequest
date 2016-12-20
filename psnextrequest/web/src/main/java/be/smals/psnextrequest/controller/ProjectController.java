package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.ProjectSessionBean;
import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceRemoteProject;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import be.smals.psnextrequest.service.tasks.PSNextRequestServiceRemoteTask;
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
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "projectControllerBean")
@RequestScoped
public class ProjectController {

    List<User> source;
    List<User> sourceFiltered = new ArrayList<User>();
    List<User> target;
    FacesContext context = FacesContext.getCurrentInstance();
    private Project project;
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private int projectStatus;
    private List<Project> projects;
    private Project selectedProject;
    private List<User> selectedMembersTeam;
    private boolean showConfirmMembersEmpty = true;
    @SuppressWarnings("unused")
    private ArrayList<User> users;
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
    @EJB(name = "ejb/PSNextRequestBeanTask")
    private PSNextRequestServiceRemoteTask serviceTask;

    @PostConstruct
    public void init() {

        if (selectedMembersTeam == null) {
            selectedMembersTeam = new ArrayList<User>();
        }
        target = new ArrayList<User>();
        source = new ArrayList<User>();

        if (sourceFiltered.isEmpty()) {
            source = getUsers();
        } else {
            source = sourceFiltered;
        }
        //remplir la liste avec l'utilisateurs
        pickListUsers = new DualListModel<User>(source, target);

    }


    /**
     * recuperer la seletion du tableau projects
     *
     * @param event
     */
    public void onSelectionProject(SelectEvent event) {
        Project project = projectSessionBean.getSelectedProject();
        List<User> teamMembers = project.getProjectTeam().getUsers();
        for (User user : teamMembers) {
            target.add(user);
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
        projectSessionBean.setPickListTeamMembers(pickListUsers);
    }

    public String createProject() {
        try {
            project = new Project();
            project.setProjectName(projectName);
            project.setProjectDescription(projectDescription);
            project.setProjectStatus(0);
            if (!project.getProjectName().equals("") && !project.getProjectName().isEmpty()) {
                if (selectedMembersTeam == null || selectedMembersTeam.isEmpty()) {
                    for (User user : pickListUsers.getTarget()) {
                        selectedMembersTeam.add(serviceUser.getUserById(user.getUserId()));
                    }
                }
                serviceProject.createProject(selectedMembersTeam, project);
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Introduisez un nom du projet svp!", ""));
                return null;
            }


            //TODO
//			if(selectedMembersTeam != null && !selectedMembersTeam.isEmpty() && showConfirmMembersEmpty != true){
//				serviceProject.createProject(selectedMembersTeam, project);
//			} else {
//				showConfirmMembersEmpty = true;
//	            return null;
//			}
            return "toProjectConsult";
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("createProjectBtn", new FacesMessage("Erreur:" + e.getMessage()));
            return null;
        }
    }

    public String updateProject() {
        try {
            // recupere le projet selectionné
            Project project = projectSessionBean.getSelectedProject();

            if (selectedMembersTeam == null || selectedMembersTeam.isEmpty()) {
                for (User user : projectSessionBean.getPickListTeamMembers().getTarget()) {
                    selectedMembersTeam.add(serviceUser.getUserById(user.getUserId()));
                }
            }
            project.getProjectTeam().setUsers(selectedMembersTeam);

            serviceProject.updateProject(selectedMembersTeam, project);
            return "toProjectConsult";
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("createProjectBtn", new FacesMessage("Erreur:" + e.getMessage()));
            return null;
        }
    }

    public String deleteProjet() {
        try {
            for (Task task : projectSessionBean.getSelectedProject().getTasks()) {
                serviceTask.disableTask(task);
            }
            serviceProject.disableProject(projectSessionBean.getSelectedProject());
            return "toProjectConsult";
        } catch (PSNextRequestServiceException e) {
            context.addMessage(null, new FacesMessage("Une erreur est survenu, la tâche ne peut pas étre supprimée"));
            return null;
        }
    }

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

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
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

    public ProjectSessionBean getProjectSessionBean() {
        return projectSessionBean;
    }

    public void setProjectSessionBean(ProjectSessionBean projectSessionBean) {
        this.projectSessionBean = projectSessionBean;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) throws PSNextRequestServiceException {
        projectSessionBean.setSelectedProject(selectedProject);
        this.selectedProject = selectedProject;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
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

    public boolean isShowConfirmMembersEmpty() {
        return showConfirmMembersEmpty;
    }

    public void setShowConfirmMembersEmpty(boolean showConfirmMembersEmpty) {
        this.showConfirmMembersEmpty = showConfirmMembersEmpty;
    }


}
