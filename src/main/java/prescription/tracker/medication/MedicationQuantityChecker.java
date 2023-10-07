package prescription.tracker.medication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class MedicationQuantityChecker {
	
	private MedicationService medicationService;
	
	
	
	
	public long quantityLef(Medication medication) {
		long daysBetween = ChronoUnit.DAYS.between(medication.getLastRefilled(), LocalDate.now());
		 return medication.getQuantity() - (medication.getTimesPerDay() * daysBetween);

	}

}
