package be.smals.psnextrequest.service;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * PSNextRequest service implementation (EJB).
 *
 * @author AndreiBozga
 */
@Stateless(name = "PSNextRequestServiceBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PSNextRequestServiceBean implements PSNextRequestService {

    @PersistenceContext(unitName = "PSNextRequestUnit")
    private EntityManager em;

    private Logger logger = Logger.getLogger("UserLogger");

    protected void flush() {
        em.flush();
    }


}
