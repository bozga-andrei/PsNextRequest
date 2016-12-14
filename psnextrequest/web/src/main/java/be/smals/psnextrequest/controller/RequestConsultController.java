
package be.smals.psnextrequest.controller;

import be.smals.psnextrequest.bean.SessionBean;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.requests.PSNextRequestServiceRemoteRequest;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Managed bean controller for the consult Requests selection screen.
 *
 * @author AndreiBozga
 */

@ManagedBean(name = "requestConsultController", eager = true)
@RequestScoped
public class RequestConsultController {

    private List<Object> dataList;

    private List<Request> requests;

    private List<User> responsibles;

    private String responsiblesString;

    private String responsiblesStringShort;

    private List<SelectItem> responsiblesItems;

    private List<Request> filteredRequests;

    private boolean reqChecked;

    private RequestConsultController selectedRequest;

    private List<RequestConsultController> selectedRequests;

    private int selectedResp;

    private Long id;

    private int status;

    private String projectName;

    private String taskName;

    private Date startDate;

    private Date endDate;

    private Date creationDate;

    private String duration;

    private String description;

    private String shortDescription;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    @EJB(name = "ejb/PSNextRequestBeanRequest")
    private PSNextRequestServiceRemoteRequest serviceRequest;

    FacesContext context = FacesContext.getCurrentInstance();


    public RequestConsultController() {
    }

    @PostConstruct
    public void init() {
        dataList = new ArrayList<Object>();
    }

    /**
     * recuperer la seletion du tableau
     *
     * @param event
     */
    public void onSelection(SelectEvent event) {
        RequestConsultController selected = ((RequestConsultController) event.getObject());
        try {
            sessionBean.setRequest(serviceRequest.getRequestById(selected.getId()));
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifier s'il y a eu une sélection
     *
     * @return toUpdateRequest
     */
    public String prepareToUpdate() {
        if (sessionBean.getRequest() == null) {
            context.addMessage("form:updateBtn", new FacesMessage("Selectionnez une demande svp!"));
            return null;
        } else if (sessionBean.getRequest().getRequestStatus() != 0 && sessionBean.getRequest().getRequestStatus() != 3) {
            context.addMessage("form:updateBtn", new FacesMessage("La demande doit être en cours de validation ou refusée pour pouvoir la modifier.!"));
            return null;
        } else {
            return "toUpdateRequest";
        }
    }

    /**
     * Request: set visible for user attribut to "false" and and return the appropriate outcome
     *
     * @return the outcome.
     */
    public String deleteAll() {
        System.out.println("DeleteAll Enter");

        try {
            for (RequestConsultController req : selectedRequests) {
                if (req.getStatus() == 0) {
                    context.addMessage("deleteBtn", new FacesMessage("Une ou plusieurs demandes ne peuvent pas être supprimées elles doivent être en cours de validation."));
                    return null;
                }
                System.out.println("selectedRequests: " + req.getDescription());
                Request request = new Request();
                request = serviceRequest.getRequestById(req.getId());
                request.setRequestVisibleForUser(true);
                serviceRequest.updateRequest(request);
            }
            return "toRequestsConsult";
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("deleteBtn", new FacesMessage("Demande n'a pas été supprimé: " + e.getMessage()));
            return null;
        }
    }

    public List<Object> getDataList() {
        setDataList();
        return dataList;
    }

    public void setDataList() {
        RequestConsultController objConsult;
        requests = new ArrayList<Request>();
        if (dataList == null || dataList.isEmpty()) {
            try {
                requests = serviceRequest.getVisibleRequestsByUser(sessionBean.getUser());
                for (Request req : requests) {
                    responsiblesString = "";
                    responsiblesItems = new ArrayList<SelectItem>();
                    objConsult = new RequestConsultController();
                    objConsult.setId(req.getRequestId());
                    objConsult.setStatus(req.getRequestStatus());
                    objConsult.setTaskName(req.getTask().getTaskName());
                    objConsult.setProjectName(req.getTask().getProject().getProjectName());
                    List<User> responsibles = serviceRequest.getResponsiblesByRequest(req);

                    for (User resp : responsibles) {
                        responsiblesItems.add(new SelectItem(resp.getUserId(), resp.getUserFirstName() + " " + resp.getUserLastName()));
                        responsiblesString += resp.getUserFirstName() + "-" + resp.getUserLastName() + "; \n ";
                    }
                    if (responsiblesString.length() > 35) {
                        objConsult.setResponsiblesStringShort(responsiblesString.substring(0, 35) + "...");
                    } else {
                        objConsult.setResponsiblesStringShort(responsiblesString);
                    }
                    objConsult.setResponsiblesString(responsiblesString);
                    objConsult.setResponsiblesItems(responsiblesItems);
                    objConsult.setStartDate(req.getRequestStartDate());
                    objConsult.setEndDate(req.getRequestEndDate());
                    objConsult.setCreationDate(req.getRequestCreationDate());
                    objConsult.setDuration(req.getRequestDuration());
                    objConsult.setDescription(req.getRequestDescription());
                    if (req.getRequestDescription().length() > 19) {
                        String desc = req.getRequestDescription().substring(0, 19) + "...";
                        objConsult.setShortDescription(desc);
                    } else {
                        objConsult.setShortDescription(req.getRequestDescription());
                    }

                    dataList.add(objConsult);
                }
            } catch (PSNextRequestServiceException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Remplir la liste avec les demandes faites pas l'utilisateur connecté
     */
    public void setRequests() throws PSNextRequestServiceException {
        requests = new ArrayList<Request>();
        responsiblesItems = new ArrayList<SelectItem>();
        requests = serviceRequest.getVisibleRequestsByUser(sessionBean.getUser());
    }

    public List<Request> getRequests() {
        try {
            setRequests();
        } catch (PSNextRequestServiceException e) {
            e.printStackTrace();
            context.addMessage("form:loginControllerBtn", new FacesMessage("Les demandes n'ont pas pu être recupérées"));
            return null;
        }
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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

    public RequestConsultController getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(RequestConsultController selectedRequest) throws PSNextRequestServiceException {
        sessionBean.setRequest(serviceRequest.getRequestById(selectedRequest.getId()));
        this.selectedRequest = selectedRequest;
    }

    public List<RequestConsultController> getSelectedRequests() {
        return selectedRequests;
    }

    public void setSelectedRequests(List<RequestConsultController> selectedRequests) {
        System.out.println("size selected requests: " + selectedRequests.size());
        this.selectedRequests = selectedRequests;
    }


    public boolean isReqChecked() {
        return reqChecked;
    }

    public void setReqChecked(boolean reqChecked) {
        this.reqChecked = reqChecked;
    }

    public List<User> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<User> responsibles) {
        this.responsibles = responsibles;
    }

    public List<SelectItem> getResponsiblesItems() {
        return responsiblesItems;
    }

    public void setResponsiblesItems(List<SelectItem> responsiblesItems) {
        this.responsiblesItems = responsiblesItems;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSelectedResp() {
        return selectedResp;
    }

    public void setSelectedResp(int selectedResp) {
        this.selectedResp = selectedResp;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getResponsiblesString() {
        return responsiblesString;
    }

    public void setResponsiblesString(String responsiblesString) {
        this.responsiblesString = responsiblesString;
    }

    public String getResponsiblesStringShort() {
        return responsiblesStringShort;
    }

    public void setResponsiblesStringShort(String responsiblesStringShort) {
        this.responsiblesStringShort = responsiblesStringShort;
    }


}
