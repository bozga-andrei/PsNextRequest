package be.smals.psnextrequest.service.tasks;

import java.util.List;

import javax.ejb.Remote;

import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;

@Remote
public interface PSNextRequestServiceRemoteTask {

	/* ---------------- Task -------------------- */
    /**
     * Creates a new task.
     * 
     * @param task
     * @throws PSNextRequestServiceException
     */
    Task createTask(List<User> users, Task task) throws PSNextRequestServiceException;
    
    /**
     * Get task.
     * 
     * @param task
     * @throws PSNextRequestServiceException
     */
    Task updateTask(List<User> users, List<User> responsiblesToRemove, Task task) throws PSNextRequestServiceException;
    
    /**
     * Get task.
     * 
     * @param taskId
     * @return a Task
     * @throws PSNextRequestServiceException
     */
    Task getTaskById(Long taskId) throws PSNextRequestServiceException; 
    
    /**
     * Disable task.
     * 
     * @param task
     * @throws PSNextRequestServiceException
     */
    void disableTask(Task task) throws PSNextRequestServiceException;
    
    /**
     * Get tasks by selected project.
     * 
     * @param Project
     * @return a List of Tasks
     * @throws PSNextRequestServiceException
     */
    public List<Task> getTasksByProject(Project project) throws PSNextRequestServiceException;
    
    /**
     * Get all tasks.
     * 
     * @param
     * @return a List of all Tasks
     * @throws PSNextRequestServiceException
     */
    List<Task> getAllTasks() throws PSNextRequestServiceException;
    
    /**
     * Add new responsibles user to task
     * 
     * @param List<User> responsibles, Task
     * @throws PSNextRequestServiceException
     */
	void addTaskResponsibles(List<User> responsibles, Task task) throws PSNextRequestServiceException;
	
	/**
     * Get all tasks for a specific responsible.
     * 
     * @param userId
     * @return a List of tasks found
     * @throws PSNextRequestServiceException
     */
	List<Task> getTasksByResponsibleId(Long userId) throws PSNextRequestServiceException;
	
	/**
     * Get all responsibles for selected task
     * 
     * @param task
     * @return a list of responsibles
     * @throws PSNextRequestServiceException
     */
	List<User> getResponsiblesByTask(Task task) throws PSNextRequestServiceException;
	
    /**
     * Set visibility of task for responsibles.
     * 
     * @throws PSNextRequestServiceException
     */
    void setTaskVisibleForResp(Request req, User resp, boolean isVisible) throws PSNextRequestServiceException;
}
