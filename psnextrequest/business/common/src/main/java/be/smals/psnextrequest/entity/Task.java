package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PSNEXT_TASK database table.
 *
 * @author AndreiBozga
 */
@Entity
@Table(name = "PSNEXT_TASK")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TASK_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "TASK_NAME", unique = true, nullable = false)
    private String taskName;

    @Column(name = "TASK_DESCRIPTION")
    private String taskDescription;

    @Column(name = "TASK_STATUS", nullable = false, columnDefinition = "INT(1)")
    private int taskStatus;

    @Column(name = "TASK_CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date taskCreationDate;

    @Column(name = "TASK_LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taskLastUpdateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TASK_PROJECT_ID", nullable = false)
    private Project project;


    // bi-directional one-to-many association to Responsible
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<Responsible> responsibles;

    // bi-directional one-to-many association to Request
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<Request> requests;


    public Task() {
        responsibles = new ArrayList<Responsible>();
        requests = new ArrayList<Request>();
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public Date getTaskLastUpdateDate() {
        return taskLastUpdateDate;
    }

    public void setTaskLastUpdateDate(Date taskLastUpdateDate) {
        this.taskLastUpdateDate = taskLastUpdateDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Responsible> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<Responsible> responsibles) {
        this.responsibles = responsibles;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }


    // Add an responsible to the task.
    // Create an association object for the relationship and set its data.
    public void addResponsible(User user, boolean isVisible) {
        Responsible resp = new Responsible();
        resp.setUser(user);
        resp.setTask(this);
        resp.setIsVisible(isVisible);

        this.responsibles.add(resp);

        // Also add the association object to the user.
        user.getResponsibles().add(resp);
    }


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
        String responsiblesEnum = "";
        for (Responsible resp : responsibles) {
            responsiblesEnum += resp.getUser().getUserDistinguishedName() + ", ";
        }
        ;

        return "ID: " + taskId +
                "\nName: " + taskName +
                "\nResponsibles: " + responsiblesEnum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (project == null) {
            if (other.project != null)
                return false;
        } else if (!project.equals(other.project))
            return false;
        if (responsibles == null) {
            if (other.responsibles != null)
                return false;
        } else if (!responsibles.equals(other.responsibles))
            return false;
        if (taskCreationDate == null) {
            if (other.taskCreationDate != null)
                return false;
        } else if (!taskCreationDate.equals(other.taskCreationDate))
            return false;
        if (taskDescription == null) {
            if (other.taskDescription != null)
                return false;
        } else if (!taskDescription.equals(other.taskDescription))
            return false;
        if (taskId == null) {
            if (other.taskId != null)
                return false;
        } else if (!taskId.equals(other.taskId))
            return false;
        if (taskLastUpdateDate == null) {
            if (other.taskLastUpdateDate != null)
                return false;
        } else if (!taskLastUpdateDate.equals(other.taskLastUpdateDate))
            return false;
        if (taskName == null) {
            if (other.taskName != null)
                return false;
        } else if (!taskName.equals(other.taskName))
            return false;
        if (taskStatus != other.taskStatus)
            return false;
        return true;
    }


}