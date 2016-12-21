package be.smals.psnextrequest.service.tasks;


import be.smals.psnextrequest.entity.*;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.users.PSNextRequestServiceRemoteUser;

import javax.ejb.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PSNextRequest service task implementation (EJB).
 *
 * @author AndreiBozga
 */
@Stateless(name = "PSNextRequestServiceBeanTask")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PSNextRequestServiceBeanTask implements PSNextRequestServiceRemoteTask {

    @PersistenceContext(unitName = "PSNextRequestUnit")
    private EntityManager em;

    @EJB
    private PSNextRequestServiceRemoteUser serviceUser;

    private Logger logger = Logger.getLogger("UserLogger");

    
    /* ---------------- Task -------------------- */

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Task createTask(List<User> responsibles, Task task) throws PSNextRequestServiceException {
        try {
            if (!responsibles.isEmpty() && responsibles != null) {
                task.setTaskCreationDate(new Date());
                if (task.getProject().getProjectId() != null) {
                    if (task.getTaskStatus() == 0) {
                        em.persist(task);
                        em.flush();
                        logger.log(Level.INFO, "A new task has been created: " + task.getTaskName());

                        //add responsibles for task
                        //many-to-many responsibles
                        for (User user : responsibles) {
                            task.addResponsible(user, true);
                            em.merge(user);
                            em.merge(task);
                            user.getResponsibles().size();
                            task.getResponsibles().size();
                            logger.log(Level.INFO, "Responsible added: " + user.getUserFirstName());
                        }
                        em.flush();
                        return task;
                    }
                    throw new PSNextRequestServiceException("The project have to be open!");
                } else {
                    throw new PSNextRequestServiceException("The project has not been selected!");
                }
            } else {
                throw new PSNextRequestServiceException("Please add a responsible");
            }
        } catch (PersistenceException e) {
            if (e.getMessage().contains("Error Code: 1062")) {
                throw new PSNextRequestServiceException("Le nom de la tâche existe déjà", e);
            } else if (e.getMessage().contains("Error Code: 1048")) {
                throw new PSNextRequestServiceException("Le projet n'a pas été sélectioné", e);
            } else {
                throw new PSNextRequestServiceException("Contrainte violation exception", e);
            }
        } catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
        } catch (Exception e) {
            throw new PSNextRequestServiceException("Task cannot be created", e);
        }
    }

    @Override
    public Task updateTask(List<User> newResponsibles, List<User> responsiblesToRemove, Task task) throws PSNextRequestServiceException {
        Task existingTask = null;
        try {
            if (task.getTaskId() != null) {
                existingTask = em.find(Task.class, task.getTaskId());
            }
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("Task cannot be found");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
        try {
            if (existingTask != null) {
                if (!newResponsibles.isEmpty() && newResponsibles != null) {
                    task.setTaskLastUpdateDate(new Date());
                    em.merge(task);
                    em.flush();
                    for (User user : newResponsibles) {
                        task.addResponsible(user, true);
                        em.merge(user);
                        em.merge(task);
                        user.getResponsibles().size();
                        task.getResponsibles().size();
                        logger.log(Level.INFO, "Responsable ajouté: " + user.getUserFirstName());
                    }
                    for (User user : responsiblesToRemove) {
//	        			Responsible resp = serviceUser.getResponsibleById(user.getUserId(), task.getTaskId());
                        Responsible resp = new Responsible();
                        Query query = em.createQuery("SELECT r FROM Responsible r WHERE r.user = :user AND r.task = :task");
                        query.setParameter("user", user);
                        query.setParameter("task", task);
                        resp = (Responsible) query.getSingleResult();
                        em.remove(resp);
                        em.flush();
                        logger.log(Level.INFO, "Responsible deleted: " + user.getUserFirstName());
                    }
                    em.merge(task);
                    em.flush();
                    logger.log(Level.INFO, "TAsk updated: " + task.getTaskName());
                    return task;
                } else {
                    throw new PSNextRequestServiceException("Ajouter au moins un responsable svp!");
                }
            } else {
                throw new PSNextRequestServiceException("Task cannot be found");
            }
        } catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }


    }

    @Override
    public void disableTask(Task task) throws PSNextRequestServiceException {
        try {
            //disable Task by status 3
            task.setTaskStatus(3);
            em.merge(task);
            em.flush();
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    @Override
    public Task getTaskById(Long taskId) throws PSNextRequestServiceException {
        try {
            return em.find(Task.class, taskId);
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("Task cannot be found");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    @Override
    public List<Task> getTasksByProject(Project project) throws PSNextRequestServiceException {
        try {
            Query query = em.createQuery("SELECT t FROM Task t WHERE t.project = :project AND t.taskStatus != 3");
            query.setParameter("project", project);
            @SuppressWarnings("unchecked")
            List<Task> tasks = (List<Task>) query.getResultList();
            if (!tasks.isEmpty()) {
                return tasks;
            } else {
                throw new PSNextRequestServiceException("Aucune tâche trouvée pour ce projet!");
            }
        } catch (PSNextRequestServiceException e) {
            throw new PSNextRequestServiceException(e.getMessage());
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasks() throws PSNextRequestServiceException {
        try {
            Query createQuery = em.createQuery("SELECT t FROM Task t");
            @SuppressWarnings("unchecked")
            List<Task> resultList = (List<Task>) createQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    @Override
    public void addTaskResponsibles(List<User> responsibles, Task task) throws PSNextRequestServiceException {
        try {
            if (!responsibles.isEmpty()) {
                if (task.getTaskId() != null) {
                    if (!task.equals(null)) {
                        for (User user : responsibles) {
                            task.addResponsible(user, true);
                            em.merge(user);
                            em.merge(task);
                            user.getResponsibles().size();
                            task.getResponsibles().size();
                        }
                        em.flush();
                    } else {
                        throw new PSNextRequestServiceException("Not project selected");
                    }
                } else {
                    throw new PSNextRequestServiceException("Task do not find");
                }
            } else {
                throw new PSNextRequestServiceException("List of responsibles is empty");
            }
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> getTasksByResponsibleId(Long userId) throws PSNextRequestServiceException {
        try {
            List<Task> tasks = new ArrayList<Task>();
            User user = em.find(User.class, userId);
            List<Responsible> resultList = user.getResponsibles();
            resultList.size();
            if (!resultList.isEmpty()) {
                for (Responsible resp : resultList) {
                    tasks.add(resp.getTask());
                }
            }
            return tasks;
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("Responsible cannot be found");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    public List<User> getResponsiblesByTask(Task task) throws PSNextRequestServiceException {
        try {
            List<User> userResponsibles = new ArrayList<User>();
            Task myTask = em.find(Task.class, task.getTaskId());
            List<Responsible> responsibles = myTask.getResponsibles();
            responsibles.size();
            for (Responsible resp : responsibles) {
                userResponsibles.add(resp.getUser());
            }
            return userResponsibles;
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("Responsible cannot be found");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    @Override
    public void setTaskVisibleForResp(Request req, User resp, boolean isVisible) throws PSNextRequestServiceException {
        //TODO
    }

}
