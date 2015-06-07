//package be.smals.psnextrequest.service;
//
//
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import be.smals.psnextrequest.entity.Responsible;
//import be.smals.psnextrequest.entity.Role;
//import be.smals.psnextrequest.entity.User;
//import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
//import be.smals.psnextrequest.service.users.PSNextRequestServiceBeanUser;
//import be.smals.stve.unitilsmodules.runner.StveJunit4TestClassRunner;
//
//
//
///**
// * Unit test for the for PSNextRequestServiceBean class.
// * 
// * @author AndreiBozga
// * 
// * @since 
// * 
// */
//@RunWith(StveJunit4TestClassRunner.class)
//@SpringApplicationContext("classpath:test-jpa-context.xml")
//public class PSNextRequestServiceBeanTest {
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
////        Assert.assertNotNull(psNextRequestServiceBean);
//    }
//    
////    /**
////     * Simple test for exception on update.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test(expected = PSNextRequestServiceException.class)
////    public void testExceptionOnUpdate() throws PSNextRequestServiceException {
////        Request request = new Request();
////        psNextRequestServiceBean.updateRequest(request);
////    }
////    
////    /**
////     * Simple test for the createProject method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testCreateProject() throws PSNextRequestServiceException {
////        Project project = new Project();
////        project.setProjectName("Projet de test");
////        
////        Project  createdProject = psNextRequestServiceBean.createProject(project);
////        
////        Assert.assertNotNull(createdProject.getProjectId());
////        Assert.assertEquals("Projet de test", createdProject.getProjectName());
////        
////        Project selectProject = psNextRequestServiceBean.getProjectById(createdProject.getProjectId());
////        
////        Assert.assertNotNull(selectProject);
////        // Failed
////        Assert.assertNull(psNextRequestServiceBean.getProjectById(createdProject.getProjectId() +1));
////    }
////    
////    /**
////     * Simple test for the updateProject method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testUpdateProject() throws PSNextRequestServiceException {
////    	Project project = new Project();
////        project.setProjectName("Projet de test2");
////        Project  createdProject = psNextRequestServiceBean.createProject(project);
////        
////        createdProject.setProjectName("Updated Name");
////        psNextRequestServiceBean.updateProject(createdProject);
////        
////        Assert.assertEquals("Updated Name", createdProject.getProjectName());
////    }
////    
////	 /**
////	  * Simple test for the getAllProjects method.
////	  * 
////	  * @throws PSNextRequestServiceException
////	  */
////	 @Test
////	public void testGetAllProjects() throws PSNextRequestServiceException {
////	 	 List<Project> allProjects = psNextRequestServiceBean.getAllProjects();
////	 	 for (Project temp : allProjects){
////	 		 System.out.println(temp.getProjectName());
////	 	}
////	 }
////    
////    /**
////     * Simple test for the createUser method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testCreateUser() throws PSNextRequestServiceException {
////        User user = new User();
////        //populate User
////        user.setUserFirstName("userFirstnameTest1");
////        user.setUserLastName("userLastnameTest1");
////        user.setUserDistinguishedName("userDisName1");
////        user.setUserPassword("123456");
////        user.setUserMail("mail@mail.com");
////        //populate roles
////        psNextRequestServiceBean.createRoles();
////        //create new User
////        User createdUser = psNextRequestServiceBean.createUser(user);
////        User findCreatedUser = psNextRequestServiceBean.getUserById(createdUser.getUserId());
////        Assert.assertEquals("userFirstnameTest1", findCreatedUser.getUserFirstName());
////    }
////    
////    /**
////     * Simple test for the createUser method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testAddUserAdminRole() throws PSNextRequestServiceException {
////        User user = new User();
////        //populate User
////        user.setUserFirstName("userFirstnameTest1");
////        user.setUserLastName("userLastnameTest1");
////        user.setUserDistinguishedName("userDisName1");
////        user.setUserPassword("123456");
////        user.setUserMail("mail@mail.com");
////        // populate roles
////        psNextRequestServiceBean.createRoles();
////        //create new User
////        User createdUser = psNextRequestServiceBean.createUser(user);
////        User findCreatedUser = psNextRequestServiceBean.getUserById(createdUser.getUserId());
////        
////
////        List<Role> lesRoles = new ArrayList<Role>();
////        lesRoles = findCreatedUser.getRoles();
////        for(Role roles : lesRoles){
////        	System.out.println(roles.getRoleName());
////        }
////        
////        User userAdminRole = psNextRequestServiceBean.addUserAdminRole(findCreatedUser);
////        lesRoles = new ArrayList<Role>();
////        lesRoles = userAdminRole.getRoles();
////        for(Role roles : lesRoles){
////        	System.out.println(roles.getRoleName());
////        }
////
////        psNextRequestServiceBean.deleteUser(userAdminRole);
////        
////    }
//    
////    
////    /**
////     * Simple test for users login test
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testLoginUser() throws PSNextRequestServiceException {
////    	User user = new User();
////    	user = getUserInstance("NomLoginTest", "PrenomLoginTest", "admin", "password", "email");
////    	psNextRequestServiceBean.createUser(user);
////    	
////    	User userLogged = psNextRequestServiceBean.loginUser("admin","password");
////    	Assert.assertEquals("NomLoginTest", userLogged.getUserFirstName());
////    }
////    
////    /**
////     * Simple test for user login with bad password
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test(expected = PSNextRequestServiceException.class)
////    public void testLoginUserBadPassword() throws PSNextRequestServiceException {
////    	User user = new User();
////    	user = getUserInstance("NomLoginUserBadPasswordTest", "PrenomLoginLoginUserBadPasswordTest", "adminLoginUserBadPassword", "password", "emailLoginUserBadPassword");
////    	psNextRequestServiceBean.createUser(user);
////    	psNextRequestServiceBean.loginUser("adminLoginUserBadPassword","badPassword");
////    }
////    
////    /**
////     * Simple test for user login with bad login name
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test(expected = PSNextRequestServiceException.class)
////    public void testLoginUserBadLogin() throws PSNextRequestServiceException {
////    	User user = new User();
////    	user = getUserInstance("NomLoginUserBadLoginTest", "PrenomLoginUserBadLoginTest", "adminUserBadLogin", "password", "emailUserBadLogin");
////    	psNextRequestServiceBean.createUser(user);
////    	psNextRequestServiceBean.loginUser("badLogin","password");
////    }
////    
////    /**
////     * Simple test for the createRequest method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testCreateRequest() throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	request = getRequestInstance("Project created by Create Request1", "FirstNameForCreateRequest", "LastNameForCreateRequest", "DistinguishedNameForCreateRequest");
////    	
////    	psNextRequestServiceBean.createRequest(request);
////    }
////    
////    /**
////     * Simple test for the getRequestById method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testFindRequest() throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	request = getRequestInstance("Project created by find Request", "FirstNameForFindRequest", "LastNameForFindRequest", "DistinguishedNameForFindRequest");
////    	psNextRequestServiceBean.createRequest(request);
////    	Request requestFond = psNextRequestServiceBean.getRequestById(request.getId());
////    	Assert.assertEquals(request.getId(), requestFond.getId());
////    }
////    
////    /**
////     * Simple test for the updateRequest method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testUpdateRequest() throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	request = getRequestInstance("Project created by Update Request", "FirstNameForUpdateRequest", "LastNameForUpdateRequest", "DistinguishedNameForUpdateRequest");
////    	psNextRequestServiceBean.createRequest(request);
////    	
////    	Request requestToUpdate = psNextRequestServiceBean.getRequestById(request.getId());
////    	requestToUpdate.setDescription("Update description");
////    	requestToUpdate.setCommentResp("Update comment responsible");
////    	requestToUpdate.setLastUpdateDate(new Date());
////    	psNextRequestServiceBean.updateRequest(requestToUpdate);
////    	
////    	Request createdRequest = psNextRequestServiceBean.getRequestById(request.getId());
////    	Assert.assertEquals("Update description", createdRequest.getDescription());
////    }
////    
////    /**
////     * Simple test for the deleteRequest method.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test(expected = PSNextRequestServiceException.class)
////    public void testDeleteRequest() throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	request = getRequestInstance("Project created by Delete Request1", "FirstNameForDeleteRequest", "LastNameForDeleteRequest", "DistinguishedNameForDeleteRequest");
////    	
////    	psNextRequestServiceBean.createRequest(request);
////    	psNextRequestServiceBean.deleteRequest(request.getId());
////
////    	Request requestRetrieved = psNextRequestServiceBean.getRequestById(request.getId());
////    }
////    
////    /**
////     * Simple test for the deleteRequest method on a non existing request.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test(expected = PSNextRequestServiceException.class)
////    public void testDeleteNonExistingRequest() throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	request = getRequestInstance("Project created by test delete non existing request", "FirstNameForDeleteNonExistingRequest", "LastNameForDeleteNonExistingRequest", "DistinguishedNameForDeleteNonExistingRequest");
////    	psNextRequestServiceBean.createRequest(request);
////    	
////    	psNextRequestServiceBean.deleteRequest(request.getId() +1);
////    }
////    
////    /**
////     * Simple test for association project responsible user. And set one project not visible for responsible
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testAddProjectResponsible() throws PSNextRequestServiceException {
////    	//create instance user 1
////        User user1 = new User();
////        user1 = getUserInstance("UserFirstNameForAddProjectResponsibleMethod", "UserLastNameForAddProjectResponsibleMethod", "UDisName4AddProResp", "password", "email@email.com");
////        
////        //create instance user 2
////        User user2 = new User();
////        user2.setUserFirstName("UserFirstNameForAddProjectResponsibleMethod2");
////        user2.setUserLastName("UserLastNameForAddProjectResponsibleMethod2");
////        user2.setUserDistinguishedName("UDisName4AddProResp2");
////        user2.setUserPassword("123456");
////        user2.setUserMail("mail@mail.com");
////        
////        //create roles
////        psNextRequestServiceBean.createRoles();
////        //persist user 1 and user 2
////        psNextRequestServiceBean.createUser(user1);
////        psNextRequestServiceBean.createUser(user2);
////        
////        //create instance and persist project 1 and project 2
////        Project project1 = new Project();
////        project1.setProjectName("Projet created by add project responsible methode1");
////        
////        Project project2 = new Project();
////        project2.setProjectName("Projet created by add project responsible methode2");
////        
////        psNextRequestServiceBean.createProject(project1);
////        psNextRequestServiceBean.createProject(project2);
////        
////        //create 2 responsible list and add users
////        List<User> responsiblesList1 = new ArrayList<User>();
////        List<User> responsiblesList2 = new ArrayList<User>();
////        responsiblesList1.add(user1);
////        responsiblesList2.add(user1);
////        responsiblesList2.add(user2);
////        
////        //assign responsibles to project
////        psNextRequestServiceBean.addProjectResponsible(responsiblesList1, project1.getProjectId());
////        psNextRequestServiceBean.addProjectResponsible(responsiblesList2, project2.getProjectId());
////        
////        //set project to not visible
////        psNextRequestServiceBean.addProjectResponsibleNotVisible(user1.getUserId(), project1.getProjectId());
////        
////        //check if a user is responsible 
////        User findUser = psNextRequestServiceBean.getUserById(user1.getUserId());
////        List<Responsible> responsibles = findUser.getResponsibles();
////        Assert.assertFalse(responsibles.isEmpty());
////        
////        for(Responsible resp : responsibles){
////        	Project project = resp.getProject();
////        	User user = resp.getUser();
////        	Assert.assertNotNull(project.getProjectName());
////        	Assert.assertNotNull(user.getUserFirstName());
////        }
////    }
//    
////    /**
////     * Simple test for get all requests for a specific user.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testGetRequestsByUser() throws PSNextRequestServiceException {
////    	User user = new User();
////    	user = getUserInstance("UserFirstNameForGetRequestsByUserMethod", "UserLastNameForGetRequestsByUserMethod", "UserDistinquishedNameForGetRequestsByUserMethod", "password", "email");
////    	psNextRequestServiceBean.createUser(user);
////    	
////    	Project project1 = new Project();
////    	project1.setProjectName("Project1 created by getRequestByUser methode");
////    	psNextRequestServiceBean.createProject(project1);
////    	
////    	Project project2 = new Project();
////    	project2.setProjectName("Project2 created by getRequestByUser methode");
////    	psNextRequestServiceBean.createProject(project2);
////    	
////    	Request request1 = new Request();
////    	request1 = getRequestInstance(user,project1);
////    	psNextRequestServiceBean.createRequest(request1);
////    	
////    	Request request2 = new Request();
////    	request2 = getRequestInstance(user,project2);
////    	psNextRequestServiceBean.createRequest(request2);
////    	
////    	List<Request> requests = psNextRequestServiceBean.getRequestsByUser(user);
////    	
////    	for(Request tempRequest : requests){
////    		Project project = new Project();
////    		project = psNextRequestServiceBean.getProjectById(tempRequest.getProject().getProjectId());
////    		System.out.println(tempRequest.getDescription());
////    		System.out.println(project.getProjectName());
////    	}
////    }
////    
////    
////    /**
////     * Simple test for get all requests for a specific project.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////   	public void testGetRequestsByProject() throws PSNextRequestServiceException {
////    	User user = new User();
////    	user = getUserInstance("UserFirstNameForGetRequestsByProjectMethod", "UserLastNameForGetRequestsByProjectMethod", "UDisName4GetReqByPro", "password", "email");
////    	psNextRequestServiceBean.createUser(user);
////    	
////    	Project project1 = new Project();
////    	project1.setProjectName("Project1 created by getRequestsByproject methode");
////    	psNextRequestServiceBean.createProject(project1);
////    	
////    	Project project2 = new Project();
////    	project2.setProjectName("Project2 created by getRequestsByproject methode");
////    	psNextRequestServiceBean.createProject(project2);
////    	
////    	Request request1 = new Request();
////    	request1 = getRequestInstance(user,project1);
////    	psNextRequestServiceBean.createRequest(request1);
////    	
////    	Request request2 = new Request();
////    	request2 = getRequestInstance(user,project1);
////    	psNextRequestServiceBean.createRequest(request2);
////    	
////    	List<Request> requests = psNextRequestServiceBean.getRequestsByProject(project1);
////    	
////    	for(Request tempRequest : requests){
////    		Project project = new Project();
////    		project = psNextRequestServiceBean.getProjectById(tempRequest.getProject().getProjectId());
////    		System.out.println(tempRequest.getDescription());
////    		System.out.println(project.getProjectName());
////    	}
////    }
////   
////    /**
////     * Simple test for get all projects requests for a specific responsible.
////     * 
////     * @throws PSNextRequestServiceException
////     */
////    @Test
////    public void testGetProjectRequestByResponsible() throws PSNextRequestServiceException {
////    	List<Project> projects = new ArrayList<Project>();
////    	User user1 = new User();
////        user1 = getUserInstance("UserFirstNameForGetProjectRequestByResponsibleMethod", "UserLastNameForGetProjectRequestByResponsibleMethod", "UDisName2", "password", "email");
////        
////        User user2 = new User();
////        user2.setUserFirstName("UserFirstNameForGetProjectRequestByResponsibleMethod2");
////        user2.setUserLastName("UserLastNameForGetProjectRequestByResponsibleMethod2");
////        user2.setUserDistinguishedName("UserDisName3");
////        user2.setUserPassword("123456");
////        user2.setUserMail("mail@mail.com");
////        
////        psNextRequestServiceBean.createUser(user1);
////        psNextRequestServiceBean.createUser(user2);
////        
////        Project project1 = new Project();
////        project1.setProjectName("Project1 created by getProjectRequestByResponsible methode");
////        
////        Project project2 = new Project();
////        project2.setProjectName("Project2 created by getProjectRequestByResponsible methode");
////        
////        psNextRequestServiceBean.createProject(project1);
////        psNextRequestServiceBean.createProject(project2);
////        
////        psNextRequestServiceBean.addProjectResponsible(user1.getUserId(), project1.getProjectId());
////        psNextRequestServiceBean.addProjectResponsible(user1.getUserId(), project2.getProjectId());
////        psNextRequestServiceBean.addProjectResponsible(user2.getUserId(), project2.getProjectId());
////        
////        projects = psNextRequestServiceBean.getProjectsByResponsibleId(user1.getUserId());
////    	
////        for(Project tempProject : projects){
////        	System.out.println(tempProject.getProjectName());
////        	
////        }
////    }
//    
////    
//    private User getUserInstance(String userFirstName, String userLastName, String userDistinguishedName, String userPassword, String email){
//    	User user = new User();
//    	
//    	user.setUserFirstName(userFirstName);
//        user.setUserLastName(userLastName);
//        user.setUserDistinguishedName(userDistinguishedName);
//        user.setUserPassword(userPassword);
//        user.setUserMail(email);
//    	
//    	return user;
//    }
////    
////    private Request getRequestInstance(String projectName, String userFirstName, String userLastName, String userDistinguishedName) throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	Project project = new Project();
////    	User user = new User();
////    	
////    	project.setProjectName(projectName);
////    	user = getUserInstance(userFirstName, userLastName, userDistinguishedName, "password", "email@email.com");
////    	
////    	psNextRequestServiceBean.createUser(user);
////    	psNextRequestServiceBean.createProject(project);
////    	
////    	GregorianCalendar startDate = new GregorianCalendar(2013,01,10,10,30);
////    	GregorianCalendar endDate = new GregorianCalendar(2013,02,10,16,30);
////    	
////    	request.setProject(psNextRequestServiceBean.getProjectById(project.getProjectId()));
////    	request.setUser(psNextRequestServiceBean.getUserById(user.getUserId()));
////    	request.setStartDate(startDate.getTime());
////    	request.setEndDate(endDate.getTime());
////    	request.setDuration("15h");
////    	request.setCreationDate(new Date());
////    	request.setStatus(0);
////    	request.setDescription("Request Description text");
////    	request.setCommentResp("");
////    	request.setVisibleForResp(true);
////    	request.setVisibleForUser(true);
////    	
////    	return request;
////    }
////    
////    private Request getRequestInstance(User user, Project project) throws PSNextRequestServiceException {
////    	Request request = new Request();
////    	
////    	GregorianCalendar startDate = new GregorianCalendar(2013,01,10,10,30);
////    	GregorianCalendar endDate = new GregorianCalendar(2013,02,10,16,30);
////    	
////    	request.setProject(psNextRequestServiceBean.getProjectById(project.getProjectId()));
////    	request.setUser(psNextRequestServiceBean.getUserById(user.getUserId()));
////    	request.setStartDate(startDate.getTime());
////    	request.setEndDate(endDate.getTime());
////    	request.setDuration("15h");
////    	request.setCreationDate(new Date());
////    	request.setStatus(0);
////    	request.setDescription("Description text");
////    	request.setCommentResp("");
////    	request.setVisibleForResp(true);
////    	request.setVisibleForUser(true);
////    	
////    	return request;
////    }
//    
//    
//    
//
//}
