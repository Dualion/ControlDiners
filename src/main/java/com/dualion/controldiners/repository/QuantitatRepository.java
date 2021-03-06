package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.Quantitat;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Quantitat entity.
 */
@SuppressWarnings("unused")
public interface QuantitatRepository extends JpaRepository<Quantitat,Long> {

	Optional<Quantitat> findFirstByActiu(Boolean actiu);
	
}
