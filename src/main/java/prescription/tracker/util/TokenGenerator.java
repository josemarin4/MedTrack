package prescription.tracker.util;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenGenerator {
	
	private static final int TOKEN_EXPIRATION_HOUR = 24;
	
	public static Token generateToken() {
		
		Token token = new Token();
		token.setValue(UUID.randomUUID().toString());
		token.setExpirationDateTime(LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOUR));
		return token;
	}

}
