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
//public class PSNextRequestServiceBeanUserTest {
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
////        Assert.assertNotNull(psNextRequestServiceBeanUser);
//    }
//    
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
////        psNextRequestServiceBeanUser.populateRole();
////        //create new User
////        User createdUser = psNextRequestServiceBeanUser.createUser(user);
////        User findCreatedUser = psNextRequestServiceBeanUser.getUserById(createdUser.getUserId());
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
////        psNextRequestServiceBeanUser.populateRole();
////        //create new User
////        User createdUser = psNextRequestServiceBeanUser.createUser(user);
////        User findCreatedUser = psNextRequestServiceBeanUser.getUserById(createdUser.getUserId());
////        
////
////        List<Role> lesRoles = new ArrayList<Role>();
////        lesRoles = findCreatedUser.getRoles();
////        for(Role roles : lesRoles){
////        	System.out.println(roles.getRoleName());
////        }
////        
////        User userAdminRole = psNextRequestServiceBeanUser.addUserAdminRole(findCreatedUser);
////        lesRoles = new ArrayList<Role>();
////        lesRoles = userAdminRole.getRoles();
////        for(Role roles : lesRoles){
////        	System.out.println(roles.getRoleName());
////        }
////
////        psNextRequestServiceBeanUser.deleteUser(userAdminRole);
////        
////    }
////    
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
////    	psNextRequestServiceBeanUser.populateRole();
////    	psNextRequestServiceBeanUser.createUser(user);
////    	
////    	User userLogged = psNextRequestServiceBeanUser.loginUser("admin","password");
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
////    	psNextRequestServiceBeanUser.populateRole();
////    	psNextRequestServiceBeanUser.createUser(user);
////    	psNextRequestServiceBeanUser.loginUser("adminLoginUserBadPassword","badPassword");
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
////    	psNextRequestServiceBeanUser.populateRole();
////    	psNextRequestServiceBeanUser.createUser(user);
////    	psNextRequestServiceBeanUser.loginUser("badLogin","password");
////    }
////    
////
//    public User getUserInstance(String userFirstName, String userLastName, String userDistinguishedName, String userPassword, String email){
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
//    
//    public User getUserInstance(){
//    	User user = new User();
//    	
//    	user.setUserFirstName("userInstanceFirstName");
//        user.setUserLastName("userInstanceLastName");
//        user.setUserDistinguishedName("userInstanceDistinguishedName");
//        user.setUserPassword("123456");
//        user.setUserMail("userInstanceEmail@email.com");
//    	
//    	return user;
//    }
//
//}
