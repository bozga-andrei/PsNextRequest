//package be.smals.psnextrequest.service;
//
//
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.unitils.database.annotations.TestDataSource;
//
//import org.unitils.spring.annotation.SpringApplicationContext;
//import org.unitils.spring.annotation.SpringBeanByName;
//
//import be.smals.psnextrequest.entity.Project;
//import be.smals.psnextrequest.entity.Request;
//import be.smals.psnextrequest.entity.Task;
//import be.smals.psnextrequest.entity.User;
//import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
//import be.smals.psnextrequest.service.projects.PSNextRequestServiceBeanProject;
//import be.smals.psnextrequest.service.requests.PSNextRequestServiceBeanRequest;
//import be.smals.psnextrequest.service.tasks.PSNextRequestServiceBeanTask;
//import be.smals.psnextrequest.service.users.PSNextRequestServiceBeanUser;
//import be.smals.stve.unitilsmodules.runner.StveJunit4TestClassRunner;
//
//
//
///**
// * Unit test for the for PSNextRequestServiceBeanProject class.
// * 
// * @author AndreiBozga
// * 
// * @since 
// * 
// */
//@RunWith(StveJunit4TestClassRunner.class)
//@SpringApplicationContext("classpath:test-jpa-context.xml")
//public class PSNextRequestServiceBeanRequestTest {
//    
//    @SpringBeanByName
//    private PSNextRequestServiceBeanRequest psNextRequestServiceBeanRequest;
//    
//    @SpringBeanByName
//    private PSNextRequestServiceBeanProject psNextRequestServiceBeanProject;
//    
//    @SpringBeanByName
//    private PSNextRequestServiceBeanTask psNextRequestServiceBeanTask;
//    
//    @SpringBeanByName
//    private PSNextRequestServiceBeanUser psNextRequestServiceBeanUser;
//    
//    @TestDataSource
//    private DataSource dataSource;
//    
//    
//    @Before
//    public void setUp() {
////        EasyMock.expect(sessionContext.getCallerPrincipal()).andReturn(null).anyTimes();
////        EasyMock.replay(sessionContext);
//    }
//    
//    @Test
//    public void testCheckSpringContext() {
//        Assert.assertNotNull(psNextRequestServiceBeanRequest);
//    }
//    
//
////    /**
////     * Simple test for the createRequest method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testCreateRequest() throws PSNextRequestServiceException {
////    	Project project = new Project();
////    	Project createdProject = new Project();
////    	Task task1 = new Task();
////    	Task task2 = new Task();
////    	Task createdTask1 = new Task();
////    	Task createdTask2 = new Task();
////    	Request request = new Request();
////    	Request createdRequest = new Request();
////    	PSNextRequestServiceBeanUserTest userTest = new PSNextRequestServiceBeanUserTest();
////    	User user1 = new User();
////    	User user2 = new User();
////    	User user3 = new User();
////    	User user4 = new User();
////    	
////    	//create responsibles and project members 
////    	List<User> membersTeam = new ArrayList<User>();
////    	List<User> responsibles = new ArrayList<User>();
////    	user1 = userTest.getUserInstance("createProjectTeamFirstName1", "createProjectTeamLastName1", "crPrjTDsgName1", "12346", "createprojectteamuser1@email.com");
////    	user2 = userTest.getUserInstance("createProjectTeamFirstName2", "createProjectTeamLastName2", "crPrjTDsgName2", "12346", "createprojectteamuser2@email.com");
////    	user3 = userTest.getUserInstance("createProjectTeamFirstName3", "createProjectTeamLastName3", "crPrjTDsgName3", "12346", "createprojectteamuser3@email.com");
////    	user4 = userTest.getUserInstance("createProjectTeamFirstName4", "createProjectTeamLastName4", "crPrjTDsgName4", "12346", "createprojectteamuser4@email.com");
////    	psNextRequestServiceBeanUser.populateRole();
////    	psNextRequestServiceBeanUser.createUser(user1);
////    	psNextRequestServiceBeanUser.createUser(user2);
////    	psNextRequestServiceBeanUser.createUser(user3);
////    	psNextRequestServiceBeanUser.createUser(user4);
////    	membersTeam.add(user1);
////    	membersTeam.add(user2);
////    	responsibles.add(user3);
////    	responsibles.add(user4);
////    	
////    	//Create project
////    	project.setProjectName("ProjetTestCreateProjectName");
////    	project.setProjectDescription("CreateProject Description");
////    	project.setProjectStatus(0);
////
////    	createdProject = psNextRequestServiceBeanProject.createProject(membersTeam, project);
////        
////    	//Create task 1
////    	task1.setTaskName("TaskTestCreateTask");
////    	task1.setTaskDescription("CreateTask Description");
////    	task1.setTaskStatus(0);
////    	task1.setProject(createdProject);
////    	createdTask1 = psNextRequestServiceBeanTask.createTask(responsibles, task1);
////    	
////    	//Create task 2
////    	task2.setTaskName("TaskTestCreateTask2");
////    	task2.setTaskDescription("CreateTask2 Description2");
////    	task2.setTaskStatus(0);
////    	task2.setProject(createdProject);
////    	responsibles.add(user2);
////    	createdTask2 = psNextRequestServiceBeanTask.createTask(responsibles, task2);
////    	
////    	//Create request
////    	request = getRequestInstance(user1,task1);
////    	createdRequest = psNextRequestServiceBeanRequest.createRequest(request);
////    	
////    	
////    	Request findedRequest = psNextRequestServiceBeanRequest.getRequestById(createdRequest.getRequestId());
////    	
////        Assert.assertNotNull(findedRequest.getRequestId());
////        Assert.assertEquals("Description text", findedRequest.getRequestDescription());
////        
////        Project selectProject = psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId());
////        
////        Assert.assertNotNull(selectProject);
////        // Failed
////        Assert.assertNull(psNextRequestServiceBeanProject.getProjectById(createdProject.getProjectId() +1));
////    }
////    
////    
////    
////    // Get a Request instance
////	private Request getRequestInstance(User user, Task task) throws PSNextRequestServiceException {
////		Request request = new Request();
////		
////		GregorianCalendar startDate = new GregorianCalendar(2013,01,10,10,30);
////		GregorianCalendar endDate = new GregorianCalendar(2013,02,10,16,30);
////		
////		request.setTask(task);
////		request.setUser(user);
////		request.setRequestStartDate(startDate.getTime());
////		request.setRequestEndDate(endDate.getTime());
////		request.setRequestDuration("15h");
////		request.setRequestStatus(0);
////		request.setRequestDescription("Description text");
////		request.setRequestCommentResp("");
////		request.setRequestVisibleForUser(true);
////		
////		return request;
////	}
//
//}
