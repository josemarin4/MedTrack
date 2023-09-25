package prescription.tracker.exception;

public class MedicationNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public MedicationNotFoundException(String msg) {
		super(msg);
	}
	
	public MedicationNotFoundException() {
		super();
	}

}
