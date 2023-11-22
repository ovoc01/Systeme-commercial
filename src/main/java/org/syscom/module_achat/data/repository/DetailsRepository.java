package org.syscom.module_achat.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
    @Query(value = "select nextval('seq_idDetails')", nativeQuery = true)
    Integer getLastId();

    @Query(value = "select * from validate_besoin_details where   idProduits= ?1 ", nativeQuery = true)
    List<Details> findByIdProduits( int idProduits);
}
