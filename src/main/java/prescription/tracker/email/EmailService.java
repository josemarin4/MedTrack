package prescription.tracker.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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
			context.setVariable("confirmationLink", "/confirm?token=" + confirmationToken);
			String emailContent = templateEngine.process("confirmationEmailTemplate", context);
			
			helper.setText(emailContent, true);
			
			javaMailSender.send(message);
			
		}
		catch(MessagingException ex) {
			ex.printStackTrace();

		}
	}

}
