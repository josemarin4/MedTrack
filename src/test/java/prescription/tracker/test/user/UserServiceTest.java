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

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MedicationService medicationService;

	@InjectMocks
	private UserService userService;


	private User testUser;

	@BeforeEach
	public void setUp() {

		testUser = new User(2L, "email@gmail.com", "password", true, Collections.emptyList());

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
	public void shouldThrowExceptionGetInexistentUser() {
		
		 assertThrows(UserNotFoundException.class, () ->
					userService.getUser(1L));
	}
	
	@Test
	public void shouldThrowExceptionGetNotVerifiedUser() {
		User user = new User(1L, "email@gmail.com", "password", false, Collections.emptyList());
		
		given(userRepository.findById(1L))
				.willReturn(Optional.of(user));
		
		
		assertThrows(UserNotVerifiedException.class, () ->
				userService.getUser(1L));
	}



}
