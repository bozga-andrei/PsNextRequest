package be.smals.psnextrequest.service;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.sql.DataSource;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import be.smals.psnextrequest.service.projects.PSNextRequestServiceBeanProject;
import be.smals.psnextrequest.service.users.PSNextRequestServiceBeanUser;



/**
 * Unit test for the for PSNextRequestServiceBeanProject class.
 * 
 * @author AndreiBozga
 * 
 * @since 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/test-jpa-context.xml" })
public class PSNextRequestServiceBeanProjectTest {
    
	@Autowired
    private PSNextRequestServiceBeanProject psNextRequestServiceBeanProject;
    
	@Autowired
    private PSNextRequestServiceBeanUser psNextRequestServiceBeanUser;
    
	@Resource
	private SessionContext sessionContext;
    
    @Before
    public void setUp() {
        EasyMock.expect(sessionContext.getCallerPrincipal()).andReturn(null).anyTimes();
        EasyMock.replay(sessionContext);
    }
    
    @Test
    public void testCheckSpringContext() {
        Assert.assertNotNull(psNextRequestServiceBeanProject);
    }
    

//    /**
//     * Simple test for the createProject method: Create Project and team.
//     * 
//     * @throws PSNextRequestServiceException
//     */
//    @Test
//    public void testCreateProject() throws PSNextRequestServiceException {
//    	Project project = new Project();
//    	Project createdProject = new Project();
//    	PSNextRequestServiceBeanUserTest userTest = new PSNextRequestServiceBeanUserTest();
//    	User user1 = new User();
//    	User user2 = new User();
//    	User user3 = new User();
//    	User user4 = new User();
//    	
//    	//create members
//    	List<User> membersTeam = new ArrayList<User>();
//    	user1 = userTest.getUserInstance("createProjectTeamFirstName1", "createProjectTeamLastName1", "crPrjTDsgName1", "12346", "createprojectteamuser1@email.com");
//    	user2 = userTest.getUserInstance("createProjectTeamFirstName2", "createProjectTeamLastName2", "crPrjTDsgName2", "12346", "createprojectteamuser2@email.com");
//    	user3 = userTest.getUserInstance("createProjectTeamFirstName3", "createProjectTeamLastName3", "crPrjTDsgName3", "12346", "createprojectteamuser3@email.com");
//    	user4 = userTest.getUserInstance("createProjectTeamFirstName4", "createProjectTeamLastName4", "crPrjTDsgName4", "12346", "createprojectteamuser4@email.com");
//    	psNextRequestServiceBeanUser.populateRole();
//    	psNextRequestServiceBeanUser.createUser(user1);
//    	psNextRequestServiceBeanUser.createUser(user2);
//    	psNextRequestServiceBeanUser.createUser(user3);
//    	psNextRequestServiceBeanUser.createUser(user4);
//    	membersTeam.add(user1);
//    	membersTeam.add(user2);
//    	
//    	//Create project
//    	project.setProjectName("ProjetTestCreateProjectName");
//    	project.setProjectDescription("CreateProject Description");
//    	project.setProjectStatus(0);
//
//    	createdProject = psNextRequestServiceBeanProject.createProject(membersTeam, project);
//        
//    	Project findedProject = psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId());
//    	
//        Assert.assertNotNull(findedProject.getProjectId());
//        Assert.assertEquals("ProjetTestCreateProjectName", findedProject.getProjectName());
//        
//        Project selectProject = psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId());
//        
//        Assert.assertNotNull(selectProject);
//        // Failed
//        Assert.assertNull(psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId() +1));
//    }
//    
//    /**
//     * Simple test for the updateProject method: Update project and team.
//     * 
//     * @throws PSNextRequestServiceException
//     */
//    @Test
//    public void testUpdateProject() throws PSNextRequestServiceException {
//    	Project project = new Project();
//        
//        
//        //create members
//        List<User> membersTeam = new ArrayList<User>();
//    	PSNextRequestServiceBeanUserTest userTest = new PSNextRequestServiceBeanUserTest();
//    	User user1 = new User();
//    	User user2 = new User();
//    	User user3 = new User();
//    	User user4 = new User();
//    	user1 = userTest.getUserInstance("updateProjectTeamFirstName1", "updateProjectLastName1", "updatePrjDsgName1", "12346", "updateProjectuser1@email.com");
//    	user2 = userTest.getUserInstance("updateProjectTeamFirstName2", "updateProjectLastName2", "updatePrjDsgName2", "12346", "updateProjectuser2@email.com");
//    	user3 = userTest.getUserInstance("updateProjectTeamFirstName3", "updateProjectLastName3", "updatePrjDsgName3", "12346", "updateProjectuser3@email.com");
//    	user4 = userTest.getUserInstance("updateProjectTeamFirstName4", "updateProjectLastName4", "updatePrjDsgName4", "12346", "updateProjectuser4@email.com");
//    	//populate roles
//    	if(psNextRequestServiceBeanUser.getRoleById((long)1) == null){
//    		psNextRequestServiceBeanUser.populateRole();
//    	}
//    	
//    	//create users
//    	psNextRequestServiceBeanUser.createUser(user1);
//    	psNextRequestServiceBeanUser.createUser(user2);
//    	psNextRequestServiceBeanUser.createUser(user3);
//    	psNextRequestServiceBeanUser.createUser(user4);
//    	
//    	membersTeam.add(user1);
//    	membersTeam.add(user2);
//    	
//    	//Create project with members
//    	project.setProjectName("Projet de test2");
//        project.setProjectDescription("UpdateProject Description");
//    	project.setProjectStatus(0);
//        Project  createdProject = psNextRequestServiceBeanProject.createProject(membersTeam, project);
//        
//        //Finded project 
//        Project findedProject = psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId());
//        
//        //update project name and members
//        findedProject.setProjectName("ProjectName : Updated Name");
//        //findedProject.getProjectTeam().getUsers().add(user3);
//        List<User> updateTeam = new ArrayList<User>();
//        updateTeam.add(user1);
//        updateTeam.add(user3);
//        updateTeam.add(user4);
//        findedProject.getProjectTeam().setUsers(updateTeam);
//        Project updateProject = psNextRequestServiceBeanProject.updateProject(updateTeam, findedProject);
//        
//        Assert.assertEquals("ProjectName : Updated Name", updateProject.getProjectName());
//        
//        Assert.assertEquals("ProjectName : Updated Name", findedProject.getProjectName());
//    }
//    
//	/**
//	 * Simple test for the getAllProjects method.
//	 * 
//	 * @throws PSNextRequestServiceException
//	 */
//	@Test
//	public void testGetAllProjects() throws PSNextRequestServiceException {
//	 	 List<Project> allProjects = psNextRequestServiceBeanProject.getAllProjects();
//	 	 for (Project temp : allProjects){
//	 		 System.out.println(temp.getProjectName());
//	 	}
//	 }

}
