package prescription.tracker.medication;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class represents a controller for medications in the MedTrack application.
 * It contains endpoints to perform various operations on medications, such as adding,
 * retrieving, updating, and deleting medications.
 * 
 * @author josemarin
 */
@RequestMapping("/api/medication")
public class MedicationController {
	
	private MedicationService medService;
	
	
	public MedicationController(MedicationService medService) {
		this.medService = medService;
	}
	
	/**
	 * Endpoint to add a new medication.
	 * 
	 * @param medication The medication to be added, provided in the request body.
	 * @return A ResponseEntity with the added medication and a status code (201 - Created).
	 */
	@PostMapping("/add")
	public ResponseEntity<Medication> addMedication(@RequestBody Medication medication){
		medService.addMedication(medication);
		return ResponseEntity.status(HttpStatus.CREATED).body(medication);
	}
	
	/**
	 * Endpoint to retrieve medication details by its unique ID.
	 * 
	 * @param medId The unique ID of the medication to retrieve.
	 * @return A ResponseEntity with the retrieved medication and a status code (200 - OK).
	 */
	@GetMapping("/{medId}")
	public ResponseEntity<Medication> getMedication(Long medId){
		return ResponseEntity.ok(medService.getMedication(medId));
	}
	
	/**
	 * Retrieves a list of medications associated with a specific user by their user ID.
	 * 
	 * @param userId The unique identifier of the user whose medications are to be retrieved.
	 * @return A ResponseEntity containing the list of medications associated with the specified user.
	 */
	@GetMapping("/get/{userId}")
	public ResponseEntity<List<Medication>> getUserMedications(@PathVariable Long userId){
		return ResponseEntity.ok(medService.getUserMedication(userId));
	}
	
	/**
	 * Endpoint to delete a medication by its ID.
	 * 
	 * @param medId The unique ID of the medication to delete.
	 * @return A ResponseEntity with the deleted medication and a status code (200 - OK).
	 */
	@DeleteMapping("/delete/{medId}")
	public ResponseEntity<Medication> deleteMedication(Long medId){
		return ResponseEntity.ok(medService.deleteMedication(medId));
	}
	
	/**
	 * Endpoint to update an existing medication.
	 * 
	 * @param medication The updated medication details provided in the request body.
	 * @return A ResponseEntity with the updated medication and a status code (200 - OK).
	 */
	@PutMapping("/update")
	public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication){
		return ResponseEntity.ok(medService.updateMedication(medication));
	}
}
