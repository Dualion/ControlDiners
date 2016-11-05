package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.UsuarisProces;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UsuarisProces entity.
 */
@SuppressWarnings("unused")
public interface UsuarisProcesRepository extends JpaRepository<UsuarisProces,Long> {

}
