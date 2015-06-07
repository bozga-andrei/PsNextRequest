package be.smals.psnextrequest.service.projects;

import java.util.List;

import javax.ejb.Remote;

import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.ProjectTeam;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;

@Remote
public interface PSNextRequestServiceRemoteProject {

	 /* ---------------- Project -------------------- */
    /**
     * Creates a new project.
     * 
     * @param project
     * @throws PSNextRequestServiceException
     */
    Project createProject(List<User> users, Project project) throws PSNextRequestServiceException;
    
    /**
     * Get project.
     * 
     * @param project
     * @return a Project
     * @throws PSNextRequestServiceException
     */
    Project updateProject(List<User> users, Project project) throws PSNextRequestServiceException; 
    
    /**
     * Disabled the specific project
     * 
     * @param Project
     * @throws PSNextRequestServiceException
     */
    void disableProject(Project project) throws PSNextRequestServiceException;
	
    /**
     * Get project.
     * 
     * @param projectId
     * @return a Project
     * @throws PSNextRequestServiceException
     */
    Project getProjectById(Long projectId) throws PSNextRequestServiceException; 
 	
    /**
     * Get a project by specific team.
     * 
     * @param Team
     * @return project
     * @throws PSNextRequestServiceException
     */
    Project getProjectByTeam(ProjectTeam team) throws PSNextRequestServiceException;
    
    /**
     * Get all projects.
     * 
     * @return a list of all projects
     * @throws PSNextRequestServiceException
     */
    List<Project> getAllProjects()throws PSNextRequestServiceException;
    
    /**
     * Crate a team project.
     * 
     * @param membersTeam, project
     * @return created TeamProject 
     * @throws PSNextRequestServiceException
     */
    ProjectTeam createProjectTeam(List<User> membersTeam, Project project)throws PSNextRequestServiceException;
    
    /**
     * Update a team project.
     * 
     * @param membersTeam, project
     * @return TeamProject updated
     * @throws PSNextRequestServiceException
     */
    void updateProjectTeam(List<User> membersTeam, Project project) throws PSNextRequestServiceException;
    
    /**
     * Get a project team by name.
     * 
     * @param name
     * @return TeamProject
     * @throws PSNextRequestServiceException
     */
    ProjectTeam getProjectTeamByName(String name) throws PSNextRequestServiceException;
    
    
}
