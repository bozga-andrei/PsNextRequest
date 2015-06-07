package be.smals.psnextrequest.service.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import be.smals.psnextrequest.entity.ReqVisibleForResponsible;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Responsible;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.tasks.PSNextRequestServiceRemoteTask;

/**
 * PSNextRequest service request implementation (EJB).
 * 
 * @author AndreiBozga
 * 
 * @since
 * 
 */
@Stateless (name = "PSNextRequestServiceBeanRequest")
public class PSNextRequestServiceBeanRequest implements PSNextRequestServiceRemoteRequest{

	@PersistenceContext(unitName = "PSNextRequestUnit") 
	private EntityManager em;
	
	
	private Logger logger = Logger.getLogger("UserLogger");
	FileHandler fh = setLogger();
    
	@EJB 
    private PSNextRequestServiceRemoteTask serviceTask;
	
    protected void flush() {
        em.flush();
    }
    
    
    /* ---------------- Request -------------------- */
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#createRequest(be.smals.psnextrequest.entity.Request)
     */
    @Override
    public Request createRequest(Request request) throws PSNextRequestServiceException {
        try {
        	request.setRequestCreationDate(new Date());
        	em.getTransaction().begin();
            em.persist(request);
            request.getTask().getRequests().add(request);
            em.merge(request.getTask());
            em.flush();
            
            //add to table ReqVisbleForResponsible request and user 
            for(Responsible resp: request.getTask().getResponsibles()){
        		request.addReqVisibleForResp(resp.getUser(), true);
            }
            
            em.merge(request.getUser());
		    em.merge(request);
            em.flush();
            logger.log(Level.INFO, "Nouvelle demande créée par: " + request.getUser().getUserFirstName() + ". Pour la tâche: " +request.getTask().getTaskName()+ ". Description demande: " + request.getRequestDescription());
            em.getTransaction().commit();
            return request;
        } catch (PersistenceException e) {
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			if(e.getMessage().contains("Error Code: 1048")){
				throw new PSNextRequestServiceException("Un des champs n'a pas été correctement complété ou récuperé.", e);
			} else {
				throw new PSNextRequestServiceException("Contrainte violation exception!", e);
			} 
        } catch (Exception e) {
        	e.printStackTrace();
        	if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
            throw new PSNextRequestServiceException("Request cannot be created!", e);
            
        }
    }

    /**
     * @throws PSNextRequestServiceException 
     * @see be.smals.psnextrequest.service.PSNextRequestService#updateRequest(be.smals.psnextrequest.entity.Request)
     */
    @Override
    public void updateRequest(Request request) throws PSNextRequestServiceException {
    	try{
    		if(request.getRequestCreationDate() != null){
    			request.setRequestLastUpdateDate(new Date());
    			em.getTransaction().begin();
    			em.merge(request);
    			em.flush();
    			em.getTransaction().commit();    		}
    		else 
    			throw new PSNextRequestServiceException("La demande n'a pas pu être recupérée.");
    	} catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
        } catch (Exception e) {
            throw new PSNextRequestServiceException("Request cannot be updated: " + e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#deleteRequest(be.smals.psnextrequest.entity.Request)
     */
    @Override
    public void deleteRequest(Request requestId) throws PSNextRequestServiceException {
    	try{
    		if (requestId != null){
    			Request existingRequest = em.find(Request.class, requestId);
    	        if (existingRequest != null) {
    	        	em.getTransaction().begin();
    	            em.remove(existingRequest);
    	            flush();
    	            em.getTransaction().commit();
    	        } else {
    	            throw new PSNextRequestServiceException("Request not found!");
    	        }
        	} else {
        		throw new PSNextRequestServiceException("Request ID is null!");
        	}
    	} catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
    	} catch(Exception e){
    		throw new PSNextRequestServiceException("Request cannot be deleted: " + e.getMessage());
    	}
	} 

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#setRequestVisibleForResp(be.smals.psnextrequest.entity.Request, be.smals.psnextrequest.entity.User, boolean)
     */
    @Override
    public void setRequestVisibleForResp(Request req, User user, boolean isVisible) throws PSNextRequestServiceException{
    	try{
    		em.getTransaction().begin();
        	Query createQuery = em.createQuery("SELECT r FROM ReqVisibleForResponsible r WHERE r.request = :req AND r.user =:user");
    		createQuery.setParameter("req", req);
    		createQuery.setParameter("user", user);
    		ReqVisibleForResponsible visibleForResp = (ReqVisibleForResponsible) createQuery.getSingleResult();
    		visibleForResp.setIsVisible(isVisible);
            em.merge(visibleForResp);
            em.flush();
            em.getTransaction().commit();
    	} catch (Exception e){
    		throw new PSNextRequestServiceException("Request cannot be deleted: " + e.getMessage());
    	}
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getRequest(int)
     */
    @Override
    public Request getRequestById(Long requestId) throws PSNextRequestServiceException {
    	try{
    		if(requestId != null){
    			Request request = em.find(Request.class, requestId);
    			if(request != null){
    				return request;
    			} else {
    				throw new PSNextRequestServiceException("Request cannot be fond!");
				}
    		} else {
    			throw new PSNextRequestServiceException("Request ID is null!");
			}
    	} catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
    	} catch (Exception e){
    		throw new PSNextRequestServiceException("Request Error: " + e.getMessage());
    	}
    }
    
	@Override
	public List<Request> getRequestsByUser(User user) throws PSNextRequestServiceException {
		Query createQuery = em.createQuery("SELECT r FROM Request r WHERE r.user = :user");
		createQuery.setParameter("user", user);
        @SuppressWarnings("unchecked")
		List<Request> resultList = (List<Request>) createQuery.getResultList();
		return resultList;
	}
	
	@Override
	public List<Request> getVisibleRequestsByUser(User user) throws PSNextRequestServiceException {
		Query createQuery = em.createQuery("SELECT r FROM Request r WHERE r.user = :user AND r.requestVisibleForUser = 1");
		createQuery.setParameter("user", user);
        @SuppressWarnings("unchecked")
		List<Request> resultList = (List<Request>) createQuery.getResultList();
		return resultList;
	}
	
	@Override
	public List<Request> getVisibleRequestsForRespByUser(User user) throws PSNextRequestServiceException {
		try{
			Query createQuery = em.createQuery("SELECT r.request FROM ReqVisibleForResponsible r WHERE r.user = :user AND r.isVisible = 1");
			createQuery.setParameter("user", user);
	        @SuppressWarnings("unchecked")
			List<Request> resultList = (List<Request>) createQuery.getResultList();
			return resultList;
		} catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred! ");
		}
		
	}
	
	@Override
	public List<Request> getVisibleRequestsByTask(Task task) throws PSNextRequestServiceException{
		Query createQuery = em.createQuery("SELECT r FROM ReqVisibleForResponsible r WHERE r.task = :task AND r.isVisible = 0");
		createQuery.setParameter("task", task);
        @SuppressWarnings("unchecked")
		List<Request> resultList = (List<Request>) createQuery.getResultList();
		return resultList;
	}
	
	@Override
	public List<Request> getRequestsByTask(Task task) throws PSNextRequestServiceException{
		em.getTransaction().begin();
		Query createQuery = em.createQuery("SELECT r FROM Request r WHERE r.task = :task");
		createQuery.setParameter("task", task);
        @SuppressWarnings("unchecked")
		List<Request> resultList = (List<Request>) createQuery.getResultList();
        em.flush();
        em.getTransaction().commit();
		return resultList;
	}
	
	/**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getRequestsByResponsible(be.smals.psnextrequest.entity.User)
     */
	@Override
	public List<Request> getRequestsByResponsible(User user)throws PSNextRequestServiceException{
		try{
			em.getTransaction().begin();
			
			Query createQuery = em.createQuery("SELECT r FROM Responsible r WHERE r.user = :user AND r.isVisible = 1");
			createQuery.setParameter("user", user);
	        @SuppressWarnings({ "unchecked", "unused" })
			List<Responsible> responsibles = (List<Responsible>) createQuery.getResultList();
	        
	        for(Responsible resp : user.getResponsibles()){
	        	resp.getTask().getRequests();
	        }
	

			List<Request> requests = new ArrayList<Request>();
			List<Task> tasks = new ArrayList<Task>();
			List<Request> allRequests = new ArrayList<Request>();
			
			// find all projects for specific responsible
			tasks = serviceTask.getTasksByResponsibleId(user.getUserId());
			
			//for all projects collect all requests
			for(Task task: tasks){
				requests = getVisibleRequestsByTask(task);
				for(Request req : requests){
					allRequests.add(req);
				}
			}
			em.flush();
			em.getTransaction().commit();
			return allRequests;

		} catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
		} catch (Exception e) {
			em.getTransaction().rollback();
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
		}
	}
	
	/**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getResponsibleByRequest(be.smals.psnextrequest.entity.Request)
     */
	@Override
	public List<User> getResponsiblesByRequest(Request request) throws PSNextRequestServiceException{
		try{
			em.getTransaction().begin();
			List<User> usersResp = new ArrayList<User>();
			Request myRequest = em.find(Request.class, request.getRequestId());
			Task task = myRequest.getTask();
			List<Responsible> responsibles = task.getResponsibles();
			for(Responsible resp : responsibles){
				usersResp.add(resp.getUser());
			}
			em.getTransaction().commit();
		    return usersResp;
		 } catch (NoResultException e){
			throw new PSNextRequestServiceException("Responsible cannot be found");
		 } catch (Exception e) {
			throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
		 }
	}
	
	/**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getVisibleRequestsByResponsible(be.smals.psnextrequest.entity.User)
     */
	@Override
	public List<Request> getVisibleRequestsByResponsible(User responsible)throws PSNextRequestServiceException{
		try{
			em.getTransaction().begin();
			List<Request> visibleRequests = new ArrayList<Request>();
			List<Task> tasks = new ArrayList<Task>();
			List<Request> allVisibleRequests = new ArrayList<Request>();
			
			// find all tasks for specific responsible
			tasks = serviceTask.getTasksByResponsibleId(responsible.getUserId());
						
			//for all tasks collect all requests
			for(Task task: tasks){
				allVisibleRequests = getVisibleRequestsByTask(task);
				for(Request req : allVisibleRequests){
					visibleRequests.add(req);
				}
			}
			em.getTransaction().commit();
			return visibleRequests;
		} catch (Exception e) {
			em.getTransaction().rollback();
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
		}
	}

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#listAllRequests()
     */
    @Override
    public List<Request> getAllRequests() throws PSNextRequestServiceException {
        Query createQuery = em.createQuery("SELECT r FROM Request r");
        @SuppressWarnings("unchecked")
		List<Request> resultList = (List<Request>) createQuery.getResultList();
        return resultList;
    }
    
    
    
	//Initialiser et formater un fichier log 
    public FileHandler setLogger(){
		try {
	      // This block configure the logger with handler and formatter
	      fh = new FileHandler("c:\\PsNextLogs\\UserLogFile.log", true);
	      logger.addHandler(fh);
	      logger.setLevel(Level.ALL);
	      SimpleFormatter formatter = new SimpleFormatter();
	      fh.setFormatter(formatter);
	    } catch (SecurityException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return fh;
	}
}
