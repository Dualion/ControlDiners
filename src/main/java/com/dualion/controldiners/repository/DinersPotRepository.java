package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.DinersPot;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DinersPot entity.
 */
@SuppressWarnings("unused")
public interface DinersPotRepository extends JpaRepository<DinersPot,Long> {

	Optional<DinersPot> findFirstByOrderByDataDesc();
	
}
