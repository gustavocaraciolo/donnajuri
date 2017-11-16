package com.br.donna.repository;

import com.br.donna.domain.LegalProcess;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the LegalProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegalProcessRepository extends JpaRepository<LegalProcess, Long> {
    @Query("select distinct legal_process from LegalProcess legal_process left join fetch legal_process.lawyers")
    List<LegalProcess> findAllWithEagerRelationships();

    @Query("select legal_process from LegalProcess legal_process left join fetch legal_process.lawyers where legal_process.id =:id")
    LegalProcess findOneWithEagerRelationships(@Param("id") Long id);

}
