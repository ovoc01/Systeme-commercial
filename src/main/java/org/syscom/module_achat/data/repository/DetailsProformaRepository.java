package org.syscom.module_achat.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.DetailsProforma;

@Repository
public interface DetailsProformaRepository extends JpaRepository<DetailsProforma, Integer> {
     @Query(value = "select nextval('seq_idDetails_proforma')", nativeQuery = true)
    Integer getLastId();

    @Query(value = "select * from details_proforma where idProforma =?1",nativeQuery = true)
    List<DetailsProforma> findByIdProforma(int val);
}