package prescription.tracker.user;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prescription.tracker.exception.DuplicateUserException;
import prescription.tracker.exception.UserNotFoundException;
import prescription.tracker.exception.UserNotVerifiedException;
import prescription.tracker.medication.MedicationService;
/**
 * Service class responsible for managing user data in the MedTrack application.
 * It provides methods for retrieving, registering, removing, and updating user information.
 * @author josemarin
 */

@Service
public class UserService {
	
	private UserRepository userRepo;
	private MedicationService medicationService;
	private PasswordEncoder passEncoder;
	
	public UserService(UserRepository userRepo, MedicationService medicationService, PasswordEncoder passEncoder) {
		this.userRepo = userRepo;
		this.medicationService = medicationService;
		this.passEncoder = passEncoder;
	}
	
	/**
	 * Retrieves a user by their user ID.
	 * 
	 * @param userId The ID of the user to retrieve.
	 * @return The retrieved user.
	 * @throws UserNotFoundException if the user does not exists.
	 * @throws UserNotVerifiedException if the user is not verified.
	 */
	public User getUser(Long userId) {
		User user = findEnabledUserById(userId);
		
		return user;
	}
	
	/**
	 * Removes a user by their user ID.
	 * 
	 * @param userId The ID of the user to remove.
	 * @return The removed user.
	 * @throws UserNotFoundException if the user does not exists.
	 * @throws UserNotVerifiedException if the user is not verified.
	 */
	@Transactional
	public User removeUser(Long userId) {
		User user = findEnabledUserById(userId);
		
		// Ensure to delete all medications associated with specified user ID.
		medicationService.deleteUserMedications(userId);
		
		userRepo.deleteById(userId);
		return user;
	}
	
	/**
	 * Updates user information. Updates password only if it's different
	 * from the stored one.
	 * 
	 * @param user The updated user information.
	 * @return The updated user.
	 * @throws UserNotFoundException if the user does not exists.
	 * @throws DuplicateUserException if email already exists in DB.
	 * @throws UserNotVerifiedException if the user is not verified.
	 */
	@Transactional
	public User updateUser(User user) {
		User userToUpdate = findEnabledUserById(user.getUserId());
		
		if(userRepo.findUserByEmail(user.getEmail()).isPresent()){
			throw new DuplicateUserException("Email: " + user.getEmail() + " is already in use.");
		}
		userToUpdate.setEmail(user.getEmail());
		
		// If old password does not match new one; encode new one and update it.
		if(!passEncoder.matches(user.getPassword(), userToUpdate.getPassword())) {
			String encodedPassword = passEncoder.encode(user.getPassword());
			userToUpdate.setPassword(encodedPassword);
		}

		userToUpdate.setMedications(user.getMedications());
		
		userRepo.save(userToUpdate);
		return userToUpdate;
	}
	
	/**
	 * Retrieves a user by their user ID.
	 * 
	 * @param userId The ID of the user to retrieve.
	 * @return The retrieved user.
	 * @throws UserNotFoundException if the user does not exists.
	 * @throws UserNotVerifiedException if the user is not verified.
	 */
	private User findEnabledUserById(Long userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> {
			return new UserNotFoundException("User with ID: " + userId + " not found.");
		});
		
		if(!user.isEnabled()) {
			throw new UserNotVerifiedException("Please confirm your account using the confirmation email sent to: " + user.getEmail() + ".");
		}
		
		return user;
	}
}
