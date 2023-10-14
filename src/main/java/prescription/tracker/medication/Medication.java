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
import prescription.tracker.user.User;

/**
 * This class represents a Medication in the MedTrack application.
 * It contains medication-related data and methods for managing medications.
 * 
 * @author josemarin
 */
@Data
@NoArgsConstructor
@Entity
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medId;
	private String  name;
	private double dosage;
	private int quantity;
	private int refills;
	private int timesPerDay;
	private LocalDate lastRefilled;
	private int reminderDays;
	private LocalDate reminderDate;
	
	private static final int DEFAULT_REMINDER_DAYS = 7;
	private static final int DEFAULT_QUANTITY = 0;
	
	//Join column annotation specifies the foreign key column (user_id) 
	//in the medication table that references the user table.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public Medication(Long medId, String name, Double dosage, Integer quantity, Integer refills, Integer timesPerDay,
			LocalDate lastRefilled, int reminderDays, User user) {
		this.medId = medId;
		this.name = name;
		setDosage(dosage);
		this.quantity = quantity;
		setRefills(refills);
		setTimesPerDay(timesPerDay);
		this.lastRefilled = lastRefilled;
		setReminderDays(reminderDays);
		calculateReminderDate();
		this.user = user;
	}
	
	public void setTimesPerDay(Integer timesPerDay) {
		if(timesPerDay == null || timesPerDay < 0) {
			this.timesPerDay = DEFAULT_QUANTITY;
		}
		else {
			this.timesPerDay = timesPerDay;
		}
	}
	
	public void setDosage(Double dosage) {
		
		if(dosage == null || dosage < 0) {
			this.dosage = (double) DEFAULT_QUANTITY;
		}
		else {
			this.dosage = dosage;
		}
	}
	
	public void setRefills(Integer refills) {
		if(refills == null || refills < 0) {
			this.refills = DEFAULT_QUANTITY;
		}
		else {
			this.refills = refills;
		}
	}
	
	public void setReminderDays(int reminderDays) {
		
		if(reminderDays < 0) {
			this.reminderDays = DEFAULT_REMINDER_DAYS;
		}
		else {
			this.reminderDays = reminderDays;
		}
	}
	
	private void calculateReminderDate() {
		
		long ammonutToRemind = timesPerDay * reminderDays;
		long pillsLeft = quantity - ammonutToRemind;
		long days = pillsLeft / timesPerDay;
		reminderDate = ChronoUnit.DAYS.addTo(lastRefilled, days);
		
	}


}
