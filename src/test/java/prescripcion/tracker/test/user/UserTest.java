package prescripcion.tracker.test.user;

import prescription.tracker.medication.Medication;
import prescription.tracker.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import prescription.tracker.exception.MedicationNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class UserTest {
	
	private User testUser;
	private Medication testMedication;
	
	@BeforeEach
	public void setUp() {
		testUser = new User();
		testMedication = new Medication();
		testMedication.setUser(testUser);
	}

	@Test
	public void shouldCreateEmptyUser() {
		User user = new User();
		
		assertNull(user.getEmail());
		assertNull(user.getPassword());
		assertNull(user.getConfirmationToken());
		assertNull(user.getConfirmationTokenExpiration());
		assertFalse(user.isEnabled());
		assertEquals(0, user.getMedications().size());

	}
	
	@Test
	public void shouldCreateUserWithInitialValues() {
		User user = new User(1L, "user@gmail.com", "password",false, List.of());
		
		assertEquals(1L, user.getUserId());
		assertEquals("user@gmail.com", user.getEmail());
		assertEquals("password", user.getPassword());
		assertFalse(user.isEnabled());
		assertEquals(0, user.getMedications().size());
	}
	
	@Test
	public void shouldSetEmail() {
		testUser.setEmail("email@gmail.com");
		
		assertEquals("email@gmail.com", testUser.getEmail());
	}
	
	@Test 
	public void shouldFailSetEmptyEmail() {
		
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setEmail(""));
	}
	
	@Test
	public void shouldFailSetNullEmail() {
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setEmail(null));
		
	}
	
	@Test
	public void shoulFailSetTooLongEmail() {
		assertThrows(IllegalArgumentException.class, () ->
				testUser.setEmail(Arrays.toString(new int[321])));
	}
	
	@Test
	public void shouldSetPassword() {
		testUser.setPassword("password");
		
		assertEquals("password", testUser.getPassword());
	}
	
	@Test
	public void shouldFailSetEmptyPassword() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testUser.setPassword(""));
		
	}
	
	@Test
	public void shouldFailSetNullPassword() {
		
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setPassword(null));
	}
	
	@Test
	public void shouldFailSetTooLongPassword() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testUser.setPassword(Arrays.toString(new int[65])));
		
	}
	
	@Test
	public void shouldAddMedicationToUser() {
		testUser.setMedications(new ArrayList<>());
		testUser.addMedication(testMedication);
		
		assertEquals(1, testUser.getMedications().size());
		assertEquals(testUser, testMedication.getUser());
	}
	
	@Test
	public void shouldFailAddNullMedication() {
		testMedication = null;
		assertThrows(IllegalArgumentException.class, () ->
				testUser.addMedication(testMedication));
	}
	
	@Test
	public void shouldFailAddDuplicateMedication() {
		testUser.addMedication(testMedication);
		
		assertThrows(IllegalArgumentException.class, () ->
				testUser.addMedication(testMedication));
	}
	
	@Test
	public void shouldRemoveMedicationFromUser() {
		testUser.setMedications(new ArrayList<>());
		testUser.addMedication(testMedication);
		
		testUser.removeMedication(testMedication);
		
		assertTrue(testUser.getMedications().isEmpty());
		
	}
	
	@Test
	public void shouldFailRemoveNullMedication() {
		testMedication = null;
		
		assertThrows(MedicationNotFoundException.class, () ->
				testUser.removeMedication(testMedication));
	}
	
	@Test
	public void shouldFailNotFoundMedication() {
		Medication med = new Medication();
		
		assertThrows(MedicationNotFoundException.class, () ->
				testUser.removeMedication(med));
	}
	

}
