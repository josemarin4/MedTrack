package prescripcion.tracker.test.user;

import prescription.tracker.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	@Test
	public void testDefaultConstructor() {
		User user = new User();
		
		assertNull(user.getEmail());
		assertNull(user.getPassword());
		assertNull(user.getConfirmationToken());
		assertNull(user.getConfirmationTokenExpiration());
		assertNull(user.getIsEnabled());
		assertNull(user.getMedications());

	}

}
