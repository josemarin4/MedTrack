package prescription.tracker.test.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.Optional;
import prescription.tracker.exception.UserNotFoundException;
import prescription.tracker.exception.UserNotVerifiedException;
import prescription.tracker.medication.MedicationService;
import prescription.tracker.user.User;
import prescription.tracker.user.UserRepository;
import prescription.tracker.user.UserService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MedicationService medicationService;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;


	private User testUser;

	@BeforeEach
	public void setUp() {

		testUser = new User(2L, "email@gmail.com", "passwordpassword", 
				true, Collections.emptyList());
		
		// encoced password
		given(passwordEncoder.encode("passwordpassword"))
		.willReturn("$2y$10$ASANMXXDgld7EkZrlePg..LVp6I/VqFI3KTkOESnb6YK4gQwqWv0C");
		
		testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));

	}

	@Test
	public void shouldGetValidUser() {

		//given
		given(userRepository.findById(2L))
		.willReturn(Optional.of(testUser));

		//when
		User result = userService.getUser(2L);

		// then
		assertEquals(result, testUser);

		verify(userRepository).findById(2L);

	}

	@Test
	public void shouldThrowExceptionWhenUserNotFound() {
		
		given(userRepository.findById(1L)).willReturn(Optional.empty());
		
		 assertThrows(UserNotFoundException.class, () ->
					userService.getUser(1L));
		 
		 verify(userRepository).findById(1L);
	}
	
	@Test
	public void shouldThrowExceptionGetNotVerifiedUser() {
		User user = new User(1L, "email@gmail.com", "password", false, Collections.emptyList());
		
		given(userRepository.findById(1L))
				.willReturn(Optional.of(user));
		
		
		assertThrows(UserNotVerifiedException.class, () ->
				userService.getUser(1L));
		
		verify(userRepository).findById(1L);

	}
	
	@Test
	public void shouldRemoveValidUser() {
		
		given(userRepository.findById(2L)).willReturn(Optional.of(testUser));
		
		User removedUser = userService.removeUser(2L);
		
		assertEquals(testUser, removedUser);
		
		verify(userRepository).findById(2L);
		verify(userRepository).deleteById(2L);
		verify(medicationService).deleteUserMedications(2L);

	}
	
	@Test
	public void shouldUpdateValidUserButNotPassword() {
		
		User update = new User(2L, "newEmail@gmail.com", "passwordpassword", true, Collections.emptyList());
		String hashedPassword = "$2y$10$ASANMXXDgld7EkZrlePg..LVp6I/VqFI3KTkOESnb6YK4gQwqWv0C";
		given(userRepository.findById(2L)).willReturn(Optional.of(testUser));
		given(passwordEncoder.matches("passwordpassword", hashedPassword))
			.willReturn(true);
		
		User updatedUser = userService.updateUser(update);
		
		assertEquals("newEmail@gmail.com", updatedUser.getEmail());
		assertEquals(hashedPassword, updatedUser.getPassword());
		assertTrue(updatedUser.getMedications().size() == 0);
		
		verify(userRepository).findById(2L);
		verify(userRepository).save(any(User.class));
		verify(passwordEncoder).matches("passwordpassword", hashedPassword);
		verify(passwordEncoder).encode(anyString());
		
		
	}


}
