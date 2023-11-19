package org.syscom.module_achat.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Besoin;

@Repository
public interface BesoinRepository extends JpaRepository<Besoin, Integer> {
    @Query(value = "select nextval('seq_idBesoins')", nativeQuery = true)
    Integer getLastId();
    Besoin findById(int id);
    List<Besoin> findByIdServices(int idServices);
    boolean deleteById(int id);
}