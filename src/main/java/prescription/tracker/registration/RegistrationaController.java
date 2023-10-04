package prescription.tracker.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import prescription.tracker.user.User;

@RequestMapping("/api/register")
public class RegistrationaController {
	
	@PostMapping()
	public ResponseEntity<String> register(@RequestBody User user){
		
		return ResponseEntity.ok("Reigstration Successfull");
		
	}

}
