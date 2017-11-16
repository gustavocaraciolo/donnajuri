package com.br.donna.repository;

import com.br.donna.domain.Lawyer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lawyer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

}
