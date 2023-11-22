package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Proforma;

@Repository
public  interface ProformaRepository extends JpaRepository <Proforma,Integer> {
    @Query(value = "select nextval('seq_idProforma')", nativeQuery = true)
    Integer getLastId();
}
