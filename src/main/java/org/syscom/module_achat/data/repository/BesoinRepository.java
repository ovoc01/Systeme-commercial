package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.syscom.module_achat.data.entity.Besoin;

@Repository
public interface BesoinRepository extends JpaRepository<Besoin, Integer> {
}