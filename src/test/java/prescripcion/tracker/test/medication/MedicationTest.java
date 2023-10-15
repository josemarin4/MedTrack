package prescripcion.tracker.test.medication;
import prescription.tracker.medication.Medication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class MedicationTest {

	@Test
	public void shouldCreateMedicationWithLastRefilledNow() {
		Medication med = new Medication();
		
		assertEquals(LocalDate.now(), med.getLastRefilled());
	}
}
