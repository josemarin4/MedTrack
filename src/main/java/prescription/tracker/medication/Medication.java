package prescription.tracker.medication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;
import prescription.tracker.user.User;

/**
 * This class represents a Medication in the MedTrack application.
 * It contains medication-related data and methods for managing medications.
 * 
 * @author josemarin
 */
@Data
@Entity
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long medId;
	private String  name;
	private double dosage;
	private int quantity;
	private int refills;
	private int timesPerDay;
	private LocalDate lastRefilled;
	private int reminderDays;
	
	@Setter(AccessLevel.NONE)
	private LocalDate reminderDate;
	
	private static final int DEFAULT_QUANTITY = 0;
	
	//Join column annotation specifies the foreign key column (user_id) 
	//in the medication table that references the user table.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public Medication() {
		this.lastRefilled = LocalDate.now();
	}
	
	public Medication(Long medId, String name, double dosage, int quantity, int refills, int timesPerDay,
			LocalDate lastRefilled, int reminderDays, User user) {
		this.medId = medId;
		setTimesPerDay(timesPerDay);
		setName(name);
		setDosage(dosage);
		setQuantity(quantity);
		setRefills(refills);
		setLastRefilled(lastRefilled);
		setReminderDays(reminderDays);
		this.user = user;
	}
	
	
	public void setName(String name) {
		
		if(name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid medication name");
		}
		
		this.name = name;
	}
	public void setTimesPerDay(int timesPerDay) {
		
		if(timesPerDay > 0) {
			this.timesPerDay = timesPerDay;
			updateReminderDate();
			
		}
	}
	
	public void setDosage(double dosage) {
		
		this.dosage = dosage < 0 ? DEFAULT_QUANTITY : dosage;
	}
	
	public void setRefills(int refills) {
		
		this.refills = refills < 0 ? DEFAULT_QUANTITY : refills;
	}
	
	public void setQuantity(int quantity) {
		
		this.quantity = quantity < 0 ? DEFAULT_QUANTITY : quantity;
		updateReminderDate();
	}
	public void setReminderDays(int reminderDays) {
		
		if(reminderDays <= 0) {
			throw new IllegalArgumentException("Reminder days should be a positive number");
		}
		
		this.reminderDays = reminderDays;
		updateReminderDate();
		
	}
	
	public void setLastRefilled(LocalDate lastRefilled) {
		
		if(lastRefilled == null) {
			throw new IllegalStateException("Not a valid date");
		}
		
		this.lastRefilled = lastRefilled;
	}
	
	private void updateReminderDate() {
		if(lastRefilled != null) {
		long amountToRemind = timesPerDay * reminderDays;
		
		if(amountToRemind > quantity) {
			throw new IllegalStateException("Not enough medication to set reminder duration");
		}
		
		if(timesPerDay == 0) {
			throw new IllegalStateException("Times per day has to be positive");
		}
		long pillsLeft = quantity - amountToRemind;
		long days = pillsLeft / timesPerDay;
		
		reminderDate = ChronoUnit.DAYS.addTo(lastRefilled, days);
		}
		
	}


}
