package prescription.tracker.medication;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prescription.tracker.exception.DuplicateMedicationException;
import prescription.tracker.exception.MedicationNotFoundException;

/**
 * Service class responsible for managing medication data in the MedTrack application.
 * It provides methods to add, retrieve, delete, and update medication information.
 *
 * @author josemarin
 */
@Service
public class MedicationService {
	
	private MedicationRepository medicationRepo;
	
	
	public MedicationService(MedicationRepository medicationRepo) {
		this.medicationRepo = medicationRepo;
	}
	
	/**
	 * Adds a new medication to the system.
	 * @param medication The medication to be added.
	 * @throws DuplicateMedicationException if a medication with the same ID already exists.
	 */
	@Transactional
	public void addMedication(Medication medication) {
		
		medicationRepo.findById(medication.getMedId()).orElseThrow(() -> 
			new DuplicateMedicationException("Medication with ID: " + medication.getMedId() + " already exists.")
		);
		
		medicationRepo.save(medication);
	}

	/**
	 * Retrieves medication details by its unique ID.
	 * 
	 * @param medId The unique ID of the medication to retrieve.
	 * @return The retrieved medication.
	 * @throws MedicationNotFoundException if the medication with the specified ID is not found.
	 */
	public Medication getMedication(Long medId) {
		// Checks if the medication exists and throws a medication not found exception if it doesn't.
		return medicationRepo.findById(medId).orElseThrow(() -> 
			new MedicationNotFoundException("Medication with ID: " + medId + " not found.")
		);
	}
	
	/**
	 * Retrieves a list of medications associated with a specific user by their user ID.
	 * 
	 * @param userId The unique identifier of the user whose medications are to be retrieved.
	 * @return A list of medications associated with the specified user.
	 * @throws MedicationNotFoundException If no medications are found for the given user ID.
	 */
	public List<Medication> getUserMedication(Long userId){
		return medicationRepo.findAllByUserId(userId).orElseThrow(()-> 
			new MedicationNotFoundException("Medication with user ID: " + userId + " not found.")
			);
	}

	/**
	 * Deletes a medication by its ID.
	 * 
	 * @param medId The unique ID of the medication to delete.
	 * @return The deleted medication.
	 * @throws MedicationNotFoundException if the medication with the specified ID is not found.
	 */
	@Transactional
	public Medication deleteMedication(Long medId) {
		Medication med = medicationRepo.findById(medId).orElseThrow(() -> 
			new MedicationNotFoundException("Medication with ID: " + medId + " not found.")
		);
		
		medicationRepo.deleteById(medId);
		return med;
	}

	/**
	 * Updates an existing medication.
	 * 
	 * @param medication The updated medication details.
	 * @return The updated medication.
	 * @throws MedicationNotFoundException if the medication with the specified ID is not found.
	 */
	@Transactional
	public Medication updateMedication(Medication medication) {
		Medication med = medicationRepo.findById(medication.getMedId()).orElseThrow(() -> 
			new MedicationNotFoundException("Medication with ID: " + medication.getMedId() + " not found.")
		);
		
		// Update medication properties
		med.setName(medication.getName());
		med.setRefills(medication.getRefills());
		med.setDosage(medication.getDosage());
		med.setLastRefilled(medication.getLastRefilled());
		med.setTimesPerDay(medication.getTimesPerDay());
		med.setQuantity(medication.getQuantity());
		med.setUser(medication.getUser());
		medicationRepo.save(med);
		
		return med;
	}
	
	@Transactional
	public void deleteUserMedications(Long userId) {
		
		medicationRepo.deleteAllByUserId(userId);
	}
}
