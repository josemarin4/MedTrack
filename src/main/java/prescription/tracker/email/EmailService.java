package prescription.tracker.email;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import prescription.tracker.medication.Medication;

import org.thymeleaf.context.Context;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;
	private TemplateEngine templateEngine;

	public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	public void sendConfirmationEmail(String recipientEmail, String confirmationToken){

		try {
			
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.addTo(recipientEmail);
			helper.setSubject("Confirm Your Email");
			
			Context context = new Context();
			context.setVariable("confirmationLink", "http://localhost:8080/api/register/confirm?token=" + confirmationToken);
			String emailContent = templateEngine.process("confirmationEmailTemplate", context);
			
			helper.setText(emailContent, true);
			
			javaMailSender.send(message);
			
		}
		catch(MessagingException ex) {
			ex.printStackTrace();

		}
	}
	
	public void sendLowMedicationsNotification(String recipientEmail, List<Medication> medications) {
		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
		
			helper.addTo(recipientEmail);
			helper.setSubject("Low Medication Quantity Alert");
			helper.setText("You have 7 days of dosis for these prescription. It's time to order a refill. \n" 
							+ "Medications: \n" + medications.toString());
			
			javaMailSender.send(message);
			
		}
		catch(MessagingException ex) {
			ex.printStackTrace();
		}
	}

}
