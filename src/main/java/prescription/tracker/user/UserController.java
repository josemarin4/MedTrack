package prescription.tracker.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		
		return ResponseEntity.ok(userService.getUser(userId));
	}
	
	@DeleteMapping("/remove/{userId}")
	public ResponseEntity<User> removeUser(@PathVariable Long userId){
		
		return ResponseEntity.ok(userService.removeUser(userId));
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		
		return ResponseEntity.ok(userService.updateUser(user));
	}

}
