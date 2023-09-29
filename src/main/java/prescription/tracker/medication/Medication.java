package prescription.tracker.medication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Medication in the MedTrack application.
 * It contains medication-related data and methods for managing medications.
 * 
 * @author josemarin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medId;
	private String  name;
	private Double dosage;
	private Integer quantity;
	private Integer refills;
	private Integer timesPerDay;
	private LocalDate lastRefilled;
	
	//Join column annotation specifies the foreign key column (user_id) 
	//in the medication table that references the user table.
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Long userId;
	
	
	public void setTimesPerDay(Integer timesPerDay) {
		//ensures remaining quantity is non-negative
		this.timesPerDay = timesPerDay < 0? 0 : timesPerDay;
	}
	
	public void setDosage(Double dosage) {
		
		//ensures dosage is non-negative
		this.dosage = dosage < 0? 0 : dosage;
	}
	
	public void setRefills(Integer refills) {
		//ensures refills is non-negative
		this.refills = refills < 0? 0 : refills;
	}
	
	public long quantityLeft() {
		long daysBetween = ChronoUnit.DAYS.between(lastRefilled, LocalDate.now());
		 return this.quantity - (timesPerDay * daysBetween);
		
	}
	

}
