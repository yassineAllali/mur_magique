package com.ensim.muremagique.controllers.security;

import com.ensim.muremagique.security.JwtService;
import com.ensim.muremagique.services.security.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController
{
	private final SecurityService securityService;

	public SecurityController(SecurityService securityService)
	{
		this.securityService = securityService;
	}

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthenticationToken(
		@RequestBody AuthenticationRequest authenticationRequest)
	{
		String jwt = securityService.authenticate(authenticationRequest.getUsername(),
			authenticationRequest.getPassword());

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest)
	{
		String jwt = securityService.register(registrationRequest.getEmail(),
			registrationRequest.getPassword(), registrationRequest.getFirstName(),
			registrationRequest.getLastName(), registrationRequest.getRole());

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
