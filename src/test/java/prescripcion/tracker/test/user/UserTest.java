package prescripcion.tracker.test.user;

import prescription.tracker.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class UserTest {
	
	private User testUser;
	
	@BeforeEach
	public void setUp() {
		testUser = new User();
	}

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
	
	@Test
	public void testParametizedConstructor() {
		User user = new User(1L, "user@gmail.com", "password",false, null);
		
		assertEquals(1L, user.getUserId());
		assertEquals("user@gmail.com", user.getEmail());
		assertEquals("password", user.getPassword());
		assertFalse(user.getIsEnabled());
		assertNull(user.getMedications());
	}
	
	@Test
	public void testSettingValidEmail() {
		testUser.setEmail("email@gmail.com");
		
		assertEquals("email@gmail.com", testUser.getEmail());
	}
	
	@Test 
	public void testSettingEmptyEmail() {
		
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setEmail(""));
	}
	
	@Test
	public void testSettingNullEmail() {
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setEmail(null));
		
	}
	
	@Test
	public void testSettingValidPasssword() {
		testUser.setPassword("password");
		
		assertEquals("password", testUser.getPassword());
	}
	
	@Test
	public void testSettingEmptyPassword() {
		
		assertThrows(IllegalArgumentException.class, () ->
				testUser.setPassword(""));
		
	}
	
	@Test
	public void testSettingNullPassword() {
		
		assertThrows(IllegalArgumentException.class, () -> 
				testUser.setPassword(null));
	}
	

}
