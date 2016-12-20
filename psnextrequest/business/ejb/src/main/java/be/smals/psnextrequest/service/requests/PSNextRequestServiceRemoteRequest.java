package be.smals.psnextrequest.service.requests;

import be.smals.psnextrequest.entity.Request;
import be.smals.psnextrequest.entity.Task;
import be.smals.psnextrequest.entity.User;
import be.smals.psnextrequest.service.exception.PSNextRequestServiceException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PSNextRequestServiceRemoteRequest {

	/* ---------------- Request -------------------- */

    /**
     * Creates a new request.
     *
     * @param request
     *
     * @return a Request
     *
     * @throws PSNextRequestServiceException
     */
    Request createRequest(Request request) throws PSNextRequestServiceException;

    /**
     * Updates the request.
     *
     * @param request
     *
     * @throws PSNextRequestServiceException
     */
    void updateRequest(Request request) throws PSNextRequestServiceException;

    /**
     * Delete the request.
     *
     * @param request
     *
     * @throws PSNextRequestServiceException
     */
    void deleteRequest(Request request) throws PSNextRequestServiceException;

    /**
     * Set visibility of requests for responsibles.
     *
     * @throws PSNextRequestServiceException
     */
    void setRequestVisibleForResp(Request req, User resp, boolean isVisible) throws PSNextRequestServiceException;

    /**
     * Get the request.
     *
     * @param requestId
     *
     * @return a request
     *
     * @throws PSNextRequestServiceException
     */
    Request getRequestById(Long requestId) throws PSNextRequestServiceException;

    /**
     * Get all requests of given user.
     *
     * @param user
     *
     * @return a list of requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getRequestsByUser(User user) throws PSNextRequestServiceException;

    /**
     * Get all visible requests of given user.
     *
     * @param user
     *
     * @return a list of visible requests by user
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getVisibleRequestsByUser(User user) throws PSNextRequestServiceException;

    /**
     * Get all visible requests for responsible by given user.
     *
     * @param user
     *
     * @return a list of visible requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getVisibleRequestsForRespByUser(User user) throws PSNextRequestServiceException;

    /**
     * Get all visible requests of given task.
     *
     * @param task
     *
     * @return a list of visible requests by task
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getVisibleRequestsByTask(Task task) throws PSNextRequestServiceException;

    /**
     * Get all requests of given task.
     *
     * @param task
     *
     * @return a list of requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getRequestsByTask(Task task) throws PSNextRequestServiceException;

    /**
     * Get all requests of given responsible.
     *
     * @param responsible
     *
     * @return a list of requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getRequestsByResponsible(User responsible) throws PSNextRequestServiceException;

    /**
     * Get all responsibles for selected request
     *
     * @param request
     *
     * @return a list of responsibles
     *
     * @throws PSNextRequestServiceException
     */
    List<User> getResponsiblesByRequest(Request request) throws PSNextRequestServiceException;

    /**
     * Get all visible requests of given responsible.
     *
     * @param responsible
     *
     * @return a list of requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getVisibleRequestsByResponsible(User responsible) throws PSNextRequestServiceException;

    /**
     * Get all requests.
     *
     * @return a list of all requests
     *
     * @throws PSNextRequestServiceException
     */
    List<Request> getAllRequests() throws PSNextRequestServiceException;
}
