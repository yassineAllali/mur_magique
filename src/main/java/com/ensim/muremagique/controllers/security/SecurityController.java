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
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;

	public SecurityController(AuthenticationManager authenticationManager,
		UserDetailsService userDetailsService, JwtService jwtService)
	{
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	}

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthenticationToken(
		@RequestBody AuthenticationRequest authenticationRequest)
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		UserDetails userDetails = userDetailsService.loadUserByUsername(
			authenticationRequest.getUsername());

		String jwt = jwtService.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
