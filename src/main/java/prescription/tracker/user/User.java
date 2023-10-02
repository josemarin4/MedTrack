package prescription.tracker.user;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
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
}
