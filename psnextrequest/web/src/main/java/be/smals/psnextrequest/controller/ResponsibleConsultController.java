
package be.smals.psnextrequest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;


/**
 * Managed bean controller for the consult Requests selection screen.
 * 
 * @author AndreiBozga
 * 
 * @since
 * 
 */

@ManagedBean(name = "responsibleConsultController")
@RequestScoped
public class ResponsibleConsultController {
    
	private Request request = new Request();
	
	private String shortDescription;
	
    private List<Request> requests;
    
    private List<Request> filteredRequests;
    
    private Request selectedRequest;
    
    private boolean reqChecked;
    
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    
    @EJB(name = "ejb/PSNextRequestBeanRequest")
	private PSNextRequestServiceRemoteRequest serviceRequest;
    
    @EJB(name = "ejb/PSNextRequestBeanUser")
	private PSNextRequestServiceRemoteUser serviceUser;
	
	FacesContext context = FacesContext.getCurrentInstance();
    
 
	 /**
     * Initiation.
     */
    @PostConstruct
	public void init() {
		this.setRequests();
	}
    //recuperer la seletion du tableau
    public void onSelection(SelectEvent event){
    	
    	sessionBean.setResponsibleRequest((Request)event.getObject());
    } 
    
    /**
     * Validate request by the Responsible
     * 
     */
    public void validateByResponsible(AjaxBehaviorEvent event){
    	request = new Request();
		try {
			if(checkResponsibleSelection()){
				request = sessionBean.getResponsibleRequest();
				if(request.getRequestStatus() == 1 || request.getRequestStatus() == 2){
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La demande a déjà été validée", ""));
				} else if(request.getRequestCommentResp() == null  || request.getRequestCommentResp().equals("")){
					//Accepté
					request.setRequestStatus(1);
					request.setRequestManagedBy(sessionBean.getUser().getUserDistinguishedName());
					serviceRequest.updateRequest(request);
					this.setRequests();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande validée", ""));
				} else {
					//Accepté avec commentaire
					request.setRequestStatus(2);
					request.setRequestManagedBy(sessionBean.getUser().getUserDistinguishedName());
					serviceRequest.updateRequest(request);
					this.setRequests();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande validée", ""));
				}
			}
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage("validateBtn", new FacesMessage("Erreur: " + e.getMessage()));
		}
    }
     
    
    /**
     * Cancel request by the Responsible
     * 
     */
    public String refuseByResponsible(AjaxBehaviorEvent event){
    	request = new Request();
		try {
			if(checkResponsibleSelection()){
				request = sessionBean.getResponsibleRequest();
				if(request.getRequestStatus() == 3){
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La demande a déjà été refusée", "La demande a déjà été refusée"));
					return null;
				} else {
					// Status 3 = Refusée
					request.setRequestStatus(3);
					request.setRequestManagedBy(sessionBean.getUser().getUserDistinguishedName());
					serviceRequest.updateRequest(request);
					this.setRequests();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande refusée", "La demande a été refusée"));
					return null;
				}
				
			}
			return null;
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage("cancelBtn", new FacesMessage("Erreur: " + e.getMessage()));
			return null;
		}
    }
    
    /**
     * Request: set visible for responsible attribut to "false" and and return the appropriate outcome
     * 
     * @return the outcome.
     */
    public String deleteByResponsible(){
    	request = new Request();
    	try {
    		if(checkResponsibleSelection()){
    			request = sessionBean.getResponsibleRequest();
        		if(request.getRequestStatus() == 0 ){
    				context.addMessage("deleteBtn", new FacesMessage("La demande ne peut pas être supprimé elle est en cours de validation."));
    				return null;
        		}
        		serviceRequest.setRequestVisibleForResp(request, sessionBean.getUser(),false);
    			sessionBean.setResponsibleRequest(null);
    			this.setRequests();
    			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Demande supprimé", "La demande a été refusée"));
				return null;
    		}
    		return null;
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage("deleteBtn", new FacesMessage("Demande n'a pas été supprimé: " + e.getMessage()));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage (e.getMessage()));
			return null;
		}
    }
    	
    public boolean checkResponsibleSelection(){
    	if(sessionBean.getResponsibleRequest() == null){
    		context.addMessage("form:modificationBtn", new FacesMessage("Selectionnez une demande svp!"));
    		return false;
    	} else {
    		return true;
    	}
    }
    
    
	public void setRequests() {
		try{
			requests = new ArrayList<Request>();
			List<Request> visibleRequests = new ArrayList<Request>();
			
			//Select only visible requests for table psnext_req_visible_for_responsible
			visibleRequests = serviceRequest.getVisibleRequestsForRespByUser(sessionBean.getUser());
			
			for(Request req : visibleRequests){
				if(req.getRequestStatus() != 4){
        			if(req.getRequestDescription().length() > 25){
            			req.setRequestShortDescription(req.getRequestDescription().substring(0,25)+ "...");
            		} else 
            			req.setRequestShortDescription(req.getRequestDescription());
            		requests.add(req);
        		}
			}
			
		} catch (PSNextRequestServiceException e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), ""));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unexpected error please try again!", ""));
		}
		
	}
	
	public List<Request> getRequests(){
		return requests;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<Request> getFilteredRequests() {
		return filteredRequests;
	}

	public void setFilteredRequests(List<Request> filteredRequests) {
		this.filteredRequests = filteredRequests;
	}

	public Request getSelectedRequest() {
		return selectedRequest;
	}

	public void setSelectedRequest(Request selectedRequest) {
		this.selectedRequest = selectedRequest;
	}

	public boolean isReqChecked() {
		return reqChecked;
	}

	public void setReqChecked(boolean reqChecked) {
		this.reqChecked = reqChecked;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	
	
}
