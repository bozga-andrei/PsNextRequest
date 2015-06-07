package be.smals.psnextrequest.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the psnext_req_historic database table.
 * 
 * @author AndreiBozga
 * 
 */
@Entity
@Table(name="psnext_req_historic")
public class ReqHistoric implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REQ_HIST_ID", updatable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long reqHistId;

	@Column(name="REQ_HIST_COMMENT_RESP")
	private String reqHistCommentResp;

	@Column(name="REQ_HIST_DESCRIPTION", nullable = false)
	private String reqHistDescription;

	@Column(name="REQ_HIST_DURATION", nullable = false)
	private String reqHistDuration;

	@Temporal(TemporalType.DATE)
	@Column(name="REQ_HIST_END_DATE", nullable = false)
	private Date reqHistEndDate;

	@Column(name="REQ_HIST_MANAGED_BY")
	private String reqHistManagedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="REQ_HIST_START_DATE")
	private Date reqHistStartDate;

	@Column(name="REQ_HIST_STATUS", nullable = false)
	private int reqHistStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REQ_HIST_UPDATE_DATE", nullable = false)
	private Date reqHistUpdateDate;

	@Column(name="REQ_HIST_VISIBLE_FOR_RESP")
	private boolean reqHistVisibleForResp;

	@Column(name="REQ_HIST_VISIBLE_FOR_USER")
	private boolean reqHistVisibleForUser;

	//bi-directional many-to-one association to Request
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REQ_HIST_REQ_ID")
	private Request request;

	public ReqHistoric() {
	}

	public Long getReqHistId() {
		return this.reqHistId;
	}

	public void setReqHistId(Long reqHistId) {
		this.reqHistId = reqHistId;
	}

	public String getReqHistCommentResp() {
		return this.reqHistCommentResp;
	}

	public void setReqHistCommentResp(String reqHistCommentResp) {
		this.reqHistCommentResp = reqHistCommentResp;
	}

	public String getReqHistDescription() {
		return this.reqHistDescription;
	}

	public void setReqHistDescription(String reqHistDescription) {
		this.reqHistDescription = reqHistDescription;
	}

	public String getReqHistDuration() {
		return this.reqHistDuration;
	}

	public void setReqHistDuration(String reqHistDuration) {
		this.reqHistDuration = reqHistDuration;
	}

	public Date getReqHistEndDate() {
		return this.reqHistEndDate;
	}

	public void setReqHistEndDate(Date reqHistEndDate) {
		this.reqHistEndDate = reqHistEndDate;
	}

	public String getReqHistManagedBy() {
		return this.reqHistManagedBy;
	}

	public void setReqHistManagedBy(String reqHistManagedBy) {
		this.reqHistManagedBy = reqHistManagedBy;
	}

	public Date getReqHistStartDate() {
		return this.reqHistStartDate;
	}

	public void setReqHistStartDate(Date reqHistStartDate) {
		this.reqHistStartDate = reqHistStartDate;
	}

	public int getReqHistStatus() {
		return this.reqHistStatus;
	}

	public void setReqHistStatus(int reqHistStatus) {
		this.reqHistStatus = reqHistStatus;
	}

	public Date getReqHistUpdateDate() {
		return this.reqHistUpdateDate;
	}

	public void setReqHistUpdateDate(Date reqHistUpdateDate) {
		this.reqHistUpdateDate = reqHistUpdateDate;
	}

	public boolean getReqHistVisibleForResp() {
		return this.reqHistVisibleForResp;
	}

	public void setReqHistVisibleForResp(boolean reqHistVisibleForResp) {
		this.reqHistVisibleForResp = reqHistVisibleForResp;
	}

	public boolean getReqHistVisibleForUser() {
		return this.reqHistVisibleForUser;
	}

	public void setReqHistVisibleForUser(boolean reqHistVisibleForUser) {
		this.reqHistVisibleForUser = reqHistVisibleForUser;
	}

	public Request getRequest() {
		return this.request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}