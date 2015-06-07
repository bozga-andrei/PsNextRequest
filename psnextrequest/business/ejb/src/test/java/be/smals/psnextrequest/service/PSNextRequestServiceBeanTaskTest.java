//package be.smals.psnextrequest.service;
//
//
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import be.smals.psnextrequest.entity.Responsible;
//import be.smals.psnextrequest.entity.Task;
//import be.smals.psnextrequest.entity.User;
//import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
//import be.smals.psnextrequest.service.projects.PSNextRequestServiceBeanProject;
//import be.smals.psnextrequest.service.tasks.PSNextRequestServiceBeanTask;
//import be.smals.psnextrequest.service.users.PSNextRequestServiceBeanUser;
//import be.smals.stve.unitilsmodules.runner.StveJunit4TestClassRunner;
//
//
//
///**
// * Unit test for the for PSNextRequestServiceBeanTask class.
// * 
// * @author AndreiBozga
// * 
// * @since 
// * 
// */
//@RunWith(StveJunit4TestClassRunner.class)
//@SpringApplicationContext("classpath:test-jpa-context.xml")
//public class PSNextRequestServiceBeanTaskTest {
//    
//	@SpringBeanByName
//    private PSNextRequestServiceBeanTask psNextRequestServiceBeanTask;
//	
//    @SpringBeanByName
//    private PSNextRequestServiceBeanProject psNextRequestServiceBeanProject;
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
//        Assert.assertNotNull(psNextRequestServiceBeanProject);
//    }
//    
//
////    /**
////     * Simple test for the createTask method: Create Task and Responsible.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testCreateTask() throws PSNextRequestServiceException {
////    	Task task1 = new Task();
////    	Task task2 = new Task();
////    	Task createdTask1 = new Task();
////    	Task createdTask2 = new Task();
////    	Project project = new Project();
////    	Project project2 = new Project();
////    	Project createdProject1 = new Project();
////    	Project createdProject2 = new Project();
////    	PSNextRequestServiceBeanUserTest userTest = new PSNextRequestServiceBeanUserTest();
////    	User user1 = new User();
////    	User user2 = new User();
////    	User user3 = new User();
////    	User user4 = new User();
////    	
////    	//create responsibles and project members 
////    	List<User> responsibles = new ArrayList<User>();
////    	List<User> projectMembers = new ArrayList<User>();
////    	user1 = userTest.getUserInstance("createTaskFirstName1", "createTaskLastName1", "crTskTDsgName1", "12346", "createTaskuser1@email.com");
////    	user2 = userTest.getUserInstance("createTaskFirstName2", "createTaskLastName2", "crTskTDsgName2", "12346", "createTaskuser2@email.com");
////    	user3 = userTest.getUserInstance("createTaskFirstName3", "createTaskLastName3", "crTskTDsgName3", "12346", "createTaskuser3@email.com");
////    	user4 = userTest.getUserInstance("createTaskFirstName4", "createTaskLastName4", "crTskTDsgName4", "12346", "createTaskuser4@email.com");
////    	psNextRequestServiceBeanUser.populateRole();
////    	psNextRequestServiceBeanUser.createUser(user1);
////    	psNextRequestServiceBeanUser.createUser(user2);
////    	psNextRequestServiceBeanUser.createUser(user3);
////    	psNextRequestServiceBeanUser.createUser(user4);
////    	
////    	projectMembers.add(user1);
////    	projectMembers.add(user2);
////    	responsibles.add(user3);
////    	responsibles.add(user4);
////    	
////    	//Create project with members
////    	project.setProjectName("TaskTestCreateTaskProjectName");
////    	project.setProjectDescription("CreateTask Project Description");
////    	project.setProjectStatus(0);
////    	createdProject1 = psNextRequestServiceBeanProject.createProject(projectMembers, project);
////    	
////    	//Create project with members
////    	project2.setProjectName("TaskTestCreateTaskProjectName2");
////    	project2.setProjectDescription("CreateTask2 Project2 Description2");
////    	project2.setProjectStatus(0);
////    	createdProject2 = psNextRequestServiceBeanProject.createProject(projectMembers, project2);
////        
////    	
////    	//Create task 1
////    	task1.setTaskName("TaskTestCreateTask");
////    	task1.setTaskDescription("CreateTask Description");
////    	task1.setTaskStatus(0);
////    	task1.setProject(createdProject1);
////    	createdTask1 = psNextRequestServiceBeanTask.createTask(responsibles, task1);
////    	
////    	//Create task 2
////    	task2.setTaskName("TaskTestCreateTask2");
////    	task2.setTaskDescription("CreateTask2 Description2");
////    	task2.setTaskStatus(0);
////    	task2.setProject(createdProject2);
////    	responsibles.add(user2);
////    	createdTask2 = psNextRequestServiceBeanTask.createTask(responsibles, task2);
////    	
////    	//Test result
////    	Assert.assertNotNull(createdProject1.getProjectId());
////    	Assert.assertEquals("TaskTestCreateTaskProjectName", createdProject1.getProjectName());
////    	Assert.assertNotNull(createdProject2.getProjectId());
////    	
////        Assert.assertNotNull(createdTask1.getTaskId());
////        Assert.assertEquals("TaskTestCreateTask", createdTask1.getTaskName());
////        Assert.assertEquals(2, createdTask1.getResponsibles().size());
////        Assert.assertEquals("TaskTestCreateTaskProjectName", createdTask1.getProject().getProjectName());
////        Assert.assertEquals(0, createdTask1.getTaskStatus());
////        
////        Assert.assertNotNull(createdTask2.getTaskId());
////        Assert.assertEquals("TaskTestCreateTask2", createdTask2.getTaskName());
////        Assert.assertEquals(3, createdTask2.getResponsibles().size());
////        Assert.assertEquals("TaskTestCreateTaskProjectName2", createdTask2.getProject().getProjectName());
////        Assert.assertEquals(0, createdTask1.getTaskStatus());
////        
////    }
////    
////    /**
////     * Simple test for the updateTask method: Update Task and team.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testUpdateTask() throws PSNextRequestServiceException {
////    	Task task = new Task();
////    	Task createdTask = new Task();
////    	Project project = new Project();
////    	Project createdProject = new Project();
////    	PSNextRequestServiceBeanUserTest userTest = new PSNextRequestServiceBeanUserTest();
////    	User user1 = new User();
////    	User user2 = new User();
////    	User user3 = new User();
////    	User user4 = new User();
////        
////    	//create responsibles and project members 
////    	List<User> responsibles = new ArrayList<User>();
////    	List<User> projectMembers = new ArrayList<User>();
////    	user1 = userTest.getUserInstance("updateTaskFirstName1", "updateTaskLastName1", "updateTskDsgName1", "12346", "updateTaskuser1@email.com");
////    	user2 = userTest.getUserInstance("updateTaskFirstName2", "updateTaskLastName2", "updateTskDsgName2", "12346", "updateTaskuser2@email.com");
////    	user3 = userTest.getUserInstance("updateTaskFirstName3", "updateTaskLastName3", "updateTskDsgName3", "12346", "updateTaskuser3@email.com");
////    	user4 = userTest.getUserInstance("updateTaskFirstName4", "updateTaskLastName4", "updateTskDsgName4", "12346", "updateTaskuser4@email.com");
////    	//populate roles
////    	if(psNextRequestServiceBeanUser.getRoleById((long)1) == null){
////    		psNextRequestServiceBeanUser.populateRole();
////    	}
////    	psNextRequestServiceBeanUser.createUser(user1);
////    	psNextRequestServiceBeanUser.createUser(user2);
////    	psNextRequestServiceBeanUser.createUser(user3);
////    	psNextRequestServiceBeanUser.createUser(user4);
////    	projectMembers.add(user1);
////    	projectMembers.add(user2);
////    	responsibles.add(user3);
////    	responsibles.add(user4);
////    	
////    	//Create project with members
////    	project.setProjectName("TaskTestUpdateTaskProjectName");
////    	project.setProjectDescription("UpdateTask Project Description");
////    	project.setProjectStatus(0);
////    	createdProject = psNextRequestServiceBeanProject.createProject(projectMembers, project);
////    	
////    	//Create task with responsibles 
////    	task.setTaskName("TaskTestCreateTask");
////    	task.setTaskDescription("CreateTask Description");
////    	task.setTaskStatus(0);
////    	task.setProject(createdProject);
////    	createdTask = psNextRequestServiceBeanTask.createTask(responsibles, task);
////        
////        
////        //update task name and members
////    	createdTask.setTaskName("TaskName : Updated Name");
////    	List<User> newResponsibles = new ArrayList<User>();
////    	newResponsibles.add(user1);
////    	newResponsibles.add(user2);
////    	newResponsibles.add(user3);
////    	
////    	List<Responsible> exitingResponsibles = new ArrayList<Responsible>();
////    	List<User> exitingUserResponsibles = new ArrayList<User>();
////    	List<User> userResponsiblesToRemove = new ArrayList<User>();
////    	List<Integer> indexs = new ArrayList<Integer>();
////    	
////    	exitingResponsibles = createdTask.getResponsibles();
////    	for(Responsible resp : exitingResponsibles){
////    		exitingUserResponsibles.add(resp.getUser());
////    	}
////    	
////    	for(User existingResp: exitingUserResponsibles){
////    		for(User newResp : newResponsibles){
////    			if(existingResp.getUserId().equals(newResp.getUserId())){
////    				indexs.add(exitingUserResponsibles.indexOf(existingResp));
////    			}
////    		}
////    	}
////    	
////
////    	int nbRemoved=0;
////		for(int index : indexs){
////			System.out.println("remove index: " + index);
////			exitingUserResponsibles.remove(index-nbRemoved);
////			nbRemoved++;
////			System.out.println("isRemoved index: " + index);
////		}
////		
////		for(User existingResp: exitingUserResponsibles){
////			userResponsiblesToRemove.add(existingResp);
////		}
////    	
////    	
////    	
////    	
////        Task updateTask = psNextRequestServiceBeanTask.updateTask(newResponsibles, userResponsiblesToRemove, createdTask);
////        
////        //Test result
////    	Assert.assertNotNull(createdProject.getProjectId());
////        Assert.assertNotNull(createdTask.getTaskId());
////        
////        Assert.assertEquals("TaskName : Updated Name", updateTask.getTaskName());
////        
////    }
////    
////	/**
////	 * Simple test for the getAllTasks method.
////	 * 
////	 * @throws PSNextRequestServiceException
////	 */
////	@Test
////	public void testGetAllTasks() throws PSNextRequestServiceException {
////	 	 List<Task> allTasks = psNextRequestServiceBeanTask.getAllTasks();
////	 	 for (Task temp : allTasks){
////	 		 System.out.println(temp.getTaskName());
////	 	}
////	 }
//
//}
