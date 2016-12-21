package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PSNEXT_PROJECT database table.
 *
 * @author AndreiBozga
 */
@Entity
@Table(name = "PSNEXT_PROJECT")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PROJECT_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(name = "PROJECT_NAME", unique = true, nullable = false)
    private String projectName;

    @Column(name = "PROJECT_DESCRIPTION")
    private String projectDescription;

    @Column(name = "PROJECT_STATUS", nullable = false, columnDefinition = "INT(1)")
    private int projectStatus;

    @Column(name = "PROJECT_CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectCreationDate;

    @Column(name = "PROJECT_LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectLastUpdateDate;

    // bi-directional many-to-one association to Responsible
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Task> tasks;

    //bi-directional one-to-one association to ProjectTeam
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_PROJECT_TEAM_ID", unique = true, nullable = false)
    private ProjectTeam projectTeam;

    // bi-directional many-to-many association to User
//    @ManyToMany
//    @JoinTable(name="PSNEXT_RESPONSIBLE", 
//    joinColumns=@JoinColumn(name="PROJECT_ID"), 
//    inverseJoinColumns=@JoinColumn(name="USER_ID"))  
//    private List<User> responsibles;
//
//    public Project() {
//    	responsibles = new ArrayList<User>();
//    }


    public Project() {
    }


    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getProjectCreationDate() {
        return projectCreationDate;
    }

    public void setProjectCreationDate(Date projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public Date getProjectLastUpdateDate() {
        return projectLastUpdateDate;
    }

    public void setProjectLastUpdateDate(Date projectLastUpdateDate) {
        this.projectLastUpdateDate = projectLastUpdateDate;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public ProjectTeam getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(ProjectTeam projectTeam) {
        this.projectTeam = projectTeam;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    // Add an responsible to the project.
    // Create an association object for the relationship and set its data.
//	public void addResponsible(User user, boolean isVisible) {
//	    Responsible resp = new Responsible();
//	    resp.setUser(user);
//	    resp.setProject(this);
//	    resp.setUserId(user.getUserId());
//	    resp.setProjectId(this.getProjectId());
//	    resp.setIsVisible(isVisible);
//	 
//	    this.responsibles.add(resp);
//	    // Also add the association object to the user.
//	    user.getResponsibles().add(resp);
//	}


//
//    public void addResponsible(User responsible) {
//    	if(!getResponsibles().contains(responsible)){
//    		getResponsibles().add(responsible);
//    	}
//    	if(!responsible.getProjects().contains(this)){
//    		responsible.getProjects().add(this);
//    	}
//    	
//    }


    public String toString() {
        return "Project id: " + getProjectId() +
                "\nTeam: " + getProjectTeam() +
                "\nProjectName: " + getProjectName();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        if (projectCreationDate == null) {
            if (other.projectCreationDate != null)
                return false;
        } else if (!projectCreationDate.equals(other.projectCreationDate))
            return false;
        if (projectDescription == null) {
            if (other.projectDescription != null)
                return false;
        } else if (!projectDescription.equals(other.projectDescription))
            return false;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        if (projectLastUpdateDate == null) {
            if (other.projectLastUpdateDate != null)
                return false;
        } else if (!projectLastUpdateDate.equals(other.projectLastUpdateDate))
            return false;
        if (projectName == null) {
            if (other.projectName != null)
                return false;
        } else if (!projectName.equals(other.projectName))
            return false;
        if (projectStatus != other.projectStatus)
            return false;
        if (projectTeam == null) {
            if (other.projectTeam != null)
                return false;
        } else if (!projectTeam.equals(other.projectTeam))
            return false;
        if (tasks == null) {
            if (other.tasks != null)
                return false;
        } else if (!tasks.equals(other.tasks))
            return false;
        return true;
    }


}