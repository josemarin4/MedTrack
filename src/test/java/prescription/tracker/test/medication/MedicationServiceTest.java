package prescription.tracker.test.medication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prescription.tracker.exception.DuplicateMedicationException;
import prescription.tracker.exception.MedicationNotFoundException;
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
		user = new User(2L, "email@email.com", "password", true, Collections.emptyList());
		
		medication = new Medication(1L, "BUP", 3.4, 30, 2, 2, LocalDate.now(), 7, user);
		
		given(medicationRepository.findById(1L)).willReturn(Optional.of(medication));
	}
	
	@Test
	public void shouldAddMedication() {
		
		given(medicationRepository.save(medication)).willReturn(medication);
		
		medicationService.addMedication(medication);
		
		verify(medicationRepository).findById(1L);
		verify(medicationRepository).save(medication);
		
	}
	
	@Test
	public void shouldFailAddingDuplicateMedication() {
		
		given(medicationRepository.findById(1L)).willReturn(Optional.empty());
		
		Medication duplicateMed = new Medication(1L, "BUP", 3.4, 30, 2, 2, LocalDate.now(), 7, user);
		
		assertThrows(DuplicateMedicationException.class, () ->
				medicationService.addMedication(duplicateMed));
		
		verify(medicationRepository).findById(1L);
		
	}
	
	@Test
	public void shouldGetValidMedication() {
		
		Medication med = medicationService.getMedication(1L);
		
		assertEquals(1L, med.getMedId());
		assertEquals(medication, med);
		
		verify(medicationRepository).findById(1L);
	}
	
	@Test
	public void shouldFailGetNonExistentMedication(){
		
		given(medicationRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(MedicationNotFoundException.class, () ->
				medicationService.getMedication(1L));
		
		verify(medicationRepository).findById(1L);
				
		
	}

}
