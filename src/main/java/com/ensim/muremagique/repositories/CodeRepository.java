package com.ensim.muremagique.repositories;

import java.util.List;
import java.util.Optional;

import com.ensim.muremagique.entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
	Optional<Code> findFirstByPath(String path);
	boolean existsByPath(String path);
}