package raihanhori.security_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import raihanhori.security_jwt.entity.User;
import raihanhori.security_jwt.repository.UserRepository;
import raihanhori.security_jwt.response.UserResponse;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findFirstByEmail(username).orElse(null);
	}

	@Override
	public UserResponse getUser() {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		return UserResponse.builder()
					.name(user.getName())
					.email(user.getEmail())
					.build();
	}

}
