package com.ensim.muremagique.services.security;

import com.ensim.muremagique.entities.User;
import com.ensim.muremagique.repositories.UserRepository;
import com.ensim.muremagique.security.JwtService;
import com.ensim.muremagique.services.BusinessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService
{
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	public SecurityService(AuthenticationManager authenticationManager,
		UserRepository userRepository, UserDetailsService userDetailsService, JwtService jwtService,
		PasswordEncoder passwordEncoder)
	{
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}

	public String authenticate(String username, String password)
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password));

		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return jwtService.generateToken(userDetails);
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Email not found");
		}
	}

	public String register(String email, String password, String firstName, String lastName,
		String role)
	{
		if (userRepository.existsByEmail(email)) {
			throw new BusinessException("email already exist");
		}
		if (password.length() < 6) {
			throw new BusinessException("short password, try with a stronger one");
		}

		User user = new User(email, passwordEncoder.encode(password), firstName, lastName, role);
		userRepository.save(user);

		return authenticate(email, password);
	}

	public User getUser(Long id)
	{
		return userRepository.findById(id).orElseThrow(
			() -> new BusinessException("User Not Found"));
	}

	public Long getUserId(String username)
	{
		User user = userRepository.findFirstByEmail(username).orElseThrow(
			() -> new BusinessException("User not found"));

		return user.getId();
	}

	public User updateUser(Long id, String firstName, String lastName, String email)
	{
		User user = userRepository.findById(id).orElseThrow(
			() -> new BusinessException("User Not Found"));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);

		return userRepository.save(user);
	}
}
