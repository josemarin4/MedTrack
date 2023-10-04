package prescription.tracker.registration;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import prescription.tracker.user.User;

public interface RegistrationRepository extends JpaRepository<User, Long>{
	
	Optional<User> findUserByEmail(String email);

}
