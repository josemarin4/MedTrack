package prescripcion.tracker.test.medication;
import prescription.tracker.medication.Medication;
import prescription.tracker.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class MedicationTest {

	private Medication testMedication;
	
	@BeforeEach
	public void setUp() {
		this.testMedication = new Medication();
	}
	
	@Test
	public void shouldCreateMedicationWithLastRefilledNow() {
		Medication med = new Medication();
		
		assertEquals(LocalDate.now(), med.getLastRefilled());
	}
	
	@Test
	public void shouldCreateMedicationWithParameters() {
		User user = new User(2L, "email", "password", true, new ArrayList<>());
		Medication med = new Medication(1L, "name", 2.0, 30, 4, 2, LocalDate.now(), 6,
			user);
		
		
		assertEquals(1L, med.getMedId());
		assertEquals("name", med.getName());
		assertEquals(2.0, med.getDosage());
		assertEquals(30, med.getQuantity());
		assertEquals(4, med.getRefills());
		assertEquals(2, med.getTimesPerDay());
		assertEquals(LocalDate.now(), med.getLastRefilled());
		assertEquals(6, med.getReminderDays());
		assertEquals(user, med.getUser());
		
				
	}
	
	@Test
	public void shouldSetValidName() {
		testMedication.setName("name");
		
		assertEquals("name", testMedication.getName());
	}
	
	@Test
	public void shouldFailSetNullName() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setName(null));
	}
	
	@Test
	public void shouldFailSetEmptyName() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setName(""));
	}
	
	@Test
	public void shoulSetValidDosage() {
		
		testMedication.setDosage(4.55);
		
		assertEquals(4.55, testMedication.getDosage());
	}
	
	
}
