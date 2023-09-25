package prescription.tracker.exception;

public class DuplicateMedicationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DuplicateMedicationException(String msg) {
		super(msg);
	}
	
	public DuplicateMedicationException() {
		super();
	}

}
