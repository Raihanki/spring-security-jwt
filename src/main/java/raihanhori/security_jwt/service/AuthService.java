package raihanhori.security_jwt.service;

import raihanhori.security_jwt.request.LoginRequest;
import raihanhori.security_jwt.request.RegisterRequest;
import raihanhori.security_jwt.response.LoginResponse;
import raihanhori.security_jwt.response.RegisterResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);
	
	RegisterResponse register(RegisterRequest request);
	
}
