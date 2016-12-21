package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the PSNEXT_GROUP database table.
 */
@Entity
@Table(name = "PSNEXT_GROUP")
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID", updatable = false)
    private Long groupId;

    @Column(name = "GROUP_NAME", unique = true, nullable = false, length = 58)
    private String groupName;

    // bi-directional many-to-many association to User
    @ManyToMany
    @JoinTable(name = "PSNEXT_USER_GROUP",
            joinColumns = @JoinColumn(name = "USER_ID_psnext_user"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID_psnext_group"))
    private List<User> users;

    public Group() {
        users = new ArrayList<User>();
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}