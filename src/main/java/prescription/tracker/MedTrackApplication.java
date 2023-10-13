package prescription.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedTrackApplication.class, args);
	}

}
