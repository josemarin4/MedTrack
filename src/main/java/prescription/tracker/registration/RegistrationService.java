package prescription.tracker.registration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import prescription.tracker.exception.DuplicateUserException;
import prescription.tracker.user.User;
import prescription.tracker.util.Token;
import prescription.tracker.util.TokenGenerator;

@Service
public class RegistrationService {
	
	private RegistrationRepository registrationRepository;
	private PasswordEncoder passwordEncoder;
	
	public RegistrationService(RegistrationRepository registrationRepository, 
			PasswordEncoder passwordEncoder) {
		this.registrationRepository = registrationRepository;
		this.passwordEncoder = passwordEncoder;
		
	}
	/**
	 * Registers a new user.
	 * @param user The user to register.
	 * @throws DuplicateUserException if the email is already taken.
	 */
	public void register(User user) {
		registrationRepository.findUserByEmail(user.getEmail()).orElseThrow(() -> {
			return new DuplicateUserException("Username " + user.getEmail() + " already taken.");
		});
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		Token token = TokenGenerator.generateToken();
		user.setConfirmationToken(token.getValue());
		user.setConfirmationTokenExpiration(token.getExpirationDateTime());
		
		registrationRepository.save(user);
	}

}
