package prescription.tracker.medication;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * This interface defines the repository for managing Medication entities in the database.
 * It extends JpaRepository to inherit basic CRUD operations.
 * 
 * <p>The additional method {@link #findAllByUserId(Long)} allows retrieving medications by user ID.
 * 
 * @see JpaRepository
 * @see Medication
 * @author josemarin
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
    /**
     * Retrieves a list of medications associated with a specific user by their user ID.
     * 
     * @param userId The unique identifier of the user whose medications are to be retrieved.
     * @return An optional list of medications associated with the specified user.
     */
	@Query("SELECT m FROM Medication m WHERE m.user.userId = :userId")
	Optional<List<Medication>> findAllByUserId(Long userId);
	
	/**
	 * Deletes all medications associated with a specific user by their user ID.
	 * 
	 * @param userId The unique identifier of the user whose medications are to be deleted.
	 */
	@Modifying
	@Query("DELETE FROM Medication m WHERE m.user.userId =:userId")
	void deleteAllByUserId(Long userId);
}
