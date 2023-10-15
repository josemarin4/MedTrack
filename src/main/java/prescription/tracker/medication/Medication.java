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

	//Join column annotation specifies the foreign key column (user_id) 
	//in the medication table that references the user table.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	/**
     * Default constructor initializing the 'lastRefilled' attribute to the current date.
     */
	public Medication() {
		this.lastRefilled = LocalDate.now();
	}

	/**
     * Constructor to initialize all attributes of the medication.
     *
     * @param medId         Unique identifier for the medication.
     * @param name          Name of the medication.
     * @param dosage        Dosage of the medication.
     * @param quantity      Total quantity of the medication.
     * @param refills       Number of refills available for the medication.
     * @param timesPerDay   Frequency of medication intake daily.
     * @param lastRefilled  Date when the medication was last refilled.
     * @param reminderDays  Number of days before running out to trigger a reminder.
     * @param user          The user associated with the medication.
     */
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


	/**
     * Sets the medication name after validating it's neither null nor empty.
     *
     * @param name The desired name for the medication.
     * @throws IllegalArgumentException if name is null or empty.
     */
	public void setName(String name) {

		if(name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid medication name");
		}

		this.name = name;
	}

	/**
     * Sets the number of times per day the medication should be taken after validating the value.
     *
     * @param timesPerDay The desired intake frequency for the medication.
     * @throws IllegalArgumentException if timesPerDay is negative.
     */
	public void setTimesPerDay(int timesPerDay) {
		if (timesPerDay < 0) {
			throw new IllegalArgumentException("timesPerDay should be positive");
		}
		this.timesPerDay = timesPerDay;
		updateReminderDate();
	}

	/**
     * Sets the dosage for the medication after validating the dosage value.
     *
     * @param dosage The desired dosage of the medication.
     * @throws IllegalArgumentException if dosage is negative.
     */
	public void setDosage(double dosage) {
		if (dosage < 0) {
			throw new IllegalArgumentException("Dosage should be positive");
		}
		this.dosage = dosage;
	}

	/**
     * Sets the refills count for the medication after validating the refills value.
     *
     * @param refills The desired number of refills for the medication.
     * @throws IllegalArgumentException if refills is negative.
     */
	public void setRefills(int refills) {
		if (refills < 0) {
			throw new IllegalArgumentException("Refills should be positive");
		}
		this.refills = refills;
	}

	/**
     * Sets the quantity of the medication after validating the quantity value.
     *
     * @param quantity The desired quantity for the medication.
     * @throws IllegalArgumentException if quantity is negative.
     */
	public void setQuantity(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity should be positive");
		}
		this.quantity = quantity;
		updateReminderDate();
	}

	  /**
     * Sets the reminder days for the medication after validating the value.
     *
     * @param reminderDays The desired days before running out to trigger a reminder.
     * @throws IllegalArgumentException if reminderDays is negative.
     */
	public void setReminderDays(int reminderDays) {
		if (reminderDays < 0) {
			throw new IllegalArgumentException("Reminder days should be positive");
		}
		this.reminderDays = reminderDays;
		updateReminderDate();
	}

	/**
     * Sets the last refill date for the medication after validating the date.
     *
     * @param lastRefilled The date the medication was last refilled.
     * @throws IllegalArgumentException if lastRefilled is null.
     */
	public void setLastRefilled(LocalDate lastRefilled) {
		if (lastRefilled == null) {
			throw new IllegalArgumentException("Not a valid date");
		}
		this.lastRefilled = lastRefilled;
	}
	
	/**
     * Updates the reminder date based on the quantity of medication left,
     * the number of times the medication is taken per day, 
     * and the number of reminder days set by the user.
     *
     * @throws IllegalStateException if not enough medication is available to set the reminder duration.
     */
	private void updateReminderDate() {

		if(lastRefilled != null && timesPerDay > 0) {

			long amountToRemind = timesPerDay * reminderDays;

			if(amountToRemind > quantity) {
				throw new IllegalStateException("Not enough medication to set reminder duration");
			}

			long pillsLeft = quantity - amountToRemind;
			long days = pillsLeft / timesPerDay;

			reminderDate = ChronoUnit.DAYS.addTo(lastRefilled, days);
		}

	}


}
