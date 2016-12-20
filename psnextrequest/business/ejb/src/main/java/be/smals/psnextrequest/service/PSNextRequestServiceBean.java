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
    FileHandler fh = setLogger();

    protected void flush() {
        em.flush();
    }


    public FileHandler setLogger() {
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("c:\\PsNextLogs\\UserLogFile.log", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fh;
    }


}
