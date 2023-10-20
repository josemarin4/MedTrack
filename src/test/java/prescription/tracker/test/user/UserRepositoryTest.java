package prescription.tracker.test.user;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;
import prescription.tracker.user.User;
import prescription.tracker.user.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

		@Autowired
		TestEntityManager entityManager;
		
		@Autowired
		UserRepository userRepository;
		
		
		@Test
		public void shouldReturnEmptyWhenFindByNonExistentEmail() {
			
			Optional<User> user = userRepository.findUserByEmail("email@gmail.com");
			
			assertFalse(user.isPresent());
		}
		
		@Test 
		public void ShoulReturnEmptyWhenFindByEmptyEmail(){
			
			Optional<User> user = userRepository.findUserByEmail("");
			
			assertFalse(user.isPresent());
			
		}
		
		@Test
		public void shouldReturnValidUserWhenFindbyEmail() {
			
			User testUser = new User();
			testUser.setEmail("email@gmail.com");
			
			entityManager.persistAndFlush(testUser);
			
			Optional<User> user = userRepository.findUserByEmail("email@gmail.com");
			
			assertTrue(user.isPresent());
			assertEquals("email@gmail.com", user.get().getEmail());
		}
}
