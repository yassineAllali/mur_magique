package com.ensim.muremagique.security;

import java.util.ArrayList;
import java.util.Optional;

import com.ensim.muremagique.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnsimUserDetailsService implements UserDetailsService
{
	private final UserRepository userRepository;

	public EnsimUserDetailsService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		com.ensim.muremagique.entities.User user = userRepository.findFirstByEmail(
			username).orElseThrow(() -> new UsernameNotFoundException("email not found"));

		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
