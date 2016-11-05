package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.UsuarisProces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the UsuarisProces entity.
 */
@SuppressWarnings("unused")
public interface UsuarisProcesRepository extends JpaRepository<UsuarisProces,Long> {

	@Query("select up from UsuarisProces up join up.proces p where p.id = :procesId")
	Page<UsuarisProces> findAllByProcesId(@Param("procesId") Long procesId, Pageable pageable);
	
	@Query("select up from UsuarisProces up join up.proces p where p.id = :procesId")
	List<UsuarisProces> findAllByProcesId(@Param("procesId") Long procesId);
	
	@Query("select up from UsuarisProces up join up.proces p join up.usuaris u where p.id = :procesId and u.id = :usuariId")
	Optional<UsuarisProces> findOneByUsuarisIdAndProcesId(@Param("usuariId") Long usuariId, @Param("procesId") Long procesId);
	
}
