package raihanhori.security_jwt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import raihanhori.security_jwt.entity.Role;
import raihanhori.security_jwt.entity.User;
import raihanhori.security_jwt.repository.UserRepository;
import raihanhori.security_jwt.request.LoginRequest;
import raihanhori.security_jwt.request.RegisterRequest;
import raihanhori.security_jwt.response.LoginResponse;
import raihanhori.security_jwt.response.RegisterResponse;
import raihanhori.security_jwt.security.JwtUtils;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public LoginResponse login(LoginRequest request) {
		UsernamePasswordAuthenticationToken credentials = 
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		
		try {
			authenticationManager.authenticate(credentials);
		} catch (AuthenticationException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong email or password");
		}
		
		
		User user = userRepository.findFirstByEmail(request.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	
		String token = jwtUtils.generateToken(user);
		Date expiredAt = jwtUtils.getTokenExpiredAt(token);
		
		return LoginResponse.builder()
					.token(token)
					.expiredAt(expiredAt)
					.build();
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
		
		String token = jwtUtils.generateToken(user);
		Date expiredAt = jwtUtils.getTokenExpiredAt(token);
		
		return RegisterResponse.builder()
					.token(token)
					.expiredAt(expiredAt)
					.build();
	}

}
