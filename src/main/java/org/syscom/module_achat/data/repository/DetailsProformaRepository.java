package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.DetailsProforma;

@Repository
public interface DetailsProformaRepository extends JpaRepository<DetailsProforma, Integer> {
}