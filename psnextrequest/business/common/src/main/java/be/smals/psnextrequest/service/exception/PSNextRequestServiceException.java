package be.smals.psnextrequest.service.exception;


/**
 * TODO: Description of the class.
 *
 * @author AndreiBozga
 */
public class PSNextRequestServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public PSNextRequestServiceException() {
        super();
    }


    /**
     * @param message
     */
    public PSNextRequestServiceException(String message) {
        super(message);
    }


    /**
     * @param message
     * @param cause
     */
    public PSNextRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }


}
