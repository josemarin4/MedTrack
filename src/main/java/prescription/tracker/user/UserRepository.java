package prescription.tracker.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * This interface defines the repository for managing User entities in the database.
 * It extends JpaRepository to inherit basic CRUD operations.
 * 
 * <p>The additional method {@link #findUserByUsername(String)} allows retrieving a user by their username.
 * 
 * @see JpaRepository
 * @see User
 * @author josemarin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Retrieves a user by their username.
     * 
     * @param username The unique username of the user to be retrieved.
     * @return An optional User object representing the user with the specified username.
     */
    Optional<User> findUserByEmail(String username);
}

