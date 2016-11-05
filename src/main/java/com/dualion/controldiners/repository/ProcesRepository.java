package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.Proces;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proces entity.
 */
@SuppressWarnings("unused")
public interface ProcesRepository extends JpaRepository<Proces,Long> {

}
