package be.smals.psnextrequest.service.users;

import be.smals.psnextrequest.entity.Responsible;
import be.smals.psnextrequest.entity.Role;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * User Service Bean
 * PSNextRequest service users implementation (EJB).ss
 *
 * @author AndreiBozga
 */
@Stateless(name = "PSNextRequestServiceBeanUser")
public class PSNextRequestServiceBeanUser implements PSNextRequestServiceRemoteUser {

    @PersistenceContext(type = javax.persistence.PersistenceContextType.TRANSACTION)
    private EntityManager em;

    private Logger logger = Logger.getLogger("UserLogger");

    protected void flush() {
        em.flush();
    }

    /* ---------------- User -------------------- */
    @Override
    public User createUser(User user) throws PSNextRequestServiceException {
        try {
            StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
            //Encrypting password
            user.setUserPassword(pwEncryptor.encryptPassword(user.getUserPassword()));
            em.getTransaction().begin();
            //set ressource role
            Role role = getRoleByRoleName("ressource");
            //Transaction many-to-many User_Role
            user.getRoles().add(role);
            em.persist(user);
            em.flush();
            logger.log(Level.INFO, "L'utilisateur: " + user.getUserFirstName() + " " + user.getUserLastName() + " a été créé.");
            em.getTransaction().commit();
            return user;
        } catch (PersistenceException a) {
            if (a.getMessage().contains("USER_DISTINGUISHED_NAME_UNIQUE")) {
                throw new PSNextRequestServiceException("Contraint Exception: Le nom d'utilisateur existe déjà.");
            } else if (a.getMessage().contains("UQ_FirstName_LastName")) {
                throw new PSNextRequestServiceException("Contraint Exception: Le nom et le prenom existe déjà");
            } else if (a.getMessage().contains("UQ_UserMail")) {
                throw new PSNextRequestServiceException("Contraint Exception: L'adresse email existe déjà.");
            } else
                throw new PSNextRequestServiceException("Contraint Exception: " + a.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PSNextRequestServiceException("User cannot be created: " + e.getMessage());
        }
    }

    @Override
    public User updateUser(User user) throws PSNextRequestServiceException {
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.flush();
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PSNextRequestServiceException("User cannot be updated.");
        }

    }

    @Override
    public void deleteUser(User user) throws PSNextRequestServiceException {
        try {
            em.getTransaction().begin();
            em.remove(user);
            logger.log(Level.INFO, "L'utilisateur: " + user.getUserFirstName() + " " + user.getUserLastName() + " a été supprimé.");
            flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PSNextRequestServiceException("Error: " + e.getMessage());
        }

    }

    @Override
    public User loginUser(String userDistinguishedName, String password) throws PSNextRequestServiceException {
        try {
            StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
            Query query = em.createQuery("SELECT u FROM User u WHERE u.userDistinguishedName = :userDistinguishedName");
            query.setParameter("userDistinguishedName", userDistinguishedName);
            User user = (User) query.getSingleResult();
            if (user != null) {
                if (pwEncryptor.checkPassword(password, user.getUserPassword())) {
                    logger.log(Level.INFO, "L'utilisater: " + user.getUserFirstName() + " " + user.getUserLastName() + " s'est connecté.");
                    return user;
                } else {
                    throw new PSNextRequestServiceException("Login failed: Invalid Password");
                }
            } else
                throw new PSNextRequestServiceException("Login failed: Invalid Username");
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("Login failed: Invalid Username");
        } catch (Exception e) {
            throw new PSNextRequestServiceException(e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getUserById(java.lang.Long)
     */
    @Override
    public User getUserById(Long user) throws PSNextRequestServiceException {
        try {
            return em.find(User.class, user);
        } catch (NoResultException e) {
            throw new PSNextRequestServiceException("User cannot be found");
        } catch (IllegalArgumentException e) {
            throw new PSNextRequestServiceException("The user id is not a valid type for that entity's primary key");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getAllUsers()
     */
    @Override
    public List<User> getAllUsers() throws PSNextRequestServiceException {
        try {
            Query createQuery = em.createQuery("SELECT u FROM User u");
            @SuppressWarnings("unchecked")
            List<User> resultList = (List<User>) createQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }
    
    
    
    
    /* ---------------- Role -------------------- */

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#createRole(be.smals.psnextrequest.entity.Role)
     */
    public void createRole(Role role) throws PSNextRequestServiceException {
        try {
            em.getTransaction().begin();
            em.persist(role);
            em.flush();
            logger.log(Level.INFO, "Un nouveau rôle a été créé: " + role.getRoleName());
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#createRole(be.smals.psnextrequest.entity.Role)
     */
    public void populateRole() throws PSNextRequestServiceException {
        try {
            em.getTransaction().begin();
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("admin");
            em.persist(roleAdmin);

            Role roleRessource = new Role();
            roleRessource.setRoleName("ressource");
            em.persist(roleRessource);

            em.flush();
            logger.log(Level.INFO, "La methode populate Role: ");
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#addUserResponsibleRole(be.smals.psnextrequest.entity.User)
     */
    @Override
    public User addUserAdminRole(User user) throws PSNextRequestServiceException {
        em.getTransaction().begin();
        Role role = getRoleByRoleName("admin");
        user.addRole(role);
        em.persist(user);
        logger.log(Level.INFO, "L'utilisateur: " + user.getUserFirstName() + " " + user.getUserLastName() + " est promové comme admin.");
        flush();
        em.getTransaction().commit();
        return user;
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getRoleById(java.lang.Long)
     */
    @Override
    public Role getRoleById(Long roleId) {
        return em.find(Role.class, roleId);
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getRoleById(java.lang.Long)
     */
    @Override
    public Role getRoleByRoleName(String roleName) {
        try {
            Role role = new Role();
            Query query = em.createQuery("SELECT r FROM Role r WHERE r.roleName = :roleName");
            query.setParameter("roleName", roleName);
            role = (Role) query.getSingleResult();
            return role;
        } catch (EntityNotFoundException e) {
            e.getStackTrace();
            return null;
        }

    }

	
	/* ---------------- Responsible -------------------- */

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getResponsibleById(be.smals.psnextrequest.entity.Responsible)
     */
    @Override
    public Responsible getResponsibleById(Long userId, Long taskId) throws PSNextRequestServiceException {
        try {
            User user = em.find(User.class, userId);
            if (!user.equals(null)) {
                Task task = em.find(Task.class, taskId);
                if (!task.equals(null)) {
                    Responsible responsible = new Responsible();
                    Query query = em.createQuery("SELECT r FROM Responsible r WHERE r.user = :user AND r.task = :task");
                    query.setParameter("user", user);
                    query.setParameter("task", task);
                    responsible = (Responsible) query.getSingleResult();
                    return responsible;
                } else {
                    throw new PSNextRequestServiceException("Not project selected");
                }
            } else {
                throw new PSNextRequestServiceException("Not responsible selected");
            }
        } catch (EntityNotFoundException e) {
            throw new PSNextRequestServiceException("Responsible was not found");
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getVisibleResponsiblesByUser(be.smals.psnextrequest.entity.User)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Responsible> getVisibleResponsiblesByUser(User user) throws PSNextRequestServiceException {
        try {
            List<Responsible> responsibles = new ArrayList<Responsible>();
            Query query = em.createQuery("SELECT r FROM Responsible r WHERE r.user = :user AND r.isVisible = 1");
            query.setParameter("user", user);
            responsibles = (List<Responsible>) query.getResultList();
            return responsibles;
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred!");
        }

    }

    /**
     * @see be.smals.psnextrequest.service.PSNextRequestService#getAllResponsibles()
     */
    @Override
    public List<Responsible> getAllResponsibles() throws PSNextRequestServiceException {
        try {
            Query createQuery = em.createQuery("SELECT r FROM Responsible ");
            @SuppressWarnings("unchecked")
            List<Responsible> responsibles = (List<Responsible>) createQuery.getResultList();
            return responsibles;
        } catch (Exception e) {
            throw new PSNextRequestServiceException("An unexpected error has occurred: " + e.getMessage());
        }
    }

}
