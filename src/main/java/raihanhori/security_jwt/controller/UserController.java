package raihanhori.security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raihanhori.security_jwt.helper.SuccessApiResponseHelper;
import raihanhori.security_jwt.response.UserResponse;
import raihanhori.security_jwt.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessApiResponseHelper<UserResponse> getUser() {
		 UserResponse response = userService.getUser();
		 
		 return SuccessApiResponseHelper.<UserResponse>builder()
				 	.data(response)
				 	.statusCode(200)
				 	.build();
	}
	
	@GetMapping("/free")
	public String free() {
		return "free endpoint";
	}
	
}
