package prescription.tracker.exception;

public class UserNotVerifiedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserNotVerifiedException(String msg) {
		super(msg);
	}
	
	public UserNotVerifiedException() {
		super();
	}

}
