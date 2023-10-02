package prescription.tracker.user;

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
	private String confirmationToke;
	private Boolean enabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Medication> medications;
	
	public User() {}
	
	public User(long userId, String email, String password, String confirmationToke, Boolean enabled,
			List<Medication> medications) {
		super();
		this.userId = userId;
		setEmail(email);
		setPassword(password);
		this.confirmationToke = confirmationToke;
		this.enabled = enabled;
		this.medications = medications;
	}
	
	public void setEmail(String email) {
		
		if(email == null || email.length() == 0) {
			return;
		}
		
		this.email = email;
		
	}
	
	public void setPassword(String password) {
		
		if(password == null || password.length() < 8) {
			return;
		}
		
		this.password = password; 
	}
}
