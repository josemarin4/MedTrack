package prescription.tracker.test.medication;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prescription.tracker.medication.Medication;
import prescription.tracker.medication.MedicationRepository;
import prescription.tracker.medication.MedicationService;
import prescription.tracker.user.User;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {
	
	@Mock
	private MedicationRepository medicationRepository;
	
	@InjectMocks
	private MedicationService medicationService;
	
	private Medication medication; 
	private User user;
	
	@BeforeEach
	public void setUp() {
		User user = new User(2L, "email@email.com", "password", true, null);
		
		Medication medication = new Medication(1L, "BUP", 3.4, 30, 2, 2, LocalDate.now(), 7, user);
		
		given(medicationRepository.findById(1L)).willReturn(Optional.of(medication));
	}
	@Test
	public void shouldAddMedication() {
		
		
	}

}
