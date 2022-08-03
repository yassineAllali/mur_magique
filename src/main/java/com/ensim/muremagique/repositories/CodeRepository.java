package com.ensim.muremagique.repositories;

import java.util.List;

import com.ensim.muremagique.entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {

	List<Code> findByOrderByOrderAsc();
}