package raihanhori.security_jwt;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import raihanhori.security_jwt.helper.ErrorApiResponseHelper;
import raihanhori.security_jwt.helper.SuccessApiResponseHelper;
import raihanhori.security_jwt.request.LoginRequest;
import raihanhori.security_jwt.request.RegisterRequest;
import raihanhori.security_jwt.response.LoginResponse;
import raihanhori.security_jwt.response.RegisterResponse;
import raihanhori.security_jwt.response.UserResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Disabled
	void testLoginSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("raihanki@gmail.com");
		loginRequest.setPassword("password");
		
		mockMvc.perform(
				post("/api/v1/auth/login")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessApiResponseHelper<LoginResponse> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			Assertions.assertNotNull(response.getData());
		});
	}
	
	@Test
	@Disabled
	void testLoginFailed() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("raihankis@gmail.com");
		loginRequest.setPassword("passwords");
		
		mockMvc.perform(
				post("/api/v1/auth/login")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))
		).andExpect(status().isBadRequest())
		.andDo(result -> {
			ErrorApiResponseHelper response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			Assertions.assertNotNull(response.getMessage());
		});
	}
	
	@Test
	@Disabled
	void testRegisterSuccess() throws Exception {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("shiina@gmail.com");
		request.setPassword("password");
		request.setName("Shiina Mahiru");
		
		mockMvc.perform(
				post("/api/v1/auth/register")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		).andExpect(status().isCreated())
		.andDo(result -> {
			SuccessApiResponseHelper<RegisterResponse> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			Assertions.assertNotNull(response.getData());
		});
	}
	
	@Test
	@Disabled
	void testGetAuthenticatedUserSuccess() throws Exception {
		mockMvc.perform(
				get("/api/v1/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyYWloYW5raUBnbWFpbC5jb20iLCJpYXQiOjE3MjEyODQ5MTYsImV4cCI6MTcyMTI4NjM1Nn0.VDd21HEXsLTNYN8y3PP0SL_-FPfZsBbTPeSvwbZiSunwNwiBgzoRfQdQwpSsOZwf")
		).andExpect(status().isOk())
		.andDo(result -> {
			SuccessApiResponseHelper<UserResponse> response = 
					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
		
			Assertions.assertNotNull(response.getData());
		});
	}
	
	@Test
	void testGetAuthenticatedUserInvalidToken() throws Exception {
		mockMvc.perform(
				get("/api/v1/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "W5raUNwiBgzoRfQdQwpSsOZwf")
		).andExpect(status().is4xxClientError());
//		.andDo(result -> {
//			ErrorApiResponseHelper response = 
//					objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
//		
//			log.info("response :" + response);
//			Assertions.assertNotNull(response);
//		});
	}

}
