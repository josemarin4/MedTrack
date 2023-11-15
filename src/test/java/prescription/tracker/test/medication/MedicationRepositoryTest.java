package prescription.tracker.test.medication;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import prescription.tracker.user.User;
import prescription.tracker.medication.Medication;
import prescription.tracker.medication.MedicationRepository;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MedicationRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private MedicationRepository medicationRepository;
	
	
	@Test
	public void shouldReturnMedicationsFromUserId() {
		
		Medication med1 = new Medication();
		med1.setName("Med1");
		Medication med2 = new Medication();
		med2.setName("Med2");
		
		User user = new User();
		user.setEmail("email@email.com");
		user.setPassword("password");
		user.setEnabled(true);
		med1.setUser(user);
		med2.setUser(user);
		
		entityManager.persistAndFlush(user);
		entityManager.clear();
		
		List<Medication> medications = medicationRepository.findAllByUserId(user.getUserId()).orElse(null);
		
		assertNotNull(medications);
		
		assertEquals(user.getMedications(), medications);
				
	}
	
	@Test
	public void shouldDeleteAllMedicationsFromUserId() {
		
		Medication med1 = new Medication();
		med1.setName("Med1");
		Medication med2 = new Medication();
		med2.setName("Med2");
		
		User user = new User();
		user.setEmail("email@email.com");
		user.setPassword("password");
		user.setEnabled(true);
		med1.setUser(user);
		med2.setUser(user);
		
		entityManager.persistAndFlush(user);
		entityManager.clear();
		
		medicationRepository.deleteAllByUserId(user.getUserId());
		
		List<Medication> medications = medicationRepository.findAllByUserId(user.getUserId()).orElse(null);
		
		assertNotNull(medications);
		
		assertEquals(Collections.EMPTY_LIST, medications);
		
	}

}
