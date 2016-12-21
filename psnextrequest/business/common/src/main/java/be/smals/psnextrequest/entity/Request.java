package be.smals.psnextrequest.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PSNEXT_REQUEST database table.
 *
 * @author AndreiBozga
 */
@Entity
@Table(name = "PSNEXT_REQUEST")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REQ_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REQ_USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQ_TASK_ID", nullable = false)
    private Task task;

    @Column(name = "REQ_START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date requestStartDate;

    @Column(name = "REQ_END_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date requestEndDate;

    @Column(name = "REQ_DURATION", nullable = false, length = 28)
    private String requestDuration;

    @Column(name = "REQ_CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestCreationDate;

    @Column(name = "REQ_LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestLastUpdateDate;

    @Column(name = "REQ_STATUS", nullable = false, columnDefinition = "INT(1)")
    private int requestStatus;

    @Column(name = "REQ_DESCRIPTION", nullable = false)
    private String requestDescription;

    @Transient
    private String requestShortDescription;

    @Column(name = "REQ_COMMENT_RESP")
    private String requestCommentResp;

    @Column(name = "REQ_VISIBLE_FOR_USER", columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean requestVisibleForUser;

    @Column(name = "REQ_MANAGED_BY", length = 48)
    private String requestManagedBy;

    //bi-directional one-to-many association to ReqVisibleForResponsible
    @OneToMany(mappedBy = "request")
    private List<ReqVisibleForResponsible> reqVisibleForResponsibles;

    //bi-directional one-to-many association to ReqHistoric
    @OneToMany(mappedBy = "request")
    private List<ReqHistoric> reqHistorics;


    public Request() {
        reqVisibleForResponsibles = new ArrayList<ReqVisibleForResponsible>();
        reqHistorics = new ArrayList<ReqHistoric>();
    }


    public Long getRequestId() {
        return requestId;
    }


    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Task getTask() {
        return task;
    }


    public void setTask(Task task) {
        this.task = task;
    }


    public Date getRequestStartDate() {
        return requestStartDate;
    }


    public void setRequestStartDate(Date requestStartDate) {
        this.requestStartDate = requestStartDate;
    }


    public Date getRequestEndDate() {
        return requestEndDate;
    }


    public void setRequestEndDate(Date requestEndDate) {
        this.requestEndDate = requestEndDate;
    }


    public String getRequestDuration() {
        return requestDuration;
    }


    public void setRequestDuration(String requestDuration) {
        this.requestDuration = requestDuration;
    }


    public Date getRequestCreationDate() {
        return requestCreationDate;
    }


    public void setRequestCreationDate(Date requestCreationDate) {
        this.requestCreationDate = requestCreationDate;
    }


    public Date getRequestLastUpdateDate() {
        return requestLastUpdateDate;
    }


    public void setRequestLastUpdateDate(Date requestLastUpdateDate) {
        this.requestLastUpdateDate = requestLastUpdateDate;
    }


    public int getRequestStatus() {
        return requestStatus;
    }


    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }


    public String getRequestDescription() {
        return requestDescription;
    }


    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }


    public String getRequestCommentResp() {
        return requestCommentResp;
    }


    public void setRequestCommentResp(String requestCommentResp) {
        this.requestCommentResp = requestCommentResp;
    }


    public boolean isRequestVisibleForUser() {
        return requestVisibleForUser;
    }


    public void setRequestVisibleForUser(boolean requestVisibleForUser) {
        this.requestVisibleForUser = requestVisibleForUser;
    }


    public String getRequestManagedBy() {
        return requestManagedBy;
    }


    public void setRequestManagedBy(String requestManagedBy) {
        this.requestManagedBy = requestManagedBy;
    }


    public List<ReqVisibleForResponsible> getReqVisibleForResponsibles() {
        return reqVisibleForResponsibles;
    }


    public void setReqVisibleForResponsibles(
            List<ReqVisibleForResponsible> reqVisibleForResponsibles) {
        this.reqVisibleForResponsibles = reqVisibleForResponsibles;
    }


    public List<ReqHistoric> getReqHistorics() {
        return reqHistorics;
    }


    public void setReqHistorics(List<ReqHistoric> reqHistorics) {
        this.reqHistorics = reqHistorics;
    }


    public String getRequestShortDescription() {
        return requestShortDescription;
    }


    public void setRequestShortDescription(String requestShortDescription) {
        this.requestShortDescription = requestShortDescription;
    }


    public String toString() {
        return "\nRequest ID: " + requestId + "\nUser: " + user + "\nTask: " + task;
    }


    // Add an responsible to the task.
    // Create an association object for the relationship and set its data.
    public void addReqVisibleForResp(User user, boolean isVisible) {
        ReqVisibleForResponsible reqVisForResp = new ReqVisibleForResponsible();
        reqVisForResp.setUser(user);
        reqVisForResp.setRequest(this);
        reqVisForResp.setIsVisible(isVisible);

        //Transaction many-to-many ReqVisibleForResponsibles
        this.reqVisibleForResponsibles.add(reqVisForResp);

        // Also add the association object to the user.
        user.getReqVisibleForResponsibles().add(reqVisForResp);
    }

}