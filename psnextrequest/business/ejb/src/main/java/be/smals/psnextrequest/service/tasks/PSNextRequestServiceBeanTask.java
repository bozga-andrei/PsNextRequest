package be.smals.psnextrequest.service.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Responsible;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;

/**
 * PSNextRequest service task implementation (EJB).
 * 
 * @author AndreiBozga
 * 
 * @since
 * 
 */
@Stateless (name = "PSNextRequestServiceBeanTask")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PSNextRequestServiceBeanTask implements PSNextRequestServiceRemoteTask{

	@PersistenceContext(unitName = "PSNextRequestUnit") 
	private EntityManager em;
	
	@Resource
	private SessionContext sessionContext;
	
	@EJB 
    private PSNextRequestServiceRemoteUser serviceUser;
	
	private Logger logger = Logger.getLogger("UserLogger");
	FileHandler fh = setLogger();
    
    protected void flush() {
        em.flush();
    }
    
    
    /* ---------------- Task -------------------- */
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#createTask(be.smals.psnextrequest.entity.Task)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Task createTask(List<User> responsibles, Task task) throws PSNextRequestServiceException {
		try{
			em.getTransaction().begin();
			if(!responsibles.isEmpty() && responsibles != null){
				task.setTaskCreationDate(new Date());
				if(task.getProject().getProjectId() != null){
					if( task.getTaskStatus() == 0){
						em.persist(task);
						em.flush();
						logger.log(Level.INFO, "Nouvelle t�che cr��e: " + task.getTaskName());
						
						//add responsibles for task
						//many-to-many responsibles
						for(User user : responsibles){
							task.addResponsible(user, true);
						    em.merge(user);
						    em.merge(task);
						    user.getResponsibles().size();
						    task.getResponsibles().size();
						    logger.log(Level.INFO, "Responsable ajout�: " + user.getUserFirstName());
						}
						em.flush();
						em.getTransaction().commit();
			    		return task;
					}
					throw new PSNextRequestServiceException("Le projet doit �tre en cours!");
				} else {
					throw new PSNextRequestServiceException("Le projet n'a pas �t� selectionn�!");
				}
			} else {
				throw new PSNextRequestServiceException("Ajouter au moins un responsable svp!");
			}
		} catch (PersistenceException e) {
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			if(e.getMessage().contains("Error Code: 1062")){
				throw new PSNextRequestServiceException("Le nom de la t�che existe d�j�", e);
			} else if(e.getMessage().contains("Error Code: 1048")){
				throw new PSNextRequestServiceException("Le projet n'a pas �t� s�lection�", e);
			} else {
				throw new PSNextRequestServiceException("Contrainte violation exception", e);
			}
		}catch (PSNextRequestServiceException e) {
	        if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			throw new PSNextRequestServiceException(e.getMessage());
        } catch (Exception e) {
        	if(em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
            throw new PSNextRequestServiceException("Task cannot be created", e);
        }   
    }
    
    @Override
    public Task updateTask(List<User> newResponsibles, List<User> responsiblesToRemove, Task task) throws PSNextRequestServiceException {
    	em.getTransaction().begin();
    	Task existingTask = null;
        try {
            if (task.getTaskId() != null) {
            	existingTask = em.find(Task.class, task.getTaskId());
            } 
        } catch(NoResultException e){
        	throw new PSNextRequestServiceException("Task cannot be found");
        } catch (Exception e) {
        	throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
        try{
        	if (existingTask != null) {
        		if(!newResponsibles.isEmpty() && newResponsibles != null){
	            	task.setTaskLastUpdateDate(new Date());
	            	em.merge(task);
	                em.flush();
	            	for(User user : newResponsibles){
	    				task.addResponsible(user, true);
	    			    em.merge(user);
	    			    em.merge(task);
	    			    user.getResponsibles().size();
	    			    task.getResponsibles().size();
	    			    logger.log(Level.INFO, "Responsable ajout�: " + user.getUserFirstName());
	    			}
	            	for(User user : responsiblesToRemove){
//	        			Responsible resp = serviceUser.getResponsibleById(user.getUserId(), task.getTaskId());
	            		Responsible resp = new Responsible();
						Query query = em.createQuery("SELECT r FROM Responsible r WHERE r.user = :user AND r.task = :task");
						query.setParameter("user", user);
						query.setParameter("task", task);
						resp = (Responsible) query.getSingleResult();
	    			    em.remove(resp);
	    			    em.flush();
	    			    logger.log(Level.INFO, "Responsable supprim�: " + user.getUserFirstName());
	    			}
	                em.merge(task);
	                em.flush();
					em.getTransaction().commit();
	                logger.log(Level.INFO, "T�che modifi�: " + task.getTaskName());
	                return task;
        		} else {
        			throw new PSNextRequestServiceException("Ajouter au moins un responsable svp!");
        		}
            } else {
            	throw new PSNextRequestServiceException("Task cannot be found");
            }
        } catch (PSNextRequestServiceException e){
	        if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
        	throw new PSNextRequestServiceException(e.getMessage());
        }catch (Exception e){
	        if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
        	throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
        
        
    }
    
    @Override
    public void disableTask(Task task) throws PSNextRequestServiceException {
        try {
        	em.getTransaction().begin();
        	//disable Task by status 3
        	task.setTaskStatus(3);
        	em.merge(task);
        	em.flush();
        	em.getTransaction().commit();
        }catch (Exception e){
	        if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
        	throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }
    
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getTaskById(java.lang.Long)
     */
    @Override
    public Task getTaskById(Long taskId) throws PSNextRequestServiceException {
    	try{
    		return em.find(Task.class, taskId);
    	} catch (NoResultException e){
    		throw new PSNextRequestServiceException("Task cannot be found");
    	} catch (Exception e) {
    		throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }
    
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getTasksByProject()
     */
    @Override
    public List<Task> getTasksByProject(Project project) throws PSNextRequestServiceException {
    	try{
    		Query query = em.createQuery("SELECT t FROM Task t WHERE t.project = :project AND t.taskStatus != 3");
			query.setParameter("project", project);
			@SuppressWarnings("unchecked")
			List<Task> tasks = (List<Task>) query.getResultList();
			if(!tasks.isEmpty()){
				return tasks;
			} else {
				throw new PSNextRequestServiceException("Aucune t�che trouv�e pour ce projet!");
			}
    	} catch(PSNextRequestServiceException e){
    		throw new PSNextRequestServiceException(e.getMessage());
    	}catch(Exception e){
    		throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
    	}
    }
    
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getAllTasks()
     */
    @Override
    public List<Task> getAllTasks() throws PSNextRequestServiceException {
    	try{
    		Query createQuery = em.createQuery("SELECT t FROM Task t");
            @SuppressWarnings("unchecked")
			List<Task> resultList = (List<Task>) createQuery.getResultList();
            return resultList;
    	} catch(Exception e){
    		throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
    	}
    }
    
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#addProjectResponsible()
     */
	@Override
	public void addTaskResponsibles(List<User> responsibles, Task task) throws PSNextRequestServiceException {
		try{
			if(!responsibles.isEmpty()){
				if(task.getTaskId() != null){
					if(!task.equals(null)){
						for(User user : responsibles){
							task.addResponsible(user, true);
						    em.merge(user);
						    em.merge(task);
						    user.getResponsibles().size();
						    task.getResponsibles().size();
						}
						em.flush();
					    em.getTransaction().commit();
					} else {
						em.getTransaction().rollback();
						throw new PSNextRequestServiceException("Not project selected");
					}
				} else {
					throw new PSNextRequestServiceException("Task do not find");
				}
			} else {
				throw new PSNextRequestServiceException("List of responsibles is empty");
			}
		} catch(Exception e) {
			throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage(),e);
		}
	}
	
	/**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getProjectsByResponsibleId(java.lang.Long)
     */
    @Override
	public List<Task> getTasksByResponsibleId(Long userId) throws PSNextRequestServiceException {
		try{
			List<Task> tasks = new ArrayList<Task>();
			User user = em.find(User.class, userId);
			List<Responsible> resultList = user.getResponsibles();
			resultList.size();
			if(!resultList.isEmpty()){
				for(Responsible resp: resultList){
					tasks.add(resp.getTask());
				}
			}
		    return tasks;
		} catch (NoResultException e){
			throw new PSNextRequestServiceException("Responsible cannot be found");
		}catch (Exception e) {
			throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
		}
	}
    
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getResponsiblesByProject(be.smals.psnextrequest.entity.Project)
     */
	public List<User> getResponsiblesByTask(Task task) throws PSNextRequestServiceException{
		try{
			em.getTransaction().begin();
			List<User> userResponsibles = new ArrayList<User>();
			Task myTask = em.find(Task.class, task.getTaskId());
			List<Responsible> responsibles = myTask.getResponsibles();
			responsibles.size();
			for(Responsible resp : responsibles ){
				userResponsibles.add(resp.getUser());
			}
			em.getTransaction().commit();
		    return userResponsibles;
		 } catch (NoResultException e){
			throw new PSNextRequestServiceException("Responsible cannot be found");
		 } catch (Exception e) {
			throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
		 }
	}
	
    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#setTaskVisibleForResp()
     */
	@Override
	public void setTaskVisibleForResp(Request req, User resp, boolean isVisible) throws PSNextRequestServiceException{
		//TODO
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
