package prescription.tracker.user;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import prescription.tracker.medication.Medication;

/**
 * This class represents a User in the MedTrack application.
 * It contains user-related data and establishes a one-to-many relationship
 * with medications, allowing a user to have multiple medications associated with them.
 * 
 * The cascade operations (e.g., save, delete) from the user entity to associated medication entities
 * are configured, and orphan removal is enabled to remove orphaned medications when they are no longer associated with a user.
 * @author josemarin
 */
@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	private String email;
	private String password;
	private String confirmationToken;
	private LocalDateTime confirmationTokenExpiration;
	private Boolean isEnabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Medication> medications;
	
	private final Integer MIN_PASSWORD_LENGTH = 8;
	private final Integer MAX_PASSWORD_LENGTH = 20;
	/**
	 * Default constructor for creating User instances.
	 */
	public User() {}
	
	/**
	 * Parameterized constructor for creating User instances with initial data.
	 * 
	 * @param userId The unique identifier for the user.
	 * @param email The user's email address.
	 * @param password The user's password (securely hashed).
	 * @param confirmationToken The confirmation token used during email confirmation.
	 * @param isEnabled Indicates whether the user's account is enabled.
	 * @param medications A list of medications associated with the user.
	 */
	public User(long userId, String email, String password, String confirmationToken, Boolean isEnabled,
			List<Medication> medications) {
		super();
		this.userId = userId;
		setEmail(email);
		setPassword(password);
		
		/**
		 * The confirmation token used during the email confirmation process.
		 * It ensures the validity of the user's email address.
		 */
		this.confirmationToken = confirmationToken;
		
		/**
		 * Indicates whether the user's account is enabled.
		 * An enabled account allows the user to log in and use the application.
		 */
		this.isEnabled = isEnabled;
		this.medications = medications;
	}
	
	/**
	 * Sets the user's email address after validation.
	 * 
	 * @param email The email address to set.
	 * @throws IllegalArgumentException If the provided email is null or empty.
	 */
	public void setEmail(String email) {
		
		if(email == null || email.length() == 0) {
			throw new IllegalArgumentException("Email: " + email + " is not valid");
		}
		
		this.email = email;
		
	}
	
	
	/**
	 * Sets the user's password after validation.
	 * 
	 * @param password The password to set.
	 * @throws IllegalArgumentException If the provided password is null or does not meet length criteria.
	 */
	public void setPassword(String password) {
		
		if(password == null || password.length() < MIN_PASSWORD_LENGTH
				|| password.length() > MAX_PASSWORD_LENGTH) {
			throw new IllegalArgumentException("Password: " + password + " is not valid");
		}
		
		this.password = password; 
	}
}
