package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.Usuaris;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Usuaris entity.
 */
@SuppressWarnings("unused")
public interface UsuarisRepository extends JpaRepository<Usuaris,Long> {

}
