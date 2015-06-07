package be.smals.psnextrequest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the role database table.
 * 
 * @author AndreiBozga
 * 
 */


@Entity
@Table(name="psnext_role")
public class Role implements Serializable { 
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -2793276374848997267L;

	@Id
    @Column(name = "ROLE_ID",updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "ROLE_NAME", unique=true, nullable=false, updatable=false, length=48)
    private String roleName;

   
    // bi-directional many-to-many association to User
    @ManyToMany (mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;
    
    public Role() {
    	users = new ArrayList<User>();
    }

    public void addUser(User user) {
        if (!getUsers().contains(user)) {
            getUsers().add(user);
        }
        if (!user.getRoles().contains(this)) {
            user.getRoles().add(this);
        }
    }
    
    public void setUsers(List<User> users){
    	this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
    
    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}