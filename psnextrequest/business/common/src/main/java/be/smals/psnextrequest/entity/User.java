package be.smals.psnextrequest.entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the PSNEXT_USER database table.
 *
 * @author AndreiBozga
 */

@TypeDef(name = "encryptedString", typeClass = EncryptedStringType.class,
        parameters = {@Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")}
)

@Entity
@Table(name = "PSNEXT_USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_FIRST_NAME", "USER_LAST_NAME"})})
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5960818051520591658L;

    @Id
    @Column(name = "USER_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USER_DISTINGUISHED_NAME", unique = true, nullable = false, length = 20)
    private String userDistinguishedName;

    @Column(name = "USER_FIRST_NAME", nullable = false, length = 48)
    private String userFirstName;

    @Column(name = "USER_LAST_NAME", nullable = false, length = 48)
    private String userLastName;

    @Type(type = "encryptedString")
    @Column(name = "USER_PASSWORD", nullable = false)
    private String userPassword;

    @Column(name = "USER_MAIL", unique = true, nullable = false, length = 120)
    private String userMail;


    // bi-directional one-to-many association to Request
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Request> requests;

    // bi-directional one-to-Many association to Responsible
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Responsible> responsibles;

    //bi-directional one-to-many association to ReqVisibleForResponsible
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReqVisibleForResponsible> reqVisibleForResponsibles;

    //bi-directional many-to-many association to Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PSNEXT_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID_psnext_user"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID_psnext_role"))
    private List<Role> roles;

    //bi-directional many-to-many association to ProjectTeam
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PSNEXT_PROJECT_TEAM_USER",
            joinColumns = @JoinColumn(name = "USER_ID_psnext_user"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_TEAM_ID_psnext_project_team"))
    private List<ProjectTeam> teams;

    // bi-directional many-to-many association to User
//    @ManyToMany(mappedBy="users", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    private List<ProjectTeam> teams;


    public User() {
        requests = new ArrayList<Request>();
        responsibles = new ArrayList<Responsible>();
        roles = new ArrayList<Role>();
        teams = new ArrayList<ProjectTeam>();
    }


    public void addTeam(ProjectTeam team) {
        if (!getTeams().contains(team)) {
            getTeams().add(team);
        }
        if (!team.getUsers().contains(this)) {
            team.getUsers().add(this);
        }
    }

    public void addRole(Role role) {
        if (!getRoles().contains(role)) {
            getRoles().add(role);
        }
        if (!role.getUsers().contains(this)) {
            role.getUsers().add(this);
        }
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Responsible> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<Responsible> responsibles) {
        this.responsibles = responsibles;
    }

    public List<ProjectTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<ProjectTeam> teams) {
        this.teams = teams;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserDistinguishedName() {
        return this.userDistinguishedName;
    }

    public void setUserDistinguishedName(String userDistinguishedName) {
        this.userDistinguishedName = userDistinguishedName;
    }

    public String getUserFirstName() {
        return this.userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return this.userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public List<ReqVisibleForResponsible> getReqVisibleForResponsibles() {
        return reqVisibleForResponsibles;
    }

    public void setReqVisibleForResponsibles(
            List<ReqVisibleForResponsible> reqVisibleForResponsibles) {
        this.reqVisibleForResponsibles = reqVisibleForResponsibles;
    }


    public String toString() {
        return "\nID:" + userId + "\nName:" + userDistinguishedName;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (reqVisibleForResponsibles == null) {
            if (other.reqVisibleForResponsibles != null)
                return false;
        } else if (!reqVisibleForResponsibles
                .equals(other.reqVisibleForResponsibles))
            return false;
        if (responsibles == null) {
            if (other.responsibles != null)
                return false;
        } else if (!responsibles.equals(other.responsibles))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (teams == null) {
            if (other.teams != null)
                return false;
        } else if (!teams.equals(other.teams))
            return false;
        if (userDistinguishedName == null) {
            if (other.userDistinguishedName != null)
                return false;
        } else if (!userDistinguishedName.equals(other.userDistinguishedName))
            return false;
        if (userFirstName == null) {
            if (other.userFirstName != null)
                return false;
        } else if (!userFirstName.equals(other.userFirstName))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (userLastName == null) {
            if (other.userLastName != null)
                return false;
        } else if (!userLastName.equals(other.userLastName))
            return false;
        if (userMail == null) {
            if (other.userMail != null)
                return false;
        } else if (!userMail.equals(other.userMail))
            return false;
        if (userPassword == null) {
            if (other.userPassword != null)
                return false;
        } else if (!userPassword.equals(other.userPassword))
            return false;
        return true;
    }

}