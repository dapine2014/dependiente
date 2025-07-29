package org.dependiente.repository;

import org.dependiente.model.PersonAffiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonAffiliationRepository extends JpaRepository<PersonAffiliation, Long> {
}