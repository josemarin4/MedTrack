package prescripcion.tracker.test.medication;
import prescription.tracker.medication.Medication;
import prescription.tracker.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class MedicationTest {

	private static final Clock FIXED_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());
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
	public void shouldSetValidDosage() {
		
		testMedication.setDosage(4.55);
		
		assertEquals(4.55, testMedication.getDosage());
	}
	
	@Test
	public void shouldFailSetNegativeDosage() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setDosage(-1));
		
	}
	
	@Test
	public void shouldSetValidRefill() {
		
		testMedication.setRefills(5);
		
		assertEquals(5, testMedication.getRefills());
	}
	
	@Test
	public void shouldFailSetNegativeRefills() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setRefills(-10));
		
	}
	
	@Test
	public void shouldSetValidQuantity() {
		
		testMedication.setQuantity(30);
		
		assertEquals(30, testMedication.getQuantity());
	}
	
	@Test
	public void shouldFailSetNegativeQuantity() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setQuantity(-100));
		
	}
	
	@Test
	public void shouldSetValidReminderDays() {
		
		testMedication.setReminderDays(10);
		
		assertEquals(10, testMedication.getReminderDays());
	}
	
	@Test
	public void shouldFailSetNegativeReminderDays() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setReminderDays(-50));
	}
	
	@Test
	public void shouldSetValidLastRefilled() {
		
		testMedication.setLastRefilled(LocalDate.now(FIXED_CLOCK));
		
		assertEquals(LocalDate.now(FIXED_CLOCK), testMedication.getLastRefilled());
		
	}
	
	@Test
	public void shouldFailSetNullLastRefilled() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setLastRefilled(null));

	}
	
	@Test
	public void shouldSetValidTimesPerDay() {
		
		testMedication.setTimesPerDay(3);
		
		assertEquals(3, testMedication.getTimesPerDay());
	}
	
	@Test
	public void shouldFailSettingNegativeimesPerDay() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testMedication.setTimesPerDay(-10));
	}
	
	@Test
	public void shouldUpdateReminderDateCorreclty() {
		
		 testMedication.setTimesPerDay(3);
	     testMedication.setQuantity(30);
	     testMedication.setReminderDays(2);
	     
	     assertEquals(LocalDate.now(FIXED_CLOCK).plusDays(8), testMedication.getReminderDate());
	}
}
