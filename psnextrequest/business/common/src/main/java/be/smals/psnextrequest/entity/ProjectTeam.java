package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the PSNEXT_PROJECT_TEAM database table.
 */
@Entity
@Table(name = "PSNEXT_PROJECT_TEAM")
public class ProjectTeam implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8827606745743848400L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_TEAM_ID", updatable = false)
    private Long projectTeamId;

    @Column(name = "PROJECT_TEAM_NAME", unique = true, nullable = false)
    private String projectTeamName;


    // bi-directional many-to-many association to User
//    @ManyToMany(mappedBy="teams", fetch = FetchType.EAGER, cascade=CascadeType.ALL) 
//    private List<User> users;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PSNEXT_PROJECT_TEAM_USER",
            joinColumns = @JoinColumn(name = "PROJECT_TEAM_ID_psnext_project_team"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID_psnext_user"))
    private List<User> users;

    public ProjectTeam() {
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        if (!getUsers().contains(user)) {
            getUsers().add(user);
        }
        if (!user.getTeams().contains(this)) {
            user.getTeams().add(this);
        }
    }

    public Long getProjectTeamId() {
        return this.projectTeamId;
    }

    public void setProjectTeamId(Long projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public String getProjectTeamName() {
        return this.projectTeamName;
    }

    public void setProjectTeamName(String projectTeamName) {
        this.projectTeamName = projectTeamName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String toString() {
        return "Team id: " + getProjectTeamId() +
                ", name: " + getProjectTeamName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjectTeam other = (ProjectTeam) obj;
        if (projectTeamId == null) {
            if (other.projectTeamId != null)
                return false;
        } else if (!projectTeamId.equals(other.projectTeamId))
            return false;
        if (projectTeamName == null) {
            if (other.projectTeamName != null)
                return false;
        } else if (!projectTeamName.equals(other.projectTeamName))
            return false;
        if (users == null) {
            if (other.users != null)
                return false;
        } else if (!users.equals(other.users))
            return false;
        return true;
    }


}