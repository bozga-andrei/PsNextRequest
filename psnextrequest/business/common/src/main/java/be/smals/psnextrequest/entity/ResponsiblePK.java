package be.smals.psnextrequest.entity;

import java.io.Serializable;


/**
 * The primary key class for the responsible database table.
 *
 * @author AndreiBozga
 */
public class ResponsiblePK implements Serializable {

    private static final long serialVersionUID = 1L;

    private long projectId;

    private long userId;


    public ResponsiblePK() {
    }

    public ResponsiblePK(long userId, long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    public long getPrjectId() {
        return this.projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


//    public boolean equals(Object obj) {
//    	boolean resultat = false;
//    	
//        if (obj == this) {
//            return true;
//        } else {
//        	if (!(obj instanceof ResponsiblePK)) {
//                return false;
//            } else {
//            	ResponsiblePK autre = (ResponsiblePK)obj;
//            	if(userId !=autre.userId){
//            		resultat = false;
//            	} else {
//            		if(projectId != autre.projectId){
//            			resultat = false;
//            		} else {
//            			resultat = true;
//            		}
//            	}
//            }
//        }
//        return resultat;
//    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ResponsiblePK) {
            ResponsiblePK otherId = (ResponsiblePK) object;
            return (otherId.userId == this.userId) && (otherId.projectId == this.projectId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (userId + projectId);
    }

}