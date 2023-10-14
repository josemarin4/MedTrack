package prescription.tracker.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import prescription.tracker.exception.MedicationNotFoundException;
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
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	private String email;
	private String password;
	private String confirmationToken;
	private LocalDateTime confirmationTokenExpiration;
	
	/**
	 * Indicates whether the user's account is enabled.
	 * An enabled account allows the user to log in and use the application.
	 */
	private Boolean isEnabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Medication> medications;
	
	@Transient
	private static final Integer MIN_PASSWORD_LENGTH = 8;
	
	@Transient
	private static final Integer MAX_PASSWORD_LENGTH = 64;
	
	@Transient
	private static final Integer MAX_EMAIL_LENGTH = 320;
	
	/**
	 * Default constructor for creating User instances.
	 */
	public User() {
		medications = new ArrayList<>();
		isEnabled = false;
	}
	
	/**
	 * Parameterized constructor for creating User instances with initial data.
	 * 
	 * @param userId The unique identifier for the user.
	 * @param email The user's email address.
	 * @param password The user's password
	 * @param isEnabled Indicates whether the user's account is enabled.
	 * @param medications A list of medications associated with the user.
	 */
	public User(long userId, String email, String password, Boolean isEnabled,
			List<Medication> medications) {
		super();
		this.userId = userId;
		setEmail(email);
		setPassword(password);
		
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
		
		if(email == null || email.length() == 0 || email.length() > MAX_EMAIL_LENGTH) {
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
		
		if(password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
			throw new IllegalArgumentException("Password: " + password + " is not valid");
		}
		
		this.password = password; 
	}
	
	public void addMedication(Medication medication) {
		
		if(medication == null) {
			throw new IllegalArgumentException("Trying to add a null medication");
		}
		
		if(medications.contains(medication)) {
			throw new IllegalArgumentException("Trying to add duplicate medication");
		}
		
		medications.add(medication);
	}
	
	public void removeMedication(Medication medication) {
		if(medication == null || !medications.contains(medication)) {
			throw new MedicationNotFoundException("Medication " + medication + " not found");
		}
		
		medications.remove(medication);
	}
	
}
