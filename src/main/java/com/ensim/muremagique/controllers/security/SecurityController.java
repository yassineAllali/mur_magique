package com.ensim.muremagique.controllers.security;

import com.ensim.muremagique.controllers.Mapper;
import com.ensim.muremagique.services.security.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController
{
	private final SecurityService securityService;
	private final Mapper mapper;

	public SecurityController(SecurityService securityService)
	{
		this.securityService = securityService;
		mapper = new Mapper();
	}

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthenticationToken(
		@RequestBody AuthenticationRequest authenticationRequest)
	{
		String jwt = securityService.authenticate(authenticationRequest.getUsername(),
			authenticationRequest.getPassword());
		Long userId = securityService.getUserId(authenticationRequest.getUsername());
		return ResponseEntity.ok(new AuthenticationResponse(jwt, userId));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest)
	{
		String jwt = securityService.register(registrationRequest.getEmail(),
			registrationRequest.getPassword(), registrationRequest.getFirstName(),
			registrationRequest.getLastName(), registrationRequest.getRole());
		Long userId = securityService.getUserId(registrationRequest.getEmail());
		return ResponseEntity.ok(new AuthenticationResponse(jwt, userId));
	}

	@GetMapping("/users/{id}")
	public UserResponse getUser(@PathVariable Long id)
	{
		return mapper.map(securityService.getUser(id));
	}

	@PutMapping("/users/{id}")
	public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request)
	{
		return mapper.map(
			securityService.updateUser(id, request.getFirstName(), request.getLastName(),
				request.getEmail()));
	}
}
