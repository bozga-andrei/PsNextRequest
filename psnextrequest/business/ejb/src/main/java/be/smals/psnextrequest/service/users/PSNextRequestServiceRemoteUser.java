package be.smals.psnextrequest.service.users;

import be.smals.psnextrequest.entity.Responsible;
import be.smals.psnextrequest.entity.Role;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PSNextRequestServiceRemoteUser {

	/* ---------------- User -------------------- */

    /**
     * Create a new user.
     *
     * @param user
     *
     * @throws PSNextRequestServiceException
     */
    User createUser(User user) throws PSNextRequestServiceException;

    /**
     * Update existing user.
     *
     * @param user
     *
     * @return user
     *
     * @throws PSNextRequestServiceException
     */
    User updateUser(User user) throws PSNextRequestServiceException;

    /**
     * Delete a user.
     *
     * @param user
     *
     * @throws PSNextRequestServiceException
     */
    public void deleteUser(User user) throws PSNextRequestServiceException;

    /**
     * Add administrator role for user.
     *
     * @param user
     *
     * @throws PSNextRequestServiceException
     */
    User addUserAdminRole(User user) throws PSNextRequestServiceException;

    /**
     * Login method.
     *
     * @param userDistinguishedName, password
     *
     * @return the user
     *
     * @throws PSNextRequestServiceException
     */
    User loginUser(String userDistinguishedName, String password) throws PSNextRequestServiceException;

    /**
     * Get the user of the given user.
     *
     * @param userId
     *
     * @return a request
     *
     * @throws PSNextRequestServiceException
     */
    User getUserById(Long userId) throws PSNextRequestServiceException;

    /**
     * Get all users.
     *
     * @return a list of all users
     *
     * @throws PSNextRequestServiceException
     */
    List<User> getAllUsers() throws PSNextRequestServiceException;
    
    /* ---------------- Role -------------------- */


    /**
     * Create new role
     *
     * @param role
     */
    void createRole(Role role) throws PSNextRequestServiceException;

    /**
     * Get the role of the given roleId.
     *
     * @param roleId
     *
     * @return a Role
     */
    Role getRoleById(Long roleId);

    /**
     * Get the role of the given role Name.
     *
     * @param roleName
     *
     * @return a Role
     */
    Role getRoleByRoleName(String roleName);

	
	/* ---------------- Responsible -------------------- */

    /**
     * Get responsible by Ids
     *
     * @param project, user
     *
     * @return a responsible
     *
     * @throws PSNextRequestServiceException
     */
    Responsible getResponsibleById(Long project, Long user) throws PSNextRequestServiceException;

    /**
     * Get from responsible table only responsible with isVisible to true by user
     *
     * @param user
     *
     * @return a list of responsibles
     *
     * @throws PSNextRequestServiceException
     */
    List<Responsible> getVisibleResponsiblesByUser(User user) throws PSNextRequestServiceException;

    /**
     * Get all responsibles.
     *
     * @return a list of all responsibles
     *
     * @throws PSNextRequestServiceException
     */
    List<Responsible> getAllResponsibles() throws PSNextRequestServiceException;
}
