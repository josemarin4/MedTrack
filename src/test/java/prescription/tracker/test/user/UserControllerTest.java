package prescription.tracker.test.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import prescription.tracker.exception.UserNotFoundException;
import prescription.tracker.medication.Medication;
import prescription.tracker.medication.MedicationService;
import prescription.tracker.user.User;
import prescription.tracker.user.UserController;
import prescription.tracker.user.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private MedicationService medicationService;
	
	@Test
	public void shouldGetUser() throws Exception{
		
		User user = new User(2L, "email@email.com", "password", true, Collections.emptyList());
		
		given(userService.getUser(2L))
			.willReturn(user);
		
		mockMvc.perform(get("/api/user/{userId}", 2L))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(user)));
			
	}
	
	@Test
	public void shouldFailGetInvalidUser() throws Exception{
		
		given(userService.getUser(2L)).willThrow(new UserNotFoundException("User not found!"));
		
		mockMvc.perform(get("/api/user/{userId}", 2L))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType("text/plain;charset=UTF-8"))
			.andExpect(content().string("User not found!"));
		
	}
	
	@Test
	public void shouldRemoveUser() throws Exception{
		
		User user = new User(2L, "email@email.com", "password", true, Collections.emptyList());
		
		given(userService.removeUser(2L)).willReturn(user);
		mockMvc.perform(delete("/api/user/remove/{userId}", 2L))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(user)));
		
		

	}
	

}
