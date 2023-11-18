package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
    @Query(value = "select nextval('seq_idDetails')", nativeQuery = true)
    Integer getLastId();
}