package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.Pot;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pot entity.
 */
public interface PotRepository extends JpaRepository<Pot,Long> {

	Optional<Pot> findFirstByOrderByIdDesc();
	
}
