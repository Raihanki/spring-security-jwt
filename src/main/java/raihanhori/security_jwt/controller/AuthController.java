package raihanhori.security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import raihanhori.security_jwt.helper.SuccessApiResponseHelper;
import raihanhori.security_jwt.request.LoginRequest;
import raihanhori.security_jwt.request.RegisterRequest;
import raihanhori.security_jwt.response.LoginResponse;
import raihanhori.security_jwt.response.RegisterResponse;
import raihanhori.security_jwt.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessApiResponseHelper<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse response = authService.login(request);

		return SuccessApiResponseHelper.<LoginResponse>builder()
				.data(response)
				.statusCode(HttpStatus.OK.value())
				.build();
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuccessApiResponseHelper<RegisterResponse> register(@RequestBody RegisterRequest request) {
		RegisterResponse response = authService.register(request);

		return SuccessApiResponseHelper.<RegisterResponse>builder()
				.data(response)
				.statusCode(HttpStatus.OK.value())
				.build();
	}

}
