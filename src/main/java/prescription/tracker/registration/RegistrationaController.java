package prescription.tracker.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prescription.tracker.user.User;

@RestController
@RequestMapping("/api/register")
public class RegistrationaController {
	
	private RegistrationService registrationService;
	
	
	public RegistrationaController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	@PostMapping()
	public ResponseEntity<User> register(@RequestBody User user){
		return ResponseEntity.ok(registrationService.register(user));
		
	}
	
	@GetMapping("/confirm")
	public ResponseEntity<User> confirm(@RequestParam("token") String confirmationToken){
		
		return ResponseEntity.ok(registrationService.confirm(confirmationToken));
		
	}

}
