package be.smals.psnextrequest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the psnext_visible_for_responsible database table.
 */
@Embeddable
public class ReqVisibleForResponsiblePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "REQ_ID_REQ", updatable = false)
    private Long reqIdReq;

    @Column(name = "USER_ID_USER", updatable = false)
    private Long userIdUser;

    public ReqVisibleForResponsiblePK() {
    }

    public Long getReqIdReq() {
        return this.reqIdReq;
    }

    public void setReqIdReq(Long reqIdReq) {
        this.reqIdReq = reqIdReq;
    }

    public Long getUserIdUser() {
        return this.userIdUser;
    }

    public void setUserIdUser(Long userIdUser) {
        this.userIdUser = userIdUser;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReqVisibleForResponsiblePK)) {
            return false;
        }
        ReqVisibleForResponsiblePK castOther = (ReqVisibleForResponsiblePK) other;
        return
                this.reqIdReq.equals(castOther.reqIdReq)
                        && this.userIdUser.equals(castOther.userIdUser);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.reqIdReq.hashCode();
        hash = hash * prime + this.userIdUser.hashCode();

        return hash;
    }
}