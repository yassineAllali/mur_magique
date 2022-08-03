package com.ensim.muremagique.repositories;

import java.util.Optional;

import com.ensim.muremagique.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findFirstByEmail(String email);
	boolean existsByEmail(String email);
}
