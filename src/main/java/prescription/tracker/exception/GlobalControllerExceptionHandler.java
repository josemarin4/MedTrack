package prescription.tracker.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class represents a global handler for exceptions in the MedTrack application.
 * It contains methods to handle all custom exceptions thrown by the controllers.
 * 
 * @author josemarin
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	
	/**
	 * Handles UserNotFoundException and returns a ResponseEntity with a bad request status
	 * and the exception message.
	 * 
	 * @param ex The UserNotFoundException to handle.
	 * @return A ResponseEntity with a bad request status and the exception message.
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	/**
	 * Handles DuplicateUserException and returns a ResponseEntity with a bad request status
	 * and the exception message.
	 * 
	 * @param ex The DuplicateUserException to handle.
	 * @return A ResponseEntity with a bad request status and the exception message.
	 */
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	/**
	 * Handles MedicationNotFoundException and returns a ResponseEntity with a bad request status
	 * and the exception message.
	 * 
	 * @param ex The MedicationNotFoundException to handle.
	 * @return A ResponseEntity with a bad request status and the exception message.
	 */
	@ExceptionHandler(MedicationNotFoundException.class)
	public ResponseEntity<String> hanldeMedicationNotFoundException(MedicationNotFoundException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	/**
	 * Handles DuplicateMedicationException and returns a ResponseEntity with a bad request status
	 * and the exception message.
	 * 
	 * @param ex The DuplicateMedicationException to handle.
	 * @return A ResponseEntity with a bad request status and the exception message.
	 */
	@ExceptionHandler(DuplicateMedicationException.class)
	public ResponseEntity<String> handleDuplicateMedicationException(DuplicateMedicationException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
