package com.dualion.controldiners.repository;

import com.dualion.controldiners.domain.Pot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pot entity.
 */
@SuppressWarnings("unused")
public interface PotRepository extends JpaRepository<Pot,Long> {

}
