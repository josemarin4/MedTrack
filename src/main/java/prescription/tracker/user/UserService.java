package prescription.tracker.user;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prescription.tracker.exception.UserNotFoundException;
import prescription.tracker.exception.UserNotVerifiedException;
/**
 * Service class responsible for managing user data in the MedTrack application.
 * It provides methods for retrieving, registering, removing, and updating user information.
 * @author josemarin
 */

@Service
public class UserService {
	
	private UserRepository userRepo;
	private PasswordEncoder passEncoder;
	
	public UserService(UserRepository userRepo, PasswordEncoder passEncoder) {
		this.userRepo = userRepo;
		this.passEncoder = passEncoder;
	}
	
	/**
	 * Retrieves a user by their user ID.
	 * @param userId The ID of the user to retrieve.
	 * @return The user if found, or throws a UserNotFoundException if not found.
	 */
	public User getUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> {
			return new UserNotFoundException("User with ID: " + userId + " not found.");
		});
		return user;
	}
	
	/**
	 * Removes a user by their user ID.
	 * @param userId The ID of the user to remove.
	 * @return The removed user if found, or throws a UserNotFoundException if not found.
	 */
	public User removeUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> {
			return new UserNotFoundException("User with ID: " + userId + " not found.");
		});
		
		userRepo.deleteById(userId);
		return user;
	}
	
	/**
	 * Updates user information.
	 * @param user The updated user information.
	 * @return The updated user if found, or throws a UserNotFoundException if not found.
	 */
	public User updateUser(User user) {
		User userToUpdate = userRepo.findById(user.getUserId()).orElseThrow(() -> {
			return new UserNotFoundException("User with ID: " + user.getUserId() + " not found.");
		});
		
		userToUpdate.setUserId(user.getUserId());
		userToUpdate.setEmail(user.getEmail());
		
		String encodedPassword = passEncoder.encode(user.getPassword());
		userToUpdate.setPassword(encodedPassword);
		userToUpdate.setMedications(user.getMedications());
		
		userRepo.save(userToUpdate);
		return userToUpdate;
	}
	
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
