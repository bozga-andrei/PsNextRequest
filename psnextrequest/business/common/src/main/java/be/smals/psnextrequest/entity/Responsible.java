package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the PSNEXT_RESPONSIBLE database table.
 *
 * @author AndreiBozga
 */
@Entity
@Table(name = "PSNEXT_RESPONSIBLE")
public class Responsible implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @ManyToOne // bi-directional many-to-one association to User
    @JoinColumn(name = "RESP_USER_ID", nullable = false, updatable = false)
    private User user;

    @Id
    @ManyToOne // bi-directional many-to-one association to Task
    @JoinColumn(name = "RESP_TASK_ID", nullable = false, updatable = false)
    private Task task;

    @Column(name = "IS_VISIBLE", columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isVisible;


    public Responsible() {
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean getIsVisible() {
        return this.isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}